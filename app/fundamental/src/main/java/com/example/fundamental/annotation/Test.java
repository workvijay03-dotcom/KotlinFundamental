package com.example.fundamental.annotation;

public class Test {
    public static void main(String[] args) {
        JvmField jvmField = new JvmField();
        System.out.println(jvmField.name);

        JvmStatic.greet();
        JvmStatic.Companion.greet();

        JvmOverload jvmOverload = new JvmOverload();
        jvmOverload.name("neha");
        jvmOverload.name();
    }
}



