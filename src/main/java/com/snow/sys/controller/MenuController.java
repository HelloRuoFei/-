package com.snow.sys.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.snow.sys.common.TreeNodeBuilder;
import com.snow.sys.common.WebUtils;

import com.snow.sys.entity.Permission;
import com.snow.sys.entity.User;
import com.snow.sys.service.PermissionService;
import com.snow.sys.service.RoleService;
import com.snow.sys.vo.PermissionVo;

/**
 * <p>
 *        菜单管理前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-12
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private PermissionService permissionService ;

	@Autowired
	private RoleService roleService;
	/*
	 * 主页左边菜单显示
	 * */
	@RequestMapping("loadIndexLeftMenuJson")
	public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo) {
		//查询所有菜单
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		//设置只能查询菜单
		queryWrapper.eq("type",Constast.TYPE_MNEU);
		queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
		
		User user = (User) WebUtils.getSession().getAttribute("user");//判断用户是不是超级用户，根据不同的用户来显示不同的菜单
		List<Permission> list = null;
		if(user.getType()==Constast.USER_TYPE_SUPER) {//超级用户显示下面查询到的菜单
			
			list = permissionService.list(queryWrapper);//所有的菜单
		}else {
			//根据用户id+角色+权限去查询
			Integer userId = user.getId();//拿到用户id
			//根据用户id查询用户所拥有的角色id
			List<Integer> queryUserRoleIdsByUid = roleService.queryUserRoleIdsByUid(userId);
			//根据角色id查询角色拥有的所有的权限或菜单id
			Set<Integer> pids = new HashSet<>();//存放权限或菜单id
			for(Integer rid: queryUserRoleIdsByUid) {
				
				List<Integer> queryRolePermissionIdsByRid = roleService.queryRolePermissionIdsByRid(rid);//查询到角色拥有的所有的权限或菜单id
				pids.addAll(queryRolePermissionIdsByRid);//放进去
			}
			//根据角色id去查找权限
			if(pids.size()>0) {//该角色有权限或者菜单
				queryWrapper.in("id", pids);
				list = permissionService.list(queryWrapper);
			}else {
				list = new ArrayList<>();//该角色没有有权限或者菜单，直接返回一个空list就可以
			}
		
		}
		List<TreeNode> treeNodes = new ArrayList<>();
		for(Permission permission:list) {
			
			Integer id = permission.getId();
			Integer pid = permission.getPid();
			String title = permission.getTitle();
			String icon = permission.getIcon();
			String href = permission.getHref();
			Boolean spread = permission.getOpen()==Constast.OPEN_TRUE?true:false;
			treeNodes.add(new TreeNode(id,pid,title,icon,href,spread));
		}
		
		//构造层级关系
		
		List<TreeNode> list2 = TreeNodeBuilder.build(treeNodes, 1);
		return new DataGridView(list2);
	}
		
	/*
	 * 加载菜单管理左边的菜单树的json
	 * 
	 * */
	@RequestMapping("loadMenuManagerLeftTreeJson")
	public DataGridView loadMenuManagerLeftTreeJson(PermissionVo permissionVo) {
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
	@RequestMapping("loadAllMenu")
	public DataGridView loadAllMenu(PermissionVo permissionVo) {
		IPage<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit()); 
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		
		if(permissionVo.getId()!=null&&permissionVo.getId()!=0) {
			queryWrapper.eq( "pid",permissionVo.getId());
		}
		if(permissionVo.getId()!=null&&permissionVo.getId()==0) {
			queryWrapper.eq( "pid",permissionVo.getId()).or().eq("id",permissionVo.getId());
		}
		queryWrapper.eq("type", Constast.TYPE_MNEU);//只能查询菜单
		//拼接sql，模糊查询
		queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title",permissionVo.getTitle());
		
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
	
	@RequestMapping("loadMenuMaxOrderNum")
	public Map<String,Object> loadMenuMaxOrderNum(){
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
	@RequestMapping("addMenu")
	public ResultObj addMenu(PermissionVo permissionVo) {
		
		try {
			permissionVo.setType(Constast.TYPE_MNEU);//设置添加类型
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
	@RequestMapping("updateMenu")
	public ResultObj updateMenu(PermissionVo permissionVo) {
		
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
	 * 删除checkMenuHasChildrenNode，如果有子节点就提示先删除子节点
	 * */
	@RequestMapping("checkMenuHasChildrenNode")
	public  Map<String,Object> checkMenuHasChildrenNode(PermissionVo permissionVo) {
			Map<String,Object> map = new HashMap<String,Object>();
			QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("pid", permissionVo.getId());
			List<Permission> list = this.permissionService.list(queryWrapper);//查询是不是有子节点
			if(list.size()>0) {
				map.put("value", true);
			}else {
				map.put("value", false);
			}
			return map;
		
	}
	/*
	 * 
	 * 删除deleteMenu，单个的删除没有子节点的数据
	 * */
	@RequestMapping("deleteMenu")
	public ResultObj deleteMenu(PermissionVo permissionVo) {
		
		try {
			this.permissionService.removeById(permissionVo.getId());
			return ResultObj.DELETE_SUCCESS;  //删除成功
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;  //删除失败
		}
	}	
}

