package cn.com.gwssi.patent.xml.util;

import io.swagger.annotations.ApiModelProperty;

/**
 * 业务码定义
 */
public class BizCodeContants {

    @ApiModelProperty(value = "文件不存在")
    public static final String FILE_NOT_EXISTS="99001";

    @ApiModelProperty(value = "目录创建异常")
    public static final String DIR_CREATE_EXCEPTION="99002";

    @ApiModelProperty(value = "拷贝文件异常")
    public static final String COPY_FILE_EXCEPTION="99003";

    @ApiModelProperty(value = "删除文件异常")
    public static final String DELETE_FILE_EXCEPTION="99004";

    @ApiModelProperty(value = "zip文件解压缩异常")
    public static final String UNZIP_FILE_EXCEPTION="99005";

    @ApiModelProperty(value = "类未找到")
    public static final String CLASS_NOT_FIND_EXCEPTION="99006";

    @ApiModelProperty(value = "类方法未找到")
    public static final String METHOD_NOT_FIND_EXCEPTION="99007";

    @ApiModelProperty(value = "类实例化异常")
    public static final String CLASS_INSTANTIATION_EXCEPTION="99008";
}
