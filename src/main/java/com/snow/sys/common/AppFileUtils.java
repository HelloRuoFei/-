package com.snow.sys.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;

/*
 * 
 * 文件上传下载工具类
 * */
public class AppFileUtils {
	
	//文件上传的保存路径
	public static  String UPLOAD_PATH="D:/upload/";//默认值
	
	static {
		//读取文件上传的保存路径配置文件的存储地址
		InputStream stream = AppFileUtils.class.getClassLoader().getResourceAsStream("file.properties");//获取文件字节流
		Properties properties=new Properties();
		try {
			properties.load(stream);//加载
		} catch (IOException e) {
			e.printStackTrace();
		}
		String property = properties.getProperty("filepath");
		if(null!=property) {
			UPLOAD_PATH=property;//如果配置文件改变这里可以重新赋值
		}
	}

	/**
	 * 根据文件老名字得到新名字
	 * @param oldName
	 * @return
	 */
	public static String createNewFileName(String oldName) {
		String stuff=oldName.substring(oldName.lastIndexOf("."), oldName.length());//拿到后缀，从“ 。” 开始
		return IdUtil.simpleUUID().toUpperCase()+stuff;//生成新的文件名字
	}

	/**
	 * 文件下载
	 * @param path
	 * @return
	 */
	public static ResponseEntity<Object> createResponseEntity(String path) {
		//1,构造文件对象
		File file=new File(UPLOAD_PATH, path);//组合成绝对路径
		if(file.exists()) {
			//将下载的文件，封装byte[]
			byte[] bytes=null;
			try {
				bytes =FileUtil.readBytes(file);//下载文件存放到数组当中,这个方法可以传字节流也可以传路径
			} catch (Exception e) {
				e.printStackTrace();
			}

			//创建封装响应头信息的对象
			HttpHeaders header=new HttpHeaders();
			//封装响应内容类型(APPLICATION_OCTET_STREAM 响应的内容不限定)
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			//设置下载的文件的名称   下载的时候使用
			//header.setContentDispositionFormData("attachment", "123.jpg");
			//创建ResponseEntity对象
			ResponseEntity<Object> entity=new ResponseEntity<Object>(bytes, header, HttpStatus.CREATED);//返回ResponseEntity，包含所有信息
			return entity;
		}
		return null;
	}
	
	/*
	 * 
	 * 根据路径改名字去掉_temp
	 * 在修改存入数据库的路径时。也需要把已经上传的图片文件的名字也进行修改
	 * */
	public static String renameFile(String goodsimg) {
		
		File file = new File(UPLOAD_PATH, goodsimg);//拿到流
		String replace = goodsimg.replace("_temp", "");//去除过_temp的文件名，返回出去存储到数据库中
		if(file.exists()) {
			file.renameTo(new File(UPLOAD_PATH,replace ));//更改文件名
		}
		return replace;
	}
	/*
	 * 
	 * 根据路径删除的文件
	 * */
	public static void removeFileByPath(String goodsimg) {
		File file = new File(UPLOAD_PATH, goodsimg);//拿到流
		if(file.exists()) {
			file.delete();//删除文件
		}
	}
	
}
