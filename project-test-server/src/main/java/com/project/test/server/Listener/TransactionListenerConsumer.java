package com.project.test.server.Listener;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.project.test.server.entity.RouteMessage;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: wh
 * @Date: 2022/07/15/16:34
 * @Description: 消费者
 */
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.trGroup}", topic = "${rocketmq.consumer.topic}",
        selectorExpression = "${rocketmq.consumer.transaction-tag}", messageModel = MessageModel.CLUSTERING)
public class TransactionListenerConsumer implements RocketMQListener<RouteMessage> {
    Log logger = LogFactory.get(TransactionListenerConsumer.class);
    @Override
    public void onMessage(RouteMessage message) {
        // 进行业务处理
        logger.info("进行业务处理中。。。");
        // 消费时，如果有异常的话，默认会进行重试消费，默认16次，之后放到死信队列
         int i = 1/0;
    }
}
