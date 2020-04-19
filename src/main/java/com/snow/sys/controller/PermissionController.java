package com.snow.sys.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snow.sys.common.Constast;
import com.snow.sys.common.DataGridView;
import com.snow.sys.common.ResultObj;
import com.snow.sys.common.TreeNode;
import com.snow.sys.entity.Permission;
import com.snow.sys.service.PermissionService;
import com.snow.sys.vo.PermissionVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-12
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	/*
	 * 加载菜单管理左边的菜单树的json
	 * 
	 * */
	@RequestMapping("loadMenuManagerLeftTreeJson")
	public DataGridView loadPermissionManagerLeftTreeJson(PermissionVo permissionVo) {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("type",Constast.TYPE_MNEU);
		//查询所有的菜单信息
		List<Permission> list = this.permissionService.list(queryWrapper);
		//使用treeNode来封装数据
		List<TreeNode> treeNodes = new ArrayList<>();
		for(Permission menu:list) {
			Integer id = menu.getId();
			Integer pid = menu.getPid();
			String title = menu.getTitle();
			Boolean spread =menu.getOpen()==Constast.OPEN_TRUE?true:false;
			treeNodes.add(new TreeNode(id,pid,title,spread));
		}
		return new DataGridView(treeNodes);
	}
	
	/*
	 * 加载菜单管理右边的数据及模糊查询
	 * 
	 * 
	 * */
	@RequestMapping("loadAllPermission")
	public DataGridView loadAllPermission(PermissionVo permissionVo) {
		IPage<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit()); 
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		
		if(permissionVo.getId()!=null&&permissionVo.getId()!=0) {
			queryWrapper.eq( "pid",permissionVo.getId());
		}
		if(permissionVo.getId()!=null&&permissionVo.getId()==0) {
			queryWrapper.eq( "pid",permissionVo.getId()).or().eq("id",permissionVo.getId());
		}
		queryWrapper.eq("type", Constast.TYPE_PERMISSION);//只能查询权限名称
		                                                                                            
		//拼接sql，模糊查询
		queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title",permissionVo.getTitle());
		queryWrapper.eq(StringUtils.isNotBlank(permissionVo.getPercode()),"percode",permissionVo.getPercode());//只能查询权限编码                                        
		//左边页面点击也可以查询,查询本身和本身包含的菜单
		
		//排序
		queryWrapper.orderByAsc("ordernum");
		//查询
		this.permissionService.page(page, queryWrapper);
		return new DataGridView(page.getTotal(), page.getRecords());
	}
	
	/*
	 * 
	 * 加载最大的排序码
	 * 
	 * */
	
	@RequestMapping("loadPermissionMaxOrderNum")
	public Map<String,Object> loadPermissionMaxOrderNum(){
		Map<String,Object> map = new HashMap<String,Object>();
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("ordernum");//按排序码倒叙排序
		List<Permission> list = this.permissionService.list(queryWrapper);//查询到所有的菜单
		if(list.size()>0) {
			map.put("value", list.get(0).getOrdernum()+1);//拿到最大的排序码然后+1返回到页面
		}else {
			map.put("value", 1);//若是没有菜单信息则直接返回1
		}
		return map;
	}
	
	/*
	 * 
	 * 添加菜单
	 * 
	 * */
	@RequestMapping("addPermission")
	public ResultObj addPermission(PermissionVo permissionVo) {
		
		try {
			permissionVo.setType(Constast.TYPE_PERMISSION);//设置添加类型
			this.permissionService.save(permissionVo);//保存
			return ResultObj.ADD_SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	/*
	 * 
	 * 修改菜单
	 * 
	 * */
	@RequestMapping("updatePermission")
	public ResultObj updatePermission(PermissionVo permissionVo) {
		
		try {
			this.permissionService.updateById(permissionVo);//根据id进行修改
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	} 
	/*
	 * 
	 * 删除deletePermission，单个的删除没有子节点的数据
	 * */
	@RequestMapping("deletePermission")
	public ResultObj deletePermission(PermissionVo permissionVo) {
		
		try {
			this.permissionService.removeById(permissionVo.getId());
			return ResultObj.DELETE_SUCCESS;  //删除成功
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;  //删除失败
		}
	}	
}

