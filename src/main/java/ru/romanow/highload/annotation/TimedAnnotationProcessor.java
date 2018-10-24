package ru.romanow.highload.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static java.lang.System.currentTimeMillis;

/**
 * Created by romanow on 21.10.16
 */
@Aspect
@Component
public class TimedAnnotationProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TimedAnnotationProcessor.class);

    @Around("@annotation(ru.romanow.highload.annotation.Timed)")
    public Object timed(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = currentTimeMillis();
        long duration = end - start;
        logger.info("Method [{}] execution time {} mills", getMethodName(joinPoint), duration);

        return result;
    }

    private String getMethodName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Timed timed = method.getAnnotation(Timed.class);
        return timed.value();
    }
}
