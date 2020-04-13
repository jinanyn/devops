package com.gwssi.devops.utilitypage.controller;

import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.model.CaseInfo;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("utility/dataMonitor/")
public class UtilityCommonController {
    @Autowired
    private PathConfig pathConfig;
    @ResponseBody
    @RequestMapping(value = "/utilityCommonOne", method = RequestMethod.GET)
    public List<CaseInfo> utilityCommonOne(@RequestParam("viewDate") String viewDate,@RequestParam("menuid") String menuid) {
        if(StringUtils.isNotEmpty(menuid)){
            String bizName = BusinessConstant.MENU_BIZ_MAP.get(menuid);
            if(StringUtils.isEmpty(bizName)){
                return new ArrayList<CaseInfo>();
            }
            return UtilityServiceInvoke.loadFileData(pathConfig,viewDate,bizName);
        }else{
            return new ArrayList<CaseInfo>();
        }
    }
}
