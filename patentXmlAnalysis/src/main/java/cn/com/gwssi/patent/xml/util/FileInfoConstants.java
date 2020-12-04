package cn.com.gwssi.patent.xml.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileInfoConstants {
    public final static String POINT = ".";//点

    public static final Integer FILE_CODE_CLAIM = 100001;//权利要求书
    public static final Integer FILE_CODE_DESCRIPTION = 100002;//说明书
    public static final Integer FILE_CODE_DESCRIPTION_DRAWING = 100003;//说明书附图
    public static final Integer FILE_CODE_DESCRIPTION_ABSTRACT = 100004;//说明书摘要
    public static final Integer FILE_CODE_ABSTRACT_DRAWING= 100005;//摘要附图

    public static final Map<Integer,String> FILE_STORED_LOCATION = new ConcurrentHashMap<>();
    static {
        FILE_STORED_LOCATION.put(FILE_CODE_CLAIM,"F:\\ideaProject\\nextEditor\\fileEntity\\100001.zip");
        FILE_STORED_LOCATION.put(FILE_CODE_DESCRIPTION,"F:\\ideaProject\\nextEditor\\fileEntity\\100002.zip");
        FILE_STORED_LOCATION.put(FILE_CODE_DESCRIPTION_DRAWING,"F:\\ideaProject\\nextEditor\\fileEntity\\100003.zip");
        FILE_STORED_LOCATION.put(FILE_CODE_DESCRIPTION_ABSTRACT,"F:\\ideaProject\\nextEditor\\fileEntity\\100004.zip");
        FILE_STORED_LOCATION.put(FILE_CODE_ABSTRACT_DRAWING,"F:\\ideaProject\\nextEditor\\fileEntity\\100005.zip");
    }
}
