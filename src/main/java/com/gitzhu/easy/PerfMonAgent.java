package com.gitzhu.easy;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * Created by Michael on 9/24/17.
 */
public class PerfMonAgent {

    static private Instrumentation inst = null;

    public static void premain(String agentArges, Instrumentation _inst){
        System.out.println("PerfMonAgent.premain() was called.");
        inst = _inst;
        ClassFileTransformer transformer = new PerfMonXformer();
        System.out.println("Adding a PerfMonXformer instance to the JVM.");
        _inst.addTransformer(transformer);
    }
}
