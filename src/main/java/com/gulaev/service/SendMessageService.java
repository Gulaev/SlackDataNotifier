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
//    String token = System.getenv("SLACK_");
    MethodsClient methods = slack.methods("xoxb-6838897828131-6864457349584-cGyhieWiX4TVfYYbH1tYgOtR");
    ChatPostMessageRequest request = ChatPostMessageRequest.builder()
        .channel("#random")
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
