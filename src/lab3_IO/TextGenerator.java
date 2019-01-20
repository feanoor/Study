package lab3_IO;

import lab1_Sorting.MySimpleException;

import java.io.*;
import java.security.AccessController;

/**
 * Функционал класса заключается в предоставлении инструментария для
 * генерации текста и последующим его сохранением его в файлы
 */

public class TextGenerator {
    String[] dict;
    int probability;
    String[] urlDict;
    boolean randomGen;
    int size = 2; //default
    private String url = null;
    String[] masPunct = {". ", "! ", "? "};

    /**
     * При инициализации класса вызывается метод рандомного создания словаря слов (из п.7 домашнего задания)
     */
    public TextGenerator() {
        getDict();
    }

    /**
     * Печатает на экране сформированный словарь
     */
    public void printDict() {
        if (dict != null & dict.length > 0) {
            for (String i : dict) {
                System.out.println(i);
            }
        }
    }

    /**
     * Создает n файлов, размером в size абзацев в файле, и кладет их в путь path
     * @param path путь сохранения полученных файлов
     * @param n количество файлов
     * @param size количество абзацев в файле
     * @param words словарь слов, необходимый для включения одного из его слов каждое из предложений,
     *              с вероятностью 1/probability
     * @param probability вероятность вхождения слова из словаря words в предожения
     */
    public void getFiles(String path, int n, int size, String[] words, int probability) {
        try {
            if (probability < 1) {
                try {
                    throw new MySimpleException("Значение probability не может быть меньше 1!");
                } catch (MySimpleException e) {
                    System.out.println(e.toString());
                    return;
                }
            }
            this.probability = probability;
            this.size = size;
            for (int i = 1; i <= n; i++) {
                FileOutputStream fos = new FileOutputStream(path + "text-" + i + ".txt");
                byte[] buf;
                if (url != null) {
                    buf = getTextByType(url, new TextUrlGetter()).getBytes();
                } else {
                    buf = getTextByType().getBytes();
                }
                fos.write(buf);
                fos.close();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Перегруженный метод, сохраняет функционал, но в качестве слов для составления текста
     * использует слова, считанные с указанного url
     * @param path
     * @param n
     * @param size
     * @param words
     * @param probability
     * @param url ссылка на html страницу типа String
     */
    void getFiles(String path, int n, int size, String[] words, int probability, String url) {
        this.url = url;
        getFiles(path, n, size, words, probability);

    }


    /**
     * Генерирует слово из букв латинского алфавита, сложенные в рандомном порядке
     * количество букв в котором определяется равновероятно
     * из указанного дивпазона (по умолчанию от 1 до 15)
     * @return возвращает слово в формате String
     */
    public String genWord() {
        StringBuilder sb = new StringBuilder();
        int len = getRand(1, 15);
        for (int i = 1; i <= len; i++) {
            sb.append(genChar());
        }
        return sb.toString();
    }

    /**
     * Создает массив из количества слов, количество которых определяется равновероятно
     * из указанного дивпазона (по умолчанию от 1 до 15)
     * @return возвразает массив String
     */
    public String[] getRandWordMas() {
        int len = getRand(1, 15);
        String[] str = new String[len];
        for (int i = 0; i < len; i++) {
            if (!randomGen) {
                str[i] = urlDict[getRand(0, urlDict.length - 1)];
            } else {
                str[i] = genWord();
            }

        }
        return str;
    }

    /**
     * Генерирует предложение из предоставленного массива слов и словаря
     * @param strMas массив слов типа String
     * @param dictionary словарь типа String
     * @return
     */
    public String genSentence(String[] strMas, String[] dictionary) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strMas.length; i++) {
            String word = strMas[i];
            if (i == 0) {
                String str = getWordFromDict(dictionary);
                if (str != null) {
                    word = str;
                }
                word = word.substring(0, 1).toUpperCase() + word.substring(1);
            }
            sb.append(word);
            if (i != strMas.length - 1) {
                if (getRand(1, 2) == 2) {
                    sb.append(", ");
                } else {
                    sb.append(" ");
                }
            } else {
                sb.append(masPunct[getRand(0, 2)]);
            }
        }
        return sb.toString();
    }

    /**
     * Генерирует массив предлодений
     * размерность которого, определяется равновероятно
     * из указанного дивпазона (по умолчанию от 5 до 20)
     * @return массив String
     */
    public String[] getRandSentenceMas() {
        int len = getRand(5, 20);
        String[] str = new String[len];
        for (int i = 0; i < len; i++) {
            str[i] = genSentence(getRandWordMas(), dict);
        }
        return str;
    }

    /**
     * Создает абзац, из массива
     * @param str массив предложений типа String
     * @return String
     */
    public String getParag(String[] str) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        for (int i = 0; i < str.length; i++) {
            sb.append(str[i]);
        }
        return sb.toString();
    }

    /**
     * Создает массив абзацев, размерностью size
     * @return массив String
     */
    public String[] getRandParagMas() {
        int len = size;//getRand(5,10);
        String[] str = new String[len];
        for (int i = 0; i < str.length; i++) {
            str[i] = getParag(getRandSentenceMas());
        }
        return str;
    }

    /**
     * Генерирует текст из переданного массива абзацев
     * @param str массив абзацев
     * @return String
     */

    public String getText(String[] str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length; i++) {
            sb.append(str[i]);
            if (i != str.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Выдывает метод getText(), в момент формирования слов они формируются рандомно
     * @return возвращает текст в формате String
     */
    public String getTextByType() {
        randomGen = true;
        return getText(getRandParagMas());
    }

    /**
     * Выдывает метод getText(), в момент формирования слов они формируются из массива слов,
     * сформированном из html страницы
     * @param url ссылка на html страницу
     * @param tug объект класса TextUrlGetter
     * @return возвращает текст в формате String
     */
    public String getTextByType(String url, TextUrlGetter tug) {
        randomGen = false;
        urlDict = tug.getTextMasUrl(url);
        return getText(getRandParagMas());
    }

    /**
     * Вспомогательный метод, возвращает числов диапазоне [i1,i2]
     * @param i1 нижняя граница диапазона типа int
     * @param i2 верхняя граница диапазона типа int
     * @return число типа int
     */
    private int getRand(int i1, int i2) {
        return (int) (Math.random() * (i2 + 1 - i1) + i1);
    }

    /**
     * Формирвет словарь слов типа массива String, размерностью,
     * которая, определяется равновероятно
     * из указанного дивпазона (по умолчанию от 1 до 1000),
     * и заносит его в переменную dict.
     */
    public void getDict() {
        int len = getRand(1, 1000);
        dict = new String[len];
        for (int i = 0; i < len; i++) {
            dict[i] = genWord();
        }
    }

    /**
     * Получает элемент массива, индекс которого, определяется равновероятно
     * из дивпазона [0,<размерность массива>]
     * @param str
     * @return
     */
    public String getWordFromDict(String[] str) {
        if (getRand(1, probability) == 1) {
            return str[getRand(0, str.length - 1)];
        }
        return null;
    }

    /**
     * Генерирует символ из ascii таблицы, из диапазона латинских букв
     * нижнего регистра.
     * Код символа определяется равновероятно
     * из указанного дивпазона (по умолчанию от 97 до 122)
     * @return
     */
    public char genChar() {
        char c = (char) (getRand(97, 122));
        return c;
    }

}
