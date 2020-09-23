package cn.gwssi.server;

import cn.gwssi.util.ExceptionUtil;
import cn.gwssi.util.FileHelperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class XXWeblogicHealthCheck {
    public static void main(String[] args) {
        XXWeblogicHealthCheck myTst = new XXWeblogicHealthCheck();
        try {
            myTst.execProcess(args);
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }
    private void execProcess(String[] args) throws IOException {
        String userDir = System.getProperty("user.dir");
        String driverDir = "";
        if(args != null && args.length >0){
            driverDir = args[0];
        }else{
            driverDir = userDir;
        }
        //System.out.println("projectPath="+projectpath);
        System.setProperty("webdriver.chrome.driver", driverDir + "/driver/chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        // 设置为 headless 模式 （无头浏览器）
        chromeOptions.addArguments("--headless");

        Set<Map<String, String>> serverInfoSet = this.buildServerInfoSet();
        String fullPathFile = userDir+"/xxWeblogicHealthCheck.txt";
        FileHelperUtil.clearInfoForFile(fullPathFile);
        serverInfoSet.parallelStream().forEach(map->{
            boolean flag = true;
            String serverAddress = map.get("serverAddress");
            String loginUri = map.get("loginUri");
            String loginUserName  = map.get("loginUserName");
            String loginPasswd  = map.get("loginPasswd");
            String bizUri  = map.get("bizUri");
            String dataSource  = map.get("dataSource");

            WebDriver driver = new ChromeDriver(chromeOptions);

            //Dimension dimension = new Dimension(0,0);
            //driver.manage().window().setSize(dimension);

            //设置隐性等待时间
            driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
            driver.navigate().to(serverAddress+loginUri);
            String title = driver.getTitle();
            //System.out.println("Page title:"+ title);

            // 开启开发者模式，方便观察请求[没有生效]
            //Actions builder = new Actions(driver);
            //builder.sendKeys(Keys.F12).perform();

            WebElement usernameEle = driver.findElement(By.id("j_username"));
            WebElement passwdEle = driver.findElement(By.id("j_password"));
            WebElement loginButton = driver.findElement(By.id("loginData"));

            usernameEle.sendKeys(loginUserName);
            passwdEle.sendKeys(loginPasswd);

            loginButton.submit();

            /*XMLHttpRequest xhr = new XMLHttpRequest();
            xhr.open("POST","http://10.50.168.8:7001console/login/LoginForm.jsp",true,null,null);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.send("j_username=weblogic&j_password=G@sip02o18");
            String str = xhr.getResponseText();
            System.out.println(str);*/

            driver.navigate().to(serverAddress+bizUri+"?_nfpb=true&_pageLabel=DomainMonitorHealthPage&handle=com.bea.console.handles.JMXHandle%28%22com.bea%3AName%3DxxznscDomain%2CType%3DDomain%22%29");
            String html = driver.getPageSource();
            //System.out.println(html);
            String checkResult = this.parseResponseResult_healthCheck(serverAddress, html);
            if(StringUtils.isNotBlank(checkResult)){
                flag = false;
                FileHelperUtil.appendContentToFile(fullPathFile,checkResult);
            }
            driver.navigate().to(serverAddress+bizUri+"?_nfpb=true&_pageLabel=JdbcDatasourcesJDBCDataSourceMonitorStatisticsPage&handle=com.bea.console.handles.JMXHandle%28%22com.bea%3AName%3D" + dataSource + "%2CType%3Dweblogic.j2ee.descriptor.wl.JDBCDataSourceBean%2CParent%3D%5BxxznscDomain%5D%2FJDBCSystemResources%5B" + dataSource + "%5D%2CPath%3DJDBCResource%5B" + dataSource + "%5D%22%29");
            html = driver.getPageSource();

            checkResult = this.parseResponseResult_datasource(serverAddress,dataSource,html);
            if(StringUtils.isNotBlank(checkResult)){
                flag = false;
                FileHelperUtil.appendContentToFile(fullPathFile,checkResult);
            }
            if(flag){
                FileHelperUtil.appendContentToFile(fullPathFile,serverAddress+"检测结果正常!!!");
            }
            FileHelperUtil.appendContentToFile(fullPathFile,FileHelperUtil.LINE_SEPARATOR);
            driver.quit();
        });
    }

    public String parseResponseResult_healthCheck(String server, String html) {
        StringBuilder strBui = new StringBuilder();//System.out.println(html);
        //Jsoup解析html
        Document document = Jsoup.parse(html);

        Elements items = document.getElementById("domainHealthTableFormtable").getElementsByTag("tr");
        int itemSize = items.size();
        for (int loop = 1; loop < itemSize; loop++) {
            Element item = items.get(loop);
            //System.out.println(item.toString());
            Element objEle = item.getElementById("name" + loop).getElementsByTag("a").last();
            if (objEle != null && objEle.html() != null && objEle.html().length() > 0) {
                String serverName = objEle.text();
                objEle = item.getElementById("state" + loop);
                String state = objEle.text();
                objEle = item.getElementById("health" + loop);
                String health = objEle.text();
                if ("OK".equals(health)) {
                    continue;
                }
                objEle = item.getElementById("reason" + loop);
                String reason = objEle.text();
                strBui.append("服务 [" + server + "中部署" + serverName + "],[state=" + state + "],[health=" + health + "],[reason=" + reason + "]");
                strBui.append("\r\n");
            } else {
                //System.out.println("DOM元素 id = server"+loop+" 不存在！");
            }
        }
        return strBui.toString();
        //System.out.println("--------------------------------------------------------------------------------------------------");
    }

    public String parseResponseResult_datasource(String server, String dataSource, String html) {
        StringBuilder strBui = new StringBuilder();
        //Jsoup解析html
        Document document = Jsoup.parse(html);

        Elements items = document.getElementById("genericTableFormtable").getElementsByTag("tr");
        int itemSize = items.size();
        for (int loop = 0; loop < itemSize; loop++) {
            Element item = items.get(loop);
            Element objEle = item.getElementById("server" + loop);
            if (objEle != null && objEle.html() != null && objEle.html().length() > 0) {
                String serverName = objEle.text();
                objEle = item.getElementById("NumAvailable" + loop);
                String availConnection = objEle.text();
                if (StringUtils.isNotBlank(availConnection) && Integer.parseInt(availConnection) < 16) {
                    strBui.append("服务 [" + server + "中部署" + serverName + "],[" + dataSource + "]可用数据库连接[" + availConnection + "]个。目前可用[" + availConnection + "]");
                    strBui.append("\r\n");
                } else {
                    //System.out.println("服务 [" + server + "中部署" + serverName + "],[" + dataSource + "]可用数据库连接[" + availConnection + "]个。");
                }
            } else {
                //System.out.println("DOM元素 id = server"+loop+" 不存在！");
            }
        }
        return strBui.toString();
    }

    public Set<Map<String, String>> buildServerInfoSet() {
        Set<Map<String, String>> serverInfoSet = new HashSet<Map<String, String>>();
        Map<String, String> infoMap = new HashMap<String, String>();
        /*
        String serverAddress = "http://10.50.168.8:7001";
        infoMap.put("serverAddress", serverAddress);
        String loginUri = "console/login/LoginForm.jsp";
        infoMap.put("loginUri", loginUri);
        String loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        String loginPasswd = "G@sip02o18";
        infoMap.put("loginPasswd", loginPasswd);
        String bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        String dataSource = "GWSSI";
        infoMap.put("dataSource", dataSource);
        serverInfoSet.add(infoMap);
        */

        String serverAddress = "http://10.50.168.1:7001";
        infoMap.put("serverAddress", serverAddress);
        String loginUri = "/console/login/LoginForm.jsp";
        infoMap.put("loginUri", loginUri);
        String loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        String loginPasswd = "9KXgy7]6Pc97H39n";
        infoMap.put("loginPasswd", loginPasswd);
        String bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        String dataSource = "GWSSI";
        infoMap.put("dataSource", dataSource);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.2:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/login/LoginForm.jsp";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "2M0x4ZxH9Vz26~g3";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSource = "GWSSI";
        infoMap.put("dataSource", dataSource);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.3:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/login/LoginForm.jsp";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "+RX6V8o2]#9345#*";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSource = "GWSSI";
        infoMap.put("dataSource", dataSource);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.4:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/login/LoginForm.jsp";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "99{7e85(~=*3PiI2";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSource = "GWSSI";
        infoMap.put("dataSource", dataSource);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.5:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/login/LoginForm.jsp";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "'?_K6J9v_c^5(RC[";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSource = "GWSSI";
        infoMap.put("dataSource", dataSource);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.6:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/login/LoginForm.jsp";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "8p=sd6#y5-M6_NnX";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSource = "GWSSI";
        infoMap.put("dataSource", dataSource);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.7:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/login/LoginForm.jsp";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "#(-3u4^('6xa3]X?";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSource = "GWSSI";
        infoMap.put("dataSource", dataSource);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.8:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/login/LoginForm.jsp";
        //loginUri = "/console/login/LoginForm.jsp";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        //loginPasswd = "G@sip02o18";
        loginPasswd = "P.SyO98h3OH9F)C=";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSource = "GWSSI";
        infoMap.put("dataSource", dataSource);
        serverInfoSet.add(infoMap);

        return serverInfoSet;
    }
}
