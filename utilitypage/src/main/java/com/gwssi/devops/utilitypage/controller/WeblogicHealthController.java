package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.http.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping({"utility/weblogic/"})
public class WeblogicHealthController {

    @RequestMapping(value = {"xxHealthCheck"}, method = {RequestMethod.GET})
    public Map<String, String> healthCheck() throws IOException {
        StringBuilder responseText = new StringBuilder();
        Set<Map<String, String>> serverInfoSet = this.buildServerInfoSet();
        for (Map<String, String> infoMap : serverInfoSet) {
            String serverAddress = infoMap.get("serverAddress");
            String loginUri = infoMap.get("loginUri");
            String loginUserName = infoMap.get("loginUserName");
            String loginPasswd = infoMap.get("loginPasswd");
            String bizUri = infoMap.get("bizUri");
            String dataSource = infoMap.get("dataSouce");

            String loginUrl = serverAddress + loginUri;
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("j_username", loginUserName);
            paramMap.put("j_password", loginPasswd);
            paramMap.put("success_keyword", "查看健康状况为 OK 的服务器列表");
            CloseableHttpClient httpClient = HttpRequestUtil.loginHttpClient(loginUrl, paramMap);

            String bizUrl = serverAddress + bizUri;
            paramMap = new HashMap<String, String>();
            paramMap.put("_nfpb", "true");
            paramMap.put("_pageLabel", "JdbcDatasourcesJDBCDataSourceMonitorStatisticsPage");
            paramMap.put("handle", "com.bea.console.handles.JMXHandle%28%22com.bea%3AName%3D" + dataSource + "%2CType%3Dweblogic.j2ee.descriptor.wl.JDBCDataSourceBean%2CParent%3D%5BxxznscDomain%5D%2FJDBCSystemResources%5B" + dataSource + "%5D%2CPath%3DJDBCResource%5B" + dataSource + "%5D%22%29");

            boolean flag = false;
            String html = HttpRequestUtil.sessionRequest(httpClient, bizUrl, paramMap);
            String resultOne = this.parseResponseResult_datasource(serverAddress, dataSource, html);//解析数据源连接情况
            if (org.apache.commons.lang.StringUtils.isNotBlank(resultOne)) {
                responseText.append(resultOne);
                flag = true;
            }

            paramMap = new HashMap<String, String>();
            paramMap.put("_nfpb", "true");
            paramMap.put("_pageLabel", "DomainMonitorHealthPage");
            paramMap.put("handle", "com.bea.console.handles.JMXHandle%28%22com.bea%3AName%3DxxznscDomain%2CType%3DDomain%22%29");
            html = HttpRequestUtil.sessionRequest(httpClient, bizUrl, paramMap);
            String resultTwo = this.parseResponseResult_healthCheck(serverAddress, html);
            if (StringUtils.isNotBlank(resultTwo)) {
                responseText.append(resultTwo);
                flag = true;
            }

            if (flag) {
                responseText.append("--------------------------------------------------------------------------------------------------\r\n");
            }
            if (httpClient != null) {
                httpClient.close();
            }

        }
        Map map = new HashMap();
        map.put("result", "success");
        map.put("data", responseText.toString());
        return map;
    }

    public static String parseResponseResult_healthCheck(String server, String html) {
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

    public static String parseResponseResult_datasource(String server, String dataSource, String html) {
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
        String serverAddress = "http://10.50.168.1:7001";
        infoMap.put("serverAddress", serverAddress);
        String loginUri = "/console/j_security_check";
        infoMap.put("loginUri", loginUri);
        String loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        String loginPasswd = "G@sip02o18";
        infoMap.put("loginPasswd", loginPasswd);
        String bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        String dataSouce = "GWSSI";
        infoMap.put("dataSouce", dataSouce);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.2:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/j_security_check";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "G@sip02o18";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSouce = "GWSSI";
        infoMap.put("dataSouce", dataSouce);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.3:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/j_security_check";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "G@sip02o18";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSouce = "GWSSI";
        infoMap.put("dataSouce", dataSouce);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.4:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/j_security_check";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "G@sip02o18";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSouce = "GWSSI";
        infoMap.put("dataSouce", dataSouce);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.5:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/j_security_check";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "G@sip02o18";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSouce = "GWSSI";
        infoMap.put("dataSouce", dataSouce);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.6:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/j_security_check";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "G@sip02o18";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSouce = "GWSSI";
        infoMap.put("dataSouce", dataSouce);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.7:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/j_security_check";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "G@sip02o18";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSouce = "GWSSI";
        infoMap.put("dataSouce", dataSouce);
        serverInfoSet.add(infoMap);

        infoMap = new HashMap<String, String>();
        serverAddress = "http://10.50.168.8:7001";
        infoMap.put("serverAddress", serverAddress);
        loginUri = "/console/j_security_check";
        infoMap.put("loginUri", loginUri);
        loginUserName = "weblogic";
        infoMap.put("loginUserName", loginUserName);
        loginPasswd = "G@sip02o18";
        infoMap.put("loginPasswd", loginPasswd);
        bizUri = "/console/console.portal";
        infoMap.put("bizUri", bizUri);
        dataSouce = "GWSSI";
        infoMap.put("dataSouce", dataSouce);
        serverInfoSet.add(infoMap);
        return serverInfoSet;
    }
}
