package org.reconan.ui;

public class Banner {
    public static void print() {
        System.out.println("\u001B[38;2;178;34;34m---------------------------------------------------------");
        System.out.println("ReConan: The OSINT and Cyber Investigation Platform.");
        System.out.println("---------------------------------------------------------\u001B[0m");
    }
}