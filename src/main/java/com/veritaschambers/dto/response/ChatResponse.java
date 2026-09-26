package com.veritaschambers.dto.response;

public class ChatResponse {

    private String reply;
    private boolean requiresDisclaimer;

    public ChatResponse() {
    }

    public ChatResponse(String reply, boolean requiresDisclaimer) {
        this.reply = reply;
        this.requiresDisclaimer = requiresDisclaimer;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean isRequiresDisclaimer() {
        return requiresDisclaimer;
    }

    public void setRequiresDisclaimer(boolean requiresDisclaimer) {
        this.requiresDisclaimer = requiresDisclaimer;
    }
}
