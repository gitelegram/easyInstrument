package com.gitzhu.easy;

/**
 * Created by Michael on 10/6/17.
 */
public class TestMainInJar {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(new TransClass().getNumber());
        int count = 0;
        while (true) {
            Thread.sleep(500);
            count++;
            int number = new TransClass().getNumber();
            System.out.println(number);
            if (3 == number || count >= 10) {
                break;
            }
        }
    }
}
