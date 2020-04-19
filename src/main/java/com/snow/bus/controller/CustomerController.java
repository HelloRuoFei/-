package com.snow.bus.controller;


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
import com.snow.bus.entity.Customer;
import com.snow.bus.service.CustomerService;
import com.snow.bus.vo.CustomerVo;
import com.snow.sys.common.Constast;
import com.snow.sys.common.DataGridView;
import com.snow.sys.common.ResultObj;

/**
 * <p>
 *  客户管理前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-28
 */


@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	/*
	 * 查询所有客户,到页面进行展示
	 * */
	@RequestMapping("loadAllCustomer")
	public DataGridView loadAllCustomer(CustomerVo customerVo) {
		IPage<Customer> page = new Page<>(customerVo.getPage(),customerVo.getLimit());//分页设置
		QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("available", Constast.AVAILABLE_TRUE);//查询可用的
		//拼接sql，模糊查询,当下面为null时是不生效的
		queryWrapper.like(StringUtils.isNotBlank(customerVo.getCustomername()), "customername",customerVo.getCustomername());
		queryWrapper.like(StringUtils.isNotBlank(customerVo.getTelephone()), "telephone",customerVo.getTelephone());
		queryWrapper.like(StringUtils.isNotBlank(customerVo.getConnectionperson()), "connectionperson",customerVo.getConnectionperson());
		//排序
		//queryWrapper.orderByAsc("zip");//按邮编进行排序  id是乱的不好看，有需要再排序
		this.customerService.page(page, queryWrapper);//分页方法调用
		return new DataGridView(page.getTotal(),page.getRecords());
	}
	
	/*
	 *客户添加
	 * */
	@RequestMapping("addCustomer")
	public ResultObj addCustomer(CustomerVo customerVo) {
		
		try {
			this.customerService.save(customerVo);//保存客户
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
		
	}
	
	/*
	 *客户添加
	 * */
	@RequestMapping("updateCustomer")
	public ResultObj updateCustomer(CustomerVo customerVo) {
		
		try {
			this.customerService.updateById(customerVo);//更新客户
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
		
	}
	
	/*
	 *客户删除
	 * */
	@RequestMapping("deleteCustomer")
	public ResultObj deleteCustomer(Integer id) {
		
		try {
			this.customerService.removeById(id);//删除客户信息
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
		
	}
	
	/*
	 * 批量删除客户
	 * 
	 * */
	@RequestMapping("batchDeleteCustomer")
	public ResultObj batchDeleteCustomer(CustomerVo customerVo) {
		
		try {
			Integer[] ids = customerVo.getIds();//拿到ids
			Collection<Serializable> idList = new ArrayList<Serializable>();//转化为集合
			for(Integer id:ids) {
				idList.add(id);
			}
			this.customerService.removeByIds(idList);//批量删除，参数是集合类型
			return ResultObj.DELETE_SUCCESS;  //删除成功
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;  //删除失败
		}
	}
}

