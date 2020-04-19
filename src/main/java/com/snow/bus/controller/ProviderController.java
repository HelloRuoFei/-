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
import com.snow.bus.entity.Provider;
import com.snow.bus.service.ProviderService;
import com.snow.bus.vo.ProviderVo;
import com.snow.sys.common.Constast;
import com.snow.sys.common.DataGridView;
import com.snow.sys.common.ResultObj;

/**
 * <p>
 * 供应商 前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-29
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {
	
	@Autowired
	private ProviderService providerService;
	/*
	 * 查询所有供应商,到页面进行展示
	 * */
	@RequestMapping("loadAllProvider")
	public DataGridView loadAllProvider(ProviderVo ProviderVo) {
		IPage<Provider> page = new Page<>(ProviderVo.getPage(),ProviderVo.getLimit());//分页设置
		QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("available", Constast.AVAILABLE_TRUE);//查询可用的
		//拼接sql，模糊查询,当下面为null时是不生效的
		queryWrapper.like(StringUtils.isNotBlank(ProviderVo.getProvidername()), "providername",ProviderVo.getProvidername());
		queryWrapper.like(StringUtils.isNotBlank(ProviderVo.getTelephone()), "telephone",ProviderVo.getTelephone());
		queryWrapper.like(StringUtils.isNotBlank(ProviderVo.getConnectionperson()), "connectionperson",ProviderVo.getConnectionperson());
		//排序
		//queryWrapper.orderByAsc("zip");//按邮编进行排序  id是乱的不好看，有需要再排序
		this.providerService.page(page, queryWrapper);//分页方法调用
		return new DataGridView(page.getTotal(),page.getRecords());
	}
	
	/*
	 *供应商添加
	 * */
	@RequestMapping("addProvider")
	public ResultObj addProvider(ProviderVo ProviderVo) {
		
		try {
			this.providerService.save(ProviderVo);//保存供应商
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
		
	}
	
	/*
	 *供应商更新
	 * */
	@RequestMapping("updateProvider")
	public ResultObj updateProvider(ProviderVo ProviderVo) {
		
		try {
			this.providerService.updateById(ProviderVo);//更新供应商
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
		
	}
	
	/*
	 *供应商删除
	 * */
	@RequestMapping("deleteProvider")
	public ResultObj deleteProvider(Integer id) {
		
		try {
			this.providerService.removeById(id);//删除供应商信息
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
		
	}
	
	/*
	 * 批量删除供应商
	 * 
	 * */
	@RequestMapping("batchDeleteProvider")
	public ResultObj batchDeleteProvider(ProviderVo ProviderVo) {
		
		try {
			Integer[] ids = ProviderVo.getIds();//拿到ids
			Collection<Serializable> idList = new ArrayList<Serializable>();//转化为集合
			for(Integer id:ids) {
				idList.add(id);
			}
			this.providerService.removeByIds(idList);//批量删除，参数是集合类型
			return ResultObj.DELETE_SUCCESS;  //删除成功
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;  //删除失败
		}
	}
	
	
}

