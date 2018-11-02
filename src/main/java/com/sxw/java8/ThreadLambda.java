package com.sxw.java8;

public class ThreadLambda {

    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer();
        new Thread(() -> {
            buffer.append("通过lambda表达式开启一个子线程\n");
            System.out.println(buffer);
        }).start();

    }
}

