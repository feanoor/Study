package lab6_ClassLoaders;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClassCompiler {

    void doCompile(String className){
        /*sb.append("package lab6_ClassLoaders;public class " + className +
                " implements Workable { @Override public void doWork(){System.out.println(\"Хоттабыч2\");}}");
                */
        try {
            String filename = "./" + className + ".java";
            ColsoleReader cr = new ColsoleReader();
            String buf;
            buf = cr.createCode(cr.read(),className);
            //System.out.println(buf);
            Files.write(Paths.get(filename), buf.getBytes());
            JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
            String[] javacOpts = {filename};
            javac.run(null, null, null, javacOpts);
            ClassLoader cl = new MyClassLoader();
            Class myClass = ((MyClassLoader) cl).findClass("lab6_ClassLoaders." + className);
            Workable worker = (Workable) myClass.newInstance();
            worker.doWork();

        } catch (IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
