package Experiments.Lambda;

public class LambdaApp {

    public static void main(String[] args) {

        Operationable operation;
        operation = (x,y) -> x*2*y;

        System.out.println(operation.calculate(1,2)); //30
    }
}
interface Operationable{
    int calculate(int x, int y);
}