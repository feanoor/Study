package Experiments.LinkedVsArray;

import java.io.Closeable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
/*        long t2;
        t2 = System.currentTimeMillis();
        for (int i = 0; i < 1_0000000; i++) {
            linList.addFirst(i);
        }

        System.out.println(System.currentTimeMillis() - t2);
        linList.clear();
        t2 = System.currentTimeMillis();
        for (int i = 0; i < 1_0000000; i++) {
            linList.add(i);
        }

        System.out.println(System.currentTimeMillis() - t2);

        long t1 = System.currentTimeMillis();
        linList.add(2000000,123);
        System.out.println("LinkedAdding : " + ((System.currentTimeMillis() - t1)));

        t2 = System.currentTimeMillis();
        ArrayList<Integer> arrList = new ArrayList<>();
        for (int i = 0; i < 1_0000000; i++) {
            arrList.add(i);
        }
        System.out.println(System.currentTimeMillis() - t2);
        t1 = System.currentTimeMillis();
        arrList.add(2000000,123);
        System.out.println("ArrayAdding : " + ((System.currentTimeMillis() - t1)));

        InputStream is;
        Object o = new Object();*/
        /*char c1 = '1';
        char c2 = '\u0031';
        System.out.println(c2);
        char c3 = 49;
        System.out.println(c1 + c2 + c3);*/
        double d1 = 1.1;
        float f1 = 1.1f;
        System.out.println(d1==f1);
        boolean b1 = true;
        boolean b2 = false;
        if(b2 = b1) System.out.println("da");

        int i = 1;
    }
//
//    public<T,U,R> Supplier<R> doAny(BiFunction<T, U, R> bi){
//        U u = bi.apply(u);
//        R r;
//        T t;
//        return (t + u);
//    }
}
