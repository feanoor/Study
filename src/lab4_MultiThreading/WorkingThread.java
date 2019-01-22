package lab4_MultiThreading;


public class WorkingThread extends Thread {
    String[] source;
    String output;
    ContainChecker ch;

    @Override
    public void run() {
        System.out.println("Поток " + this.getName() + " запущен!");
        //ContainChecker cr = new ContainChecker();
        ch.checkContain(source, output);
    }

    WorkingThread(ContainChecker ch, String[] source, String output) {
        this.ch = ch;
        this.output = output;
        this.source = source;
    }
}
