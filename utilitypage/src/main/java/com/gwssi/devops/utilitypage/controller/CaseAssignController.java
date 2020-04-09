package com.gwssi.devops.utilitypage.controller;

import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.model.CaseInfo;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("utility/dataMonitor/")
public class CaseAssignController {
    @Autowired
    private PathConfig pathConfig;
    @ResponseBody
    @RequestMapping(value = "/priorityAuditDataRepeat", method = RequestMethod.GET)
    public List<CaseInfo> priorityAuditDataRepeat(@RequestParam("viewDate") String viewDate) {
        return UtilityServiceInvoke.loadFileData(pathConfig,viewDate,"priorityAuditDataRepeat");
    }
}
