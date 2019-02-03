package lab6_ClassLoaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //System.out.println("Начат поиск класса: " + name);
        try {
            //byte[] b = Files.readAllBytes(Paths.get("./Worker.class"));
            byte[] b = Files.readAllBytes(Paths.get("./"+name.replaceAll("^.*[.]","")+".class"));
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}
