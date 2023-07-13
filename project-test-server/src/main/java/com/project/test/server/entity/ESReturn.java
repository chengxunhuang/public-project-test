package com.project.test.server.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: wh
 * @Date: 2022/08/02/14:05
 * @Description:
 */
@Data
public class ESReturn {
    private List<PayOrder> payOrders;
    // 设置上一页的 起始位置
    Object[] esSortValues;

}
