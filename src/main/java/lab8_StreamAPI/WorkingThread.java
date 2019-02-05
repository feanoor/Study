package lab8_StreamAPI;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Класс который выполняет обработку ресурсов. Каждый ресурс обрабатывается отдельным потоком.
 */
public class WorkingThread extends Thread {
    String source;
    String target;
    ContainChecker ch;
    ResourceLoader rl = new ResourceLoader();;
    BufferedReader br;


    /**
     * Метод run инициализирует обработку ресурса.
     */
    @Override
    public void run() {
        try {
            br = rl.getBufferedReader(source);
            ch.checkEquals(br, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * При инициализации класса, выполняется данный конструктор.
     *
     * @param ch     Экземпляр класса ContainChecker.
     * @param source Адрес ресурса.
     * @param target Выходной файл.
     */
    WorkingThread(ContainChecker ch, String source, String target) {
        this.ch = ch;
        this.source = source;
        this.target = target;
    }
}
