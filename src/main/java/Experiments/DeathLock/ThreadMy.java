package Experiments.DeathLock;

public class ThreadMy extends Thread {
    Object a;
    Object b;

    public ThreadMy(Object a, Object b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        synchronized (a) {
            try {
                System.out.println("Объект " + a.toString() + " Занят потоком " + Thread.currentThread().getName());
                Thread.sleep((int)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (b) {
                System.out.println("Объект " + b.toString() + " Занят потоком " + Thread.currentThread().getName());
            }
        }

    }
}