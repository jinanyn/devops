package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.util.FileHelperUtil;
import cn.gwssi.xml.XmlHelerBuilder;
import com.gwssi.devops.utilitypage.model.CaseInfo;
import com.gwssi.devops.utilitypage.model.RtnData;
import com.gwssi.devops.utilitypage.model.RtnDataList;
import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
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
        return UtilityServiceInvoke.loadFileData(pathConfig,viewDate, "caseStateException");
    }

    @ResponseBody
    @RequestMapping(value = "/authCaseFivebookMiss", method = RequestMethod.GET)
    public List<CaseInfo> authCaseFivebookMiss(@RequestParam("viewDate") String viewDate) {
        return UtilityServiceInvoke.loadFileData(pathConfig,viewDate, "authCaseFivebookMiss");
    }


}
