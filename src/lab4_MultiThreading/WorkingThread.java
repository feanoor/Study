package lab4_MultiThreading;

import java.io.FileNotFoundException;

public class WorkingThread extends Thread {
    String[] source;
    String[] dict;
    String output;
    @Override
    public void run() {
        System.out.println("Поток " + this.getName() + " запущен!");
        ContainChecker cr = new ContainChecker();
        try {
            cr.getOccurencies(source,dict,output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    WorkingThread(String[] source, String[] dict, String output)
    {
        this.dict = dict;
        this.output = output;
        this.source = source;
    }
}
