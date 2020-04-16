package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.model.NoticeInfo;
import cn.gwssi.util.ExceptionUtil;
import cn.gwssi.util.ShellExecUtil;
import com.gwssi.devops.utilitypage.config.AppConfig;
import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping({"utility/noticeOperate/"})
public class NoticeOperateController {
    @Autowired
    private AppConfig appConfig;
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

    @ResponseBody
    @RequestMapping(value = "/findSoftscanLog", method = RequestMethod.POST)
    //public Map<String,String> findSoftscanLog(@RequestParam("ruansaopc") String ruansaopc,@RequestParam("minutes") String minutes) {
    public Map<String,String> findSoftscanLog(@RequestBody Map<String,Object> paramsMap) {
        if(!"prod".equals(appConfig.getRunMode())){
            //log.info("服务器共享存储检测生产环境才能使用");
            //throw new RuntimeException("生产环境才能使用");
        }
        String ruansaopc = (String)paramsMap.get("ruansaopc");
        if(StringUtils.isEmpty(ruansaopc) || ruansaopc.trim().length() < 10){
            log.info("请输入要查询的内容长度大于10");
            throw new RuntimeException("请输入要查询的内容长度大于10");
        }
        //ssh 10.50.168.30 -p 12306 "find /XXZNSC/applogs/*/XXZNSC_WS -amin -230 -name 'gwssi.log-index.*' | xargs grep -ri 'notic_020a32a8054256a5bb0f79c8ac' -l"

        StringBuilder shellBui = new StringBuilder();
        shellBui.append("ssh 10.50.168.30 -p 12306 \"find "+pathConfig.getMainSoftscanlogdir());
        Integer minutes = Integer.parseInt((String)paramsMap.get("minutes"));
        if(minutes > 1000 || 0 > minutes){
            log.info("时间范围大于0小于10000");
            throw new RuntimeException("时间范围大于0小于10000");
        }else{
            shellBui.append(" -amin -"+minutes);
        }

        shellBui.append(" -name 'gwssi.log-index.*' | xargs grep -ri '"+ruansaopc+"' -l\"");

        log.info("shell script = "+shellBui.toString());

        List<String> rtnList = new ArrayList<>();
        try {
            rtnList = ShellExecUtil.runShell(shellBui.toString());
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new RuntimeException("执行查找结果异常");
        }

        Map<String,String> map = new HashMap<>();
        map.put("result", "success");
        map.put("logPath", rtnList.toArray().toString());
        return map;
    }
}
