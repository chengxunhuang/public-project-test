package com.project.test.server.service;

import com.project.test.server.entity.PayOrder;
import com.project.test.server.entity.PayOrderExcel;

import java.util.List;

/**
 * @Author: wh
 * @Date: 2022/06/18/15:11
 * @Description:
 */
public interface PayOrderService {
    List<PayOrder> getList();

    PayOrder getById(String payId);

    void save(List<PayOrderExcel> cachedDataList);
}
