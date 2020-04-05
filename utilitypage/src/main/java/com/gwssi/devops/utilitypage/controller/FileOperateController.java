package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.util.PathUtil;
import com.gwssi.devops.utilitypage.util.PathConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping({"utility/fileOperate/"})
public class FileOperateController {
    @Autowired
    private PathConfig pathConfig;

    @RequestMapping(value = {"serverFileSubstitute"}, method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, String> serverFileSubstitute(@RequestParam("files") MultipartFile[] files, @RequestParam("serverFilePath") String serverFilePath) throws IOException {
        Map map = new HashMap();
        MultipartFile file = files[0];
        if (file.isEmpty()) {
            log.info("没有需要上传的文件");
            throw new RuntimeException("没有需要上传的文件");
        }
        if (StringUtils.isEmpty(serverFilePath)) {
            log.info("服务器文件路径为空");
            throw new RuntimeException("服务器文件路径为空");
        }
        String curDateTime = DateFormatUtils.format(new Date(), "yyyyMMddhhmmss");
        if ("template".equals(serverFilePath)) {
            serverFilePath = PathUtil.createFolder(pathConfig.getShareDisk(), "template");
            String fileName = file.getOriginalFilename();
            Path filePath   = Paths.get(serverFilePath+File.separator+fileName);
            if(!Files.exists(filePath)){
                Files.createFile(filePath);
            }
            serverFilePath = filePath.toString();
        }
        File serverFilePathFile = new File(serverFilePath.trim());
        if (!serverFilePathFile.exists()) {
            log.info("指定服务器文件不存在");
            throw new RuntimeException("指定服务器文件不存在");
        }
        if (serverFilePathFile.isDirectory()) {
            log.info("指定的是目录,不是文件");
            throw new RuntimeException("指定的是目录,不是文件");
        }
        serverFilePathFile.renameTo(new File(serverFilePath + curDateTime));

        file.transferTo(new File(serverFilePath));

        map.put("result", "success");
        return map;
    }

    @RequestMapping(value = {"downloadFile"}, method = {RequestMethod.GET})
    public void downloadFile(@RequestParam("serverFilePath") String serverFilePath, HttpServletResponse res) throws IOException {
        if (StringUtils.isEmpty(serverFilePath)) {
            log.info("服务器文件路径为空");
            throw new RuntimeException("服务器文件路径为空");
        }
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        Path filePath = Paths.get(serverFilePath, new String[0]);
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filePath.getFileName().toString(), "UTF-8"));
        if (!Files.exists(filePath, new LinkOption[0])) {
            log.info("指定服务器文件不存在");
            throw new RuntimeException("指定服务器文件不存在");
        }

        byte[] data = Files.readAllBytes(filePath);
        OutputStream os = res.getOutputStream();
        os.write(data, 0, data.length);
        os.flush();
        os.close();
    }

    @RequestMapping(value = {"serverFileDelete"}, method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, String> serverFileDelete(@RequestBody Map<String, Object> paramsMap) throws IOException {
        String shenqingh = (String) paramsMap.get("shenqingh");
        if (StringUtils.isEmpty(shenqingh)) {
            log.info("服务器文件路径为空");
            throw new RuntimeException("服务器文件路径为空");
        }
        String sourcePath = this.pathConfig.getGzyqBasedir() + File.separator + shenqingh;
        Path sourceFilePath = Paths.get(sourcePath, new String[0]);
        if (!Files.exists(sourceFilePath, new LinkOption[0])) {
            log.info("指定服务器文件不存在");
            throw new RuntimeException("指定服务器文件不存在");
        }
        String targetPath = this.pathConfig.getGzyqBackupdir();
        String curDateTime = DateFormatUtils.format(new Date(), "yyyyMMddHH24MMss");
        Path targetFilePath = Paths.get(targetPath + File.separator + shenqingh + File.separator + curDateTime + File.separator, new String[0]);
        if (!Files.exists(targetFilePath, new LinkOption[0])) {
            Files.createDirectories(targetFilePath, new FileAttribute[0]);
        }
        FileSystemUtils.copyRecursively(sourceFilePath, targetFilePath);
        FileSystemUtils.deleteRecursively(sourceFilePath);

        Map map = new HashMap();
        map.put("result", "success");
        return map;
    }

    public static void main(String[] args)
            throws IOException {
        Path sourceFilePath = Paths.get("F:\\gzyq\\wushu\\2018215526598\\", new String[0]);
        Path targetFilePath = Paths.get("G:/gzyq/wushu/2018215526598/2019070514240735/", new String[0]);

        FileSystemUtils.copyRecursively(sourceFilePath, targetFilePath);
        FileSystemUtils.deleteRecursively(sourceFilePath);
        System.out.println("正常结束ssss");
    }
}
