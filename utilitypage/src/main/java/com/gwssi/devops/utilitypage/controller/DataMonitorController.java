package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.util.TreeNode;
import com.gwssi.devops.utilitypage.model.CaseInfo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DataMonitorController {
    @ResponseBody
    @RequestMapping(value="/caseStateException",method= RequestMethod.GET)
    public List<CaseInfo> caseStateExceptionData(){
        List<CaseInfo> rtnList = new ArrayList<>();

        CaseInfo caseInfo = new CaseInfo();
        caseInfo.setShenqingh("201920183421");
        caseInfo.setPatentName("一种名称_111");
        caseInfo.setExaminer("101341_张三");
        caseInfo.setComments("这是从后台获取的数据1");
        rtnList.add(caseInfo);

        caseInfo = new CaseInfo();
        caseInfo.setShenqingh("201920183422");
        caseInfo.setPatentName("一种名称_222");
        caseInfo.setExaminer("101342_李四");
        caseInfo.setComments("这是从后台获取的数据2");
        rtnList.add(caseInfo);

        caseInfo = new CaseInfo();
        caseInfo.setShenqingh("201920183423");
        caseInfo.setPatentName("一种名称_333");
        caseInfo.setExaminer("101343_王五");
        caseInfo.setComments("这是从后台获取的数据3");
        rtnList.add(caseInfo);

        caseInfo = new CaseInfo();
        caseInfo.setShenqingh("201920183424");
        caseInfo.setPatentName("一种名称_444");
        caseInfo.setExaminer("101344_赵六");
        caseInfo.setComments("这是从后台获取的数据4");
        rtnList.add(caseInfo);

        return rtnList;
    }
}
