package com.hermes.cloudmessaging.service;

public interface Messenger<REQ, RESP> {

    RESP sendMessageToUser(REQ request);
}
