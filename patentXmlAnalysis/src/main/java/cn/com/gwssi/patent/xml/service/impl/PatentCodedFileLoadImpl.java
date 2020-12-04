package cn.com.gwssi.patent.xml.service.impl;

import cn.com.gwssi.patent.xml.model.CodedFile;
import cn.com.gwssi.patent.xml.service.IPatentCodedFileLoad;
import cn.com.gwssi.patent.xml.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PatentCodedFileLoadImpl implements IPatentCodedFileLoad {
    @Override
    public CodedFile fileAttribueLoad(String shenqingh, Integer fileCode, Integer version) {
        CodedFile codedFile = new CodedFile();
        codedFile.setShenqingh(shenqingh);
        codedFile.setWenjianlxdm("" + fileCode);
        codedFile.setBanbenh("" + version);
        codedFile.setFileEntityPath(FileInfoConstants.FILE_STORED_LOCATION.get(fileCode));
        return codedFile;
    }

    @Override
    public String fileEntityLoad(CodedFile codedFile, Path dir) {
        String fileEntityPath = codedFile.getFileEntityPath();
        Path sourceFilePath = Paths.get(fileEntityPath);
        Path targetFilePath = Paths.get(dir.toString() + FileHelperUtil.FILE_PATH_SEPARATOR + sourceFilePath.getFileName());
        //将zip包拷贝到此目录
        try {
            Files.deleteIfExists(targetFilePath);
            Files.copy(sourceFilePath, targetFilePath);
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new BusinessException("拷贝文件:[" + fileEntityPath + "]到目录[" + dir.toString() + "]异常", BizCodeContants.COPY_FILE_EXCEPTION);
        }
        List<File> fileList = new ArrayList<>();
        //进行解压
        try {
            fileList = ZipFileUtil.unzip(targetFilePath, dir);
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new BusinessException("zip文件:[" + targetFilePath + "]解压缩异常", BizCodeContants.UNZIP_FILE_EXCEPTION);
        }
        //对文件进行处理
        fileList.parallelStream().forEach(f -> {
            String fileName = f.getName();
            String name = fileName.substring(0, fileName.lastIndexOf(FileInfoConstants.POINT));
            String suffix = fileName.substring(fileName.lastIndexOf(FileInfoConstants.POINT)).toLowerCase();
            /***如果浏览器支持tif和tiff不再需要此段代码  开始***/
            if (".tif".equals(suffix) || ".tiff".equals(suffix)) {
                String parentPath = f.getParent();
                try {
                    Tiff2JpgUtil.tiffToJpg(parentPath + FileHelperUtil.FILE_PATH_SEPARATOR + f.getName(),
                            parentPath + FileHelperUtil.FILE_PATH_SEPARATOR + name + ".jpg");
                } catch (Exception e) {
                    log.error(ExceptionUtil.getMessage(e));
                    throw new BusinessException(ExceptionUtil.getMessage(e, true));
                }
            }
            /***如果浏览器支持tif和tiff不再需要此段代码   结束***/
            if (".xml".equals(suffix)) {
                try {
                    PathentXmlUtil.gbdXmlDisposeIng(f.getPath());
                } catch (Exception e) {
                    log.error(ExceptionUtil.getMessage(e));
                    throw new BusinessException(ExceptionUtil.getMessage(e, true));
                }
            }
        });



        //删除zip包
        try {
            Files.deleteIfExists(targetFilePath);
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new BusinessException("删除文件:[" + targetFilePath + "]异常", BizCodeContants.DELETE_FILE_EXCEPTION);
        }

        String xmlFileAbsolutePath = "";
        String fileCode = codedFile.getWenjianlxdm();
        Path rtnFilePath = Paths.get(dir.toString() + FileHelperUtil.FILE_PATH_SEPARATOR + fileCode+".xml");
        if(Files.exists(rtnFilePath)){
            xmlFileAbsolutePath =  rtnFilePath.toString();//能找到对应的文件代码xml文件
        }
        //以下逻辑,如果没有找到对应文件代码xml文件,返回解压后的第一个找到的xml文件,并重命名为对应的文件代码xml文件
        for(File f : fileList) {
            String fileName = f.getName();
            if(fileName.endsWith(".xml")){
                f.renameTo(rtnFilePath.toFile());
                xmlFileAbsolutePath = rtnFilePath.toAbsolutePath().toString();
            }
        }

        return xmlFileAbsolutePath;
    }
}
