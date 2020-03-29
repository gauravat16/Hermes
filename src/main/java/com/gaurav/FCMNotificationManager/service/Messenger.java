package com.gaurav.FCMNotificationManager.service;

public interface Messenger<REQ, RESP> {

    RESP sendMessageToUser(REQ request);
}
