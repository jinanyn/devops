//package com.gwssi.devops.utilitypage.controller;
//
//import cn.gwssi.util.FileHelperUtil;
//import cn.gwssi.xml.XmlHelerBuilder;
//import com.gwssi.devops.utilitypage.config.AppConfig;
//import com.gwssi.devops.utilitypage.config.PathConfig;
//import com.gwssi.devops.utilitypage.mail.MailHelperBuilder;
//import com.gwssi.devops.utilitypage.model.*;
//import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.time.DateFormatUtils;
//import org.apache.commons.lang.time.DateUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.*;
//
//@Slf4j
//@RestController
//@RequestMapping({"utility/server/"})
//public class UtilityShareDiskController {
//
//    @Autowired
//    private PathConfig pathConfig;
//    @Autowired
//    private AppConfig appConfig;
//    @Autowired
//    private MailHelperBuilder mailHelperBuilder;
//
//    @RequestMapping(value = {"checkShareDiskState"}, method = {RequestMethod.GET})
//    public List<ServerInfo> checkShareDiskState(@RequestParam("viewDate") String viewDate) throws IOException {
//        if(!"prod".equals(appConfig.getRunMode())){
//            log.info("服务器共享存储检测生产环境才能使用");
//            throw new RuntimeException("生产环境才能使用");
//        }
//        List<ServerInfo> serverInfoList = new ArrayList<>();
//        if (StringUtils.isEmpty(viewDate)) {
//            viewDate = DateFormatUtils.format(new Date(), "yyyyMMdd") + "-";
//        }else if("realtimeCheck".equals(viewDate)){
//            return UtilityServiceInvoke.checkShareDiskState_realtime(pathConfig);//服务器共享存储访问正常
//        }
//        Date startDate = null;
//        Date endDate = null;
//        String[] dateArr = viewDate.split("-");
//        try {
//            startDate = DateUtils.parseDate(dateArr[0], new String[]{"yyyyMMdd"});
//            if (dateArr.length > 1) {
//                endDate = DateUtils.parseDate(dateArr[1], new String[]{"yyyyMMdd"});
//            } else {
//                endDate = startDate;
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            return serverInfoList;
//        }
//        while (startDate.getTime() <= endDate.getTime()) {
//            viewDate = DateFormatUtils.format(startDate, "yyyyMMdd");
//            startDate = DateUtils.addDays(startDate, 1);
//            String fullPathName = pathConfig.getShareDisk() + File.separator + viewDate + File.separator + "serverShareDiskState" + File.separator + "serverShareDiskState";
//            String content = FileHelperUtil.readContentFromFile(fullPathName);
//            if (StringUtils.isEmpty(content)) {
//                continue;
//            }
//            ServerInfoList rtnDataList = (ServerInfoList) XmlHelerBuilder.convertXmlToObject(ServerInfoList.class, "<serverInfoList>" + content + "</serverInfoList>");
//            if (rtnDataList != null) {
//                List<ServerInfo> bizDataList = rtnDataList.getServerInfoList();
//                if (bizDataList != null && bizDataList.size() > 0) {
//                    for (ServerInfo serverInfo : bizDataList) {
//                        serverInfoList.add(serverInfo);
//                    }
//                }
//            }
//        }
//        return serverInfoList;
//    }
//}
