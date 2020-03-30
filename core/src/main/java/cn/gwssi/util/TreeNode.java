package cn.gwssi.util;

import lombok.Data;

import java.util.List;

@Data
public class TreeNode<T> {
    private String id;           // 节点id
    private String parentId;    // 父节点
    private String name;         // 节点名称 ,返回给前台的是一个装有TreeUtils的集合的数据，所以在前台显示数据的时候，el-tree的lable的名字的和这个一样
    private List<TreeNode> children;  // 父节点中存放子节点的集合
    private T data;              //  节点数据
}
