package com.project.test.server.entity;

import lombok.Data;

/**
 * @Author: wh
 * @Date: 2022/05/23/10:46
 * @Description:
 */
@Data
public class RouteMessage {
    /**
     * 消息唯一标识
     */
    private String code;
    private String name;
    private String tag;
}
