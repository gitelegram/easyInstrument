package com.gitzhu.easy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by Michael on 10/6/17.
 */
public class TransClass {

    public int getNumber(){
        return 1;
    }
}

class Transformer implements ClassFileTransformer{

    public static final String classNumberReturns2 = "TransClass.class.2";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
        ProtectionDomain protectionDomain, byte[] classfileBuffer)
        throws IllegalClassFormatException {

        if(!className.equals("TransClass")){
            return null;
        }
        return getBytesFromFile(classNumberReturns2);
    }

    public static byte[] getBytesFromFile(String className){
        try{
            File file = new File(className);
            InputStream is = new FileInputStream(file);
            long length = file.length();
            byte[] bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset <bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file "
                    + file.getName());
            }
            is.close();
            return bytes;
        } catch (Exception e){
            System.out.println("error occurs in _ClassTransformer!"
                + e.getClass().getName());
            return null;
        }

    }
}

