package com.techelevator.model;

public class Request {

    private long senderId;
    private long recipientId;
    private String message;
    private String senderName;

    public long getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public void setSenderName(String senderName){
        this.senderName = senderName;
    }

    public String getSenderName(){
        return senderName;
    }

}