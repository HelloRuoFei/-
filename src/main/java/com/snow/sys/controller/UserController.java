package com.snow.sys.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snow.sys.common.Constast;
import com.snow.sys.common.DataGridView;
import com.snow.sys.common.PinyinUtils;
import com.snow.sys.common.ResultObj;
import com.snow.sys.entity.Dept;
import com.snow.sys.entity.Role;
import com.snow.sys.entity.User;
import com.snow.sys.service.DeptService;
import com.snow.sys.service.RoleService;
import com.snow.sys.service.UserService;
import com.snow.sys.vo.UserVo;

import cn.hutool.core.util.IdUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-08
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private RoleService roleService;
	
	/*
	 * 用户查询显示及模糊查询
	 * 
	 * */
	@RequestMapping("loadAllUser")
	public DataGridView loadAllUser(UserVo userVo) {
		IPage<User> page = new Page<>(userVo.getPage(),userVo.getLimit()); //分页设置
		
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		
		
		queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);//只能查询为1的系统用户
		queryWrapper.eq(userVo.getDeptid()!=null, "deptid",userVo.getDeptid()); //只查询部门不为null的                                                                                          
		//拼接sql，模糊查询
		queryWrapper.like(StringUtils.isNotBlank(userVo.getName()), "name",userVo.getName()).or().eq(StringUtils.isNotBlank(userVo.getLoginname()), "loginname",userVo.getLoginname());//用户名模糊查询
		queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());//位置的模糊查询                                      
		
		//根据排序码排序
		queryWrapper.orderByAsc("ordernum");
		//查询
		this.userService.page(page, queryWrapper);//分页查询
		
		List<User> list = page.getRecords();//查询到的数据
		//对查询到的数据进行更换   部门id更换成部门名称     直接领导根据自连接查询，更换成领导名字
		
		for (User user: list) {
			Integer deptid = user.getDeptid();//拿到部门id----->更换成部门名称
			if(deptid!=null ) {
				Dept one = deptService.getById(deptid);//查询拿到部门对象
				user.setDeptname(one.getTitle());//设置部门名称
			}
			Integer mgr = user.getMgr();//拿到直接领导id-------->更换成领导名字
			
			if(mgr!=null) {
				User one = this.userService.getById(mgr);//查询拿到用户对象
				user.setLeadername(one.getName());//设置领导名称
			}
		}
		return new DataGridView(page.getTotal(), list);
	}

	/*
	 * 
	 * 加载最大的排序码
	 * 
	 * */
	
	@RequestMapping("loadUserMaxOrderNum")
	public Map<String,Object> loadUserMaxOrderNum(){
		Map<String,Object> map = new HashMap<String,Object>();
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("ordernum");//按排序码倒叙排序
		List<User> list = this.userService.list(queryWrapper);//查询到所有的角色
		if(list.size()>0) {
			map.put("value", list.get(0).getOrdernum()+1);//拿到最大的排序码然后+1返回到页面
		}else {
			map.put("value", 1);//若是没有角色信息则直接返回1
		}
		return map;
	}
	/*
	 * 
	 * 根据选中的部门id查询用户，查询部门的负责人
	 * 
	 * */
	
	@RequestMapping("loadUsersByDeptId")
	public DataGridView loadUsersByDeptId(Integer deptid){
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(deptid!=null,"deptid", deptid);
		queryWrapper.eq("available", Constast.AVAILABLE_TRUE);//可用的
		queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);//普通用户
		List<User> list = this.userService.list(queryWrapper);
		return new DataGridView(list);
	}
	
	/**
	 * 把用户名转成拼音当成登录名使用
	 */
	@RequestMapping("changeChineseToPinyin")
	public Map<String,Object> changeChineseToPinyin(String username){
		 Map<String,Object> map=new HashMap<>();
		 if(null!=username) {
			 map.put("value", PinyinUtils.getPingYin(username));//转成拼音
		 }else {
			 map.put("value", "");
		 }
		 return map;
	}

	/*
	 * 添加用户
	 * 
	 * */
	@RequestMapping("addUser")
	public ResultObj addUser(UserVo userVo) {
		try {
			userVo.setType(Constast.USER_TYPE_NORMAL);//设置类型
			userVo.setHiredate(new Date());//设置时间
			String salt=IdUtil.simpleUUID().toUpperCase();//生成盐
			userVo.setSalt(salt);//设置盐
			userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD, salt, 2).toString());//设置密码这里的次数要和登录验证时一样
			this.userService.save(userVo);//保存
			return ResultObj.ADD_SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/*
	 * 
	 * 根据id查询用户，查询用户数据，用于前端回显
	 * 
	 * */
	
	@RequestMapping("loadUserById")
	public DataGridView loadUserById(Integer id){
	
	 User byId = this.userService.getById(id);
		return new DataGridView(byId);//返回这个用户数据到页面进行回显
	}
	
	/*
	 * 
	 * 更新用户
	 * 
	 * */
	
	@RequestMapping("updateUser")
	public ResultObj updateUser(UserVo userVo){
		try {
			 this.userService.updateById(userVo);
			 return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			 e.printStackTrace();
			 return ResultObj.UPDATE_ERROR;
		}
		
	}
	/*
	 * 
	 * 删除用户
	 * 
	 * */
	
	@RequestMapping("deleteUser")
	public ResultObj deleteUser(Integer id){
		try {
			 this.userService.removeById(id);
			 return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			 e.printStackTrace();
			 return ResultObj.DELETE_ERROR;
		}
		
	}
	
	
	/*
	 * 
	 * 重置密码
	 * */
	@RequestMapping("resetPwd")
	public ResultObj resetPwd(Integer id){
		try {
			User user = new User();//生成一个新对象
			user.setId(id);//
			String salt=IdUtil.simpleUUID().toUpperCase();//生成盐
			user.setSalt(salt);//设置盐
			user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD, salt, 2).toString());//设置密码这里的次数要和登录验证时一样
			this.userService.updateById(user);//更新密码  这里相当于拿到id去更新数据库，上面的操作是为了密文存入数据库
		
			return ResultObj.RESET_SUCCESS;
		} catch (Exception e) {
			 e.printStackTrace();
			 return ResultObj.RESET_ERROR;
		}
		
	}
	
	/*给用户分配角色
	 * 
	 * 
	 * 1、先查询所有能用的角色
	 * 2、根据用户id查询所拥有的角色，用于回显
	 * 3、保存更新后的所选中的角色信息
	 * */
	/*
	 *  1、先查询所有能用的角色
	 *  2、根据用户id查询所拥有的角色，用于回显
	 * */
	
	@RequestMapping("initRoleByUserId")
	public DataGridView initRoleByUserId(Integer id){
		//
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("available", Constast.AVAILABLE_TRUE);//可用的
		//这里可以使用listmap，也可以使用直接list，但需要在数据库中增加表示选中的属性
		List<Map<String, Object>> listMaps = this.roleService.listMaps(queryWrapper);//所有可用的角色
		
		//查询当前用户所拥有的角色id
		List<Integer> currentUserRoleIds=this.roleService.queryUserRoleIdsByUid(id);//在rolexml中实现这个方法，查询的是role——user表，查询所有属于该角色的id返回这个数据到页面进行回显
			for (Map<String, Object> map : listMaps) {//遍历所有角色
				Boolean LAY_CHECKED=false;//这个属性是json数据中标识是否被选中的，默认为false；
				Integer roleId=(Integer) map.get("id");//拿到角色id
				for (Integer rid : currentUserRoleIds) {//遍历所有该用户拥有的角色id
					if(rid==roleId) {//相等则为true
						LAY_CHECKED=true;
						break;
					}
				}
				map.put("LAY_CHECKED", LAY_CHECKED);//加入到map中发送到前端进行展示
			}

		return new DataGridView(Long.valueOf(listMaps.size()), listMaps);//返回
	}
	/**
	 * 保存更新后的所选中的角色信息，保存在用户和角色表中sys_role_user
	 */
	@RequestMapping("saveUserRole")
	public ResultObj saveUserRole(Integer uid,Integer[] ids) {
		try {
			this.userService.saveUserRole(uid,ids);
			return ResultObj.DISPATCH_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DISPATCH_ERROR;
		}	
	}
}

