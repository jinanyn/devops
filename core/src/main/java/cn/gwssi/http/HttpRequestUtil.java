package cn.gwssi.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpRequestUtil {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(HttpRequestUtil.class);
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
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000).setConnectTimeout(30000).build();
        requestBuilder.setConfig(requestConfig);
        HttpUriRequest login = requestBuilder.build();
        // httpclient访问登录网页,并得到响应对象
        CloseableHttpResponse response = httpClient.execute(login);
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

    public static String sessionRequest(CloseableHttpClient closeableHttpClient, String uri, Map<String, String> reqParam) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        // 设置连接超时时间30秒
        //设置读取超时时间480秒
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(480000).setConnectTimeout(30000).build();
        httpPost.setConfig(requestConfig);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(getParam(reqParam), "UTF-8");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
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
}
