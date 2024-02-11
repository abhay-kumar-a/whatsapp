package com.abhaykumar.whatsapp.module;

public class MessageModel {
    String uTd;
    String message ;
    Long timestamp;

    public MessageModel(String uTd, String message, Long timestamp) {
        this.uTd = uTd;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel(String uTd, String message) {
        this.uTd = uTd;
        this.message = message;
    }
    public MessageModel()
    {

    }

    public String getuTd() {
        return uTd;
    }

    public void setuTd(String uTd) {
        this.uTd = uTd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
