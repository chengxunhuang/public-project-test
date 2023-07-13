package com.project.test.server.service;

import com.project.test.server.entity.PayOrderVO;

/**
 * @Author: wh
 * @Date: 2022/07/30/15:40
 * @Description:
 */
public interface ESService {

    void search(PayOrderVO payOrderVO);

    void del();
}
