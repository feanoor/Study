package lab4_MultiThreading;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] dictMas = null;
        try {
            //считываем словарь в массив словарь для дальнейшего поиска в нем
            BufferedReader br = new BufferedReader(new FileReader("C:\\docs\\dictForMThreading.txt"));
            ArrayList<String> st = new ArrayList<>();
            String str;
            while ((str = br.readLine()) != null) {
                st.add(str);
            }
            dictMas = st.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //тут генерим список путей к файлам из указанной директории
        String[] sourcePaths;
        ArrayList<String> arrPaths = new ArrayList();
        //here is source files
        File file = new File("C:\\docs\\trash\\");
        for (File i : file.listFiles()) {
            if (i.isFile()) {

                arrPaths.add(i.getAbsolutePath());
            }
        }
        //here is source http
        sourcePaths = new String[20];
        for(int i = 0 ; i < sourcePaths.length ; i++){
            sourcePaths[i] = "http://www.gutenberg.org/files/" + (i+48562) + "/" + (i+48562) + "-0.txt";
        }
        long start = System.currentTimeMillis();
        ContainChecker ch = new ContainChecker();
        ch.getOccurencies(sourcePaths, dictMas, "C:\\docs\\output.txt");
        System.out.println(System.currentTimeMillis() - start + " ms");
    }
}
