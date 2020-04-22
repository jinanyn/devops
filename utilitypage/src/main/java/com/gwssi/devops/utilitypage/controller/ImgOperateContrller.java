package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.api.dubbo.service.IHelloService;
import cn.gwssi.api.dubbo.service.IRuleEngineService;
import cn.gwssi.redis.RedisUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gwssi.devops.utilitypage.config.AppConfig;
import com.gwssi.devops.utilitypage.config.PathConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping({"utility/imgOperate/"})
public class ImgOperateContrller {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PathConfig pathConfig;
    @Autowired
    private AppConfig appConfig;

    //@Reference
    //@Reference(url = "dubbo://10.75.12.78:22082")
    //@Autowired
    //private IHelloService iHelloService;

    @Autowired
    private IRuleEngineService iRuleEngineService;

    @RequestMapping(value = {"cmykToRgb"}, method = {RequestMethod.POST})
    public Map<String, String> cmykToRgb(@RequestParam("redisKey") String redisKey) throws IOException {
        //String rtnVal = iHelloService.hi("中文名称", 22);
        //log.info("rtnVal= "+rtnVal);
        if(!"prod".equals(appConfig.getRunMode())){
            log.info("生产环境才能使用");
            throw new RuntimeException("生产环境才能使用");
        }
        if (StringUtils.isEmpty(redisKey)) {
            log.info("redisKey为空");
            throw new RuntimeException("redisKey为空");
        }
        Object value = this.redisUtil.get(redisKey);
        if(value == null || StringUtils.isEmpty(value.toString())){
            log.info("redis中不存在key="+redisKey+"的数据");
            throw new RuntimeException("redis中不存在key="+redisKey+"的数据");
        }
        String jsonStr = value.toString();
        log.info(jsonStr);
        JsonElement jsonElement = JsonParser.parseString(jsonStr);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if(redisKey.contains("100003")){
            JsonObject jsonOo = jsonObject.getAsJsonObject("cnDrawings");
            JsonArray jsonArray = jsonOo.getAsJsonArray("figureList");
            int jsonSize = jsonArray.size();
            for (int i = 0; i < jsonSize; i++){
                String imgUrl = jsonArray.get(i).getAsJsonObject().getAsJsonObject("img").get("imgUrl").getAsString();
                iRuleEngineService.cmykToRgb(imgUrl,pathConfig.getGzyqBasedir());
            }
        }else if(redisKey.contains("100005")){
            JsonObject jsonObj = jsonObject.getAsJsonObject("cnAbstract").getAsJsonObject("cnAbstFigure");
            JsonArray jsonArray = jsonObj.getAsJsonArray("figures");
            int jsonSize = jsonArray.size();
            for (int i = 0; i < jsonSize; i++){
                String imgUrl = jsonArray.get(i).getAsJsonObject().getAsJsonObject("img").get("imgUrl").getAsString();
                iRuleEngineService.cmykToRgb(imgUrl,pathConfig.getGzyqBasedir());
            }
        }else {
            log.info("参数不支持");
            throw new RuntimeException("当前只支持说明书附图和摘要附图");
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("result", "success");
        map.put("data", "成功");
        return map;
    }
}
