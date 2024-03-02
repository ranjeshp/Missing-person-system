package com.ranjesh.facerecognition;

public class FeedbackModel {
    private String userId;
    private String feedbackText;

    public FeedbackModel() {
        // Default constructor required for Firebase
    }
    public FeedbackModel(String userId, String feedbackText) {
        this.userId = userId;
        this.feedbackText = feedbackText;
    }
    public String getUserId() {
        return userId;
    }
    public String getFeedbackText() {
        return feedbackText;
    }
}
