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

        System.out.println("parentId="+parentId);
        List<TreeNode> rtnList = new ArrayList<>();

        TreeNode levelOne1 = new TreeNode();
        List<TreeNode> levelOne1Children = new ArrayList<>();
        levelOne1.setId("01");
        levelOne1.setParentId("");
        levelOne1.setName("程序处理");

        TreeNode levelOne11 = new TreeNode<String>();
        levelOne11.setId("0101");
        levelOne11.setParentId("01");
        levelOne11.setName("图片cmyk转rgb");
        levelOne11.setData("cmyk-to-rgb");
        levelOne1Children.add(levelOne11);

        levelOne1.setChildren(levelOne1Children);


        TreeNode levelOne2 = new TreeNode();
        List<TreeNode> levelOne2Children = new ArrayList<>();
        levelOne2.setId("02");
        levelOne2.setParentId("");
        levelOne2.setName("数据监控");

        TreeNode levelOne21 = new TreeNode();
        levelOne21.setId("0201");
        levelOne21.setParentId("02");
        levelOne21.setName("案件状态异常");
        levelOne21.setData("case-state-exception");
        levelOne2Children.add(levelOne21);

        TreeNode levelOne22 = new TreeNode();
        levelOne22.setId("0202");
        levelOne22.setParentId("02");
        levelOne22.setName("授权案件五书缺失");
        levelOne22.setData("auth-case-fivebook-miss");
        levelOne2Children.add(levelOne22);

        levelOne2.setChildren(levelOne2Children);

        TreeNode levelOne3 = new TreeNode();
        List<TreeNode> levelOne3Children = new ArrayList<>();
        levelOne3.setId("03");
        levelOne3.setParentId("");
        levelOne3.setName("服务器状态");

        TreeNode levelOne31 = new TreeNode();
        levelOne31.setId("0301");
        levelOne31.setParentId("03");
        levelOne31.setName("weblogic健康状态");
        levelOne31.setData("weblogic-health-state");
        levelOne3Children.add(levelOne31);

        levelOne3.setChildren(levelOne3Children);

        TreeNode levelOne4 = new TreeNode();
        List<TreeNode> levelOne4Children = new ArrayList<>();
        levelOne4.setId("04");
        levelOne4.setParentId("");
        levelOne4.setName("文件处理");

        TreeNode levelOne41 = new TreeNode();
        levelOne41.setId("0401");
        levelOne41.setParentId("04");
        levelOne41.setName("新型文件操作");
        levelOne41.setData("utility-file-operate");
        levelOne4Children.add(levelOne41);

        levelOne4.setChildren(levelOne4Children);

        rtnList.add(levelOne1);
        rtnList.add(levelOne2);
        rtnList.add(levelOne3);
        rtnList.add(levelOne4);
        return rtnList;
    }
}
