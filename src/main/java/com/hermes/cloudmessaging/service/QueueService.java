package com.hermes.cloudmessaging.service;

import com.hermes.cloudmessaging.dto.request.QueueRequest;
import com.hermes.cloudmessaging.exception.DequeueException;
import com.hermes.cloudmessaging.exception.EnqueueException;

public interface QueueService<T extends QueueRequest> {

    void enqueue(T data) throws EnqueueException;

    Object dequeue() throws DequeueException;
}
