package cn.com.gwssi.patent.xml.config;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@Slf4j
@Data
public class XmlPathConfig {
    @ApiParam("xml存储位置根路径")
    @Value("${daimahwj.basePath}")
    private String basePath;

    @ApiParam("文件链接地址")
    @Value("${daimahwj.fileLinkUrl}")
    private String fileLinkUrl;
}
