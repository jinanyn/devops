package cn.com.gwssi.patent.xml.service;

import cn.com.gwssi.patent.xml.model.PatentFile_100001;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotEmpty;

public interface ICKEditorContentAnalysis {
    @ApiOperation("权利要求书文本还原")
    String claim(@ApiParam("权利要求书xml文件") @NotEmpty PatentFile_100001 patentFile100001) ;
}
