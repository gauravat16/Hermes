package com.hermes.cloudmessaging.service.impl;

import com.hermes.cloudmessaging.exception.DequeueException;
import com.hermes.cloudmessaging.exception.EnqueueException;
import com.hermes.cloudmessaging.service.QueueService;
import org.springframework.http.HttpStatus;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class JavaQueueService<T> implements QueueService<T> {

    private BlockingQueue<T> queue;

    @PostConstruct
    private void setQueue() {
        queue = new LinkedBlockingDeque<>();
    }

    @Override
    public void enqueue(T data) throws EnqueueException {
        queue.offer(data);
    }

    @Override
    public T dequeue() throws DequeueException {
        try {
            T cloudMessageRequest = queue.poll(2, TimeUnit.SECONDS);
            if (null == cloudMessageRequest) throw new DequeueException("Data not found!", HttpStatus.NO_CONTENT);

            return cloudMessageRequest;
        } catch (InterruptedException e) {
            throw new DequeueException("Timeout!", HttpStatus.REQUEST_TIMEOUT);
        }
    }

}
