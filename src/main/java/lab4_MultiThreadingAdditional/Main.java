package lab4_MultiThreadingAdditional;

public class Main {
    public static void main(String[] args) {
        MyThreadPool mtp = new MyThreadPool(5);
        mtp.startHandling();
        int i = 5;
        while (i != 0) {
            mtp.addTask(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " "
                            + "Начал свою работу.");
                    System.out.println("Результат операции = " + (2 + 2));

                }
            });
            i--;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mtp.stopThreadPool();
    }
}
