package com.gitzhu.easy;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by Michael on 9/24/17.
 */
public class PerfMonXformer implements ClassFileTransformer {
    public byte[] transform(ClassLoader loader, String className,
        Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
        byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] transformed = null;
        System.out.println("Transforming " + className);
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        try {
            cl = pool.makeClass(new java.io.ByteArrayInputStream(
                classfileBuffer));
            if (cl.isInterface() == false) {
                CtBehavior[] methods = cl.getDeclaredBehaviors();
                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].isEmpty() == false) {
                        doMethod(methods[i]);
                    }
                }
                transformed = cl.toBytecode();
            }
        } catch (Exception e) {
            System.err.println("Could not instrument  " + className
                + ",  exception : " + e.getMessage());
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return transformed;
    }
    private void doMethod(CtBehavior method) throws Exception {
        // method.insertBefore("long stime = System.nanoTime();");
        // method.insertAfter("System.out.println(/"leave "+method.getName()+" and time:/"+(System.nanoTime()-stime));");
        method.instrument(new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                m.replace("{ long stime = System.nanoTime(); $_ = $proceed($$); System.out.println(\""
                    + m.getClassName()+"."+m.getMethodName()
                    + ":\"+(System.nanoTime()-stime));}");
        }
    });
}
}
