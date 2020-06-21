package com.hermes.cloudmessaging.aop;

import com.hermes.cloudmessaging.entity.mongo.MessageEntity;
import com.hermes.cloudmessaging.event.NewMessageResponseEvent;
import com.hermes.cloudmessaging.exception.BaseRuntimeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Aspect
@Component
public class CloudMessagingAdvice {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Around("execution(* com.hermes.cloudmessaging.service.Messenger.sendMessageToUser(..))")
    public Object processNewCloudMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (Objects.nonNull(args)) {
            Object response = joinPoint.proceed();

            applicationEventPublisher.publishEvent(new NewMessageResponseEvent(this, MessageEntity.builder()
                    .cloudMessageResponse(response)
                    .cloudMessage(args[0])
                    .build()));
            return response;
        }

        throw new BaseRuntimeException("No args found", HttpStatus.BAD_REQUEST);
    }
}
