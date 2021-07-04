import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class Main{
    public static void main(String[] args){
        final int TAMANHO_VETOR = 50_000_000;
        int[] vetor = new int[TAMANHO_VETOR];
        // Gera valores aleatorios
        for(int i = 0; i < vetor.length; i++) {
                // Valores no intervalo [ 0; 2*TAMANHO-VETOR ]
            int numero = (int) Math.round(Math.random() * 2 * TAMANHO_VETOR);
            vetor[i] = numero;
        }
        int[] k = {8, 4, 2, 1};
        ArrayList<Thread> threads = new ArrayList<>();
        // Executa k threads por vez
        for(int numThreads : k) {
            // Porcentagem do vetor que uma thread deve ordenar
            double fracao = 1.0 / numThreads;
            for (int j = 0; j < numThreads; j++) {
                // (j * fracao) e' o percentil que indica onde essa thread deve comecar
                int inicio = (int) Math.round(Math.floor(j * fracao * TAMANHO_VETOR));
                // ((j + 1) * fracao) e' o percentil que indica onde essa thread deve terminar
                int fim = (int) Math.round(Math.ceil((j + 1) * fracao * TAMANHO_VETOR - 1));
                Ordena ordena = new Ordena(vetor, inicio, fim);
                Thread thread = new Thread(ordena);
                threads.add(thread);
                thread.start();
            }
            for(Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            threads.clear();
        }
//        System.out.println(Arrays.toString(vetor));
    }
}