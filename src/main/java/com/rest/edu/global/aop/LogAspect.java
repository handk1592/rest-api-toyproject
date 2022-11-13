package com.rest.edu.global.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

//    @Around("execution(* com.rest.edu.module..controller.*Controller.*(..)) || " +
//            "execution(* com.rest.edu.module..service.*Service.*(..))")
    @Around("execution(* com.rest.edu.module..controller.*Controller.*(..))")
    public Object controllerLogging(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        long end = 0;

        Object result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        List list = Arrays.asList(pjp.getArgs());
        String jsonList = objectMapper.writeValueAsString(list);
        String controllerName = pjp.getSignature().getDeclaringType().getSimpleName();
        String methodName = pjp.getSignature().getName();

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); // request 정보를 가져온다.
        String requestUrl = request.getRequestURI();
        String httpMethod = request.getMethod();

        try {
            result = pjp.proceed();
            end = System.currentTimeMillis();
            return result;
        } finally {
            logger.info("[{}|{}|{}] [START] :: Request URL = ({}){}, Parameter = {}", uuid, controllerName, methodName, httpMethod, requestUrl, jsonList);
            logger.info("[{}|{}|{}] [END] :: Response Data = {} ({} ms)", uuid, controllerName, methodName, result, (end-start));
        }
    }


    /**
     * request 에 담긴 정보를 JSONObject 형태로 반환한다.
     * @param request
     * @return
     */
    private static JSONObject getParams(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }
        return jsonObject;
    }

    private Map getParams2(ProceedingJoinPoint proceedingJoinPoint) {
        CodeSignature codeSignature = (CodeSignature) proceedingJoinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(parameterNames[i], args[i]);
        }
        return params;
    }

    private String paramMapToString(Map<String, String[]> paraStringMap) {
        return paraStringMap.entrySet().stream()
                .map(entry -> String.format("%s : %s",
                        entry.getKey(), Arrays.toString(entry.getValue())))
                .collect(Collectors.joining(", "));
    }
}
