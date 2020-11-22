package com.hermes.cloudmessaging.service;

import com.hermes.cloudmessaging.model.dto.request.QueueRequest;
import com.hermes.cloudmessaging.server.exception.DequeueException;
import com.hermes.cloudmessaging.server.exception.EnqueueException;

public interface QueueService<T extends QueueRequest> {

    void enqueue(T data) throws EnqueueException;

    Object dequeue() throws DequeueException;
}
