package com.springbackend.spring.email;

public interface FeedbackSender {
    void sendFeedback(String from, String name, String feedback);
}
