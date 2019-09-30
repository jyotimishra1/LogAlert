package com.cs.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around( "execution(* com.cs.dao.*.*(..))" )
    public Object logMethodTime(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        final long start = System.currentTimeMillis();
        Object obj;
        try {
            logger.debug("Starting...! Method Name - " + proceedingJoinPoint.getSignature().getName());
            obj = proceedingJoinPoint.proceed();
        } finally {
            logger.debug("Exiting...! Method Name - " + proceedingJoinPoint.getSignature().getName() + " Execution Time in Milliseconds:> " + String.valueOf(System.currentTimeMillis() - start));
        }
        return obj;
    }
}
