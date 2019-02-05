package lab8_StreamAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] dictMas = null;
        try {
            //считываем словарь в массив словарь для дальнейшего поиска в нем
            BufferedReader br = new BufferedReader(new FileReader("C:\\docs\\dictForMThreading.txt"));
            dictMas = br.lines().toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //тут генерим список путей к файлам из указанной директории
        String[] sourcePaths;
        //here is source files
        File file = new File("C:\\docs\\trash\\");
        sourcePaths = Arrays.stream(file.listFiles()).filter(File::isFile).map(File::getAbsolutePath).toArray(String[]::new);
        //here is source http
        sourcePaths = new String[20];
        Arrays.setAll(sourcePaths, i -> "http://www.gutenberg.org/files/" + (i + 48562) + "/" + (i + 48562) + "-0.txt");

        long start = System.currentTimeMillis();
        ContainChecker ch = new ContainChecker();
        ch.getOccurencies(sourcePaths, dictMas, "C:\\docs\\output.txt");
        System.out.println(System.currentTimeMillis() - start + " ms");
    }
}
