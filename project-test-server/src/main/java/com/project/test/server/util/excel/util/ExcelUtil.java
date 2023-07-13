package com.project.test.server.util.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.ReadListener;
import com.project.test.server.util.excel.exportStyle.CommonCellStyleStrategy;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Set;

/**
 * @Author: wh
 * @Date: 2022/08/09/14:05
 * @Description:
 */
public class ExcelUtil<T> {
    public Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void download(HttpServletResponse response, Collection<? extends T> data, String filename, String sheetName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        // response.getOutputStream() 这种呢直接在浏览器上 会弹出一个框，存放到什么位置
        // 当然也可以 FileOutputStream 定义文件存放位置
        EasyExcel.write(response.getOutputStream(), this.clazz).sheet(sheetName)
                // 注册通用格式策略
//                .registerWriteHandler(CommonCellStyleStrategy.getHorizontalCellStyleStrategy())
                .doWrite(data);
    }

    /**
     * excludeColumnFiledNames 需要隐藏的列
     * excludeColumnFieldNames 实际上对于这些参数而言 ，参考官网 去找就可以
     */
    public void download(HttpServletResponse response, Collection<? extends T> data, String filename, String sheetName, Set<String> excludeColumnFiledNames) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), this.clazz).sheet(sheetName).excludeColumnFieldNames(excludeColumnFiledNames)
                .doWrite(data);
    }
    public void read(InputStream inputStream, ReadListener<? extends T> readListener,String sheetName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, this.clazz, readListener).sheet(sheetName).doRead();
    }
}
