package cn.com.gwssi.patent.xml.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class ZipFileUtil {

    public static List<File> unzip(Path zipFilePath, Path descDir) throws IOException {
        List<File> _list = new ArrayList<>();
        log.info("文件:{}. 解压路径:{}. 解压开始.", zipFilePath, descDir);
        long start = System.currentTimeMillis();
        try(ZipFile zip = new ZipFile(zipFilePath.toFile(), Charset.forName("GBK"))){
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                try(InputStream in = zip.getInputStream(entry)) {
                    String outPath = (descDir + FileHelperUtil.FILE_PATH_SEPARATOR + zipEntryName).replaceAll("\\*", FileHelperUtil.FILE_PATH_SEPARATOR);
                    Path outFilePath = Paths.get(outPath);
                    if(!Files.exists(outFilePath.getParent())){
                        Files.createDirectories(outFilePath.getParent());
                    }
                    if(Files.isDirectory(outFilePath)){
                        continue;
                    }
                    Files.copy(in, Paths.get(outPath));// 输出文件路径信息
                    _list.add(outFilePath.toFile());
                }
            }
            log.info("文件:{}. 解压路径:{}. 解压完成. 耗时:{}ms. ", zipFilePath, descDir, System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.info("文件:{}. 解压路径:{}. 解压异常:{}. 耗时:{}ms. ", zipFilePath, descDir, e, System.currentTimeMillis() - start);
            throw new IOException(e);
        }
        return _list;
    }
}
