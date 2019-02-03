package lab3_IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс предназначен для создания массива слов из html страницы, и предоставляет
 * инстрменты для дальнейшей его обработки
 */
public class TextUrlGetter {

    /**
     * Метод считывает по url html страницу и формирует из нее массив слов
     * @param link адрес рессурса в формате String
     * @return массив String
     */
    public String[] getTextMasUrl(String link) {
        Matcher m1;
        Pattern p1 = Pattern.compile("([a-zA-Z]{1,14})");
        URL url = null;
        ArrayList<String> masStr = new ArrayList();
        int i = 0;
        try {
            url = new URL(link);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                m1 = p1.matcher(line);
                while (m1.find()) {
                    masStr.add(m1.group().toLowerCase());
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return masStr.toArray(new String[0]);
    }
}
