package com.snow.sys.controller;


import java.util.ArrayList;
import java.util.Date;
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
import com.snow.sys.entity.Dept;
import com.snow.sys.service.DeptService;
import com.snow.sys.vo.DeptVo;
/**
 * <p>
 *系统管理       部门管理 前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-16
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
	
	@Autowired
	private DeptService deptService;
	
	
	/*
	 * 加载部门管理左边的部门树的json
	 * 
	 * */
	@RequestMapping("loadDeptManagerLeftTreeJson")
	public DataGridView loadDeptManagerLeftTreeJson(DeptVo deptVo) {
		//查询所有的部门信息
		List<Dept> list = this.deptService.list();
		//使用treeNode来封装数据
		List<TreeNode> treeNodes = new ArrayList<>();
		for(Dept dept:list) {
			Integer id = dept.getId();
			Integer pid = dept.getPid();
			String title = dept.getTitle();
			Boolean spread =dept.getOpen()==Constast.OPEN_TRUE?true:false;
			treeNodes.add(new TreeNode(id,pid,title,spread));
		}
		return new DataGridView(treeNodes);
	}
	
	/*
	 * 加载部门管理右边的数据及模糊查询
	 * 
	 * 
	 * */
	@RequestMapping("loadAllDept")
	public DataGridView loadAllDept(DeptVo deptVo) {
		IPage<Dept> page = new Page<>(deptVo.getPage(),deptVo.getLimit()); 
		QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
		
		if(deptVo.getId()!=null&&deptVo.getId()!=1) {
			queryWrapper.eq( "pid",deptVo.getId());
		}
		if(deptVo.getId()!=null&&deptVo.getId()==1) {
			queryWrapper.eq( "pid",deptVo.getId()).or().eq("id",deptVo.getId());
		}
		//拼接sql，模糊查询
		queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()), "title",deptVo.getTitle());
		queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()), "address",deptVo.getAddress());
		queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()), "remark",deptVo.getRemark());
		//左边页面点击也可以查询,查询本身和本身包含的部门
		
		//排序
		queryWrapper.orderByAsc("ordernum");
		//查询
		this.deptService.page(page, queryWrapper);
		return new DataGridView(page.getTotal(), page.getRecords());
	}
	
	/*
	 * 
	 * 加载最大的排序码
	 * 
	 * */
	
	@RequestMapping("loadDeptMaxOrderNum")
	public Map<String,Object> loadDeptMaxOrderNum(){
		Map<String,Object> map = new HashMap<String,Object>();
		QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("ordernum");//按排序码倒叙排序
		List<Dept> list = this.deptService.list(queryWrapper);//查询到所有的部门
		if(list.size()>0) {
			map.put("value", list.get(0).getOrdernum()+1);//拿到最大的排序码然后+1返回到页面
		}else {
			map.put("value", 1);//若是没有部门信息则直接返回1
		}
		return map;
	}
	
	/*
	 * 
	 * 添加部门
	 * 
	 * */
	@RequestMapping("addDept")
	public ResultObj addDept(DeptVo deptVo) {
		
		try {
			deptVo.setCreatetime(new Date());//获取当前时间
			this.deptService.save(deptVo);//保存
			return ResultObj.ADD_SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	/*
	 * 
	 * 修改部门
	 * 
	 * */
	@RequestMapping("updateDept")
	public ResultObj updateDept(DeptVo deptVo) {
		
		try {
			this.deptService.updateById(deptVo);//根据id进行修改
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	} 
	
	/*
	 * 
	 * 删除checkDeptHasChildrenNode，如果有子节点就提示先删除子节点
	 * */
	@RequestMapping("checkDeptHasChildrenNode")
	public  Map<String,Object> checkDeptHasChildrenNode(DeptVo deptVo) {
			Map<String,Object> map = new HashMap<String,Object>();
			QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("pid", deptVo.getId());
			List<Dept> list = this.deptService.list(queryWrapper);//查询是不是有子节点
			if(list.size()>0) {
				map.put("value", true);
			}else {
				map.put("value", false);
			}
			return map;
		
	}
	/*
	 * 
	 * 删除deleteDept，单个的删除没有子节点的数据
	 * */
	@RequestMapping("deleteDept")
	public ResultObj deleteDept(DeptVo deptVo) {
		
		try {
			this.deptService.removeById(deptVo.getId());
			return ResultObj.DELETE_SUCCESS;  //删除成功
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;  //删除失败
		}
	}
}


