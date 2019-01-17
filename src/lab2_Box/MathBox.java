package lab2_Box;

import java.util.*;
import java.util.stream.Collectors;

public class MathBox <T extends Number> extends ObjectBox{
    //List<T> arrInt;
    Set<T> arrInt;
    final private int hCode;



    public MathBox(T[] mas) {
        /*arrInt = new ArrayList<>(Arrays.asList(mas));
        System.out.println(arrInt.get(0).getClass());
        objList =  (List<T>)objList;
        objList = new ArrayList(Arrays.asList(mas));
        System.out.println(objList.get(0).getClass());
        doSort();*/
        arrInt = new TreeSet<>(Arrays.asList(mas));
        hCode = (int)( Math.sqrt(Math.abs((Double)summator()))*99*Math.random());

    }

   /* void doSort() {
//            arrInt.sort((o1, o2) -> {
//                if(o1>o2) return 1;
//                if(o2>o1) return -1;
//                return 0;
//            });
        objList.sort(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                if (Double.valueOf(o1.doubleValue()) > Double.valueOf(o2.doubleValue())) return 1;
                if (Double.valueOf(o2.doubleValue()) > Double.valueOf(o1.doubleValue())) return -1;
                return 0;
            }
        });
    }
*/
    void removeElement(Integer val){
        objList.remove(val);
    }

    T summator() {
        Double sum = Double.valueOf(0);
        for (T i : arrInt) {
            sum += Double.valueOf(i.doubleValue());
        }
        return (T)sum;
        //return arrInt.stream().reduce((a,b) -> a + b).get();
    }

    List<T> splitter(T divider) {
        List<T> lst = new ArrayList<>();
        for (T i : arrInt) {
            Double d = i.doubleValue()/ Double.valueOf(divider.doubleValue());
            lst.add((T)d);
        }
        return lst;
        //return arrInt.stream().map(a -> a/divider).collect(Collectors.toList());
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
        /*if (!(obj instanceof MathBox)) {
            return false;
        }

        if (((MathBox) obj).arrInt.size() != arrInt.size()) {
            return false;
        }
        for (T i : arrInt) {
            if (!((MathBox) obj).arrInt.contains(i)) {
                return false;
            }
        }
        return true;*/
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

  /*  @Override
    void addObject(Object obj) {
        Number n;
        if(obj instanceof  )


    }*/
}
