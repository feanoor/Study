package lab4_MultiThreading;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String[] dictMas;
        try {
            //считываем словарь в массив словарь для дальнейшего поиска в нем
            BufferedReader br = new BufferedReader(new FileReader("C:\\docs\\dictForMThreading.txt"));
            ArrayList<String> st = new ArrayList<>();
            String str;
            while ((str = br.readLine())!= null){
                st.add(str);
            }
            dictMas = st.toArray(new String[0]);
            //------------------------------------------------------------------
            ContainChecker ch = new ContainChecker();

            ch.getDictHashSet(dictMas);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try {
//            DataInputStream dis = null;
//            dis = new DataInputStream(new FileInputStream("C:\\docs\\trash2\\text-1.txt"));

//            BufferedReader br = new BufferedReader(new FileReader("C:\\docs\\trash2\\\\text-1.txt"));
//            String readedStr = br.readLine();
//            Pattern p1 = Pattern.compile("([a-zA-Z]{1,14})");
//            Matcher m1;
//            m1 = p1.matcher(readedStr);
//            while (m1.find()) {
//                System.out.println(m1.group());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Thread[] threadMas = new Thread[3];
        for (int i = 0; i < 3 ; i++) {
            threadMas[i] = new WorkingThread();
            threadMas[i].start();

        }

        for(Thread i : threadMas){
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        Thread t = new WorkingThread();
//        t.start();
//        try {
//
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("result:");
//        for( String i :ContainChecker.containedStr)
//        {
//            System.out.println(i);
//        }
    }


}
