package com.ofirkp.sockettest;

public interface MessageHandler {
    public void onReceive(Connection connection, String message);
}