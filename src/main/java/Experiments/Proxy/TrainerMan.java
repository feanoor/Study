package Experiments.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TrainerMan implements InvocationHandler {

    private final Trainer trainer;

    public TrainerMan(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print("С удовольствием ");
        if ("talk".equals(method.getName())) {
            System.out.println("кричу");
            return "Крик";
        }
        Object o = method.invoke(trainer, args);
        return o;
    }
}