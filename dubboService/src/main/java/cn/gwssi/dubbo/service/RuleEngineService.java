package cn.gwssi.dubbo.service;

import cn.gwssi.api.dubbo.service.IRuleEngineService;
import com.alibaba.dubbo.config.annotation.Service;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
@Component
@Slf4j
public class RuleEngineService implements IRuleEngineService {
    @Override
    public void cmykToRgb(String imgUrl,String baseDir) throws IOException {
        if (StringUtils.isEmpty(imgUrl)) {
            log.info("服务器文件路径为空");
            throw new RuntimeException("服务器文件路径为空");
        }
        imgUrl = imgUrl.replaceFirst("http://10.50.168.200:8020/filelist/wusu/",baseDir);
        log.info("imgUrl ="+imgUrl);
        //hacks
        //此处要先利用scp将文件下载到本地
        Path sourceFilePath = Paths.get(imgUrl);
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
        //要利用scp将处理好的文件写回
        String targetFilePath = imgUrl.substring(0,idx)+"_"+curDateTime+""+imgUrl.substring(idx);
        BufferedImage bufferedImage;
        try {
            //取scp传回来的文件
            bufferedImage = ImageIO.read(new File(imgUrl));
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
