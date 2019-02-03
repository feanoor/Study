package Experiments.Lambda;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FunctionalInterfaces {

    public static void main(String[] args) {
        Supplier<Integer> getInt = () -> (int)(Math.random()*100);
        System.out.println(getInt.get());

        UnaryOperator<String> changeStr = n -> n+" hello!";
        System.out.println(changeStr.apply("Vasya"));
    }
}
