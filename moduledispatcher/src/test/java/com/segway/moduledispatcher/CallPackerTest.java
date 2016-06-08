package com.segway.moduledispatcher;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ark338 on 16/6/7.
 */
public class CallPackerTest implements Serializable{

    @Test
    public void testConstructor() throws Exception {
        CallPacker callPacker = new CallPacker();
        Assert.assertEquals("java.io.Serializable", callPacker.getClassName());
        Assert.assertEquals("testConstructor", callPacker.getMethodName());
    }

    @Test
    public void testToJsonString() throws Exception {
        CallPacker callPacker = new CallPacker("testClass", "testMethod");
        callPacker.addArg(123);
        callPacker.addArg('c');
        callPacker.addArg("string");
        System.out.println(callPacker.pack());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("v", callPacker.pack());
        System.out.println(jsonObject.toString());

        callPacker = new CallPacker("testClass", "testMethod");
        System.out.println(callPacker.pack());
        jsonObject = new JSONObject();
        jsonObject.put("v", callPacker.pack());
        System.out.println(jsonObject.toString());

        callPacker.cleanArgs();
        callPacker.setResponse();
        callPacker.addArg(new IllegalArgumentException("iat"));
        System.out.println(callPacker.pack());
        jsonObject = new JSONObject();
        jsonObject.put("v", callPacker.pack());
        System.out.println(jsonObject.toString());
    }

    @Test
    public void testFromJsonString() throws Exception {
        String testJsonString = "{\"v\":\"¬í\\u0000\\u0005sr\\u0000&com.segway.moduledispatcher.CallPacker\\u0018æ\\u0017¯Û0õ+\\u0002\\u0000\\u0005Z\\u0000\\nisResponseJ\\u0000\\u0003mIdL\\u0000\\u0005mArgst\\u0000\\u0010Ljava/util/List;L\\u0000\\nmClassNamet\\u0000\\u0012Ljava/lang/String;L\\u0000\\u000bmMethodNameq\\u0000~\\u0000\\u0002xp\\u0000\\u0000\\u0005T·a\\u008a\\u0084\\u0000sr\\u0000\\u0013java.util.ArrayListx\\u0081Ò\\u001d\\u0099Ça\\u009d\\u0003\\u0000\\u0001I\\u0000\\u0004sizexp\\u0000\\u0000\\u0000\\u0003w\\u0004\\u0000\\u0000\\u0000\\u0003sr\\u0000*com.segway.moduledispatcher.CallPacker$ArgÃÿò©*\\u0017ïþ\\u0002\\u0000\\u0003L\\u0000\\u0006this$0t\\u0000(Lcom/segway/moduledispatcher/CallPacker;L\\u0000\\u0004typet\\u0000\\u0011Ljava/lang/Class;L\\u0000\\u0005valuet\\u0000\\u0012Ljava/lang/Object;xpq\\u0000~\\u0000\\u0003vr\\u0000\\u0003int\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000xpsr\\u0000\\u0011java.lang.Integer\\u0012â ¤÷\\u0081\\u00878\\u0002\\u0000\\u0001I\\u0000\\u0005valuexr\\u0000\\u0010java.lang.Number\\u0086¬\\u0095\\u001d\\u000b\\u0094à\\u008b\\u0002\\u0000\\u0000xp\\u0000\\u0000\\u0000{sq\\u0000~\\u0000\\u0006q\\u0000~\\u0000\\u0003vr\\u0000\\u0004char\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000xpsr\\u0000\\u0013java.lang.Character4\\u008bGÙk\\u001a&x\\u0002\\u0000\\u0001C\\u0000\\u0005valuexp\\u0000csq\\u0000~\\u0000\\u0006q\\u0000~\\u0000\\u0003vr\\u0000\\u0010java.lang.String ð¤8z;³B\\u0002\\u0000\\u0000xpt\\u0000\\u0006stringxt\\u0000\\ttestClasst\\u0000\\ntestMethod\"}";
        CallPacker callPacker = CallPacker.unpack(new JSONObject(testJsonString).getString("v"));
        Assert.assertEquals("testClass", callPacker.getClassName());
        Assert.assertEquals("testMethod", callPacker.getMethodName());
        System.out.println(callPacker.getId());
        List<CallPacker.Arg> args = callPacker.getArgs();
        Assert.assertEquals(3, args.size());
        Assert.assertEquals(123, (int) args.get(0).value);
        Assert.assertEquals('c', (char) args.get(1).value);
        Assert.assertEquals("string", (String) args.get(2).value);

        testJsonString = "{\"v\":\"¬í\\u0000\\u0005sr\\u0000&com.segway.moduledispatcher.CallPacker\\u0018æ\\u0017¯Û0õ+\\u0002\\u0000\\u0005Z\\u0000\\nisResponseJ\\u0000\\u0003mIdL\\u0000\\u0005mArgst\\u0000\\u0010Ljava/util/List;L\\u0000\\nmClassNamet\\u0000\\u0012Ljava/lang/String;L\\u0000\\u000bmMethodNameq\\u0000~\\u0000\\u0002xp\\u0000\\u0000\\u0005T·a\\u008b8\\u0001pt\\u0000\\ttestClasst\\u0000\\ntestMethod\"}";
        callPacker = CallPacker.unpack(new JSONObject(testJsonString).getString("v"));
        Assert.assertEquals("testClass", callPacker.getClassName());
        Assert.assertEquals("testMethod", callPacker.getMethodName());
        System.out.println(callPacker.getId());

        testJsonString = "{\"v\":\"¬í\\u0000\\u0005sr\\u0000&com.segway.moduledispatcher.CallPacker\\u0018æ\\u0017¯Û0õ+\\u0002\\u0000\\u0005Z\\u0000\\nisResponseJ\\u0000\\u0003mIdL\\u0000\\u0005mArgst\\u0000\\u0010Ljava/util/List;L\\u0000\\nmClassNamet\\u0000\\u0012Ljava/lang/String;L\\u0000\\u000bmMethodNameq\\u0000~\\u0000\\u0002xp\\u0001\\u0000\\u0005T·a\\u008b8\\u0001sr\\u0000\\u0013java.util.ArrayListx\\u0081Ò\\u001d\\u0099Ça\\u009d\\u0003\\u0000\\u0001I\\u0000\\u0004sizexp\\u0000\\u0000\\u0000\\u0001w\\u0004\\u0000\\u0000\\u0000\\u0001sr\\u0000*com.segway.moduledispatcher.CallPacker$ArgÃÿò©*\\u0017ïþ\\u0002\\u0000\\u0003L\\u0000\\u0006this$0t\\u0000(Lcom/segway/moduledispatcher/CallPacker;L\\u0000\\u0004typet\\u0000\\u0011Ljava/lang/Class;L\\u0000\\u0005valuet\\u0000\\u0012Ljava/lang/Object;xpq\\u0000~\\u0000\\u0003vr\\u0000\\u0013java.lang.ExceptionÐý\\u001f>\\u001a;\\u001cÄ\\u0002\\u0000\\u0000xr\\u0000\\u0013java.lang.ThrowableÕÆ5'9w¸Ë\\u0003\\u0000\\u0004L\\u0000\\u0005causet\\u0000\\u0015Ljava/lang/Throwable;L\\u0000\\rdetailMessageq\\u0000~\\u0000\\u0002[\\u0000\\nstackTracet\\u0000\\u001e[Ljava/lang/StackTraceElement;L\\u0000\\u0014suppressedExceptionsq\\u0000~\\u0000\\u0001xpsr\\u0000\\\"java.lang.IllegalArgumentExceptionµ\\u0089sÓ}f\\u008f¼\\u0002\\u0000\\u0000xr\\u0000\\u001ajava.lang.RuntimeException\\u009e_\\u0006G\\n4\\u0083å\\u0002\\u0000\\u0000xq\\u0000~\\u0000\\u000bq\\u0000~\\u0000\\u0012t\\u0000\\u0003iatur\\u0000\\u001e[Ljava.lang.StackTraceElement;\\u0002F*<<ý\\\"9\\u0002\\u0000\\u0000xp\\u0000\\u0000\\u0000\\u001bsr\\u0000\\u001bjava.lang.StackTraceElementa\\tÅ\\u009a&6Ý\\u0085\\u0002\\u0000\\u0004I\\u0000\\nlineNumberL\\u0000\\u000edeclaringClassq\\u0000~\\u0000\\u0002L\\u0000\\bfileNameq\\u0000~\\u0000\\u0002L\\u0000\\nmethodNameq\\u0000~\\u0000\\u0002xp\\u0000\\u0000\\u0000*t\\u0000*com.segway.moduledispatcher.CallPackerTestt\\u0000\\u0013CallPackerTest.javat\\u0000\\u0010testToJsonStringsq\\u0000~\\u0000\\u0016ÿÿÿþt\\u0000$sun.reflect.NativeMethodAccessorImplt\\u0000\\u001dNativeMethodAccessorImpl.javat\\u0000\\u0007invoke0sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u00009q\\u0000~\\u0000\\u001cq\\u0000~\\u0000\\u001dt\\u0000\\u0006invokesq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000+t\\u0000(sun.reflect.DelegatingMethodAccessorImplt\\u0000!DelegatingMethodAccessorImpl.javaq\\u0000~\\u0000 sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0002^t\\u0000\\u0018java.lang.reflect.Methodt\\u0000\\u000bMethod.javaq\\u0000~\\u0000 sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u00002t\\u0000)org.junit.runners.model.FrameworkMethod$1t\\u0000\\u0014FrameworkMethod.javat\\u0000\\u0011runReflectiveCallsq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000\\ft\\u00003org.junit.internal.runners.model.ReflectiveCallablet\\u0000\\u0017ReflectiveCallable.javat\\u0000\\u0003runsq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000/t\\u0000'org.junit.runners.model.FrameworkMethodq\\u0000~\\u0000)t\\u0000\\u0011invokeExplosivelysq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000\\u0011t\\u00002org.junit.internal.runners.statements.InvokeMethodt\\u0000\\u0011InvokeMethod.javat\\u0000\\bevaluatesq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0001Et\\u0000\\u001eorg.junit.runners.ParentRunnert\\u0000\\u0011ParentRunner.javat\\u0000\\u0007runLeafsq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000Nt\\u0000(org.junit.runners.BlockJUnit4ClassRunnert\\u0000\\u001bBlockJUnit4ClassRunner.javat\\u0000\\brunChildsq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u00009q\\u0000~\\u0000;q\\u0000~\\u0000<q\\u0000~\\u0000=sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0001\\\"t\\u0000 org.junit.runners.ParentRunner$3q\\u0000~\\u00008q\\u0000~\\u0000.sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000Gt\\u0000 org.junit.runners.ParentRunner$1q\\u0000~\\u00008t\\u0000\\bschedulesq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0001 q\\u0000~\\u00007q\\u0000~\\u00008t\\u0000\\u000brunChildrensq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000:q\\u0000~\\u00007q\\u0000~\\u00008t\\u0000\\naccess$000sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0001\\ft\\u0000 org.junit.runners.ParentRunner$2q\\u0000~\\u00008q\\u0000~\\u00005sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0001kq\\u0000~\\u00007q\\u0000~\\u00008q\\u0000~\\u0000.sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000\\u0089t\\u0000\\u001aorg.junit.runner.JUnitCoret\\u0000\\u000eJUnitCore.javaq\\u0000~\\u0000.sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000Et\\u0000(com.intellij.junit4.JUnit4IdeaTestRunnert\\u0000\\u0019JUnit4IdeaTestRunner.javat\\u0000\\u0013startRunnerWithArgssq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000êt\\u0000,com.intellij.rt.execution.junit.JUnitStartert\\u0000\\u0011JUnitStarter.javat\\u0000\\u0016prepareStreamsAndStartsq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000Jq\\u0000~\\u0000Sq\\u0000~\\u0000Tt\\u0000\\u0004mainsq\\u0000~\\u0000\\u0016ÿÿÿþq\\u0000~\\u0000\\u001cq\\u0000~\\u0000\\u001dq\\u0000~\\u0000\\u001esq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u00009q\\u0000~\\u0000\\u001cq\\u0000~\\u0000\\u001dq\\u0000~\\u0000 sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000+q\\u0000~\\u0000\\\"q\\u0000~\\u0000#q\\u0000~\\u0000 sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0002^q\\u0000~\\u0000%q\\u0000~\\u0000&q\\u0000~\\u0000 sq\\u0000~\\u0000\\u0016\\u0000\\u0000\\u0000\\u0090t\\u0000-com.intellij.rt.execution.application.AppMaint\\u0000\\fAppMain.javaq\\u0000~\\u0000Wsr\\u0000&java.util.Collections$UnmodifiableListü\\u000f%1µì\\u008e\\u0010\\u0002\\u0000\\u0001L\\u0000\\u0004listq\\u0000~\\u0000\\u0001xr\\u0000,java.util.Collections$UnmodifiableCollection\\u0019B\\u0000\\u0080Ë^÷\\u001e\\u0002\\u0000\\u0001L\\u0000\\u0001ct\\u0000\\u0016Ljava/util/Collection;xpsq\\u0000~\\u0000\\u0004\\u0000\\u0000\\u0000\\u0000w\\u0004\\u0000\\u0000\\u0000\\u0000xq\\u0000~\\u0000cxxt\\u0000\\ttestClasst\\u0000\\ntestMethod\"}";
        callPacker = CallPacker.unpack(new JSONObject(testJsonString).getString("v"));
        args = callPacker.getArgs();
        Assert.assertTrue(callPacker.isResponse());
        Assert.assertTrue(Exception.class.isAssignableFrom(args.get(0).type));

    }
}