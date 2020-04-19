package com.snow.sys.common;

import java.util.ArrayList;
import java.util.List;


/**
 * 二级菜单实现方法
 * 
 * */
public class TreeNodeBuilder {//构建者模式
	
	
	
	/*
	 * 把没有层级关系的集合变成有层级关系的
	 * 
	 * 
	 * */
	public static List<TreeNode> build(List<TreeNode> treeNodes,Integer topPid){
		List<TreeNode> nodes = new ArrayList<>();
		for(TreeNode n1:treeNodes) {
			
			if(n1.getPid()==topPid) {
				nodes.add(n1);
			}
			for(TreeNode n2:treeNodes) {
				
				if(n1.getId()==n2.getPid()) {
					n1.getChildren().add(n2);
				}
			}
		}
		return nodes;
	}

}
