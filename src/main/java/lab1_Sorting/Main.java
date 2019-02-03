package lab1_Sorting;

public class Main {

    public static void main(String[] args) {
        Integer[] mas = new Integer[10];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = (int) (Math.random() * 100);
        }
        System.out.println("Исходный массив:");
        print(mas);
        Sort.doSort(mas);
        System.out.println("Отсортированный массив:");
        print(mas);

    }

    public static void print(Integer[] arrInt) {
        for (Integer m : arrInt)
            System.out.println(m);
    }
}
