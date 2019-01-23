package lab5_Reflection;

public class Main {
    public static void main(String[] args) {
        Serializator ser = new Serializator();
        //Alpha alp = new Alpha();
        //ser.serialize(alp, "./serclass.txt" );
        Alpha a = (Alpha) ser.deSerialize("./serclass.txt");
        System.out.println(a.surname);
    }
}
