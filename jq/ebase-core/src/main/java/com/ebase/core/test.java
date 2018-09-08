package com.ebase.core;

import java.text.DecimalFormat;

public class test {
    public static void main(String[] args) {
        System.out.println(new DecimalFormat("0000000000.00").format(new Double(1.11111)).replace(".", ""));
    }
}
