package com.hermes.cloudmessaging.service;

import com.hermes.cloudmessaging.exception.DequeueException;
import com.hermes.cloudmessaging.exception.EnqueueException;

public interface QueueService<T> {

    void enqueue(T data) throws EnqueueException;

    T dequeue() throws DequeueException;
}
