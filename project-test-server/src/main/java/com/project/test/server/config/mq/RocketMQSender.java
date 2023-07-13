package com.project.test.server.config.mq;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.project.test.server.entity.R;
import com.project.test.server.entity.RouteMessage;
import com.project.test.server.exception.BaseException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Author: wh
 * @Date: 2022/05/19/9:08
 * @Description:
 */
@Component("rocketMQSender")
public class RocketMQSender implements ApplicationContextAware {
    Log logger = LogFactory.get(RocketMQSender.class);
    private final RocketMQTemplate rocketMQTemplate;

    private static ApplicationContext applicationContext;

    @Value(value = "${rocketmq.producer.topic}:${rocketmq.producer.async-tag}")
    private String asyncag;

    @Value(value = "${rocketmq.producer.topic}:${rocketmq.producer.sync-tag}")
    private String syncag;

    @Value(value = "${rocketmq.producer.topic}:${rocketmq.producer.transaction-tag}")
    private String trag;

    // 目的是，抽象类中，不能使用 @Autowired
    public static RocketMQSender build() {
        RocketMQSender rocketMQSender = (RocketMQSender) applicationContext.getBean("rocketMQSender");
        return rocketMQSender;
    }

    @Autowired
    public RocketMQSender(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 异步消息
     *
     * @param message
     */
    public void sendAsyncMessage(RouteMessage message) {
        Message<RouteMessage> mailMessageMessage = MessageBuilder.withPayload(message)
                .build();
        rocketMQTemplate.asyncSend(asyncag, mailMessageMessage, new SendCallbackListener());
    }

    /**
     * 同步消息
     * 使用规范：https://github.com/apache/rocketmq/blob/master/docs/cn/best_practice.md?spm=a2c6h.12873639.article-detail.6.578a2869TcNYEH&file=best_practice.md
     * @param message
     */
    public void sendSyncMessage(RouteMessage message) {
        Message<RouteMessage> messageMessage = MessageBuilder.withPayload(message)
                .build();
        // send消息方法只要不抛异常，就代表发送成功
        SendResult sendResult = null;
        try {
            sendResult = rocketMQTemplate.syncSend(syncag, messageMessage);
        } catch (Exception e) {
            logger.info("消息返回的数据" + JSONObject.toJSONString(sendResult) + ",消息的key为:" + message.getCode());
        }
    }

    /**
     * 事务消息
     *
     * @param message
     */
    public void sendTransaction(RouteMessage message) {
        Message<RouteMessage> mailMessageMessage = MessageBuilder.withPayload(message)
                .build();
        rocketMQTemplate.sendMessageInTransaction(trag, mailMessageMessage,null);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
