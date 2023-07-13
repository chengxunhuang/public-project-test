package com.project.test.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.test.server.dao.PayOrderDao;
import com.project.test.server.entity.EsLogInterface;
import com.project.test.server.entity.PayOrder;
import com.project.test.server.entity.PayOrderExcel;
import com.project.test.server.service.PayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @Author: wh
 * @Date: 2022/06/18/15:11
 * @Description:
 */
@Service
public class PayOrderServiceImpl implements PayOrderService, EsLogInterface {
    private final PayOrderDao payOrderDao;

    @Autowired
    public PayOrderServiceImpl(PayOrderDao payOrderDao) {
        this.payOrderDao = payOrderDao;
    }

    @Override
    public List<PayOrder> getList() {
        Page<PayOrder> payOrderPage = new Page<>(0, 600);
        LambdaQueryWrapper<PayOrder> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(PayOrder::getMchNo, "M1631866260");
        /**
         * Inferred type 'E' for type parameter 'E' is not within its bound;
         * should implement 'com.baomidou.mybatisplus.core.metadata.IPage<com.project.test.server.dao.PayOrderDao>'
         * 原因是dao层的 extends BaseMapper<PayOrderDao> 写错了
         */
        Page<PayOrder> payOrderPage1 = payOrderDao.selectPage(payOrderPage, queryWrapper);
        //模拟打印日志，由logstash 收集到es中
        List<PayOrder> records = payOrderPage1.getRecords();
        records.forEach(item -> {
            // 并不仅仅向es 中 推送这个日志，项目启动时或者其他的日志都会打印
            setEsLog(item);
        });
        return payOrderPage1.getRecords();
    }

    @Override
    public PayOrder getById(String payId) {
        PayOrder payOrder = payOrderDao.selectById(payId);
        Assert.notNull(payOrder, "数据为空，请检查传入的id是否正确");
        return payOrderDao.selectById(payId);
    }

    @Override
    public void save(List<PayOrderExcel> cachedDataList) {
        System.out.println("存储数据库成功！！！");
    }
}
