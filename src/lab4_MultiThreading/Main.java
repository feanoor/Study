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
            //------------------------------------------------------------------

        } catch (IOException e) {
            e.printStackTrace();
        }

        //тут генерим список путей к файлам из указанной директории
        String[] sourcePaths;
        ArrayList<String> arrPaths = new ArrayList();
        //here is source
        //File file = new File("C:\\docs\\trash2\\");
        File file = new File("C:\\docs\\trash3\\");
        for (File i : file.listFiles()) {
            if (i.isFile()) {

                arrPaths.add(i.getAbsolutePath());
            }
        }
        sourcePaths = arrPaths.toArray(new String[0]);
        long start = System.currentTimeMillis();
        ContainChecker ch = new ContainChecker();
        ch.getOccurencies(sourcePaths, dictMas, "C:\\docs\\output.txt");
        System.out.println(System.currentTimeMillis() - start + " ms");
    }
}
