package com.project.test.server.advice;

import cn.hutool.log.Log;
import com.project.test.server.entity.R;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author olaf
 * @version V1.0
 * @ClassName ResponseAdvice
 * @Description
 * @date 2022/3/21 11:42
 */
@ConditionalOnProperty(
        prefix = "project.separate",
        value = {"enable"},
        havingValue = "true",
        matchIfMissing = true
)
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {

    Log log =Log.get(ResponseAdvice.class);

    private static final List<String> IGNORE_URL = Arrays.asList("swagger-resources", "api-docs", "actuator",
            "health", "metrics", "prometheus", "swagger-json");

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletRequest httpRequest = ((ServletServerHttpRequest)request).getServletRequest();
        String url = httpRequest.getRequestURL().toString();
        boolean ignore = IGNORE_URL.stream().anyMatch((item) -> {
            return url.contains(item);
        });
        if (ignore) {
            return body;
        } else {
            R success;
            if (body == null) {
                success = R.ok();
                return success;
            } else if (body instanceof R) {
                success = (R) body;
                // 是否把返回值包装成R
                if (success.isDecorate()) {
                    return body;
                } else {
                    return success.getData();
                }
            } else {
                if (body instanceof LinkedHashMap) {
                    LinkedHashMap map = (LinkedHashMap) body;
                    Integer sucessStatus = new Integer(HttpStatus.OK.value());
                    if (map.containsKey("status") && !sucessStatus.equals(map.get("status"))) {
                        R<LinkedHashMap> error = R.fail(map);
                        return error;
                    }
                }

                success = R.ok(body);
                return success;
            }
        }
    }
}
