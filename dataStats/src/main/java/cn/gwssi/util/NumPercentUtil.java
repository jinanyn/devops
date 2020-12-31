package cn.gwssi.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumPercentUtil {

    public static String toNumStr (Integer num1,Integer num2,Integer num3){
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        Integer num = num1 -num2;
        String result = df.format(((float)(num)/(float)(num3))*100);//返回的是String类型
        return result;
    }
}
