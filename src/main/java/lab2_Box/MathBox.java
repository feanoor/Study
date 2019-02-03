package lab2_Box;

import lab1_Sorting.MySimpleException;

import java.math.BigDecimal;
import java.util.*;

/**
 * Класс предназначен для хранения и обработки коллекции с типами унаследованными от Number
 * @param <T> любой наследник Number
 */
public class MathBox<T extends Number> extends ObjectBox<T> {
//    private Set<T> objList;
    final private int hCode;


    public MathBox(T[] mas) {
        objList = new TreeSet<>(Arrays.asList(mas));
        hCode = (int) (Math.sqrt(Math.abs(summator().doubleValue())) * 99 * Math.random());
    }

    /**
     * Удаляет элемент из коллекции
     * @param val
     */
    void removeElement(T val) {
        objList.remove(val);
    }

    @Override
    void deleteObject(T obj) {
        removeElement(obj);
    }

    /**
     * Суммирует все элементы коллекции
     * @return результатом является число типа BigDecimal
     */
    BigDecimal summator() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Object i : objList) {
            sum = sum.add(new BigDecimal(i.toString()));
        }
        return sum;
    }

    /**
     * Метод осуществляет деление каждого элемента коллекции на число являющееся потомком Number
     * @param divider тп Number
     * @return возвращает Set типа Double
     */
    Set<Double> splitter(Number divider) {
        TreeSet<Double> set = new TreeSet<>();
        for (Object i : objList) {
            Double d = ((Number)i).doubleValue() / Double.valueOf(divider.doubleValue());
            set.add(d);
        }
        return set;
    }

    @Override
    public int hashCode() {
        return hCode;
    }

    @Override
    public String toString() {
        String val = "[";
        for (Object i : objList) {
            val += i.toString() + ",";
        }
        val.trim();
        return val.substring(0, val.length() - 1) + "]";
    }

    @Override
    void addObject(T obj) {
        if(!(obj.getClass().getSuperclass() == Number.class)){
            try {
                throw new MySimpleException("Object нельзя добавить в MathBox.");
            } catch (MySimpleException e) {
                System.out.println(e.toString());
            }
        }
        try {
            objList.add(obj);
        } catch (ClassCastException ex){
            System.out.println("Значение такого типа невозможно добавить в данную коллекцию.");
        }
    }
}
