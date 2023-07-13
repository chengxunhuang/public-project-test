package com.project.test.server.controller;

import com.alibaba.fastjson.JSON;
import com.project.test.server.entity.PayOrder;
import com.project.test.server.entity.R;
import com.project.test.server.exception.validator.UpdateGroup;
import com.project.test.server.service.PayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wh
 * @Date: 2022/06/18/15:09
 * @Description: redis的相关操作
 */
@RestController
@RequestMapping("/pay")
public class RedisOrderController {

    /**
     * 由此看来 确实是 redis自动配置类中，默认注入了两个bean
     */
    private final RedisTemplate redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final PayOrderService payOrderService;

    @Autowired
    public RedisOrderController(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate, PayOrderService payOrderService) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.payOrderService = payOrderService;
    }

    /**
     * redis存放对象
     *
     * @return
     */
    @GetMapping("/getListObject")
    public R getListObject() {
        //绑定key操作, 如国 hash中的key相同的话 会覆盖， 如果key不同的话 不会。
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps("test1");
        List<PayOrder> list = payOrderService.getList();
        /**
         * 注入 RedisTemplate<Object, Object> 它的话 ,存放的时候可以直接存放对象，获取的时候获取的也是对象，直接存放对象的话 在redis中存放的是
         * key: \xAC\xED\test1
         * 使用的是hash类型
         * value: key: \xAC\xED\json value: \xAC\xED\x00\x05sr\x00\x13java.util.ArrayListx\x81\xD2\x1D\x99\xC7a\x9D\x03\x00\
         * 为什么存放对象的话，会有 \xAC\xED\ 这些东西呢？
         * 这些前缀是 没有指定序列化和反序列化导致的，默认使用的是JdkSerializationRedisSerializer进行序列化，最后是 ObjectOutputStream。
         * 所以需要重新 配置 RedisTemplate<Object, Object> 即可
         */
        operations.put("object", list);
        List<PayOrder> json = (List<PayOrder>) operations.get("object");
        return R.ok("缓存存放成功");
    }

    /**
     * redis存放字符串
     *
     * @return
     */
    @GetMapping("/getListString")
    public R getListString() {
        //绑定key操作, 如国 hash中的key相同的话 会覆盖， 如果key不同的话 不会。
        BoundHashOperations<String, Object, Object> operations = stringRedisTemplate.boundHashOps("test2");
        List<PayOrder> list = payOrderService.getList();
        String json = JSON.toJSONString(list);
        /**
         * StringRedisTemplate 因为这个实际上是 RedisTemplate<String, String> ,那么存放的value 自然是 String , 存放对象需要转化为 json。 获取的时候自然也是字符串
         * key: test2
         * 使用的是hash类型
         * value: key: json value: [{"amount": 1,"appId": "61444d9493e1f3ab1d7a66c0"
         */
        operations.put("json", json);
        operations.put("json", "123");
        operations.put("json2", "qwe");
        String getJson = (String) operations.get("json");
        return R.ok("缓存存放成功");
    }

    /**
     * 缓存 null 对象测试
     *
     * @return
     */
    @GetMapping("/getNull")
    public R getNull() {
        //绑定key操作, 如果 hash中的key相同的话 会覆盖， 如果key不同的话 不会。
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps("test2");
        if (ObjectUtils.isEmpty(operations.get("json2"))) {
            // 缓存为空 ，查询数据库 ，数据库 也为null ，将null 缓存起来，并设置过期时间
            operations.put("json2", "");
            // 这种形式 ， 会将 这个 键为: test2 整体进行设置过期时间
            operations.expire(30, TimeUnit.SECONDS);
        }
        String json2 = (String) operations.get("json2");
        return R.ok("缓存存放成功");
    }

    @GetMapping("/getById")
    public R getById(@Validated @NotNull(message = "ID不允许为null") @RequestParam Long payId) {
//        return R.ok(payOrderService.getById(payId));
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps("test1");
        List<PayOrder> json = (List<PayOrder>) operations.get("json");

        return null;
    }


    @PostMapping("/updateById")
    public R updateById(@Validated(UpdateGroup.class) @RequestBody PayOrder payId) {
        return null;
    }
}
