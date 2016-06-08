package com.segway.moduledispatcher;

/**
 *
 */
public interface MessageHandler {
    void sendMessage(String message);
    void setOnMessageListener(OnMessageListener listener);

    interface OnMessageListener {
        void onMessage(String message);
    }
}
