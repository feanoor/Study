package lab6_ClassLoaders;

import java.util.Scanner;

public class ColsoleReader {
    String read() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String buf;
        System.out.println("Начинайте вводить код:");
        while (!(buf = scanner.nextLine()).equals("")) {
            sb.append(buf+"\n");
        }
        return sb.toString();
    }

    String createCode(String str, String className){
        StringBuilder sb =  new StringBuilder();
        sb.append("package lab6_ClassLoaders;\npublic class " + className + "\n");
        sb.append("implements Workable { @Override public void doWork(){\n");
        sb.append(str);
        sb.append("}}");
        return sb.toString();
    }
}

