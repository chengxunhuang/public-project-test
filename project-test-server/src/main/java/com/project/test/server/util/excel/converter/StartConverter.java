package com.project.test.server.util.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: wh
 * @Date: 2022/08/09/14:49
 * @Description: 将0-6 转化成相应的支付状态码值
 * 不同版本中，convertToJavaData和convertToExcelData的方法参数有所不同 自行调整
 */
public class StartConverter implements Converter<String> {

    private static final Byte CODE0 = 0;
    private static final Byte CODE1 = 1;
    private static final Byte CODE2 = 2;
    private static final Byte CODE3 = 3;
    private static final Byte CODE4 = 4;
    private static final Byte CODE5 = 5;
    private static final Byte CODE6 = 6;

    @Override
    public Class<?> supportJavaTypeKey() {
        // 实体类中对象属性类型
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        // Excel中对应属性类型
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里读的时候会调用
     *
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     */
    @Override
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String gender = cellData.getStringValue();
        // 判断Excel中的值，将其转换为预期的数值
        if (StringUtils.isBlank(gender)) {
            return null;
        } else if (gender.equals("订单生成")) {
            return "0";
        } else if (gender.equals("支付中")) {
            return "1";
        } else if (gender.equals("支付成功")) {
            return "2";
        } else if (gender.equals("支付失败")) {
            return "3";
        } else if (gender.equals("已撤销")) {
            return "4";
        } else if (gender.equals("已退款")) {
            return "5";
        } else if (gender.equals("订单关闭")) {
            return "6";
        }
        return null;
    }

    /**
     * 导出的时候回调用
     * 从 数据库中读取完数据 ， 将 需要转化的字段 转化完成后  ， 导出来。
     *
     * @param s                   @ExcelProperty converter属性 标注的字段
     * @param contentProperty
     * @param globalConfiguration
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(String s, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        // 判断实体类中获取的值，转换为导出Excel预期的值
        if (StringUtils.isBlank(s)) {
            return new WriteCellData<>("");
        } else if (s.equals("0")) {
            return new WriteCellData<>("订单生成");
        } else if (s.equals("1")) {
            return new WriteCellData<>("支付中");
        } else if (s.equals("2")) {
            return new WriteCellData<>("支付成功");
        } else if (s.equals("3")) {
            return new WriteCellData<>("支付失败");
        } else if (s.equals("4")) {
            return new WriteCellData<>("已撤销");
        } else if (s.equals("5")) {
            return new WriteCellData<>("已退款");
        } else if (s.equals("6")) {
            return new WriteCellData<>("订单关闭");
        }
        return new WriteCellData<>("");
    }
}