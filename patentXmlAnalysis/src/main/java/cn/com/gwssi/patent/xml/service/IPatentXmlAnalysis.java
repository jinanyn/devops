package cn.com.gwssi.patent.xml.service;

import cn.com.gwssi.patent.xml.model.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotEmpty;


public interface IPatentXmlAnalysis {

    @ApiOperation("权利要求书解析")
    PatentFile_100001 claim(@ApiParam("申请号") @NotEmpty String shenqingh, @ApiParam("版本号") @NotEmpty Integer version) ;

    @ApiOperation("说明书解析")
    PatentFile_100002 description(@ApiParam("申请号") @NotEmpty String shenqingh, @ApiParam("版本号") @NotEmpty Integer version);

    @ApiOperation("说明书解析")
    PatentFile_100002 description(@ApiParam("申请号") @NotEmpty String shenqingh, @ApiParam("文件实体路径") @NotEmpty String fileEntieyPath);

    @ApiOperation("说明书附图解析")
    PatentFile_100003 descriptionDrawing(@ApiParam("申请号") @NotEmpty String shenqingh, @ApiParam("版本号") @NotEmpty Integer version);

    @ApiOperation("说明书摘要解析")
    PatentFile_100004 descriptionAbstract(@ApiParam("申请号") @NotEmpty String shenqingh, @ApiParam("版本号") @NotEmpty Integer version);

    @ApiOperation("摘要附图解析")
    PatentFile_100005 abstractDrawing(@ApiParam("申请号") @NotEmpty String shenqingh, @ApiParam("版本号") @NotEmpty Integer version);
}
