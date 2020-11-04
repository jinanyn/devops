package cn.gwssi.http;

import cn.gwssi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

@Slf4j
public class HttpRequestUtil {

    /**
     * 登录返回CloseableHttpClient
     *
     * @param uri
     * @param reqParam
     * @return
     * @throws IOException
     */
    public static CloseableHttpClient loginHttpClient(String uri, Map<String, String> reqParam) throws IOException {
        // cookie信息自动保存在HttpClient中
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(new BasicCookieStore())
                .setRedirectStrategy(new LaxRedirectStrategy()).build();
        // 携带cookie访问登录网面
        // 设置登录的账号与密码
       /* HttpUriRequest login = RequestBuilder.post().setUri(uri)
                .addParameter("j_username", "weblogic").addParameter("j_password", "G@sip02o18").build();*/
        RequestBuilder requestBuilder = RequestBuilder.post().setUri(uri);
        String successKeyword = "!@#$%^&*()";
        for (String key : reqParam.keySet()) {
            if("success_keyword".equals(key)){
                successKeyword = reqParam.get(key);
                continue;
            }
            requestBuilder.addParameter(key, reqParam.get(key));
        }
        //requestBuilder.addParameter("j_character_encoding", "UTF-8");
        RequestConfig.Builder custom = RequestConfig.custom();
        custom.setSocketTimeout(30000);
        custom.setConnectTimeout(30000);
        RequestConfig requestConfig = custom.build();
        requestBuilder.setConfig(requestConfig);
        HttpUriRequest login = requestBuilder.build();
        // httpclient访问登录网页,并得到响应对象
        CloseableHttpResponse response = httpClient.execute(login);
        StatusLine statusLine = response.getStatusLine();
        log.info("响应状态码:"+statusLine.getStatusCode());
        // 响应文本
        String content = EntityUtils.toString(response.getEntity());
        //log.info(content);
        if(!content.contains(successKeyword)){
            throw new IOException("登陆系统异常!");
        }
        EntityUtils.consume(response.getEntity());
        // 输出响应页面源代码
        //System.out.println(content);
        return httpClient;
    }

    public static String noSessionRequest(String uri, Map<String, String> reqParam) throws IOException {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(new BasicCookieStore())
                .setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpPost httpPost = new HttpPost(uri);
        // 设置连接超时时间30秒
        //设置读取超时时间480秒
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(480000).setConnectTimeout(30000).build();
        httpPost.setConfig(requestConfig);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(getParam(reqParam), "UTF-8");
        httpPost.setEntity(entity);
        try(CloseableHttpResponse response = httpClient.execute(httpPost)){
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity, "UTF-8");
        }

    }

    public static String sessionRequest(CloseableHttpClient closeableHttpClient, String uri, Map<String, String> reqParam) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        // 设置连接超时时间30秒
        //设置读取超时时间480秒
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(480000).setConnectTimeout(30000).build();
        httpPost.setConfig(requestConfig);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(getParam(reqParam), "UTF-8");
        httpPost.setEntity(entity);
        try(CloseableHttpResponse response = closeableHttpClient.execute(httpPost)){
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity, "UTF-8");
        }

    }

    public static List<NameValuePair> getParam(Map parameterMap) {
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        Iterator it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry parmEntry = (Map.Entry) it.next();
            param.add(new BasicNameValuePair((String) parmEntry.getKey(),
                    (String) parmEntry.getValue()));
        }
        return param;
    }

    public static void main(String[] args) throws IOException {
        Set<String> urlSet = new HashSet<>();
        /*urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019213547912&instanceId=1203231176&rid=10110652946240&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019213538256&instanceId=1203233137&rid=10110652953424&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019213991528&instanceId=1203233139&rid=10110652953423&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019211519725&instanceId=1203289176&rid=10110653094935&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019213335923&instanceId=1203289042&rid=10110653094924&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019214514600&instanceId=1203289219&rid=10110653094944&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=201921299884X&instanceId=1203265788&rid=10110653040079&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=201921352366X&instanceId=1203265787&rid=10110653040081&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019214467084&instanceId=1203262745&rid=10110653037751&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019213914540&instanceId=1203257470&rid=10110653058711&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019214348846&instanceId=1203232033&rid=10110653058717&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019210827610&instanceId=1203311570&rid=10110653100739&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019214141022&instanceId=1203270763&rid=10110653057571&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019216472496&instanceId=1203252379&rid=10110653016197&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019216807752&instanceId=1203289085&rid=10110653094928&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019215508946&instanceId=1203289016&rid=10110653094904&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019216026442&instanceId=1203271789&rid=10110653077845&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019216280299&instanceId=1203264460&rid=10110653037796&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019217641839&instanceId=1203265920&rid=10110653046247&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019214876419&instanceId=1203311409&rid=10110653100744&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019218101363&instanceId=1203276439&rid=10110653083889&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019210719936&instanceId=1203269158&rid=10110653047965&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019210688656&instanceId=1203258376&rid=10110653037284&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019211670664&instanceId=1203261174&rid=10110653042316&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019210823319&instanceId=1203254134&rid=10110653042313&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019211529125&instanceId=1203261943&rid=10110653042315&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019214871909&instanceId=1203264788&rid=10110653042359&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019216716217&instanceId=1203264792&rid=10110653042371&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=201921384563X&instanceId=1203260517&rid=10110653060483&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019213592392&instanceId=1203254435&rid=10110653093506&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=201921182969X&instanceId=1203269860&rid=10110653061493&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019210456343&instanceId=1203270707&rid=10110653066501&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=201920753238X&instanceId=1203271635&rid=10110653066535&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=201921306707X&instanceId=1203415730&rid=10110653232615&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019219626087&instanceId=1203260160&rid=10110653036403&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019219884318&instanceId=1203276504&rid=10110653083894&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019220016034&instanceId=1203259725&rid=10110653030091&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019209253812&instanceId=1203325069&rid=10110653100066&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019221409100&instanceId=1203275701&rid=10110653077795&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019219150812&instanceId=1203255893&rid=10110653028014&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019219202304&instanceId=1203260209&rid=10110653036412&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019216009945&instanceId=1203262440&rid=10110653036486&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019213916442&instanceId=1203262498&rid=10110653036492&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019214982507&instanceId=1203270787&rid=10110653056679&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019211890787&instanceId=1203272208&rid=10110653063496&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019212522091&instanceId=1203271262&rid=10110653069402&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019213764204&instanceId=1203332950&rid=10110653108500&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019212705425&instanceId=1203288947&rid=10110653094422&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019212241435&instanceId=1203288942&rid=10110653094421&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019212426287&instanceId=1203288878&rid=10110653094402&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019212776083&instanceId=1203280566&rid=10110653087215&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019212345171&instanceId=1203276812&rid=10110653078732&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019208562490&instanceId=1203265822&rid=10110653044161&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019211725455&instanceId=1203280761&rid=10110653085009&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019213816088&instanceId=1203264291&rid=10110653083686&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019214464372&instanceId=1203261179&rid=10110653083675&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019217411305&instanceId=1203260986&rid=10110653087105&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019212562506&instanceId=1203398460&rid=10110653195237&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019216647753&instanceId=1203280970&rid=10110653093734&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019203023107&instanceId=1203270812&rid=10110653058796&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019210837947&instanceId=1203302624&rid=10110653196642&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019213666663&instanceId=1203303245&rid=10110653196652&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019209983340&instanceId=1203302574&rid=10110653196659&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019209687667&instanceId=1203273166&rid=10110653069408&actionId=1101A10");
        urlSet.add("http://10.50.168.8:7010/txn09Sqgzltz.ajax?shenqingh=2019220476714&instanceId=1203258616&rid=10110653030071&actionId=1101A10");*/
        Map<String, String> paramMap = new HashMap<>();
        String login_flowid = "" + UUID.randomUUID();
        paramMap.put("username", "246254");
        paramMap.put("password", "d3c3840762c52fc41f7921eca9acffb9");
        paramMap.put("login_flowid", login_flowid);
        paramMap.put("success_keyword", "<a href=\"javascript:void(0)\">案件审查</a>");
        CloseableHttpClient httpClient;
        try {
            httpClient = HttpRequestUtil.loginHttpClient("http://10.50.168.8:7010/txn999999.do", paramMap);
        } catch (IOException e) {
            log.error("登陆系统异常:http://10.50.168.8:7010/txn999999.do");
            log.error(ExceptionUtil.getMessage(e));
            return;
        }

        urlSet.parallelStream().forEach(uri -> {
            HttpGet httpGet = new HttpGet(uri);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(480000).setConnectTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            try(CloseableHttpResponse response = httpClient.execute(httpGet)){
                HttpEntity httpEntity = response.getEntity();
                log.info(EntityUtils.toString(httpEntity, "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        httpClient.close();
    }
}
