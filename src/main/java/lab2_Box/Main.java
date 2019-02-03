package lab2_Box;

import java.io.*;
import java.util.List;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        System.out.println(Double.valueOf(5));
        Integer[] intMas = {10,20,30,40,70,50,60,80,90,100};
        Integer[] intMas2 = {10,20,30,40,70,50,60,80,90,100};
        MathBox mb2 = new MathBox(intMas2);
        MathBox<Integer> mb = new MathBox(intMas);
        System.out.println("summ:");
        System.out.println(mb.summator());
        System.out.println("it's split:");
        TreeSet lst = (TreeSet) mb.splitter(2);
        System.out.println("it's equals:");
        System.out.println(mb.equals(mb2));
        System.out.println("it's hashs:");
        System.out.println(mb.hashCode());
        System.out.println(mb2.hashCode());
        mb.removeElement(100);
        System.out.println(mb.toString());
        ObjectBox ob = new ObjectBox();
        ob.addObject("dgdg");
        ob.addObject(353);
        ob.addObject(true);
        System.out.println(ob.dump());
        //mb.addObject(1.0);
        System.out.println(mb.toString());

    }




}
