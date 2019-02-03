package Experiments.Lambda;
interface Equals{
    boolean isEquals(int n);
}
public class LambdaSumMas {
    public static void main(String[] args) {
        int[] mas = {1,2,3,4,5,6,7,8};
        Equals checkDivTwo = (i) -> i%2==0;
        System.out.println(summator(mas,checkDivTwo));

    }


    public static  int summator(int[] mas, Equals func){
        int sum = 0;
        for (int i : mas){
            if(func.isEquals(i)){
                sum += i;
            }
        }
        return sum;
    }




}
