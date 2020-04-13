package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.util.TreeNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("utility/")
public class Utilitycontroller {

    @ResponseBody
    @RequestMapping(value="menuTreeData",method= RequestMethod.GET)
    public List<TreeNode> menuTreeData(@RequestParam("parentId") String parentId){

        //System.out.println("parentId="+parentId);
        List<TreeNode> rtnList = new ArrayList<>();

        TreeNode topOne = new TreeNode();
        topOne.setId("-1");
        topOne.setParentId("");
        topOne.setName("新型审查");
        List<TreeNode> topOneChildren = new ArrayList<>();

        TreeNode levelOne1 = new TreeNode();
        List<TreeNode> levelOne1Children = new ArrayList<>();
        levelOne1.setId("01");
        levelOne1.setParentId("-1");
        levelOne1.setName("程序处理");


        TreeNode levelOne11 = new TreeNode<String>();
        levelOne11.setId("0101");
        levelOne11.setParentId("01");
        levelOne11.setName("图片cmyk转rgb");
        levelOne11.setData("cmyk-to-rgb");
        levelOne1Children.add(levelOne11);

        levelOne1.setChildren(levelOne1Children);

        topOneChildren.add(levelOne1);

        TreeNode levelOne2 = new TreeNode();
        List<TreeNode> levelOne2Children = new ArrayList<>();
        levelOne2.setId("02");
        levelOne2.setParentId("-1");
        levelOne2.setName("数据监控");

        TreeNode levelOne21 = new TreeNode();
        levelOne21.setId("0201");
        levelOne21.setParentId("02");
        levelOne21.setName("案件状态异常");
        levelOne21.setData("utility-common");
        levelOne2Children.add(levelOne21);

        TreeNode levelOne22 = new TreeNode();
        levelOne22.setId("0202");
        levelOne22.setParentId("02");
        levelOne22.setName("授权案件五书缺失");
        levelOne22.setData("auth-case-fivebook-miss");
        levelOne2Children.add(levelOne22);

        TreeNode levelOne23 = new TreeNode();
        levelOne23.setId("0203");
        levelOne23.setParentId("02");
        levelOne23.setName("中间文件分配过晚导致回案");
        levelOne23.setData("midfile_assign_late");
        levelOne2Children.add(levelOne23);

        TreeNode levelOne24 = new TreeNode();
        levelOne24.setId("0204");
        levelOne24.setParentId("02");
        levelOne24.setName("通知书软扫或回调失败");
        levelOne24.setData("notice_softscan_fail");
        levelOne2Children.add(levelOne24);

        TreeNode levelOne25 = new TreeNode();
        levelOne25.setId("0205");
        levelOne25.setParentId("02");
        levelOne25.setName("授权通知书发出事件记录异常");
        levelOne25.setData("warranty_event_exception");
        levelOne2Children.add(levelOne25);

        TreeNode levelOne26 = new TreeNode();
        levelOne26.setId("0206");
        levelOne26.setParentId("02");
        levelOne26.setName("分案视未通知书发出事件记录异常");
        levelOne26.setData("division_event_exception");
        levelOne2Children.add(levelOne26);

        TreeNode levelOne27 = new TreeNode();
        levelOne27.setId("0207");
        levelOne27.setParentId("02");
        levelOne27.setName("已结案件缺失结案日期");
        levelOne27.setData("division_event_exception");
        levelOne2Children.add(levelOne27);

        levelOne2.setChildren(levelOne2Children);

        topOneChildren.add(levelOne2);

        TreeNode levelOne3 = new TreeNode();
        List<TreeNode> levelOne3Children = new ArrayList<>();
        levelOne3.setId("03");
        levelOne3.setParentId("-1");
        levelOne3.setName("服务器相关");

        TreeNode levelOne31 = new TreeNode();
        levelOne31.setId("0301");
        levelOne31.setParentId("03");
        levelOne31.setName("weblogic健康状态");
        levelOne31.setData("weblogic-health-state");
        levelOne3Children.add(levelOne31);

        TreeNode levelOne32 = new TreeNode();
        levelOne32.setId("0302");
        levelOne32.setParentId("03");
        levelOne32.setName("共享存储检测");
        levelOne32.setData("check-share-disk-state");
        levelOne3Children.add(levelOne32);

        TreeNode levelOne33 = new TreeNode();
        levelOne33.setId("0303");
        levelOne33.setParentId("03");
        levelOne33.setName("软扫日志搜索");
        levelOne33.setData("softscan-log-search");
        levelOne3Children.add(levelOne33);

        levelOne3.setChildren(levelOne3Children);

        topOneChildren.add(levelOne3);

        TreeNode levelOne4 = new TreeNode();
        List<TreeNode> levelOne4Children = new ArrayList<>();
        levelOne4.setId("04");
        levelOne4.setParentId("");
        levelOne4.setName("快捷操作");

        TreeNode levelOne41 = new TreeNode();
        levelOne41.setId("0401");
        levelOne41.setParentId("04");
        levelOne41.setName("文件操作");
        levelOne41.setData("utility-file-operate");
        levelOne4Children.add(levelOne41);

        TreeNode levelOne42 = new TreeNode();
        levelOne42.setId("0402");
        levelOne42.setParentId("04");
        levelOne42.setName("redis操作");
        levelOne42.setData("utility-redis-operate");
        levelOne4Children.add(levelOne42);

        levelOne4.setChildren(levelOne4Children);

        topOneChildren.add(levelOne4);

        topOne.setChildren(topOneChildren);


        TreeNode topTwo = new TreeNode();
        topTwo.setId("-2");
        topTwo.setParentId("");
        topTwo.setName("案源配送");
        List<TreeNode> topTwoChildren = new ArrayList<>();

        TreeNode levelOne5 = new TreeNode();
        List<TreeNode> levelOne5Children = new ArrayList<>();
        levelOne5.setId("05");
        levelOne5.setParentId("");
        levelOne5.setName("数据处理");

        TreeNode levelOne51 = new TreeNode();
        levelOne51.setId("0501");
        levelOne51.setParentId("05");
        levelOne51.setName("优先审查数据重复处理");
        levelOne51.setData("priority_audit_data_repeat");
        levelOne5Children.add(levelOne51);

        levelOne5.setChildren(levelOne5Children);

        topTwoChildren.add(levelOne5);

        topTwo.setChildren(topTwoChildren);

        TreeNode topThree = new TreeNode();
        topThree.setId("-3");
        topThree.setParentId("");
        topThree.setName("在线业务指导");
        List<TreeNode> topThreeChildren = new ArrayList<>();

        TreeNode levelOne6 = new TreeNode();
        List<TreeNode> levelOne6Children = new ArrayList<>();
        levelOne6.setId("06");
        levelOne6.setParentId("-3");
        levelOne6.setName("服务监控");

        TreeNode levelOne61 = new TreeNode();
        levelOne61.setId("0601");
        levelOne61.setParentId("06");
        levelOne61.setName("通知书下载测试");
        levelOne61.setData("instruction_notice_download_test");
        levelOne6Children.add(levelOne61);

        levelOne6.setChildren(levelOne6Children);

        topThreeChildren.add(levelOne6);

        topThree.setChildren(topThreeChildren);

        rtnList.add(topOne);
        rtnList.add(topTwo);
        rtnList.add(topThree);
        return rtnList;
    }
}
