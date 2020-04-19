package com.snow.sys.common;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 二级菜单实体类
 * 
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
	private Integer id;
	@JsonProperty("parentId")
	private Integer pid;
	private String title;
	private String icon;
	private String href;
	private Boolean spread;
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	private String checkArr="0";//0代表不选中，1代表选中，在给角色分配权限的时候使用,需要给默认值，不然其他地方使用时会出现bug
	/**
	 * 
	 * 首页左边导航树的构造器
	 * 
	 * 这里使用的是标准的数据格式
	 * */
	public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
		super();
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.icon = icon;
		this.href = href;
		this.spread = spread;
	}

	
    
	/*dtree的數據格式
	 * 
	 * 这里使用的是简单的数据格式
	 * */
	public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
		super();
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.spread = spread;
	}
	/*
	 * dtree复选框构造器
	 * 
	 * */
	public TreeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr) {
		super();
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.spread = spread;
		this.checkArr = checkArr;
	}
	
}
