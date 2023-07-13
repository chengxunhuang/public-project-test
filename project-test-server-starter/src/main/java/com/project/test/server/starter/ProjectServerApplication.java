package com.project.test.server.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wh
 */
@SpringBootApplication(scanBasePackages={"com.project.*"} )
public class ProjectServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectServerApplication.class, args);
    }
}
