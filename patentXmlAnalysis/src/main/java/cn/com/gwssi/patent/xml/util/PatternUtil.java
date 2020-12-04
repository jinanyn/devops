package cn.com.gwssi.patent.xml.util;

import cn.com.gwssi.patent.xml.model.RegexMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

    public static boolean isMatch(String text,String regex){
    //表达式对象
        Pattern p = Pattern.compile(regex);
        //创建Matcher对象
        Matcher m = p.matcher(text);
        boolean result = m.matches(); //尝试将整个字符序列与该模式匹配
        return result;
    }

    public static List<RegexMatch> findMatch(String text, String regex){
        List<RegexMatch> matchList = new ArrayList<>();
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(regex);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(text);
        while (m.find()) {
            RegexMatch matchObj = new RegexMatch(m.group(),m.start(),m.end());
            matchList.add(matchObj);
        }
        return matchList;
    }

    /**
     * 标签过滤器
     * DTD
     * 和特殊的标记过滤
     */
    public static String deleletMatchStr(String text,String regexp) {
        Pattern p = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        text = m.replaceAll(""); //
        return text.trim(); // 返回文本字符串
    }

    /**
     * 获取内容中的所有汉字
     * 注：没有标点，没有空格
     * @param str
     */
    public static String getChineseInStr(String str){
        StringBuilder chineseSB = new StringBuilder();
        for(char c:str.toCharArray()){
            if(checkChinese(c)){
                chineseSB.append(c);
            }
        }
        return chineseSB.toString();
    }

    /**
     * 验证中文
     *
     * @param c
     *            中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(char c) {
        return '\u9FA5' >= c && c >= '\u4E00';
    }

    public static void main(String[] args) {
        // 按指定模式在字符串查找
        String line = "This order 100 was placed for QT3000! OK?";
        String pattern = "\\d+";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        while (m.find()) {
            //System.out.println(m.group(1));
            //System.out.println(m.group(2));
            //System.out.println(m.group(3));
            System.out.println("start(): "+m.start());
            System.out.println("end(): "+m.end());
            System.out.println(m.group());
        }
    }
}
