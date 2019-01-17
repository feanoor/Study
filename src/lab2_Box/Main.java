package lab2_Box;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Integer[] intMas = {10,20,30,40,70,50,60,80,90,100};
        /*for (int i = 0; i < intMas.length; i++) {
            intMas[i] = (int) (Math.random() * 100);
        }*/

        for(Integer i : intMas)
        System.out.println(i);

        System.out.println("blablabla");

        MathBox mb = new MathBox(intMas);
        System.out.println(mb.toString());
        System.out.println(mb.summator());
        List lst = mb.splitter(2);
        for (Object i : lst)
            System.out.println(i);
    }


}
