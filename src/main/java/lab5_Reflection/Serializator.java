package lab5_Reflection;

import lab1_Sorting.MySimpleException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Serializator implements MySerializible {
    private static Pattern p1 = Pattern.compile("<([0-9\\w]*) type=\"([\\w._]*)\" value=\"([а-яА-Я\\w]*)\"\\/>");
    private HashMap<String, DoubleString> mapAttributes;

    @Override
    public void serialize(Object object, String file) {
        Class cl = object.getClass();
        Field[] fields = cl.getDeclaredFields();

        System.out.println(fields.length);
        StringBuilder sb = new StringBuilder();
        sb.append("<0class type=\"" + object.getClass().toString().split(" ")[1] + "\"" + " value=\"\"/>\n");
        for (Field fi : fields) {
            try {
                fi.setAccessible(true);
                System.out.println(fi.getName());
                System.out.println(fi.getType());
                System.out.println(fi.get(object));
                if(fi.getType().toString().equals("class java.lang.String")&fi.get(object)==null){
                    System.out.println(fi.getType().toString());
                    System.out.println(fi.get(object));
                    continue;
                }
                sb.append("<" + fi.getName() + " "
                        + "type=\"" + fi.getType().toString().replaceAll("^.*[.]", "")
                        + "\" value=\"" + fi.get(object) + "\"/>\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.write(Paths.get(file), sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class DoubleString {
        String s1;
        String s2;

        DoubleString(String s1, String s2) {
            this.s1 = s1;
            this.s2 = s2;
        }
    }

    @Override
    public Object deSerialize(String file) {
        mapAttributes = new HashMap<>();
        readSerialize(file);
        return creatingNewInstance();
    }

    private void readSerialize(String file) {
        try {
            for (String i : Files.readAllLines(Paths.get(file))) {
                Matcher m1 = p1.matcher(i);
                if (m1.matches()) {
                    mapAttributes.put(m1.group(1), new DoubleString(m1.group(2), m1.group(3)));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object creatingNewInstance() {
        Object alpha = null;
        try {
            Class C = Class.forName(mapAttributes.get("0class").s1);
            alpha = C.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Field fin = alpha.getClass().getDeclaredField("age");
            fin.setAccessible(true);
            fin.set(alpha,80);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field[] aField = alpha.getClass().getDeclaredFields();
        for (Field f : aField) {
            try {
                f.setAccessible(true);
                if(mapAttributes.get(f.getName())==null) {
                    System.out.println(f.getName() + " continue");
                    continue;
                }
                if (mapAttributes.get(f.getName()).s1.equals("String")) {
                    f.set(alpha, mapAttributes.get(f.getName()).s2);
                    continue;
                }
                if (mapAttributes.get(f.getName()).s1.equals("int")) {
                    f.set(alpha, Integer.valueOf(mapAttributes.get(f.getName()).s2));
                    continue;
                }
                if (mapAttributes.get(f.getName()).s1.equals("long")) {
                    f.set(alpha, Long.valueOf(mapAttributes.get(f.getName()).s2));
                    continue;
                }
                if (mapAttributes.get(f.getName()).s1.equals("double")) {
                    f.set(alpha, Double.valueOf(mapAttributes.get(f.getName()).s2));
                    continue;
                }
                if (mapAttributes.get(f.getName()).s1.equals("float")) {
                    f.set(alpha, Float.valueOf(mapAttributes.get(f.getName()).s2));
                    continue;
                }
                if (mapAttributes.get(f.getName()).s1.equals("boolean")) {
                    f.set(alpha, Boolean.valueOf(mapAttributes.get(f.getName()).s2));
                    continue;
                }
                if (mapAttributes.get(f.getName()).s1.equals("short")) {
                    f.set(alpha, Short.valueOf(mapAttributes.get(f.getName()).s2));
                    continue;
                }
                if (mapAttributes.get(f.getName()).s1.equals("byte")) {
                    f.set(alpha, Byte.valueOf(mapAttributes.get(f.getName()).s2));
                    continue;
                }
                if (mapAttributes.get(f.getName()).s1.equals("char")) {
                    f.set(alpha, Character.valueOf((mapAttributes.get(f.getName()).s2).charAt(0)));
                    continue;
                }
                try {
                    throw new MySimpleException("Данный тип: " + mapAttributes.get(f.getName()).s1+ " недопустим для сериализации");
                } catch (MySimpleException e) {
                    e.printStackTrace();
                    return "";
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return alpha;
    }
}