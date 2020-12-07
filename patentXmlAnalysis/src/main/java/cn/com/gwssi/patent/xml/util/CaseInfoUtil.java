package cn.com.gwssi.patent.xml.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 案件信息实用类
 */
public class CaseInfoUtil {

    //说明书中小标题配置
    public static final Map<String,String> PATENT_DESCRIPTION_HEADING_CONTENT = new HashMap<>();

    static {
        PATENT_DESCRIPTION_HEADING_CONTENT.put("技术领域","1");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("本案技术方案","1");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("领域","1");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("技术方案","1");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("背景技术","2");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("技术背景","2");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("发明内容","3");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("实用新型内容","3");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("本案发明内容","3");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("实用新型的内容","3");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("申请内容","3");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("本申请内容","3");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("实用新型","3");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("用新型内容","3");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("实用内容","3");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("实用新形内容","3");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("附图说明","4");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("说明书附图","4");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("附图","4");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("附图简要说明","4");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("附图简述","4");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("附图描述","4");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("具体实施方式","5");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("本案实施方式","5");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("实施效果","5");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("具体实施例","5");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("具体实施方法","5");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("具体实施方式或具体例","5");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("具体实施步骤","5");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("实施例","5");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("实施方式","5");
        PATENT_DESCRIPTION_HEADING_CONTENT.put("技技术领域","1");
    }

    /**
     * 根据申请号判断案件是否为PCT案件
     * @param shenqingh
     * @return
     */
    public static Boolean isPctCase(String shenqingh){
        int len = shenqingh.length();
        char ch = 'x';
        if (len == 9) {
            ch = shenqingh.charAt(2);
        }
        if (len == 13) {
            ch = shenqingh.charAt(4);
        }
        if ('8' == ch || '9' == ch) {
            return true;
        }
        return false;
    }
    /**
     * 根据申请号判断案件专利类型
     * @param shenqingh
     * @return
     */
    public static Integer getPatentType(String shenqingh){
        int len = shenqingh.length();
        char ch = 'x';
        if (len == 9) {
            ch = shenqingh.charAt(2);
        }
        if (len == 13) {
            ch = shenqingh.charAt(4);
        }
        if ('1' == ch || '8' == ch) {
            return CaseInfoConstants.ZHUANLILX_FAMING;
        }else if('2' == ch || '9' == ch){
            return CaseInfoConstants.ZHUANLILX_XINXING;
        }else if('3' == ch){
            return CaseInfoConstants.ZHUANLILX_WAIGUAN;
        }
        return -1;
    }
    /**
     * 判断申请号是否有效
     * @param shenqingh
     * @return
     */
    public static boolean isValidateSqh(String shenqingh){
        if(StringUtils.isEmpty(shenqingh)){
            return false;
        }
        if(shenqingh.length() != 9 && shenqingh.length() != 13){
            return false;
        }
        //9位或13位,最后一位,可以是X
        if(!PatternUtil.isMatch(shenqingh, "(\\d{9})|(\\d{8}X)|(\\d{13})|(\\d{12}X)")){
            return false;
        }
        if(getPatentType(shenqingh) == -1){
            return false;
        }
        return true;
    }

}
