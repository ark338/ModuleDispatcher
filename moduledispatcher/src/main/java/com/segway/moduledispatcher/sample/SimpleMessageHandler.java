package com.segway.moduledispatcher.sample;

import com.segway.moduledispatcher.MessageHandler;

/**
 * Created by ark338 on 16/6/7.
 */
public class SimpleMessageHandler implements MessageHandler{
    SimpleMessageHandler mOtherSide;
    OnMessageListener mListener;

    @Override
    public void sendMessage(String message) {
        if (mOtherSide != null && mOtherSide.mListener != null) {
            mOtherSide.mListener.onMessage(message);
        }
    }

    @Override
    public void setOnMessageListener(OnMessageListener listener) {
        mListener = listener;
    }

    public void setOtherSide(SimpleMessageHandler otherSide) {
        mOtherSide = otherSide;
    }
}
