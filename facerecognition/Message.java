package com.ranjesh.facerecognition;
public class Message {
    private String text;
    private boolean isBot;
    public Message(String text, boolean isBot) {
        this.text = text;
        this.isBot = isBot;
    }
    public String getText() {
        return text;
    }
    public boolean isBot() {
        return isBot;
    }
}
