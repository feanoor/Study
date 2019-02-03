package lab4_MultiThreading;

/**
 * Класс который выполняет обработку ресурсов. Каждый ресурс обрабатывается отдельным потоком.
 */
public class WorkingThread extends Thread {
    String source;
    ContainChecker ch;

    /**
     * Метод run инициализирует обработку ресурса.
     */
    @Override
    public void run() {
        ch.checkResourceType(source);
    }

    /**
     * При инициализации класса, выполняется данный конструктор.
     *
     * @param ch     Экземпляр класса ContainChecker.
     * @param source Адрес ресурса.
     */
    WorkingThread(ContainChecker ch, String source) {
        this.ch = ch;
        this.source = source;
    }
}
