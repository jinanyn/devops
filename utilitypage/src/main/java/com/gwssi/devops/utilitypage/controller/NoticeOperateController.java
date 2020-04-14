package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.model.NoticeInfo;
import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping({"utility/noticeOperate/"})
public class NoticeOperateController {

    @Autowired
    private PathConfig pathConfig;

    @ResponseBody
    @RequestMapping(value = "/queryNoticeList", method = RequestMethod.GET)
    public List<NoticeInfo> queryNoticeList(@RequestParam("shenqingh") String shenqingh) {
        if (StringUtils.isEmpty(shenqingh)) {
            log.info("shenqingh");
            throw new RuntimeException("shenqingh为空");
        }
        List<NoticeInfo> noticeInfoList = UtilityServiceInvoke.<NoticeInfo>queryDataList(pathConfig, BusinessConstant.BIZ_UTILITY_SECTION_NOTICE,shenqingh,"shenqingh");
        return noticeInfoList;
    }
}
