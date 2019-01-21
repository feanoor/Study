package lab4_MultiThreading;

public class WorkingThread extends Thread {
    @Override
    public void run() {
        System.out.println("Поток " + this.getName() + " запущен!");
        ContainChecker cr = new ContainChecker();
        cr.checkContain();
    }
}
