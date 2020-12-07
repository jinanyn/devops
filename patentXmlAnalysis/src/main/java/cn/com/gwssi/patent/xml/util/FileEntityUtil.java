package cn.com.gwssi.patent.xml.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileEntityUtil {

    /**
     * 对于权利要求进行格式修整
     * @param context
     * @return
     */
    public static String getClaimsText(String context){

        Matcher matcher_claim = Pattern.compile("<claim([^>]*)>((?!(</claim>)).)+</claim>").matcher(context);
        while(matcher_claim.find()){//先找claim
            String matcher_claim_str = matcher_claim.group();
            String num="";
            Matcher claim_num = Pattern.compile("num=\"(\\d+)\"").matcher(matcher_claim.group(1));
            if (claim_num.find()) {
                num = claim_num.group(1);
            }
            Matcher matcher_claim_Text = Pattern.compile("<claim\\-text[^>]*>(((?!(</claim\\-text>)).)*)</claim\\-text>").matcher(matcher_claim_str);
            int i=0;
            while(matcher_claim_Text.find()){//再找claim-Text
                String matcher_claim_Text_str = matcher_claim_Text.group(1);
                Matcher kaitou_num = Pattern.compile("(^\\d+)\\s{0,2}[．\\.][^\\d]+").matcher(matcher_claim_Text_str);

                if (kaitou_num.find()) {
                    String shuzi = kaitou_num.group(1);//开头数字
                    if (i==0&&(shuzi.equals(num)||shuzi.equals("0"+num))) {
                        continue;
                    }
                    String claimBiaoqian="</claim><claim num=\""+shuzi+"\"><claim-text>"+matcher_claim_Text_str+"</claim-text>";
                    context=context.replace(matcher_claim_Text.group(), claimBiaoqian);
                }
                i++;

            }


        }
        return context;
    }
}
