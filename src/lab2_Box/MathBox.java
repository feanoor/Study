package lab2_Box;

import lab1_Sorting.MySimpleException;

import java.math.BigDecimal;
import java.util.*;

/**
 * Класс предназначен для хранения и обработки коллекции с типами унаследованными от Number
 * @param <T> любой наследник Number
 */
public class MathBox<T extends Number> extends ObjectBox {
    private Set<T> arrInt;
    final private int hCode;


    public MathBox(T[] mas) {
        arrInt = new TreeSet<>(Arrays.asList(mas));
        hCode = (int) (Math.sqrt(Math.abs(summator().doubleValue())) * 99 * Math.random());
    }

    /**
     * Удаляет элемент из коллекции
     * @param val
     */
    void removeElement(T val) {
        arrInt.remove(val);
    }

    @Override
    void deleteObject(Object obj) {
        removeElement((T)obj);
    }

    /**
     * Суммирует все элементы коллекции
     * @return результатом является число типа BigDecimal
     */
    BigDecimal summator() {
        BigDecimal sum = BigDecimal.ZERO;
        for (T i : arrInt) {
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
        for (T i : arrInt) {
            Double d = i.doubleValue() / Double.valueOf(divider.doubleValue());
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
        for (T i : arrInt) {
            val += i.toString() + ",";
        }
        val.trim();
        return val.substring(0, val.length() - 1) + "]";
    }

    @Override
    void addObject(Object obj) {
        if(!(obj.getClass().getSuperclass() == Number.class)){
            try {
                throw new MySimpleException("Object нельзя добавить в MathBox.");
            } catch (MySimpleException e) {
                System.out.println(e.toString());
            }
        }
        try {
            arrInt.add((T)obj);
        } catch (ClassCastException ex){
            System.out.println("Значение такого типа невозможно добавить в данную коллекцию.");
        }
    }
}
