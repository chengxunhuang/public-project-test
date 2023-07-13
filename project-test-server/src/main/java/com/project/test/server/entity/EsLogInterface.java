package com.project.test.server.entity;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: wh
 * @Date: 2022/07/29/14:04
 * @Description:
 */
public interface EsLogInterface {
    Logger esLog = LoggerFactory.getLogger(EsLogInterface.class);

    default void setEsLog(PayOrder payOrder) {
        ESPayOrder.ESPayOrderBuilder esPayOrderBuilder = ESPayOrder.builder();
        esPayOrderBuilder.payOrderId(payOrder.getPayOrderId()).amount(payOrder.getAmount()).mchNo(payOrder.getMchNo())
                .isvNo(payOrder.getIsvNo()).appId(payOrder.getAppId()).mchName(payOrder.getMchName()).mchType(payOrder.getMchType())
                .mchOrderNo(payOrder.getMchOrderNo()).ifCode(payOrder.getIfCode()).wayCode(payOrder.getWayCode()).amount(payOrder.getAmount())
                .mchFeeAmount(payOrder.getMchFeeAmount()).currency(payOrder.getCurrency());
        esLog.info(JSONObject.toJSONString(esPayOrderBuilder.build()));
    }
}
