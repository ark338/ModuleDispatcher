package com.segway.moduledispatcher;


import com.segway.moduledispatcher.sample.RobotHeaderImpl;
import com.segway.moduledispatcher.sample.RobotHeader;
import com.segway.moduledispatcher.sample.RobotHeaderProxy;
import com.segway.moduledispatcher.sample.SimpleMessageHandler;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by ark338 on 16/6/7.
 */
public class SampleTest {
    Dispatcher phoneDispatcher;
    Dispatcher robotDispatcher;
    RobotHeader phoneProxy;
    RobotHeader robotImpl;
    SimpleMessageHandler phoneMina;
    SimpleMessageHandler robotMina;

    @Before
    public void setup() {
        phoneMina = new SimpleMessageHandler();
        robotMina = new SimpleMessageHandler();
        phoneMina.setOtherSide(robotMina);
        robotMina.setOtherSide(phoneMina);

        phoneDispatcher = new Dispatcher();
        phoneProxy = new RobotHeaderProxy(phoneDispatcher);
        phoneDispatcher.setMessageHandler(phoneMina);

        robotDispatcher = new Dispatcher();
        robotImpl = new RobotHeaderImpl(robotDispatcher);
        robotDispatcher.setMessageHandler(robotMina);
    }

    @Test
    public void testSetPosition() throws Exception {
        String ret = phoneProxy.setPosition(1, 2);
        Assert.assertEquals("1 2", ret);
    }

    @Test
    public void testOnTouch() throws Exception{
        robotImpl.onTouched(1234567890);
    }
}
