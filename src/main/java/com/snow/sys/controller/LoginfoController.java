package com.snow.sys.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snow.sys.common.DataGridView;
import com.snow.sys.common.ResultObj;
import com.snow.sys.entity.Loginfo;
import com.snow.sys.service.LoginfoService;
import com.snow.sys.vo.LoginfoVo;

/**
 * <p>
 *  日志前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-13
 */
@RestController
@RequestMapping("/loginfo")
public class LoginfoController {
	@Autowired
	private LoginfoService loginfoService;
	
	/*查询日志记录并发送到页面展示*/
	@RequestMapping("loadAllLoginfo")
	public DataGridView loadAllLoginfo(LoginfoVo loginfoVo) {
	
		IPage<Loginfo> page = new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());   // 分页构造函数对象
		QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();   //QueryWrapper对象Entity 对象封装操作类
		//拼接sql，模糊查询
		queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginname()), "loginname",loginfoVo.getLoginname());
		queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginip()), "loginip",loginfoVo.getLoginip());
		//区间查询
		queryWrapper.ge(loginfoVo.getStartTime()!=null, "logintime", loginfoVo.getStartTime());
		queryWrapper.le(loginfoVo.getEndTime()!=null, "logintime", loginfoVo.getEndTime());
		//排序
		queryWrapper.orderByDesc("logintime");
		//查询
		this.loginfoService.page(page, queryWrapper);
		return new DataGridView(page.getTotal(),page.getRecords()); //返回总条数，和查询到的数据记录
	}
	
	/*
	 * 删除日志（单个日志）
	 * 
	 * */
	@RequestMapping("deleteLoginfo")
	public ResultObj deleteLoginfo(Integer id) {
		
		try {
			this.loginfoService.removeById(id);
			return ResultObj.DELETE_SUCCESS;  //删除成功
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;  //删除失败
		}
	}
	
	/*
	 * 批量删除
	 * 
	 * */
	@RequestMapping("batchDeleteLoginfo")
	public ResultObj batchDeleteLoginfo(LoginfoVo loginfoVo) {
		
		try {
			Integer[] ids = loginfoVo.getIds();//拿到ids
			Collection<Serializable> idList = new ArrayList<Serializable>();//转化为集合
			for(Integer id:ids) {
				idList.add(id);
			}
			this.loginfoService.removeByIds(idList);//批量删除，参数是集合类型
			return ResultObj.DELETE_SUCCESS;  //删除成功
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;  //删除失败
		}
	}
	
}

