package com.hermes.cloudmessaging.core.impl;

import com.hermes.cloudmessaging.model.dto.request.QueueRequest;
import com.hermes.cloudmessaging.server.exception.DequeueException;
import com.hermes.cloudmessaging.server.exception.EnqueueException;
import com.hermes.cloudmessaging.service.QueueMessageHandler;
import com.hermes.cloudmessaging.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public abstract class AbstractJavaQueueService<T extends QueueRequest> implements QueueService<T> {

    private BlockingQueue<T> queue;
    private List<QueueMessageHandler> messageHandlers;

    private final List<QueueMessageHandler> queueMessageHandlers;

    public AbstractJavaQueueService(List<QueueMessageHandler> queueMessageHandlers) {
        this.queueMessageHandlers = queueMessageHandlers;
        setQueue();
    }

    private void setQueue() {
        queue = new LinkedBlockingDeque<>();
        if (queueMessageHandlers != null) {
            messageHandlers = queueMessageHandlers.stream().filter(handler -> handler.getQueueRequestType().equals(getQueryType()))
                    .collect(Collectors.toList());
        }
        if (null == messageHandlers || messageHandlers.isEmpty()){
            throw new IllegalStateException("No Handlers defined for this queue!!");
        }
    }

    @Override
    public void enqueue(T data) throws EnqueueException {
        queue.offer(data);
    }

    @Override
    public Object dequeue() throws DequeueException {
        if (queue.size() == 0) return null;
        try {
            T request = queue.poll(2, TimeUnit.SECONDS);
            if (null == request || request.getType() == null || request.getRequest() == null)
                return null;

            QueueRequest.Type type = request.getType();

            Assert.isTrue(type.equals(getQueryType()), "Request type of queued object is different");
            Assert.isTrue(type.getClazz().isInstance(request.getRequest()), "Mismatch of req type and req obj");

            messageHandlers.forEach(handler -> handler.handleRequest(type.getClazz().cast(request.getRequest())));

            return request.getRequest();

        } catch (InterruptedException e) {
            log.debug("Exception faced when dequeue-ing", e);
            throw new DequeueException("Timeout!", HttpStatus.REQUEST_TIMEOUT, true);
        } catch (Exception e) {
            log.debug("Exception faced when dequeue-ing", e);
            throw new DequeueException("Exception faced when dequeue-ing!", HttpStatus.INTERNAL_SERVER_ERROR, true);
        }
    }

    public abstract QueueRequest.Type getQueryType();

}
