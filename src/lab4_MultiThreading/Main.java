package lab4_MultiThreading;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] dictMas = null;
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
            //ContainChecker ch = new ContainChecker();

            //ch.getDictHashSet(dictMas);
            //System.out.println(ContainChecker.dictHash);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //тут генерим список путей к файлам из указанной директории
        String[] sourcePaths;
        ArrayList<String> arrPaths = new ArrayList();
        //here is source
        //File file = new File("C:\\docs\\trash2\\");
        File file = new File("C:\\docs\\trash\\");
        for(File i : file.listFiles()){
            if(i.isFile()){

                arrPaths.add(i.getAbsolutePath());
            }
        }
        sourcePaths = arrPaths.toArray(new String[0]);
//        for(String i : sourcePaths){
//            System.out.println(i);
//        }
        //Запуск в один поток
//        ContainChecker ch = new ContainChecker();
//        long start = System.currentTimeMillis();
//        ch.getOccurencies(sourcePaths, dictMas, "C:\\docs\\output.txt");
//        System.out.println(System.currentTimeMillis()-start + " ms");
//        45sec
        //------------------------------
        ///////////////
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

        //тут запукаются треды

        long start = System.currentTimeMillis();


        Thread[] threadMas = new Thread[2];
        for (int i = 0; i < threadMas.length ; i++) {
            threadMas[i] = new WorkingThread(sourcePaths, dictMas, "C:\\docs\\output.txt");
            threadMas[i].start();

        }
        //2 потока 30sec
        //3 потока 31sec
        //5 потоков 32sec
        for(Thread i : threadMas){
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(System.currentTimeMillis()-start + " ms");
        //-------------------------------------
/*        Pattern pAll = Pattern.compile("([A-ZА-Я][^.?!]*[.!?])");
        Pattern pLeft = Pattern.compile("([A-ZА-Я][^.?!]*$)");
        Pattern pRight = Pattern.compile("([^A-ZА-Я.?!]*[.!?])");
        BufferedReader br = new BufferedReader(new FileReader("C:\\docs\\Harry Potter and the Philosophers Stone.txt"));
        String readedStr;
        String tempStr = "";
        //HashMap<Integer,String> tempStr = new HashMap<>();
        //int i = 0;
        //TODO: Для одного большого файла нужно сделать общую переменную AtomicInteger например и записывать номер считываемой строки. Чтение и запись залочить. Она будет в качестве ключа в хэш-мапе незаконченной строки.

        while((readedStr = br.readLine())!= null) {
            //i++;
            if(tempStr == "") {
                Matcher mLeft;
                mLeft = pLeft.matcher(readedStr);
                if (mLeft.find()) {
                    String str = mLeft.group();
                    //tempStr.put(i,str);
                    tempStr += str;
                }
            }

            if(!tempStr.isEmpty()) {
                Matcher mRight;
                mRight = pRight.matcher(readedStr);
                if (mRight.find()) {
                    String str = mRight.group();
                    //tempStr.put(i,str);
                    tempStr += str;
                    System.out.println(tempStr);
//                    str = str.toLowerCase();
//                    HashSet<String> setStr =
                    tempStr = "";
                }
            }

                Matcher mAll;
                mAll = pAll.matcher(readedStr);
                while (mAll.find()) {
                    String str = mAll.group();
                    System.out.println(str);
                }
*/

            //System.out.println(readedStr);
            /*

            if(!sentenseBegining & !sentenseContines){
                    Matcher mAll;
                    mAll = pAll.matcher(readedStr);
                    if(mAll.matches()
                    while (mAll.find()) {
                    //System.out.println(m1.group());
                    String str = mAll.group();
                    System.out.println(str);
                }
            }
*/


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
