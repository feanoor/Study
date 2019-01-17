package lab2_Box;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(Double.valueOf(5));
        Integer[] intMas = {10,20,30,40,70,50,60,80,90,100};
        //Double[] intMas = {6.2, -2.0, 4.4 , 5.7};
        Integer[] intMas2 = {10,20,30,40,70,50,60,80,90,100};
        /*for (int i = 0; i < intMas.length; i++) {
            intMas[i] = (int) (Math.random() * 100);
        }*/

        for(Integer i : intMas)
        System.out.println(i);

        System.out.println("blablabla");
        MathBox mb2 = new MathBox(intMas2);
        MathBox<Integer> mb = new MathBox(intMas);
        System.out.println("--------");
        System.out.println(mb.toString());
        System.out.println("--------");
        System.out.println("summ:");
        System.out.println(mb.summator());
        System.out.println("it's split:");
        List lst = mb.splitter(2);
        for (Object i : lst)
            System.out.println(i);
        System.out.println("it's equals:");
        System.out.println(mb.equals(mb2));

        System.out.println("it's hashs:");
        System.out.println(mb.hashCode());
        System.out.println(mb2.hashCode());

        mb.removeElement(2000);
        System.out.println(mb.toString());

        ObjectBox ob = new ObjectBox();
        ob.addObject("dgdg");
        ob.addObject(353);
        ob.addObject(true);
        System.out.println("----");
        System.out.println(ob.dump());
        System.out.println("----");


        //mb.addObject("asdsd");
    }




}
