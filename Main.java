import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class Main{
    public static void main(String[] args){
        final int TAMANHO_VETOR = 50_000_000;
        int[] vetorReferencia = new int[TAMANHO_VETOR];
        // Gera valores aleatorios
        for(int i = 0; i < vetorReferencia.length; i++) {
            // Valores no intervalo [ 0; 2*TAMANHO-VETOR ]
            int numero = (int) Math.round(Math.random() * 2 * TAMANHO_VETOR);
            vetorReferencia[i] = numero;
        }
//        System.out.println(Arrays.toString(vetorReferencia));
        int[] k = {8, 4, 2, 1};
        ArrayList<Thread> threads = new ArrayList<>();
        // Executa k threads por vez
        for(int numThreadsInicial : k) {
            int[] vetor = vetorReferencia;
            double numThreads = numThreadsInicial;
            System.out.println("k inicial : " + numThreadsInicial + " {");
            while(numThreads >= 1) {
                // Porcentagem do vetor que uma thread deve ordenar
                double fracao = 1.0 / numThreads;
                System.out.println("\tk efetivo : " + numThreads + " {");
                for (int j = 0; j < numThreads; j++) {
                    // (j * fracao) e' o percentil que indica onde essa thread deve comecar
                    int inicio = (int) Math.round(Math.floor(j * fracao * TAMANHO_VETOR));
                    // (inicio * fracao) e' o percentil que indica onde essa thread deve terminar
                    int fim = (int) (inicio + fracao * TAMANHO_VETOR);
                    Ordena ordena = new Ordena(vetor, inicio, fim);
                    Thread thread = new Thread(ordena);
                    threads.add(thread);
                    thread.start();
                    System.out.println("\t\tinicio : " + inicio);
                    System.out.println("\t\tfim : " + fim);
                }
                for (Thread thread : threads) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                threads.clear();
                numThreads /= 2;
                System.out.println("\t}");
            }
            System.out.println("}");
//            System.out.println(Arrays.toString(vetor));
        }
    }
}