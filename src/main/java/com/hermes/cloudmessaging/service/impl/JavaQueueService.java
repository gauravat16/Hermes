package com.hermes.cloudmessaging.service.impl;

import com.hermes.cloudmessaging.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.exception.DequeueException;
import com.hermes.cloudmessaging.exception.EnqueueException;
import com.hermes.cloudmessaging.service.QueueService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@Service(value = "java-cloudMessageRequest")
public class JavaQueueService implements QueueService<CloudMessageRequest> {

    private BlockingQueue<CloudMessageRequest> queue;

    @PostConstruct
    private void setQueue() {
        queue = new LinkedBlockingDeque<>();
    }

    @Override
    public void enqueue(CloudMessageRequest data) throws EnqueueException {
        queue.offer(data);
    }

    @Override
    public CloudMessageRequest dequeue() throws DequeueException {
        try {
            CloudMessageRequest cloudMessageRequest = queue.poll(2, TimeUnit.SECONDS);
            if (null == cloudMessageRequest) throw new DequeueException("Data not found!", HttpStatus.NO_CONTENT);

            return cloudMessageRequest;
        } catch (InterruptedException e) {
            throw new DequeueException("Timeout!", HttpStatus.REQUEST_TIMEOUT);
        }
    }


}
