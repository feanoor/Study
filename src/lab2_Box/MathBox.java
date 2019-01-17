package lab2_Box;

import java.util.*;

public class MathBox<T extends Number> extends ObjectBox {
    Set<T> arrInt;
    final private int hCode;


    public MathBox(T[] mas) {
        arrInt = new TreeSet<>(Arrays.asList(mas));
        hCode = (int) (Math.sqrt(Math.abs((Double) summator())) * 99 * Math.random());
    }

    void removeElement(Integer val) {
        objList.remove(val);
    }

    T summator() {
        Double sum = Double.valueOf(0);
        for (T i : arrInt) {
            sum += Double.valueOf(i.doubleValue());
        }
        return (T) sum;
    }

    List<T> splitter(T divider) {
        List<T> lst = new ArrayList<>();
        for (T i : arrInt) {
            Double d = i.doubleValue() / Double.valueOf(divider.doubleValue());
            lst.add((T) d);
        }
        return lst;
    }

    @Override
    public int hashCode() {
        return hCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String val = "[";
        for (T i : arrInt) {
            val += i.toString() + ",";
        }
        val.trim();
        return val.substring(0, val.length() - 1) + "]";
    }

}
