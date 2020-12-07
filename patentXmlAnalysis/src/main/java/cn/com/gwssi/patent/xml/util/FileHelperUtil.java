package cn.com.gwssi.patent.xml.util;

import cn.com.gwssi.patent.xml.config.XmlPathConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class FileHelperUtil {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String FILE_PATH_SEPARATOR = "/";

    /**
     * 查找代码化文件存放路径
     *
     * @param xmlPathConfig
     * @param shenqingh
     * @param version
     * @param lastModifyTime
     * @return
     */
    public static String findCodedFile(XmlPathConfig xmlPathConfig, String shenqingh, Integer fileCode, Integer version, String... lastModifyTime) {
        log.info(lastModifyTime.toString());
        //如果没有最后更新时间,认为是默认版本
        if (lastModifyTime == null || lastModifyTime.length == 0 || StringUtils.isBlank(lastModifyTime[0])) {
            lastModifyTime[0] = "default";
        }
        String lay_1 = shenqingh.substring(0, 4);
        String lay_2 = shenqingh.substring(4, 6);
        String lay_3 = shenqingh.substring(6, 8);

        String filePath = xmlPathConfig.getBasePath() + lay_1 + FileHelperUtil.FILE_PATH_SEPARATOR + lay_2 +
                FileHelperUtil.FILE_PATH_SEPARATOR + lay_3 + FileHelperUtil.FILE_PATH_SEPARATOR + shenqingh + FileHelperUtil.FILE_PATH_SEPARATOR + fileCode + FileHelperUtil.FILE_PATH_SEPARATOR +
                version + FileHelperUtil.FILE_PATH_SEPARATOR + lastModifyTime[0] + FileHelperUtil.FILE_PATH_SEPARATOR + fileCode + ".xml";
        return filePath;
    }

    public static String readContentFromFile(String fullPathFile, String charset) {
        Path filePath = Paths.get(fullPathFile);
        if (!Files.exists(filePath)) {
            log.error("文件不存在:" + fullPathFile);
            return "";
        }
        List<String> lines = null;
        try {
            lines = Files.readAllLines(filePath, Charset.forName(charset));
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static void writeContentToFile(String fullPathFile, String content, String charset) {
        Path filePath = Paths.get(fullPathFile);
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath);
            } catch (IOException e) {
                log.error(ExceptionUtil.getMessage(e));
                return;
            }
        }
        try (BufferedWriter bfw = Files.newBufferedWriter(filePath, Charset.forName(charset))) {
            bfw.write(content);
            bfw.flush();
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }

    }

    public static void appendContentToFile(String fullPathFile, String content, String charset) {
        Path filePath = Paths.get(fullPathFile);
        if (!Files.exists(filePath.getParent())) {
            try {
                Files.createDirectories(filePath.getParent());
            } catch (IOException e) {
                log.error(ExceptionUtil.getMessage(e));
                return ;
            }
        }
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                log.error(ExceptionUtil.getMessage(e));
                return ;
            }
        }
        try (FileOutputStream fos = new FileOutputStream(fullPathFile, true);
             OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.forName(charset))) {
            osw.write(content);
            osw.flush();
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }
}
