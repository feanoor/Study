package lab4_MultiThreading;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContainChecker implements Occurenciesable {
    static Pattern p1 = Pattern.compile("([a-zA-Z]{1,14})");
    static Pattern pAll = Pattern.compile("([A-ZА-Я][^.?!…]*[.!?…])");
    static Pattern pLeft = Pattern.compile("([A-ZА-Я][^.?!…]*$)");
    static Pattern pRight = Pattern.compile("([^A-ZА-Я.?!…]*[.!?…])");
    //static String[] dictionary = null;
    static HashSet<String> dictHash;
    static LinkedList<String> containedStr = new LinkedList<>();
    static ReentrantLock rLock = new ReentrantLock();
    static ReentrantLock rLock2 = new ReentrantLock();
    static int indexFilesMas = 0;

    void getDictHashSet(String[] str) {
        dictHash = new HashSet<>();
        dictHash.addAll(Arrays.asList(str));

    }

    public void checkContain(String[] files, String output) {

        while (indexFilesMas < files.length) {
            rLock.lock();
            File file = new File(files[indexFilesMas]);
            System.out.println(Thread.currentThread().getName() + " работает с файлом " + file.getName());
            indexFilesMas++;
            rLock.unlock();

            //checkEquals(file, "C:\\docs\\output.txt");
            checkEquals(file, output);
        }

    }

    void printContainedStr() {
        System.out.println("\n\ncontainedStr:");
        rLock2.lock();
        for (String s : containedStr) {
            System.out.println(s);
        }
        rLock2.unlock();
    }

    void writeToFile(String str, String file) {
        try {
            rLock2.lock();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(str + "\n");
            bw.close();
            rLock2.unlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void checkEquals(File fi, String fo) {
        //-------------------------
        BufferedReader br = null;
        try {
            //br = new BufferedReader(new FileReader("C:\\docs\\Harry Potter and the Philosophers Stone.txt"));
            br = new BufferedReader(new FileReader(fi));

            String readedStr;
            String tempStr = "";
            //HashMap<Integer,String> tempStr = new HashMap<>();
            //int i = 0;
            while ((readedStr = br.readLine()) != null) {
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
                        if (containsInDict(str, dictHash)) {
                            System.out.println(str);
                            writeToFile(str, fo);
                        }
                        tempStr = "";
                    }
                }
                Matcher mAll;
                mAll = pAll.matcher(readedStr);
                while (mAll.find()) {
                    String str = mAll.group();
                    //System.out.println(str);
                    if (containsInDict(str, dictHash)) {
                        System.out.println(str);
                        writeToFile(str, fo);
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

    boolean containsInDict(String st, HashSet<String> dic) {
        st = st.toLowerCase();
        HashSet<String> setWords = new HashSet<>();
        //System.out.println(st);
        String mustBeDestroyed = "([^а-яa-z]+)";
        st = st.replaceAll(mustBeDestroyed, " ");
        Collections.addAll(setWords, st.split("\\s+"));
        if (!Collections.disjoint(setWords, dic)) {
            //System.out.println(st);
            return true;
        }
        return false;
    }

    @Override
    public void getOccurencies(String[] sources, String[] words, String res) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(res);
        pw.write("");
        pw.close();
        getDictHashSet(words);
        checkContain(sources, res);

        Thread[] threadMas = new Thread[2];
        for (int i = 0; i < threadMas.length; i++) {
            threadMas[i] = new WorkingThread(this, sources, res);
            threadMas[i].start();

        }
        //2 потока 30sec
        //3 потока 31sec
        //5 потоков 32sec
        for (Thread i : threadMas) {
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
