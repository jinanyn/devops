package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.http.HttpRequstUtil;
import com.gwssi.devops.utilitypage.model.CaseInfo;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.util.PathConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("utility/dataMonitor/")
public class DataMonitorController {
    @Autowired
    private PathConfig pathConfig;

    @ResponseBody
    @RequestMapping(value = "/caseStateException", method = RequestMethod.GET)
    public List<CaseInfo> caseStateExceptionData() {
        List<CaseInfo> rtnList = new ArrayList<>();

        CaseInfo caseInfo = new CaseInfo();
        caseInfo.setShenqingh("201920183421");
        caseInfo.setPatentName("一种名称_111");
        caseInfo.setExaminer("101341_张三");
        caseInfo.setComments("这是从后台获取的数据1");
        rtnList.add(caseInfo);

        caseInfo = new CaseInfo();
        caseInfo.setShenqingh("201920183422");
        caseInfo.setPatentName("一种名称_222");
        caseInfo.setExaminer("101342_李四");
        caseInfo.setComments("这是从后台获取的数据2");
        rtnList.add(caseInfo);

        caseInfo = new CaseInfo();
        caseInfo.setShenqingh("201920183423");
        caseInfo.setPatentName("一种名称_333");
        caseInfo.setExaminer("101343_王五");
        caseInfo.setComments("这是从后台获取的数据3");
        rtnList.add(caseInfo);

        caseInfo = new CaseInfo();
        caseInfo.setShenqingh("201920183424");
        caseInfo.setPatentName("一种名称_444");
        caseInfo.setExaminer("101344_赵六");
        caseInfo.setComments("这是从后台获取的数据4");
        rtnList.add(caseInfo);

        return rtnList;
    }

    @ResponseBody
    @RequestMapping(value = "/authCaseFivebookMiss", method = RequestMethod.GET)
    public List<CaseInfo> authCaseFivebookMiss() {
        List<CaseInfo> rtnList = new ArrayList<>();
        Map<String, String> paramMap = new HashMap<String, String>();
        String login_flowid = "" + UUID.randomUUID();
        paramMap.put("username", pathConfig.getMainAppLoginUsername());
        paramMap.put("password", pathConfig.getMainAppLoginPassword());
        paramMap.put("login_flowid", login_flowid);
        paramMap.put("success_keyword", "<a href=\"javascript:void(0)\">案件审查</a>");
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpRequstUtil.loginHttpClient(pathConfig.getMainAppLoginUri(), paramMap);
        } catch (IOException e) {
            log.error("登陆系统异常:"+pathConfig.getMainAppLoginUri());
            e.printStackTrace();
            return rtnList;
        }

        paramMap = new HashMap<String, String>();
        paramMap.put("select-key:monitor_key", BusinessConstant.BIZ_AUTH_CASE_FIVEBOOK_MISS);
        try {
            String rtnData = HttpRequstUtil.sessionRequest(httpClient,pathConfig.getMainAppMonitorUri(),paramMap);
            log.info(rtnData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rtnList;
    }
}
