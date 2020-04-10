package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.redis.RedisUtil;
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

    @RequestMapping(value = {"cmykToRgb"}, method = {RequestMethod.POST})
    public Map<String, String> cmykToRgb(@RequestParam("redisKey") String redisKey) throws IOException {
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
                processImgMethod(imgUrl);
            }
        }else if(redisKey.contains("100005")){
            JsonObject jsonObj = jsonObject.getAsJsonObject("cnAbstract").getAsJsonObject("cnAbstFigure");
            JsonArray jsonArray = jsonObj.getAsJsonArray("figures");
            int jsonSize = jsonArray.size();
            for (int i = 0; i < jsonSize; i++){
                String imgUrl = jsonArray.get(i).getAsJsonObject().getAsJsonObject("img").get("imgUrl").getAsString();
                processImgMethod(imgUrl);
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

    private void processImgMethod(String imgUrl) throws IOException {
        if (StringUtils.isEmpty(imgUrl)) {
            log.info("服务器文件路径为空");
            throw new RuntimeException("服务器文件路径为空");
        }
        imgUrl = imgUrl.replaceFirst("http://10.50.168.200:8020/filelist/wusu/",pathConfig.getGzyqBasedir());
        log.info("imgUrl ="+imgUrl);
        //hacks
        Path sourceFilePath = Paths.get("ssh 10.50.168.30 -p 12306 "+imgUrl);
        if (!Files.exists(sourceFilePath)) {
            log.info("指定服务器文件不存在");
            throw new RuntimeException("指定服务器文件不存在");
        }
        int idx = imgUrl.lastIndexOf(".");
        String suffix = imgUrl.substring(idx + 1);
        if (!"jpg".equalsIgnoreCase(suffix) && !"png".equalsIgnoreCase(suffix) && !"bmp".equalsIgnoreCase(suffix)) {
            log.info("当前仅支持jpg,png,bmp");
            throw new RuntimeException("当前仅支持jpg,png,bmp");
        }
        String curDateTime = DateFormatUtils.format(new Date(), "yyyyMMddHH24MMss");
        String targetFilePath = "ssh 10.50.168.30 -p 12306 "+ imgUrl.substring(0,idx)+"_"+curDateTime+""+imgUrl.substring(idx);
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File("ssh 10.50.168.30 -p 12306 "+imgUrl));
            BufferedImage newBufferedImage = new BufferedImage(
                    bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,
                    Color.WHITE, null);
            ImageIO.write(newBufferedImage, suffix, new File(targetFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new RuntimeException("格式转换异常");
        }
        Path targetFilePathObj = Paths.get(targetFilePath);
        if(!Files.exists(targetFilePathObj)){
            throw new RuntimeException("格式转换结果异常");
        }
        if(Files.deleteIfExists(sourceFilePath)){
            //重命名一下
            targetFilePathObj.toFile().renameTo(sourceFilePath.toFile());
        }
    }
}
