package cn.com.gwssi.patent.xml.service;

import cn.com.gwssi.patent.xml.model.CodedFile;

import java.nio.file.Path;

public interface IPatentCodedFileLoad {

    CodedFile fileAttribueLoad(String shenqingh, Integer fileCode, Integer version);

    String fileEntityLoad(CodedFile codedFile, Path dir);
}
