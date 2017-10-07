package com.gitzhu.easy;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Created by Michael on 10/6/17.
 */
public class AgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst)
        throws UnmodifiableClassException {
        inst.addTransformer(new Transformer(), true);
        inst.retransformClasses(TransClass.class);
//        inst.setNativeMethodPrefix();
//        inst.appendToBootstrapClassLoaderSearch();
        System.out.println("Agent Main Done");
    }
}
