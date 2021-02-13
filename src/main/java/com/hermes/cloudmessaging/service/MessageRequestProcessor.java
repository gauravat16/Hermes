package com.hermes.cloudmessaging.service;

import com.hermes.cloudmessaging.model.dto.request.SendMsgRequest;
import com.hermes.cloudmessaging.model.dto.response.FCMResponse;

import java.util.List;

/**
 * The implementation should allow us to process a new {@link SendMsgRequest}.
 *
 * @author gaurav
 */
public interface MessageRequestProcessor {

    List<FCMResponse> process(SendMsgRequest sendMsgRequest);
}
