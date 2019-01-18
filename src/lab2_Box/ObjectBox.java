package lab2_Box;

import java.util.*;

/**
 * Класс осуществляет хранение, добавление и удаление коллекции элементов Object
 */
public class ObjectBox {
    private Collection objList = new ArrayList<>();

    /**
     * Добавляет элемент в коллекцию
     * @param obj любой элемент типа Object
     */
    void addObject(Object obj) {
        objList.add(obj);
    }

    /**
     * Удаляет элемент из коллекции
     * @param obj любой элемент типа Object
     */
    void deleteObject(Object obj) {
        objList.remove(obj);
    }

    @Override
    public String toString() {
        String str = "";
        for (Object i : objList) {
            str += i.toString() + ",";

        }
        return str.substring(0, str.length() - 1);
    }

    /**
     * Выводит список элементов коллекции в строку
     * @return возвращает String
     */
    public String dump() {
        return toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectBox objectBox = (ObjectBox) o;
        return Objects.equals(objList, objectBox.objList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objList);
    }
}
