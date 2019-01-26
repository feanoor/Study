package lab9_GarbageCollector;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int LOOP_COUNT = 100_000_000;

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //Настройки jvm:
        //-Xmx1000m -XX:MaxMetaspaceSize=15m

        //Переполняем Heap Space
        List<String> list = new ArrayList<>();
        Random random = new Random();
        String str = "";
        for (int i = 0; i < LOOP_COUNT; i++) {

            str += "" + random.nextInt();
            list.add(str);
            Thread.sleep(1);
            if (i % 1000 == 0) {
                list.clear();
            }

        }

        System.out.println(list.size());


//переполняем Metaspace
        int n = 1000;
        Class myClass[] = new Class[n * 10];
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        ClassLoader cl = new MyClassLoader();
        Object[] o = new Object[n];
        for (int i = 0; i < n; i++) {
            String filename = "./Main" + i + ".java";
            String classBody = "package lab9_GarbageCollector; public class Main" + i + " {\n" +
                    "public static void main(String[] args) {\n" +
                    "System.out.println(" + i + ");\n" +
                    "}}";
            Thread.sleep(5);
            Files.write(Paths.get(filename), classBody.getBytes());
            javac.run(null, null, null, filename);
            myClass[i] = ((MyClassLoader) cl).findClass("lab9_GarbageCollector.Main" + i);
            o[i] = myClass[i].newInstance();
        }

    }
}
