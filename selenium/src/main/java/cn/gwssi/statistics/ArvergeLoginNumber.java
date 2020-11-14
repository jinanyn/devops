package cn.gwssi.statistics;

import cn.gwssi.http.HttpRequestUtil;
import cn.gwssi.model.RtnData;
import cn.gwssi.model.RtnDataList;
import cn.gwssi.util.FileHelperUtil;
import cn.gwssi.xml.XmlHelerBuilder;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ArvergeLoginNumber {
    public static void main(String[] args) throws IOException, InterruptedException {
        //
        /*URL url = ArvergeLoginNumber.class.getResource("");
        String filePath = url.toString() + "loginData.json";*/
        String userDir = System.getProperty("user.dir");
        String filePath = userDir+"/data/loginData.json";
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

        String dateStr = dataMap.keySet().toString().replaceAll("\\[","").replaceAll("]","").replaceAll(" ","");
        //System.out.println("dataMap = " + dateStr);
        Map<String, String> reqParam = new HashMap<>();
        reqParam.put("select-key:dateStr",dateStr);
        String xmlRtnData = HttpRequestUtil.noSessionRequest("http://10.50.168.8:7010/txnDevopsMonitor03.ajax",reqParam);
        xmlRtnData = xmlRtnData.replaceAll("&#39;", "'");
        //XML转为JAVA对象
        RtnDataList rtnDataList = (RtnDataList) XmlHelerBuilder.convertXmlToObject(RtnDataList.class, xmlRtnData);
        if (rtnDataList == null) {
            log.info("返回的xml解析完毕后结果为空");
            return ;
        }
        List<RtnData> bizDataList = rtnDataList.getBizDataList();
        if (bizDataList == null) {
            log.info("返回的xml解析完毕后业务数据为空");
            return ;
        }
        int size = bizDataList.size();
        int sum = 0;
        for (RtnData rtnData : bizDataList){
            String riqi = rtnData.getXh();
            sum += Integer.parseInt(dataMap.get(riqi));
        }
        System.out.println("平均值:"+sum/size);
        Thread.sleep(10000L);
    }
}
