package Experiments.DeathLock;

public class Main {
    public static void main(String[] args) {
        String a = "a";
        String b = "b";
        Thread t1 = new ThreadMy(a,b);
        Thread t2 = new ThreadMy(b,a);
        t1.start();
        t2.start();
    }
}
