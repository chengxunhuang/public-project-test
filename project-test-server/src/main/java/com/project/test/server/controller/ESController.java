package com.project.test.server.controller;

import com.project.test.server.entity.PayOrderVO;
import com.project.test.server.service.ESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: wh
 * @Date: 2022/07/30/11:28
 * @Description:
 */
@RestController
@RequestMapping("/es")
public class ESController {

    private final ESService esService;

    @Autowired
    public ESController(ESService esService) {
        this.esService = esService;
    }

    /**
     * {
     *     "ifCode":"wxpay",
     *     "pageNum":0,
     *     "pageSize":2,
     *     "esSortValues":["zVlqXIIBIU3WQfQ3RBbo"]
     * }
     * @param payOrderVO
     */
    @PostMapping("/getEs")
    public void getEs(@RequestBody PayOrderVO payOrderVO){
        esService.search(payOrderVO);
    }

    @GetMapping("/delEs")
    public void delEs(){
        esService.del();
    }

    @PostMapping ("/ceshi")
    public String getEs(){
        return "成功";
    }
}
