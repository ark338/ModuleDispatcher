package com.segway.moduledispatcher.sample;

import com.segway.moduledispatcher.CallPacker;
import com.segway.moduledispatcher.Dispatcher;

/**
 * Created by ark338 on 16/6/7.
 */
public class RobotHeaderImpl implements RobotHeader {
    Dispatcher mDispatcher;

    public RobotHeaderImpl(Dispatcher dispatcher) {
        mDispatcher = dispatcher;
        mDispatcher.registerModule(this);
    }

    @Override
    public String setPosition(int pitch, int roll) {
        // do real set position
        return new String ("" + pitch + " " + roll);
    }

    @Override
    public void onTouched(long touchId) throws Exception {
        CallPacker callPacker = new CallPacker();
        callPacker.addArg(touchId);
        mDispatcher.sendMessage(callPacker);
    }
}
