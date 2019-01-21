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
    static Pattern pAll = Pattern.compile("([A-ZА-Я][^.?!]*[.!?])");
    static Pattern pLeft = Pattern.compile("([A-ZА-Я][^.?!]*$)");
    static Pattern pRight = Pattern.compile("([^A-ZА-Я.?!]*[.!?])");
    //static Pattern mustBeDestroyed = Pattern.compile("([^а-яa-z]*)");
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


    void checkEquals(File f ) {
    //-------------------------
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("C:\\docs\\Harry Potter and the Philosophers Stone.txt"));

        String readedStr;
        String tempStr = "";
        //HashMap<Integer,String> tempStr = new HashMap<>();
        //int i = 0;
        //TODO: Для одного большого файла нужно сделать общую переменную AtomicInteger например и записывать номер считываемой строки. Чтение и запись залочить. Она будет в качестве ключа в хэш-мапе незаконченной строки.

        while((readedStr = br.readLine())!= null) {
            //i++;
            if (tempStr == "") {
                Matcher mLeft;
                mLeft = pLeft.matcher(readedStr);
                if (mLeft.find()) {
                    String str = mLeft.group();
                    //tempStr.put(i,str);
                    tempStr += str;
                }
            }

            if (tempStr != "") {
                Matcher mRight;
                mRight = pRight.matcher(readedStr);
                if (mRight.find()) {
                    String str = mRight.group();
                    //tempStr.put(i,str);
                    tempStr += str;
                    //System.out.println(tempStr);
                    if(containsInDict(str,dictHash)){
                        System.out.println(str);
                    }
                    tempStr = "";
                }
            }

            Matcher mAll;
            mAll = pAll.matcher(readedStr);
            while (mAll.find()) {
                String str = mAll.group();
                //System.out.println(str);
                if(containsInDict(str,dictHash)){
                    System.out.println(str);
                }
            }
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //------------------------
    }

    boolean containsInDict(String st, HashSet<String> dic  ){
        st = st.toLowerCase();
        HashSet<String> setWords = new HashSet<>();
        //System.out.println(st);
        String mustBeDestroyed = "([^а-яa-z]+)";
        st = st.replaceAll(mustBeDestroyed, " ");
        Collections.addAll(setWords,st.split("\\s+")) ;
        if(!Collections.disjoint(setWords, dic)){
            //System.out.println(st);
            return true;
        }
        return false;
    }
}
