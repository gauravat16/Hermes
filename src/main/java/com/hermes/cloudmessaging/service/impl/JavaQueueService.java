package com.hermes.cloudmessaging.service.impl;

import com.hermes.cloudmessaging.dto.request.QueueRequest;
import com.hermes.cloudmessaging.exception.DequeueException;
import com.hermes.cloudmessaging.exception.EnqueueException;
import com.hermes.cloudmessaging.service.QueueMessageHandler;
import com.hermes.cloudmessaging.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JavaQueueService<T extends QueueRequest> implements QueueService<T> {

    private BlockingQueue<T> queue;
    private Map<QueueRequest.Type, QueueMessageHandler> messageHandlerMap;

    @Autowired
    private List<QueueMessageHandler> queueMessageHandlers;

    @PostConstruct
    private void setQueue() {
        queue = new LinkedBlockingDeque<>();
        if (queueMessageHandlers != null) {
            messageHandlerMap = queueMessageHandlers.stream()
                    .collect(Collectors.toMap(QueueMessageHandler::getQueueRequestType, Function.identity()));
        }
    }

    @Override
    public void enqueue(T data) throws EnqueueException {
        queue.offer(data);
    }

    @Override
    @Scheduled(cron = "*/5 * * * * *")
    public Object dequeue() throws DequeueException {
        if (queue.size() == 0) throw new DequeueException("Queue is empty", HttpStatus.NO_CONTENT, true);
        try {
            T request = queue.poll(2, TimeUnit.SECONDS);
            if (null == request || request.getType() == null || request.getRequest() == null)
                return null;

            QueueRequest.Type type = request.getType();

            Assert.isTrue(type.getClazz().isInstance(request.getRequest()), "Mismatch of req type and req obj");
            Assert.isTrue(messageHandlerMap.containsKey(type), "Handler for Request type not found");

            messageHandlerMap.get(type).handleRequest(type.getClazz().cast(request.getRequest()));
            return request.getRequest();

        } catch (InterruptedException e) {
            log.debug("Exception faced when dequeue-ing", e);
            throw new DequeueException("Timeout!", HttpStatus.REQUEST_TIMEOUT, true);
        } catch (Exception e) {
            log.debug("Exception faced when dequeue-ing", e);
            throw new DequeueException("Exception faced when dequeue-ing!", HttpStatus.INTERNAL_SERVER_ERROR, true);
        }
    }

}
