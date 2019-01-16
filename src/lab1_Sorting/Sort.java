package lab1_Sorting;

/**
 * Класс содержит метод сортировки пузырьком
 */
public class Sort {
    /**
     * Статичекий метод сортировки пузырьком
     *
     * @param arrInt массив типа Integer, который необходимо отсортировать
     * @return возвращает отсортированный массив типа Integer
     */
    public static Integer[] doSort(Integer[] arrInt) {
        int buf;
        int len = arrInt.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (arrInt[j] > arrInt[j + 1]) {
                    buf = arrInt[j];
                    arrInt[j] = arrInt[j + 1];
                    arrInt[j + 1] = buf;
                }
            }
        }
        return arrInt;
    }

}

