package com.snow.bus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author snow
 * @since 2019-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_outport")
public class Outport implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer providerid;

    private String paytype;

    private Date outputtime;

    private String operateperson;

    private Double outportprice;

    private Integer number;

    private String remark;

    private Integer goodsid;

    @TableField(exist=false)
    private String providername;//供应商名称和上面的providerid在页面进行互换
    
    @TableField(exist=false)
    private String goodsname;//商品名称和上面的goodsid在页面进行互换
    
    @TableField(exist=false)
    private String size;//商品规格和上面的paytype在页面进行互换
}
