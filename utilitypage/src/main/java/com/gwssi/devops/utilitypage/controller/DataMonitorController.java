package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.util.FileHelperUtil;
import cn.gwssi.xml.XmlHelerBuilder;
import com.gwssi.devops.utilitypage.model.CaseInfo;
import com.gwssi.devops.utilitypage.model.RtnData;
import com.gwssi.devops.utilitypage.model.RtnDataList;
import com.gwssi.devops.utilitypage.util.PathConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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
    public List<CaseInfo> caseStateExceptionData(@RequestParam("parentId") String parentId) {
        log.info("parentId="+parentId);
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
    public List<CaseInfo> authCaseFivebookMiss(@RequestParam("viewDate") String viewDate) {
        List<CaseInfo> rtnList = new ArrayList<>();
        if(StringUtils.isEmpty(viewDate)){
            viewDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
        }
        String fullPathName = pathConfig.getShareDisk() + File.separator + viewDate + File.separator + "authCaseFivebookMiss" + File.separator + "authCaseFivebookMiss";
        String content = FileHelperUtil.readContentFromFile(fullPathName);
        if(StringUtils.isEmpty(content)){
            return rtnList;
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
        return rtnList;
    }
}
