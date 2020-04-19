package com.snow.bus.controller;



import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.snow.bus.entity.Goods;
import com.snow.bus.entity.Provider;
import com.snow.bus.service.GoodsService;
import com.snow.bus.service.ProviderService;
import com.snow.bus.vo.GoodsVo;
import com.snow.bus.vo.ProviderVo;
import com.snow.sys.common.AppFileUtils;
import com.snow.sys.common.Constast;
import com.snow.sys.common.DataGridView;
import com.snow.sys.common.ResultObj;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-29
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private ProviderService providerService;
	
	/*
	 * 查询所有的供货商到商品管理页面页面展示
	 * */
	@RequestMapping("loadAllProviderForSelect")
	public DataGridView loadAllProviderForSelect(ProviderVo ProviderVo){
		
		QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("available", Constast.AVAILABLE_TRUE);//查询可用的
		List<Provider> list = this.providerService.list(queryWrapper );//拿到所有商品
		return new DataGridView(list);
	}
	

	/*
	 *根据供应商id  查询该供应商下的所有的商品到商品进货管理页面页面展示  这个方法也可以放到商品的前端控制器
	 * */
	@RequestMapping("loadAllGoodsForSelect")
	public DataGridView loadAllGoodsForSelect(Integer providerid){
		QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("available", Constast.AVAILABLE_TRUE);//查询可用的
		queryWrapper.eq(providerid!=null,"providerid",providerid);//供应商不为null时查询该供应商下的所有商品
		List<Goods> list = this.goodsService.list(queryWrapper );
		for(Goods goods:list) {
			Provider provider = this.providerService.getById(goods.getProviderid());//根据供应商id找到该供应商的所有商品
			if(null!=provider) {
				goods.setProvidername(provider.getProvidername());
			}
		}
		return new DataGridView(list);
	}
	/*
	/*
	 * 查询所有客户,到页面进行展示
	 * */
	@RequestMapping("loadAllGoods")
	public DataGridView loadAllGoods(GoodsVo goodsVo) {
		IPage<Goods> page = new Page<>(goodsVo.getPage(),goodsVo.getLimit());//分页设置
		QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("available", Constast.AVAILABLE_TRUE);//查询可用的
		queryWrapper.eq(goodsVo.getProviderid()!=null&&goodsVo.getProviderid()!=0, "providerid",goodsVo.getProviderid());
		//拼接sql，模糊查询,当下面为null时是不生效的
		queryWrapper.like(StringUtils.isNotBlank(goodsVo.getGoodsname()), "goodsname",goodsVo.getGoodsname());//商品名称
		queryWrapper.like(StringUtils.isNotBlank(goodsVo.getProductcode()), "productcode",goodsVo.getProductcode());//生产批号
		queryWrapper.like(StringUtils.isNotBlank(goodsVo.getPromitcode()), "promitcode",goodsVo.getPromitcode());//批准文号
		queryWrapper.like(StringUtils.isNotBlank(goodsVo.getDescription()), "description",goodsVo.getDescription());//商品描述
		queryWrapper.like(StringUtils.isNotBlank(goodsVo.getSize()), "size",goodsVo.getSize());//商品规格
		//排序
		//queryWrapper.orderByAsc("zip");//按邮编进行排序  id是乱的不好看，有需要再排序
		this.goodsService.page(page, queryWrapper);//分页方法调用
		List<Goods> records = page.getRecords();//扩展结果，加进providername
		for(Goods goods:records) {
			Provider byId = this.providerService.getById(goods.getProviderid());//根据id拿到供应商对象，缓存中有就从缓存拿，缓存没有就去数据库拿
			if(null!=byId) {
				goods.setProvidername(byId.getProvidername());//把供应商名字，赋值给扩展的属性
			}
		}
		return new DataGridView(page.getTotal(),records);
	}
	
	/*
	 *商品添加
	 * */
	@RequestMapping("addGoods")
	public ResultObj addGoods(GoodsVo goodsVo) {
		
		try {
			if(null!=goodsVo.getGoodsimg()&&goodsVo.getGoodsimg().endsWith("_temp")) {//判断后缀是不是带_temp
				String newName	= AppFileUtils.renameFile(goodsVo.getGoodsimg());//改成正常的文件名
				goodsVo.setGoodsimg(newName);//修改文件名
			}
			this.goodsService.save(goodsVo);//保存供应商
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}	
	}
	/*
	 *商品更新
	 *
	 *这里需要判断是不是默认图片，如果是默认图片则不需要其他操作，如果不是则又分为是不是_temp结尾的文件，如果是则代表是更换的新文件，这个时候需要把之前的老文件删除
	 *如果不是则代表还是之前的老文件和默认文件一样不需要其他操作
	 * */
	@RequestMapping("updateGoods")
	public ResultObj updateGoods(GoodsVo goodsVo) {
		
		try {
			//不是默认图片，也不为空
			if(!(null!=goodsVo.getGoodsimg()&&goodsVo.getGoodsimg().equals(Constast.IMG_GOODS_PNG))) {
				if(null!=goodsVo.getGoodsimg()&&goodsVo.getGoodsimg().endsWith("_temp")) {//判断后缀是不是带_temp
					String newName	= AppFileUtils.renameFile(goodsVo.getGoodsimg());//改成正常的文件名
					goodsVo.setGoodsimg(newName);//修改文件名
					//同时需要删除之前的文件，
					Goods byId = this.goodsService.getById(goodsVo.getId());//拿到当前id对应的对象
					String goodsimg = byId.getGoodsimg();//拿到当前id对应的图片路径
					 AppFileUtils.removeFileByPath(goodsimg);//删除老文件
				}
				
			}
			this.goodsService.updateById(goodsVo);//更新商品
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
		
	}
	
	/*
	 *商品删除
	 *
	 *这里删除记录的同时还需要把图片文件也删除
	 * */
	@RequestMapping("deleteGoods")
	public ResultObj deleteGoods(Integer id) {
		
		try {
			//同时需要删除之前的文件，  这里删除图片需要发在前面执行，因为需要获取图片路径
			Goods byId = this.goodsService.getById(id);//拿到当前id对应的对象
			String goodsimg = byId.getGoodsimg();//拿到当前id对应的图片路径
			AppFileUtils.removeFileByPath(goodsimg);//删除老文件
			this.goodsService.removeById(id);//删除供应商信息
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
		
	}
	
	
}

