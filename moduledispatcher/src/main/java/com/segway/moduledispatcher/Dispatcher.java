package com.segway.moduledispatcher;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Base of ModuleDispatcher
 */
public class Dispatcher implements MessageHandler.OnMessageListener {
    private static Dispatcher mInstance;
    protected Dispatcher(){}
    public static synchronized Dispatcher getInstance() {
        if (mInstance == null) {
            mInstance = new Dispatcher();
        }
        return mInstance;
    }

    Map<String, Object> mModuleMap = new HashMap<>();
    Map<Long, CallPacker> mCallMap = new HashMap<>();
    MessageHandler mMessageHandler;

    public void registerModule(Object module) {
        if (module == null) {
            throw new IllegalArgumentException("module is null");
        }
        mModuleMap.put(module.getClass().getInterfaces()[0].getName(), module);
    }

    public void setMessageHandler(MessageHandler handler) {
        mMessageHandler = handler;
        mMessageHandler.setOnMessageListener(this);
    }

    // TODO: 16/6/7 throw detailed exception type
    public Object sendMessage(CallPacker callPacker) throws Exception{
        mCallMap.put(callPacker.getId(), callPacker);
        mMessageHandler.sendMessage(callPacker.pack());
        synchronized (callPacker) {
            while (!mCallMap.get(callPacker.getId()).isResponse()) {
                callPacker.wait(1000);
            }
        }

        // replace with response call pack
        callPacker = mCallMap.get(callPacker.getId());

        List args = callPacker.getArgs();
        if (args == null || args.size() == 0) {
            return null;
        }

        CallPacker.Arg result = (CallPacker.Arg) args.get(0);
        if (Exception.class.isAssignableFrom(result.type)) {
            throw new Exception((String) result.value);
        }

        return result.value;
    }

    @Override
    public void onMessage(String message) {
        try {
            CallPacker callPacker = CallPacker.unpack(message);
            if (mCallMap.get(callPacker.getId()) != null && callPacker.isResponse()) {
                // is a call response
                Object object = mCallMap.get(callPacker.getId());

                // replace to response
                mCallMap.put(callPacker.getId(), callPacker);
                synchronized (object) {
                    object.notifyAll();
                }
            } else {
                // is a in coming call
                Object result = null;
                Exception exception = null;
                try {
                    result = callModuleMethod(callPacker.getClassName(), callPacker.getMethodName(), callPacker.getArgs());
                } catch (NoSuchMethodException e) {
                    exception = e;
                } catch (InvocationTargetException e) {
                    exception = e;
                } catch (IllegalAccessException e) {
                    exception = e;
                } catch (Exception e) {
                    exception = e;
                }

                callPacker.cleanArgs();
                callPacker.setResponse();
                if (exception != null) {
                    callPacker.addArg(exception);
                } else if (result != null) {
                    callPacker.addArg(result);
                }
                mMessageHandler.sendMessage(callPacker.pack());
            }
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
    }

    public Object callModuleMethod(String moduleName, String methodName,
                 List<CallPacker.Arg> args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (moduleName == null) {
            throw new IllegalArgumentException("module name is null");
        }

        if (methodName == null) {
            throw new IllegalArgumentException("method name is null");
        }

        Object module = mModuleMap.get(moduleName);
        Method method = null;

        if (module == null) {
            throw new IllegalArgumentException("can't find module " + moduleName);
        }


        if (args == null || args.size() == 0) {
            method = module.getClass().getMethod(methodName);
        } else {
            Method[] methods = module.getClass().getMethods();
methodLoop:     for (int i = 0; i < methods.length; i++) {
                Method m = methods[i];
                if (m.getName().equals(methodName)) {
                    Class<?>[] params = m.getParameterTypes();
                    if (params.length == args.size()) {
                        for (int j = 0; j < params.length; j++) {
                            if (params[j] != args.get(j).type) {
                                continue methodLoop;
                            }
                        }
                        method = m;
                        break methodLoop;
                    }
                }
            }
        }

        if (method == null) {
            throw new NoSuchMethodException("method " + methodName + " not found in module " + moduleName);
        }

        return invokeMethod(module, method, args);

    }

    private static Object invokeMethod(Object module, Method method,
                        List<CallPacker.Arg> args)
                        throws InvocationTargetException, IllegalAccessException {
        if (args == null) {
            return method.invoke(method);
        }

        switch (args.size()) {
            case 0:
                return method.invoke(module);
            case 1:
                return method.invoke(module, args.get(0).value);
            case 2:
                return method.invoke(module, args.get(0).value, args.get(1).value);
            case 3:
                return method.invoke(module, args.get(0).value, args.get(1).value,
                        args.get(2).value);
            case 4:
                return method.invoke(module, args.get(0).value, args.get(1).value,
                        args.get(2).value, args.get(3).value);
            case 5:
                return method.invoke(module, args.get(0).value, args.get(1).value,
                        args.get(2).value, args.get(3).value, args.get(4).value);
            case 6:
                return method.invoke(module, args.get(0).value, args.get(1).value,
                        args.get(2).value, args.get(3).value, args.get(4).value,
                        args.get(5).value);
            case 7:
                return method.invoke(module, args.get(0).value, args.get(1).value,
                        args.get(2).value, args.get(3).value, args.get(4).value,
                        args.get(5).value, args.get(6).value);
            case 8:
                return method.invoke(module, args.get(0).value, args.get(1).value,
                        args.get(2).value, args.get(3).value, args.get(4).value,
                        args.get(5).value, args.get(6).value, args.get(7).value);
            case 9:
                return method.invoke(module, args.get(0).value, args.get(1).value,
                        args.get(2).value, args.get(3).value, args.get(4).value,
                        args.get(5).value, args.get(6).value, args.get(7).value,
                        args.get(8).value);
            case 10:
                return method.invoke(module, args.get(0).value, args.get(1).value,
                        args.get(2).value, args.get(3).value, args.get(4).value,
                        args.get(5).value, args.get(6).value, args.get(7).value,
                        args.get(8).value, args.get(9).value);
            default:
                throw new IllegalAccessException("target method has too many arguments...");
        }
    }

}
