package com.qadr.gateway.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***
 * @author abdqadr
 * @description: Define custom com.qadr.gateway.error attributes for handling exception
 *
 */

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorMap = new HashMap<>();
        Throwable error = getError(request);
        errorMap.put("message", error.getMessage());
        errorMap.put("path", request.path());
        errorMap.put("timestamp", new Date());
        return errorMap;
    }
}
