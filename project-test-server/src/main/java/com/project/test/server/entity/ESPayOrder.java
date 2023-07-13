package com.project.test.server.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: wh
 * @Date: 2022/07/29/11:39
 * @Description: Document 可以自动创建 索引
 */
@Data
@Builder
@Document(indexName = "pay")
public class ESPayOrder {
    @Id
    private String payOrderId;
    @Field
    private String mchNo;
    @Field
    private String isvNo;
    @Field
    private String appId;
    @Field
    private String mchName;
    @Field
    private Byte mchType;
    @Field
    private String mchOrderNo;
    @Field
    private String ifCode;
    @Field
    private String wayCode;
    @Field
    private Long amount;
    @Field
    private BigDecimal mchFeeRate;
    @Field
    private Long mchFeeAmount;
    @Field
    private String currency;
}
