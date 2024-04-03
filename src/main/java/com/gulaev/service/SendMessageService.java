package com.gulaev.service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.io.IOException;

public class SendMessageService {

  public void sendMessage(String message) {
    Slack slack = Slack.getInstance();
    String tokenMock = System.getenv("SLACK_TOKEN");
    MethodsClient methods = slack.methods(tokenMock);
    ChatPostMessageRequest request = ChatPostMessageRequest.builder()
        .channel("C06S5KF4YMU")
        .text(message)
        .build();

    ChatPostMessageResponse response = null;
    try {
      response = methods.chatPostMessage(request);
    } catch (IOException e) {
      throw new RuntimeException(e);

    } catch (SlackApiException e) {
      System.out.printf(response.getError());
      throw new RuntimeException(e);
    }
    System.out.println(request.toString());
    System.out.println(response.toString());
  }

}
