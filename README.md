# 整合的部分项目

1.rocketMQ
2.redis
3.easyexcel导入导出，多线程分页导出在项目：客户加盟平台的，供应商导出里面
4.如果说启动项目的时候报出，
Parameter 0 of constructor in com.project.test.server.config.mq.RocketMQSender required a bean of type 'org.apache.rocketmq.spring.core.RocketMQTemplate' that could not be found.
也就是bean找不到。原因呢不光是 spring没有扫描到，还有另一个重要的就是 如果使用了nacos配置中心的话，由于nacos配置文件名写错了，找不到这个配置，比如rocketmq的话
找不到nameserver 自然也就没有所谓的RocketMQTemplate这个bean了
5.项目在启动的时候，由于使用了 logstash的收集功能，所以事先要将 logstash、es先启动起来才行；logstash、es 容器迁移借鉴 有道云 -> 容器化 -> docker实战-中间件安装的 4.新的机器如何启动呢？
注意 logstash、es、kibana 都是7.7.1版本

