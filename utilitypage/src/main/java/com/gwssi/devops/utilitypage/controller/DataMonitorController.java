package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.util.FileHelperUtil;
import cn.gwssi.xml.XmlHelerBuilder;
import com.gwssi.devops.utilitypage.model.CaseInfo;
import com.gwssi.devops.utilitypage.model.RtnData;
import com.gwssi.devops.utilitypage.model.RtnDataList;
import com.gwssi.devops.utilitypage.config.PathConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.ParseException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("utility/dataMonitor/")
public class DataMonitorController {
    @Autowired
    private PathConfig pathConfig;

    @ResponseBody
    @RequestMapping(value = "/caseStateException", method = RequestMethod.GET)
    public List<CaseInfo> caseStateExceptionData(@RequestParam("viewDate") String viewDate) {
        return this.loadFileData(viewDate, "caseStateException");
    }

    @ResponseBody
    @RequestMapping(value = "/authCaseFivebookMiss", method = RequestMethod.GET)
    public List<CaseInfo> authCaseFivebookMiss(@RequestParam("viewDate") String viewDate) {
        return this.loadFileData(viewDate, "authCaseFivebookMiss");
    }

    private List<CaseInfo> loadFileData(String viewDate, String bizName) {
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
