package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.http.HttpRequstUtil;
import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("utility/ayOperate/")
public class AyOperateContorller {
    @Autowired
    private PathConfig pathConfig;
    @RequestMapping(value = {"updatescyca"}, method = {RequestMethod.POST})
    public Map<String, String> getKeyValue(@RequestParam("shenchaydm") String shenchaydm) throws IOException {
        if (StringUtils.isEmpty(shenchaydm)) {
            log.info("审查员代码为空");
            throw new RuntimeException("审查员代码为空");
        }
        updateYhcazs(pathConfig, BusinessConstant.PRIORITY_UPDATE_YONGHU_CA,shenchaydm,"shenchaydm");
        Map map = new HashMap();
        map.put("result", "success");
      //  map.put("data", value.toString());
        return map;
    }

    public static void updateYhcazs(PathConfig pathConfig, String monitorKey, String monitorVal, String placeholder){
        Map<String, String> paramMap = new HashMap<>();
        String login_flowid = "" + UUID.randomUUID();
        paramMap.put("username", pathConfig.getAYAppLoginUsername());
        paramMap.put("password", pathConfig.getAYAppLoginPassword());
        paramMap.put("login_flowid", login_flowid);
        paramMap.put("success_keyword", "<a href=\"javascript:void(0)\">用户ca证书注销</a>");
        CloseableHttpClient httpClient;
        try {
            httpClient = HttpRequstUtil.loginHttpClient(pathConfig.getAYAppLoginUri(), paramMap);
        } catch (IOException e) {
            log.error("登陆系统异常:"+pathConfig.getAYAppLoginUri());
            e.printStackTrace();
            return ;
        }
        paramMap = new HashMap<String, String>();
        paramMap.put("select-key:monitor_key", monitorKey);
        paramMap.put("select-key:monitor_val", monitorVal);
        paramMap.put("select-key:placeholder", placeholder);
        String xmlRtnData;
        try {
            xmlRtnData = HttpRequstUtil.sessionRequest(httpClient,pathConfig.getAYAppLoginUri(),paramMap);
            //log.info(xmlRtnData);
        } catch (IOException e) {
            log.error("获取监控数据异常:"+pathConfig.getAYAppLoginUri()+";参数:"+paramMap.toString());
            e.printStackTrace();
            return ;
        }
    }
}
