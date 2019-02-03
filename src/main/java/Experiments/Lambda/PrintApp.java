package Experiments.Lambda;
interface Printable{
    void print(String s);
}
public class PrintApp {
    public static void main(String[] args) {
        Printable printer = (s) -> System.out.println(s);

        printer.print("ffa");
    }
}
