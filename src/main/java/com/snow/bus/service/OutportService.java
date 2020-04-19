package com.snow.bus.service;

import com.snow.bus.entity.Outport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author snow
 * @since 2019-12-30
 */
public interface OutportService extends IService<Outport> {

	void addOutport(Integer id, Integer number, String remark);

}
