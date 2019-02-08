package lab4_MultiThreadingAdditional;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread extends Thread {
    private BlockingQueue<Runnable> q;
    private Condition cond;
    private ReentrantLock lock;
    private Runnable task;
    protected Boolean exitThread = false;

    /**
     * Конструктор получает MyThreadPool, и заполняет свои поля ссылками,
     * взятыми из переданного объекта.
     *
     * @param tp
     */
    protected MyThread(MyThreadPool tp) {
        super();
        this.q = tp.queye;
        this.cond = tp.cond;
        this.lock = tp.lock;
    }

    /**
     * Берет Runnable из очереди задач и выполняет его. Если новых задач нет то ждет.
     * Завершает свою работу когда флаг exitThread становится true.
     */
    @Override
    public void run() {
        //System.out.println("Поток " + Thread.currentThread() + " запущен");
        while (!exitThread) {
            //System.out.println("Поток " + Thread.currentThread() + " проверяет есть ли задачи");
            if ((task = q.poll()) != null) {
                task.run();
            } else {
                try {
                    lock.lock();
                    //System.out.println("Поток " + Thread.currentThread() + " засыпает");
                    cond.await();
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
