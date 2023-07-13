package com.project.test.server.entity;

import lombok.Data;

/**
 * @Author: wh
 * @Date: 2022/07/30/15:38
 * @Description: es检索条件
 */
@Data
public class PayOrderVO {
    // 检索 alipay
    private String ifCode;
    // 页号 只是用于 from size 分页
    private Integer pageNum;
    // 条目
    private Integer pageSize;
    //
    Object[] esSortValues;
}
