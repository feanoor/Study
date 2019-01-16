package lab1_Sorting;

/**
 * Класс содержит метод сортировки пузырьком
 */
public class Sort {
    /**
     * Статичекий метод сортировки пузырьком
     *
     * @param arrInt массив типа Integer, который необходимо отсортировать
     * @return возвращает отсортированный массив типа Integer, или null, если элемент содержит
     * null значение, или ссылка массива null
     */
    public static Integer[] doSort(Integer[] arrInt) {
        if (arrInt == null) {
            try {
                throw new MyArrayExceprion("Массив не инициализирован");
            } catch (MyArrayExceprion ex) {
                System.out.println(ex.toString());
                return null;
            }
        }
        int buf;
        int len = arrInt.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (arrInt[j] == null | arrInt[j + 1] == null) {
                    try {
                        throw new MyArrayExceprion("Элемент содержит null значение");
                    } catch (MyArrayExceprion ex) {
                        System.out.println(ex.toString());
                        return null;
                    }
                }
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

