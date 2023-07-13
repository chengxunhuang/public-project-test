package com.project.test.server.controller;

import com.project.test.server.util.excel.listener.ReadExcelListener;
import com.project.test.server.entity.PayOrder;
import com.project.test.server.entity.PayOrderExcel;
import com.project.test.server.service.PayOrderService;
import com.project.test.server.util.BeanCopyUtil;
import com.project.test.server.util.excel.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: wh
 * @Date: 2022/08/09/11:25
 * @Description:
 */
@RestController
@RequestMapping("excel")
public class ExcelController {

    private final PayOrderService payOrderService;

    @Autowired
    public ExcelController(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    /**
     * 导出
     *
     * @param response
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        List<PayOrder> list = payOrderService.getList();
        List<PayOrderExcel> payOrderExcel = BeanCopyUtil.copyListProperties(list, PayOrderExcel::new);
        // BeanUtils.copyProperties(list, payOrderExcel); 并不能复制集合
        ExcelUtil<PayOrderExcel> excelExcelUtil = new ExcelUtil<>(PayOrderExcel.class);
        try {
            excelExcelUtil.download(response, payOrderExcel, "订单表", "数据");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入
     *
     * @param file
     */
    @PostMapping("/read")
    public void read(@RequestPart("file") MultipartFile file) {
        try {
            // 1.这里 需要指定读用哪个class去读，然后读取第一个sheet,doReadSync 同步读取带有返回结果
            // 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面
//            List<PayOrderExcel> list = EasyExcel.read(file.getInputStream())
//                    .head(PayOrderExcel.class)
//                    .sheet()
//                    .doReadSync();
            // 2.推荐使用，可以定制化 ReadExcelListener
            ExcelUtil<PayOrderExcel> excelExcelUtil = new ExcelUtil<>(PayOrderExcel.class);
            excelExcelUtil.read(file.getInputStream(), new ReadExcelListener(payOrderService),"Sheet1");
            // 3.动态导入
//            List<Map<String, String>> dataList = DynamicEasyExcelImportUtils.parseExcelToView(IoUtils.toByteArray(file.getInputStream()), 300);
//            System.out.println(JSONArray.toJSONString(dataList));
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
