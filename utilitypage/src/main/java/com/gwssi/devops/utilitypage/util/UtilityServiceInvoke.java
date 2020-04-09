package com.gwssi.devops.utilitypage.util;

import cn.gwssi.http.HttpRequstUtil;
import cn.gwssi.util.FileHelperUtil;
import cn.gwssi.xml.XmlHelerBuilder;
import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.model.CaseInfo;
import com.gwssi.devops.utilitypage.model.RtnData;
import com.gwssi.devops.utilitypage.model.RtnDataList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Slf4j
public class UtilityServiceInvoke {

    public static List<RtnData> commonBizMonitorProcess(PathConfig pathConfig,String monitorKey,String bizFolder){
        List<RtnData> rtnList= new ArrayList<>();
        Map<String, String> paramMap = new HashMap<>();
        String login_flowid = "" + UUID.randomUUID();
        paramMap.put("username", pathConfig.getMainAppLoginUsername());
        paramMap.put("password", pathConfig.getMainAppLoginPassword());
        paramMap.put("login_flowid", login_flowid);
        paramMap.put("success_keyword", "<a href=\"javascript:void(0)\">案件审查</a>");
        CloseableHttpClient httpClient;
        try {
            httpClient = HttpRequstUtil.loginHttpClient(pathConfig.getMainAppLoginUri(), paramMap);
        } catch (IOException e) {
            log.error("登陆系统异常:"+pathConfig.getMainAppLoginUri());
            e.printStackTrace();
            return rtnList;
        }
        paramMap = new HashMap<String, String>();
        paramMap.put("select-key:monitor_key", monitorKey);
        String xmlRtnData;
        try {
            xmlRtnData = HttpRequstUtil.sessionRequest(httpClient,pathConfig.getMainAppMonitorUri(),paramMap);
            //log.info(xmlRtnData);
        } catch (IOException e) {
            log.error("获取监控数据异常:"+pathConfig.getMainAppMonitorUri()+";参数:"+paramMap.toString());
            e.printStackTrace();
            return rtnList;
        }

        //XML转为JAVA对象
        RtnDataList rtnDataList = (RtnDataList) XmlHelerBuilder.convertXmlToObject(RtnDataList.class, xmlRtnData);
        if(rtnDataList == null){
            log.info("返回的xml解析完毕后结果为空");
            return rtnList;
        }
        List<RtnData> bizDataList = rtnDataList.getBizDataList();
        if(bizDataList == null){
            log.info("返回的xml解析完毕后业务数据为空");
            return rtnList;
        }
        //log.info(rtnDataList.toString());
        StringBuilder resultBui = new StringBuilder();
        for (RtnData rtnData : bizDataList){
            String bizXmlData = XmlHelerBuilder.convertObjectToXml(rtnData);
            //log.info("****************bizXmlData="+bizXmlData);
            if(bizXmlData.indexOf("<rtnData/>") != -1){//空白数据
                continue;
            }
            int idx = bizXmlData.indexOf("<rtnData>");
            //log.info("****************idx="+idx);
            if(idx ==-1){
                continue;
            }
            rtnList.add(rtnData);
            bizXmlData = bizXmlData.substring(idx);
            resultBui.append(bizXmlData);
        }
        String currDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String targetPath = pathConfig.getShareDisk()+ File.separator+currDate+File.separator+bizFolder;
        FileHelperUtil.appendContentToFile(targetPath+ File.separator+bizFolder,resultBui.toString());
        return rtnList;
    }

    public static void commonBizHandleProcess(PathConfig pathConfig,String monitorKey,String monitorVal,String placeholder){
        Map<String, String> paramMap = new HashMap<>();
        String login_flowid = "" + UUID.randomUUID();
        paramMap.put("username", pathConfig.getMainAppLoginUsername());
        paramMap.put("password", pathConfig.getMainAppLoginPassword());
        paramMap.put("login_flowid", login_flowid);
        paramMap.put("success_keyword", "<a href=\"javascript:void(0)\">案件审查</a>");
        CloseableHttpClient httpClient;
        try {
            httpClient = HttpRequstUtil.loginHttpClient(pathConfig.getMainAppLoginUri(), paramMap);
        } catch (IOException e) {
            log.error("登陆系统异常:"+pathConfig.getMainAppLoginUri());
            e.printStackTrace();
            return ;
        }
        paramMap = new HashMap<String, String>();
        paramMap.put("select-key:monitor_key", monitorKey);
        paramMap.put("select-key:monitor_val", monitorVal);
        paramMap.put("select-key:placeholder", placeholder);
        String xmlRtnData;
        try {
            xmlRtnData = HttpRequstUtil.sessionRequest(httpClient,pathConfig.getMainAppHandleUri(),paramMap);
            //log.info(xmlRtnData);
        } catch (IOException e) {
            log.error("获取监控数据异常:"+pathConfig.getMainAppMonitorUri()+";参数:"+paramMap.toString());
            e.printStackTrace();
            return ;
        }
    }

    public static List<CaseInfo> loadFileData(PathConfig pathConfig,String viewDate, String bizName) {
        List<CaseInfo> rtnList = new ArrayList<>();
        if (StringUtils.isEmpty(viewDate)) {
            viewDate = DateFormatUtils.format(new Date(), "yyyyMMdd") + "-";
        }
        Date startDate = null;
        Date endDate = null;
        String[] dateArr = viewDate.split("-");
        try {
            startDate = DateUtils.parseDate(dateArr[0], new String[]{"yyyyMMdd"});
            if (dateArr.length > 1) {
                endDate = DateUtils.parseDate(dateArr[1], new String[]{"yyyyMMdd"});
            } else {
                endDate = startDate;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return rtnList;
        }
        while (startDate.getTime() <= endDate.getTime()) {
            viewDate = DateFormatUtils.format(startDate, "yyyyMMdd");
            startDate = DateUtils.addDays(startDate, 1);
            String fullPathName = pathConfig.getShareDisk() + File.separator + viewDate + File.separator + bizName + File.separator + bizName;
            String content = FileHelperUtil.readContentFromFile(fullPathName);
            if (StringUtils.isEmpty(content)) {
                continue;
            }
            RtnDataList rtnDataList = (RtnDataList) XmlHelerBuilder.convertXmlToObject(RtnDataList.class, "<context>" + content + "</context>");
            if (rtnDataList != null) {
                List<RtnData> bizDataList = rtnDataList.getBizDataList();
                if (bizDataList != null && bizDataList.size() > 0) {
                    for (RtnData rtnData : bizDataList) {
                        CaseInfo caseInfo = new CaseInfo();
                        caseInfo.setShenqingh(rtnData.getShenqingh());
                        caseInfo.setComments(rtnData.getCnt());
                        rtnList.add(caseInfo);
                    }
                }
            }
        }
        return rtnList;
    }
}