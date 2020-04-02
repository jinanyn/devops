package com.gwssi.devops.utilitypage.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myserver")
@Slf4j
@Data
public class PathConfig
{

    private String gzyqBasedir;
    private String gzyqBackupdir;

    private String mainSoftscanlogdir;
    private String mainApplogdir;

    private String mainAppLoginUri;
    private String mainAppLoginUsername;
    private String mainAppLoginPassword;

    private String mainAppMonitorUri;

    private String shareDisk;
}
