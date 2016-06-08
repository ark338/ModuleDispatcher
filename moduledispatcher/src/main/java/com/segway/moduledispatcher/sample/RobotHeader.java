package com.segway.moduledispatcher.sample;

/**
 * Created by ark338 on 16/6/7.
 */
public interface RobotHeader {
    String setPosition(int pitch, int roll) throws Exception;

    void onTouched(long touchId) throws Exception;
}
