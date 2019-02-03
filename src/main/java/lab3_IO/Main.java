package lab3_IO;

public class Main {
    public static void main(String[] args) {
        int probability = 1000;
        int n = 1;
        int size = 20;
        String path = "C://docs//";
        TextGenerator tg = new TextGenerator();
        tg.getFiles(path,n,size,tg.dict,probability,"https://bookfrom.net/j-k-rowling/1301-harry_potter_and_the_philosophers_stone.html");
        System.out.println("Text:");
        System.out.println(tg.getTextByType());
        System.out.println("\n\nDictionary:");
        //tg.getDict();
        tg.printDict();
        tg.getFiles(path,n,size,tg.dict,probability);
        //tg.getFiles(path,n,size,tg.dict,probability,"https://bookfrom.net/j-k-rowling/1301-harry_potter_and_the_philosophers_stone.html");
    }
}
