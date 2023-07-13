package com.project.test.server.config.mq;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * @Author: wh
 * @Date: 2022/05/19/9:23
 * @Description:
 */
public class SendCallbackListener implements SendCallback {
    Log logger = LogFactory.get(RocketMQSender.class);

    @Override
    public void onSuccess(SendResult sendResult) {
        logger.info("CallBackListener on success : " + JSONObject.toJSONString(sendResult));
    }

    @Override
    public void onException(Throwable throwable) {
        logger.error("CallBackListener on exception : ", throwable);
    }
}
