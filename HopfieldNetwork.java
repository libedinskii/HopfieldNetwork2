import java.util.Arrays;

public class HopfieldNetwork {
    private int[][] weightMatrix;
    private int size;

    public HopfieldNetwork(int size) {
        this.size = size;
        this.weightMatrix = new int[size][size];
    }

    // Метод для обучения сети паттерну
    public void train(int[] pattern) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    weightMatrix[i][j] += pattern[i] * pattern[j];
                }
            }
        }
    }

    // Метод для восстановления паттерна
    public int[] recall(int[] inputPattern) {
        int[] outputPattern = Arrays.copyOf(inputPattern, size);
        boolean changed;

        do {
            changed = false;

            for (int i = 0; i < size; i++) {
                int netInput = 0;

                // Вычисление взвешенной суммы
                for (int j = 0; j < size; j++) {
                    netInput += weightMatrix[i][j] * outputPattern[j];
                }

                // Применение функции активации (знак)
                int newValue = (netInput >= 0) ? 1 : -1;

                if (newValue != outputPattern[i]) {
                    outputPattern[i] = newValue;
                    changed = true; // Произошло изменение
                }
            }
        } while (changed); // Повторяем, пока есть изменения

        return outputPattern;
    }

    public static void main(String[] args) {
        // Создание сети с размером 4 (для 4-битных образов)
        HopfieldNetwork hopfieldNetwork = new HopfieldNetwork(4);
        
        // Обучение сети с двумя образами
        int[] pattern1 = {1, 1, -1, -1}; // Образ 1
        int[] pattern2 = {1, -1, 1, -1}; // Образ 2
        
        hopfieldNetwork.train(pattern1);
        hopfieldNetwork.train(pattern2);

        // Тестирование восстановления образа с шумом
        int[] input1 = {1, 1, -1, -1}; // Идеальный ввод
        int[] noisyInput = {1, -1, -1, -1}; // Ввод с помехами

        System.out.println("Restoring pattern from input: " + Arrays.toString(input1));
        int[] restoredPattern1 = hopfieldNetwork.recall(input1);
        System.out.println("Restored pattern: " + Arrays.toString(restoredPattern1));

        System.out.println("\nRestoring pattern from noisy input: " + Arrays.toString(noisyInput));
        int[] restoredPattern2 = hopfieldNetwork.recall(noisyInput);
        System.out.println("Restored pattern: " + Arrays.toString(restoredPattern2));
    }
}
