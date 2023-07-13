FROM java:8
# 挂载点
VOLUME /tmp


# 将所有应用放到一个镜像当中
ADD payment-alipay-server-starter/target/payment-alipay-server-starter-2.0.1-RELEASE-exec.jar app.jar
EXPOSE 8011
ENV TZ Asia/Shanghai
RUN ln -fs /usr/share/zoneinfo/${TZ} /etc/localtime   && echo ${TZ} > /etc/timezone
# 拷贝启动脚本
COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

ENTRYPOINT ["docker-entrypoint.sh"]
