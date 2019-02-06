package lab4_MultiThreading;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContainChecker implements Occurenciesable {
    static Pattern pAll = Pattern.compile("([A-ZА-Я][^.?!…]*[.!?…])");
    static Pattern pLeft = Pattern.compile("([A-ZА-Я][^.?!…]*$)");
    static Pattern pRight = Pattern.compile("([^A-ZА-Я.?!…]*[.!?…])");
    static HashSet<String> dictHash;
    static ReentrantLock rLock2 = new ReentrantLock();
    private String outputFile;

    /**
     * Типы ресурсов
     */
    enum typeResource {
        URL,
        FILE,
        FTP
    }

    /**
     * Преобразует массив слов словаря в HashMap.
     *
     * @param str Массив слов словаря типа String.
     */
    void getDictHashSet(String[] str) {
        dictHash = new HashSet<>();
        dictHash.addAll(Arrays.asList(str));
    }

    /**
     * Определяет тип ресурса.
     *
     * @param resource Адрес ресурса.
     */
    public void checkResourceType(String resource) throws IOException {
        if (resource.startsWith("http")) {
            checkEquals(checkResource(typeResource.URL,resource), outputFile);
        } else {
            if (resource.startsWith("ftp")) {
                checkEquals(checkResource(typeResource.URL,resource), outputFile);
            } else {
                checkEquals(checkResource(typeResource.URL,resource), outputFile);
            }
        }
    }

    /**
     * Осуществляет запись найденных предложений в файл.
     *
     * @param str  Предложение которое нужно записать.
     * @param file Адрес файла.
     */
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

    /**
     * Метод проверяет доступность ftp или http ресурса.
     *
     * @param resource Адрес типа String.
     * @return Если ресурс доступен возвращает false, иначе true.
     */
    boolean resAvailable(String resource) {
        try {
            new URL(resource).openConnection().getInputStream();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private BufferedReader checkResource(typeResource typeR, String fi) throws IOException {
        BufferedReader br = null;
        if (typeR.equals(typeResource.FILE)) {
            br = new BufferedReader(new FileReader(fi));
        }
        if (typeR.equals(typeResource.URL) | typeR.equals(typeResource.FTP)) {
            if (!resAvailable(fi)) {
                return null;
            }
            br = new BufferedReader(new InputStreamReader(new URL(fi).openConnection().getInputStream()));
        }
        return br;
    }



    /**
     * Метод проверяет по шаблону считанные строки и собирает их в предложения.
     *
     * @param fo    Адрес выходного файла.
     */
    void checkEquals(BufferedReader br, String fo) {
        try {
//            BufferedReader br = checkResource(typeR, fi);
            if (br.equals(null)) {
                return;
            }
            String readedStr;
            String tempStr = "";
            while ((readedStr = br.readLine()) != null) {
                if (tempStr == "") {
                    Matcher mLeft;
                    mLeft = pLeft.matcher(readedStr);
                    if (mLeft.find()) {
                        String str = mLeft.group();
                        tempStr += str;
                    }
                }
                if (tempStr != "") {
                    Matcher mRight;
                    mRight = pRight.matcher(readedStr);
                    if (mRight.find()) {
                        String str = mRight.group();
                        tempStr += str;
                        if (containsInDict(tempStr, dictHash)) {
                            System.out.println("записываем: -------"  + tempStr);
                            writeToFile(tempStr, fo);
                        }
                        tempStr = "";
                    }
                }
                Matcher mAll;
                mAll = pAll.matcher(readedStr);
                while (mAll.find()) {
                    String str = mAll.group();
                    if (containsInDict(str, dictHash)) {
                        System.out.println("записываем: -------"  + str);
                        writeToFile(str, fo);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет содержится ли в данном предложении слова из словаря.
     *
     * @param st  Предложение типа String.
     * @param dic Словарь, слова из которого ищем в предложении.
     * @return true, если найдено хотя бы одно слово, иначе false.
     */
    boolean containsInDict(String st, HashSet<String> dic) {
        st = st.toLowerCase();
        HashSet<String> setWords = new HashSet<>();
        String mustBeDestroyed = "([^а-яa-z]+)";
        st = st.replaceAll(mustBeDestroyed, " ");
        Collections.addAll(setWords, st.split("\\s+"));
        if (!Collections.disjoint(setWords, dic)) {
            return true;
        }
        return false;
    }

    /**
     * Очищает выходной файл, создает ThreadPool, который раздает задания потокам.
     *
     * @param sources Массив ресурсов (http, ftp, txt) типа String.
     * @param words   Массив искомых слов типа String.
     * @param res     Адрес выходного файла типа String.
     * @throws FileNotFoundException
     */
    @Override
    public void getOccurencies(String[] sources, String[] words, String res) throws FileNotFoundException {
        outputFile = res;
        PrintWriter pw = new PrintWriter(res);
        pw.write("");
        pw.close();
        getDictHashSet(words);
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (String s : sources) {
            service.submit(new WorkingThread(this, s));
        }
        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(service.isTerminated());
    }
}
