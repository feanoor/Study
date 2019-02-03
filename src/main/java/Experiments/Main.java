package Experiments;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Set<Integer> setInts = new TreeSet<>();
        setInts.add(2);
        setInts.add(3);
        System.out.println(
                setInts.stream().
                        map((x)->String.valueOf(x*2))
                .reduce((a,b)->a+b).get()

        );
        Map<String, Integer> mapa = new HashMap<>();
        mapa.put("sfsf",2);
    }
}
