package Experiments.Proxy;

import java.lang.reflect.Proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        Trainer trainer = new TrainerImpl();
        trainer.eat("курицу");
        trainer.talk();
        trainer.teach();

        System.out.println("Создание супер-тренера:");
        TrainerMan handle = new TrainerMan(trainer);

        Trainer man = (Trainer) Proxy.newProxyInstance(
                ProxyDemo.class.getClassLoader(),
                new Class[]{Trainer.class},
                handle
        );

        System.out.println("съедено: " + man.eat("грушу"));
        System.out.println("Сказано: " + man.talk());
        man.teach();
        System.out.println(Math.random());
        System.out.println(man.getClass());
        System.out.println(man.getClass().getClassLoader());
        System.out.println(Math.class);
        System.out.println(Math.class.getClassLoader());
    }
}