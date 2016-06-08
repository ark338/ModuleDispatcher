package com.segway.moduledispatcher.sample;

import com.segway.moduledispatcher.CallPacker;
import com.segway.moduledispatcher.Dispatcher;

import java.io.Serializable;

/**
 * Created by ark338 on 16/6/7.
 */
public class RobotHeaderProxy implements RobotHeader {
    Dispatcher mDispatcher;

    public RobotHeaderProxy(Dispatcher dispatcher) {
        mDispatcher = dispatcher;
        dispatcher.registerModule(this);
    }

    @Override
    public String setPosition(int pitch, int roll) throws Exception {
        CallPacker callPacker = new CallPacker();
        callPacker.addArg(pitch);
        callPacker.addArg(roll);
        return (String) mDispatcher.sendMessage(callPacker);
    }

    @Override
    public void onTouched(long touchId) {
        // process on touch event
        System.out.println("touch id is " + touchId);
    }
}
