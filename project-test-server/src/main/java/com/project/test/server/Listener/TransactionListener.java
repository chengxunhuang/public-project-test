package com.project.test.server.Listener;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.project.test.server.config.mq.RocketMQSender;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wh
 * @Date: 2022/07/15/15:46   事务监听器
 * @Description: 在rocketmq-spring-boot-starter < 2.1.0以前的项目中，可以用多个@RocketMQTransactionListener来监听不同的txProducerGroup来发送不同类型的事务消息到topic，
 * 但是现在在一个项目中，如果你在一个project中写了多个@RocketMQTransactionListener，项目将不能启动，启动会报
 * java.lang.IllegalStateException: rocketMQTemplate already exists RocketMQLocalTransactionListener。
 * rocketmq-spring-boot-starter 2.1.0对应rocketmq 4.6.0 ; 2.0.2对应rocketmq 4.4.0
 * 2.1.0 https://blog.csdn.net/z69183787/article/details/109958380
 */
@RocketMQTransactionListener
public class TransactionListener implements RocketMQLocalTransactionListener {
    Log logger = LogFactory.get(TransactionListener.class);

    /**
     * 回调操作方法
     * 消息预提交成功就会触发该方法的执行，用于完成本地事务
     * 首先向mq中发送一个半消息，此时消费者看不到，这条消息，也就是预提交。如果预提交失败，那么全局事务结束。
     * 成功之后 触发事务监听方法，将结果交给mq，成功提交 此时消息才可以被 消费者看到
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        String transId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        logger.info(">>>> 消息回调，执行本地事务, message={},args={},事务id={} <<<<", message, o,transId);
        try {
            System.out.println("执行业务逻辑和以及向事务日志表插入transId，执行操作");
            // 正常，返回事务状态给生产者
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            // 异常回滚
            logger.error(">>>> 消息回调，执行本地事务失败, message={},args={} <<<<", e.getMessage());
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    /**
     * 消息回查方法
     * 引发消息回查的原因最常见的有两个：
     * 1)回调操作返回UNKNWON
     * 2)TC没有接收到TM的最终全局事务确认指令即 生产者没有向broker，发送提交或回滚的指令
     *
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String transId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        logger.info("回查消息 -> transId = " + transId + "");
            // 根据事务id查询事务日志表 , 如果查询为null，那么就通知回滚，存在事务日志表的话，进行提交
            // 事务日志表，一般是在执行上面的那个本地事务的时候 ， 一块插入
//            mqTransactionLogMapper.selectByPrimaryKey(transactionId);

        return RocketMQLocalTransactionState.COMMIT; // 查到表明本地事务执行成功，提交
    }
}