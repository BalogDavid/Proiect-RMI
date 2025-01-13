import java.rmi.Naming;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VectorSumClient {

    public static void main(String[] args) {
        try {
            // Conectare la server
            VectorSum server = (VectorSum) Naming.lookup("rmi://localhost/VectorSumService");

            // Crearea vectorului mare
            int[] vector = new int[30000];
            for (int i = 0; i < vector.length; i++) {
                vector[i] = i + 1; // Exemplu: valori 1, 2, 3, ...
            }

// Împărțirea vectorului în 3 segmente (fiecare de 10000 elemente)
            int segmentSize = 10000;
            int[] segment1 = java.util.Arrays.copyOfRange(vector, 0, segmentSize);
            int[] segment2 = java.util.Arrays.copyOfRange(vector, segmentSize, 2 * segmentSize);
            int[] segment3 = java.util.Arrays.copyOfRange(vector, 2 * segmentSize, vector.length);

            // ExecutorService pentru firele de execuție
            ExecutorService executor = Executors.newFixedThreadPool(3);

            // Măsurarea timpului de execuție
            long startTime = System.currentTimeMillis();

            // Crearea task-urilor pentru fiecare segment
            Callable<Integer> task1 = () -> server.calculateSum(segment1);
            Callable<Integer> task2 = () -> server.calculateSum(segment2);
            Callable<Integer> task3 = () -> server.calculateSum(segment3);

            // Lansarea firelor
            Future<Integer> future1 = executor.submit(task1);
            Future<Integer> future2 = executor.submit(task2);
            Future<Integer> future3 = executor.submit(task3);

            // Obținerea rezultatelor fiecărui segment
            int sum1 = future1.get();
            int sum2 = future2.get();
            int sum3 = future3.get();

            // Calcularea sumei totale
            int totalSum = sum1 + sum2 + sum3;

            long endTime = System.currentTimeMillis();

            // Afișarea rezultatelor
            System.out.println("Suma segmentului 1: " + sum1);
            System.out.println("Suma segmentului 2: " + sum2);
            System.out.println("Suma segmentului 3: " + sum3);
            System.out.println("Suma totală: " + totalSum);
            System.out.println("Timp de execuție: " + (endTime - startTime) + " ms");

            // Închide executorul
            executor.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}