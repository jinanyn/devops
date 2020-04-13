package cn.gwssi.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class FileHelperUtil {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static String readContentFromFile(String fullPathFile){
        Path filePath   = Paths.get(fullPathFile);
        if(!Files.exists(filePath)){
            log.error("文件不存在:"+fullPathFile);
            return "";
        }
        List<String> lines = null;
        try {
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("读取文件失败:"+fullPathFile);
            e.printStackTrace();
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(String line : lines){
            sb.append(line);
        }
        return sb.toString();
    }

    public static void writeContentToFile(String fullPathFile,String content){
        try {
            Path filePath   = Paths.get(fullPathFile);
            if(!Files.exists(filePath)){
                Files.createDirectories(filePath);
            }
            BufferedWriter bfw= Files.newBufferedWriter(filePath,StandardCharsets.UTF_8);
            bfw.write(content);
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void appendContentToFile(String fullPathFile,String content){
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            Path filePath   = Paths.get(fullPathFile);
            if(!Files.exists(filePath.getParent())){
                Files.createDirectories(filePath.getParent());
            }
            if(!Files.exists(filePath)){
                Files.createFile(filePath);
            }
            fos= new FileOutputStream(fullPathFile,true);
            osw= new OutputStreamWriter(fos, StandardCharsets.UTF_8);//指定以UTF-8格式写入文件
            osw.write(content);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(osw!= null){
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
            if(fos!= null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("关闭打开文件流异常:"+fullPathFile);
                }
            }
        }
    }
}
