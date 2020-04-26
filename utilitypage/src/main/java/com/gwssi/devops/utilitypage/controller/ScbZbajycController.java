package com.gwssi.devops.utilitypage.controller;

import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.model.CaseztInfo;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("utility/ajscdh/")
public class ScbZbajycController {
    @Autowired
    private PathConfig pathConfig;
    @ResponseBody
    @RequestMapping(value = "/getAjscdh", method = RequestMethod.GET)
    public List<CaseztInfo> getAjscdh(@RequestParam("viewDate") String viewDate) {
        List<CaseztInfo> sss=UtilityServiceInvoke.loadFileData1(pathConfig,viewDate,"ajdhZbajztyc");
        log.debug("xxx"+sss);
        return sss;
    }

    @ResponseBody
    @RequestMapping(value = "/getAjscha", method = RequestMethod.GET)
    public List<CaseztInfo> getAjscha(@RequestParam("viewDate") String viewDate) {
        List<CaseztInfo> sss=UtilityServiceInvoke.loadFileData1(pathConfig,viewDate,"scbhaZbztyc");
        log.debug("xxx"+sss);
        return sss;
    }

}
