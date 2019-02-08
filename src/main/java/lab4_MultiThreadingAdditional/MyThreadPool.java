package lab4_MultiThreadingAdditional;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Этот класс осуществляет процесс работы тредпула.
 */
public class MyThreadPool {
    private Thread[] pool;
    private Thread mainThread;
    protected BlockingQueue<Runnable> queye = new LinkedBlockingQueue<>();
    private int countThreads;
    protected ReentrantLock lock = new ReentrantLock();
    protected Condition cond = lock.newCondition();

    /**
     * В конструктор передается число потоков, которые мы хотим использовать.
     *
     * @param countThreads
     */
    public MyThreadPool(int countThreads) {
        this.countThreads = countThreads;
        pool = new MyThread[countThreads];
        createPool();
    }

    /**
     * Здесь запускается поток самого тредпула, что позволяет запустившему этот тредпул потоку
     * продолжать выполнение кода, а не ждать пока тредпул завершит свою работу.
     */
    void startHandling() {
        mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Thread t : pool) {
                    t.start();
                }
                for (Thread t : pool) {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mainThread.start();
    }

    /**
     * Останавливает тредпул, останавливает все потоки, которые он инициализировал.
     */
    void stopThreadPool() {
        //System.out.println("Инициирована остановка тредпула.");
        for (Thread t : pool) {
            ((MyThread) t).exitThread = true;
        }
        lock.lock();
        cond.signalAll();
        lock.unlock();
    }

    /**
     * Добавляет задачу на выполнение тредпулу. Будит все потоки, ожидающие новых задач.
     *
     * @param task Задача типа Runnable.
     */
    void addTask(Runnable task) {
        queye.add(task);
        lock.lock();
        cond.signalAll();
        //System.out.println("Появилась задача!");
        lock.unlock();
    }

    /**
     * Инициализирует пул потоков.
     */
    private void createPool() {
        for (int i = 0; i < countThreads; i++) {
            pool[i] = new MyThread(this);
            //System.out.println(pool[i].getName() + " поток создан.");
        }
    }

}
