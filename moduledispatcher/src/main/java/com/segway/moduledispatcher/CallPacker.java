package com.segway.moduledispatcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CallPacker implements Serializable{
    static long sBaseId = 0;

    long mId;
    String mClassName;
    String mMethodName;
    boolean isResponse;
    List<Arg> mArgs;

    public CallPacker() throws ClassNotFoundException {
        mId = createId();
        StackTraceElement[] elements =Thread.currentThread().getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].getClassName().equals(CallPacker.class.getName())) {
                // we assert here this Constructor is to at the top of current stack
                // we also assert here module interface is the last interface implemented by module or proxy
                mClassName = Class.forName(elements[i+1].getClassName()).getInterfaces()[0].getName();
                break;
            }
        }
        if (mClassName == null) {
            throw new ClassNotFoundException("can not find upper class");
        }
        mMethodName = elements[2].getMethodName();
    }

    public CallPacker(String className, String methodName, long id) {
        mClassName = className;
        mMethodName = methodName;
        mId = id;
    }

    public CallPacker(String className, String methodName) {
        mClassName = className;
        mMethodName = methodName;
        mId = createId();
    }

    public void setResponse() {
        isResponse = true;
    }

    public boolean isResponse() {
        return isResponse;
    }

    public long getId() {
        return mId;
    }

    public String getClassName() {
        return mClassName;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public void addArg(int i) {
        addArg(int.class, i);
    }

    public  void addArg(byte b) {
        addArg(byte.class, b);
    }

    public void addArg(char c) {
        addArg(char.class, c);
    }

    public void addArg(short s) {
        addArg(short.class, s);
    }

    public void addArg(long l) {
        addArg(long.class, l);
    }

    public void addArg(float f) {
        addArg(float.class, f);
    }

    public void addArg(double d) {
        addArg(double.class, d);
    }

    public void addArg(String s) {
        addArg(String.class, s);
    }

    public void addArg(Object obj) {
        addArg(Object.class, obj);
    }

    public void addArg(Exception e) {
        addArg(Exception.class, e);
    }

    public List<Arg> getArgs() {
        return mArgs;
    }

    public void cleanArgs() {
        mArgs = null;
    }

    public String pack() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);

        return byteArrayOutputStream.toString("ISO-8859-1");
    }

    public static CallPacker unpack(String packed) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packed.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        return (CallPacker) objectInputStream.readObject();
    }

    private void addArg(Class type, Object value) {
        if (mArgs == null) {
            mArgs = new ArrayList<>();
        }
        mArgs.add(new Arg(type, value));
    }

    private static synchronized long createId() {
        return (System.currentTimeMillis() << 10) | (sBaseId++ & 0x3ff);
    }

    public class Arg implements Serializable{
        public Class<?> type;
        public Object value;
        public Arg(Class<?> type, Object value) {
            this.type = type;
            this.value = value;
        }
    }
}
