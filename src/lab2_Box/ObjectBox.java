package lab2_Box;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectBox {
    protected List objList = new ArrayList<>();


    void addObject(Object obj){
        objList.add(obj);
    }

    void deleteObject(Object obj){
        objList.remove(obj);
    }
    @Override
    public String toString(){
        String str = "";
        for(Object i : objList){
            str += i.toString() + ",";

        }
        return str.substring(0, str.length()-1);
    }
    public String dump(){
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
