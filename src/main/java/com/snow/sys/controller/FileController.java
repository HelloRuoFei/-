package com.snow.sys.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.snow.sys.common.AppFileUtils;

import cn.hutool.core.date.DateUtil;

/*
 * 文件上传和下载共用类
 * */
@RestController
@RequestMapping("/file")
public class FileController {
	
	/*
	 * 文件上传  这里会出现文件太大出现异常，需要限制一下 
	 * */
	@RequestMapping("uploadFile")
	public Map<String,Object> uploadFile(MultipartFile mf) {
		//得到文件名
		String oldName = mf.getOriginalFilename();//得到上传的文件名
		//根据文件名生成新的文件名
		String newName = AppFileUtils.createNewFileName(oldName);//使用工具类的到新名字
		//得到当前日期的字符串
		String dirName = DateUtil.format(new Date(), "yyyy-MM-dd");
		//构造文件夹
		File dirFile = new File(AppFileUtils.UPLOAD_PATH,dirName);//该路径下的该方法，一个参数也可以
		//判断当前文件夹是否存在
		if(!dirFile.exists()) {
			//不存在
			dirFile.mkdirs();//创建这个文件夹
		}
		//构造文件对象
		File file = new File(dirFile,newName+"_temp");//这里解决上传图片不点提交的问题先把图片后缀加上+"_temp"，不提交的会定时处理，提交的话改成正常的名字
		//存放图片把mf里面的图片信息写入file
		try {
			mf.transferTo(file);//存放图片 mf中包含了文件的所有信息，springboot中不需要配置MultipartFile，已经自动开启
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("path", dirName+"/"+newName+"_temp");//这里存放的是相对路径，文件夹的名称加上文件的名称
		return map;
	}
	/*
	 * 文件下载
	 * */
	
	@RequestMapping("showImageByPath")
	public ResponseEntity<Object> showImageByPath(String path){//ResponseEntity标识整个http相应：状态码、头部信息以及相应体内容
		
		return AppFileUtils.createResponseEntity(path);//根据路径下载文件path=文件夹的名称加上文件的名称
	}
}
