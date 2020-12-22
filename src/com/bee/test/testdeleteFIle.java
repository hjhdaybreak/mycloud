package com.bee.test;

import java.io.File;

public class testdeleteFIle {
    public static void main(String[] args) {
        File file=new File("D:\\temp.txt");
        file.delete();
    }
}