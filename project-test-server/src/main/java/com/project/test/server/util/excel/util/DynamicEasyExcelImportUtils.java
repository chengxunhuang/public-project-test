package com.project.test.server.util.excel.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.fastjson.JSONArray;
import com.project.test.server.util.excel.listener.DynamicEasyExcelListener;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wh
 * @Date: 2022/08/10/10:34
 * @Description: 动态文件导入，使用时，不需要使用注解进行标识，只需要
 * DynamicEasyExcelImportUtils.parseExcelToView(IoUtils.toByteArray(file.getInputStream()), 300);
 * 第一个参数传入字节流的字节数组，第二个参数传入读取的条数，返回List<Map<String, String>>集合，如需转化为实体类，可以根据key来获取实际数据，key为表头
 * 如需定制文件读取的监听器，可以使用ExcelUt的read方法，动态导出功能，需要首先设置表头，效率低，可以采用注解方式，参考ExcelUtil的export方法，传入参数
 */
public class DynamicEasyExcelImportUtils {
    /**
     * 动态获取全部列和数据体，默认从第一行开始解析数据
     *
     * @param stream
     * @return
     */
    public static List<Map<String, String>> parseExcelToView(byte[] stream) {
        return parseExcelToView(stream, 1);
    }

    /**
     * 动态获取全部列和数据体
     *
     * @param stream         excel文件流
     * @param parseRowNumber 指定读取行
     * @return
     */
    public static List<Map<String, String>> parseExcelToView(byte[] stream, Integer parseRowNumber) {
        DynamicEasyExcelListener readListener = new DynamicEasyExcelListener();
        EasyExcelFactory.read(new ByteArrayInputStream(stream)).registerReadListener(readListener).headRowNumber(parseRowNumber).sheet(0).doRead();
        List<Map<Integer, String>> headList = readListener.getHeadList();
        if (CollectionUtils.isEmpty(headList)) {
            throw new RuntimeException("Excel未包含表头");
        }
        List<Map<Integer, String>> dataList = readListener.getDataList();
        if (CollectionUtils.isEmpty(dataList)) {
            throw new RuntimeException("Excel未包含数据");
        }
        //获取头部,取最后一次解析的列头数据
        Map<Integer, String> excelHeadIdxNameMap = headList.get(0);
        //封装数据体
        List<Map<String, String>> excelDataList = Lists.newArrayList();
        for (Map<Integer, String> dataRow : dataList) {
            Map<String, String> rowData = new LinkedHashMap<>();
            excelHeadIdxNameMap.entrySet().forEach(columnHead -> {
                rowData.put(columnHead.getValue(), dataRow.get(columnHead.getKey()));
            });
            excelDataList.add(rowData);
        }
        return excelDataList;
    }

    /**
     * 文件导入测试
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File("E:\\data\\订单表.xlsx"));
        byte[] stream = IoUtils.toByteArray(inputStream);
        List<Map<String, String>> dataList = parseExcelToView(stream, 300);
        System.out.println(JSONArray.toJSONString(dataList));
        inputStream.close();
    }
}