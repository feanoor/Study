package lab2_Box;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MathBoxOld {
/*    ArrayList<Integer> arrInt;

    public MathBox(Integer[] mas) {
        this.arrInt = new ArrayList<>(Arrays.asList(mas));
        doSort();
    }

    void doSort() {
//            arrInt.sort((o1, o2) -> {
//                if(o1>o2) return 1;
//                if(o2>o1) return -1;
//                return 0;
//            });
        arrInt.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) return 1;
                if (o2 > o1) return -1;
                return 0;
            }
        });
    }

    void removeElement(Integer val){
        arrInt.remove(val);
    }

    Integer summator() {
        Integer sum = 0;
        for (Integer i : arrInt) {
            sum += i;
        }
        return sum;
        //return arrInt.stream().reduce((a,b) -> a + b).get();
    }

    List splitter(Integer divider) {
        List<Integer> lst = new ArrayList<>();
        for (Integer i : arrInt) {
            lst.add(i / divider);
        }
        return lst;
        //return arrInt.stream().map(a -> a/divider).collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        return (int) Math.sqrt(Math.abs(summator()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MathBox)) {
            return false;
        }

        if (((MathBox) obj).arrInt.size() != arrInt.size()) {
            return false;
        }
        for (Integer i : arrInt) {
            if (!((MathBox) obj).arrInt.contains(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String val = "[";
        for (Integer i : arrInt) {
            val += i.toString() + ",";

        }
        val.trim();
        return val.substring(0, val.length() - 1) + "]";
    }*/
}
