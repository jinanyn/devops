package com.gwssi.devops.utilitypage.statistics;

import cn.gwssi.util.FileHelperUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ArvergeLoginNumber {
    public static void main(String[] args) {
        //
        URL url = ArvergeLoginNumber.class.getResource("");
        String filePath = url.toString() + "loginData.json";
        if (filePath.startsWith("file:/")) {
            filePath = filePath.substring(6);
        }
        //System.out.println(filePath);
        String fileContent = FileHelperUtil.readContentFromFile(filePath);
        //System.out.println(fileContent);

        JSONObject jsonObject = JSONObject.parseObject(fileContent);
        JSONObject groupByDateJsonObject = jsonObject.getJSONObject("aggregations").getJSONObject("group_by_date");
        JSONArray bucketsJSONArray = groupByDateJsonObject.getJSONArray("buckets");
        //System.out.println(bucketsJSONArray);

        Map<String,String> dataMap = new HashMap<>();
        int arrayLen = bucketsJSONArray.size();
        for (int loop = 0; loop < arrayLen; loop++) {
            JSONObject bucketsJSONObj = (JSONObject)bucketsJSONArray.get(loop);
            String dateStr = bucketsJSONObj.getString("key_as_string").replaceAll("-","");
            String loginNum = bucketsJSONObj.getJSONObject("agg_token").getString("value");
            dataMap.put(dateStr,loginNum);
        }

        System.out.println("dataMap = " + dataMap);
    }
}
