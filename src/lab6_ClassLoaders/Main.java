package lab6_ClassLoaders;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        ClassCompiler cc = new ClassCompiler();
        cc.doCompile("Worker");
    }
}
