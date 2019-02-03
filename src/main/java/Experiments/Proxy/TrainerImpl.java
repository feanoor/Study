package Experiments.Proxy;

public class TrainerImpl implements Trainer {
    @Override
    public int eat(String something) {
        System.out.println("ем " + something);
        return 2;
    }

    @Override
    public String talk() {
        System.out.println("говорю");
        return "Говорю";
    }

    @Override
    public void teach() {
        System.out.println("учу");
    }
}