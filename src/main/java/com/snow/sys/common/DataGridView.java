package com.snow.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * json数据实体
 * 通过这个实体类来进一步封装数据，并发送到页面进行解析
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridView {
	
	private Integer code = 0;
	private String msg = "";
	private Long count  = 0L;//数据总条数
	private Object data;//数据记录
	
	public DataGridView(Long count, Object data) {//包含总条数和全部查询记录，用于分页
		super();
		this.count = count;
		this.data = data;
	}
	public DataGridView(Object data) {//只包含数据
		super();
		this.data = data;
	}
	
	
	
	
	
	
}
