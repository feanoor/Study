package lab4_MultiThreading;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContainChecker{
    public static ArrayList<String> contList = new ArrayList<String>();
    static ArrayList<File> filesArr= new ArrayList<File>();
    static ListIterator li;
    static Pattern p1 = Pattern.compile("([a-zA-Z]{1,14})");
    static String[] dictionary = null;
    static HashSet<String> dictHash;
    static LinkedList<String> containedStr = new LinkedList<>();
    static ReentrantLock rLock = new ReentrantLock();
    static ReentrantLock rLock2 = new ReentrantLock();
    static {
        File file = new File("C:\\docs\\trash2\\");
        for(File i :file.listFiles()){
            if(i.isFile()){
                filesArr.add(i);
                System.out.println(i.getName());
            }
        }
        li = filesArr.listIterator();
    }

    void getDictHashSet(String[] str){
        dictHash = new HashSet<>();
        dictHash.addAll(Arrays.asList(str));

    }

    public void checkContain(){
        while (li.hasNext()){
            rLock.lock();
            File file = filesArr.get(li.nextIndex());
            System.out.println(Thread.currentThread().getName() + " работает с файлом " +file.getName());
            li.next();
            rLock.unlock();

            checkEquals(file);


        }

    }

    void printContainedStr(){
        System.out.println("\n\ncontainedStr:");
        rLock2.lock();
        for(String s : containedStr){
            System.out.println(s);
        }
        rLock2.unlock();
    }


    void checkEquals(File f ){
//        DataInputStream dis = null;
        try {
//            dis = new DataInputStream(new FileInputStream(f));
//            String readedStr = dis.readUTF();
//            Matcher m1;
//            m1 = p1.matcher(readedStr);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String readedStr;
            while((readedStr = br.readLine())!= null) {
                Matcher m1;
                m1 = p1.matcher(readedStr);
                while (m1.find()) {
                    //System.out.println(m1.group());
                    String str = m1.group();
                    if (dictHash.contains(str.toLowerCase())) {
                        rLock2.lock();
                        containedStr.add(str);
                        rLock2.unlock();
                    }
                }
            }
            printContainedStr();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
