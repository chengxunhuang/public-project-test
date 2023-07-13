package com.project.test.server.controller;

import com.project.test.server.config.mq.RocketMQSender;
import com.project.test.server.entity.RouteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wh
 * @Date: 2022/07/15/16:38
 * @Description:
 */
@RestController
@RequestMapping("/mq")
public class RocketMqController {
    private final RocketMQSender rocketMQSender;

    @Autowired
    public RocketMqController(RocketMQSender rocketMQSender) {
        this.rocketMQSender = rocketMQSender;
    }

    @GetMapping("/sendTransaction")
    public void sendTransaction() {
        RouteMessage routeMessage = new RouteMessage();
        routeMessage.setCode("1233321123333");
        routeMessage.setName("发送事务信息-你好啊");
        rocketMQSender.sendTransaction(routeMessage);
    }
}
