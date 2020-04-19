package com.snow.sys.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snow.sys.common.DataGridView;
import com.snow.sys.common.ResultObj;
import com.snow.sys.common.WebUtils;
import com.snow.sys.entity.Notice;
import com.snow.sys.entity.User;
import com.snow.sys.service.NoticeService;
import com.snow.sys.vo.NoticeVo;


/**
 * <p>
 *  系统公告前端控制器
 * </p>
 *
 * @author snow
 * @since 2019-12-14
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	
	/*
	 * 
	 * 查询所有公告及模糊查询
	 * */
	@RequestMapping("loadAllNotice")
	public DataGridView loadAllNotice(NoticeVo noticeVo) {
		IPage<Notice> page = new Page<>(noticeVo.getPage(),noticeVo.getLimit()); 
		QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
		//拼接sql，模糊查询
		queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()), "title",noticeVo.getTitle());
		queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOpername()), "opername",noticeVo.getOpername());
		//区间查询
		queryWrapper.ge(noticeVo.getStartTime()!=null, "createtime", noticeVo.getStartTime());
		queryWrapper.le(noticeVo.getEndTime()!=null, "createtime", noticeVo.getEndTime());
		//排序
		queryWrapper.orderByDesc("createtime");
		//查询
		this.noticeService.page(page, queryWrapper);
		return new DataGridView(page.getTotal(), page.getRecords());
	}
	
	
	/*
	 * 添加公告
	 * 
	 * */
	@RequestMapping("addNotice")
	public ResultObj addNotice(NoticeVo noticeVo) {
		try {
			Notice notice = new Notice();//获取公告实体类
			User user = (User) WebUtils.getSession().getAttribute("user");
			notice.setTitle(noticeVo.getTitle());//赋值
			notice.setOpername( user.getName());//获取登录的用户名
			notice.setCreatetime(new Date());//获取时间
			notice.setContent(noticeVo.getContent());
			//这里可以传notivcevo对象也可以传notice对象
			this.noticeService.save(notice);//存储
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/*
	 * 修改公告
	 * 
	 * */
	@RequestMapping("updateNotice")
	public ResultObj updateNotice(NoticeVo noticeVo) {
		try {
			this.noticeService.updateById(noticeVo);//更新。只更新标题和内容其他不变
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}	
	}
	/*
	 * 删除公告（单个公告）
	 * 
	 * */
	@RequestMapping("deleteNotice")
	public ResultObj deleteNotice(Integer id) {
		
		try {
			this.noticeService.removeById(id);
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
	@RequestMapping("batchDeleteNotice")
	public ResultObj batchDeleteNotice(NoticeVo noticeVo) {
		
		try {
			Integer[] ids = noticeVo.getIds();//拿到ids
			Collection<Serializable> idList = new ArrayList<Serializable>();//转化为集合
			for(Integer id:ids) {
				idList.add(id);
			}
			this.noticeService.removeByIds(idList);//批量删除，参数是集合类型
			return ResultObj.DELETE_SUCCESS;  //删除成功
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;  //删除失败
		}
	}
	
}

