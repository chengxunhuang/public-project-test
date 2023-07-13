package com.project.test.server.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.project.test.server.util.excel.converter.StartConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wh
 * @date 2021/11/25
 * @ExcelIgnore：忽略掉该字段；
 * @ExcelProperty("用户名")：设置该列的名称为”用户名“；
 * @ColumnWidth(20)：设置表格列的宽度为20；
 * @DateTimeFormat("yyyy-MM-dd")：按照指定的格式对日期进行格式化；
 * @ExcelProperty(value = "性别", converter = GenderConverter.class)：自定义内容转换器，类似枚举的实现，将“男”、“女”转换成“0”、“1”的数值。
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class PayOrderExcel  implements Serializable {

    /**
     * 商户订单号
     */
    @ExcelProperty(value = "商户订单号", index = 0)
    private String mchOrderNo;
    /**
     * 渠道订单号
     */
    @ExcelProperty(value = "渠道订单号", index = 1)
    private String channelOrderNo;
    /**
     * 支付方式
     */
    @ExcelProperty(value = "支付方式", index = 2)
    private String wayCode;
    /**
     * 支付状态: 0-订单生成, 1-支付中, 2-支付成功, 3-支付失败, 4-已撤销, 5-已退款, 6-订单关闭
     */
    @ExcelProperty(value = "支付状态", converter = StartConverter.class , index = 3)
    private String state;
    /**
     * 退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款
     */
    @ExcelProperty(value = "是否默认", index = 4)
    private Byte refundState;
    /**
     * 支付金额,单位分
     */
    @ExcelProperty(value = "支付金额,单位分", index = 5)
    private Long amount;
    /**
     * 订单支付成功时间
     */
    @ExcelProperty(value = "订单支付成功时间", index = 6)
    private Date successTime;
}
