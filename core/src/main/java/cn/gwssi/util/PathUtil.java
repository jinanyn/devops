package cn.gwssi.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtil {
    public static String createFolder(String rootPath,String relativepPath) throws IOException {
        Path filePath   = Paths.get(rootPath);
        if(!Files.exists(filePath)){
            Files.createDirectories(filePath);
        }
        filePath   = Paths.get(rootPath+ File.separator+relativepPath);
        if(!Files.exists(filePath)){
            Files.createDirectories(filePath);
        }
        return filePath.toString();
    }

    public static void main1(String[] args) throws IOException {
        String rootPath = "C:\\Users\\yangning\\Desktop\\log\\ecode";
        String relativepPath = "/app/11_fmss/scgz/ajlb/dcaj/";
        PathUtil.createFolder(rootPath,relativepPath);
        rootPath = "C:\\Users\\yangning\\Desktop\\log\\ecode\\WEB-INF\\classes";
        relativepPath = "cn/gwssi/cpees/k_fmss/zxywzd/txn";
        PathUtil.createFolder(rootPath,relativepPath);

    }
}
