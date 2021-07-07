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
        // Valores para o numero de threads inicial em cada ordenacao completa
        int[] k = {8, 4, 2, 1};
        ArrayList<Dados> dados = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();
        // Executa 10 vezes a ordenacao completa para um determinado valor de k inicial
        for(int numThreadsInicial : k) {
            Dados dado = new Dados(numThreadsInicial);
            for(int i = 1; i <= 10; i++) {
                int[] vetor = vetorReferencia.clone(); // Recupera vetor de referencia
                double numThreads = numThreadsInicial; // Recupera numero de threads inicial
                long tempoInicial = System.nanoTime();
                while (numThreads >= 1) {
                    // Porcentagem do vetor que uma thread deve ordenar
                    double fracao = 1.0 / numThreads;
                    // Inicia todas as k threads
                    for (int j = 0; j < numThreads; j++) {
                        // (j * fracao) e' o percentil que indica onde essa thread deve comecar
                        int inicio = (int) Math.round(Math.floor(j * fracao * TAMANHO_VETOR));
                        // (inicio * fracao) e' o percentil que indica onde essa thread deve terminar
                        int fim = (int) (inicio + fracao * TAMANHO_VETOR);
                        Ordena ordena = new Ordena(vetor, inicio, fim);
                        Thread thread = new Thread(ordena);
                        threads.add(thread);
                        thread.start();
                    }
                    // Espera a finalizacao de todas as k threads
                    for (Thread thread : threads) {
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    threads.clear();
                    numThreads /= 2;  // Atualiza o valor de k
                }
                long tempoFinal = System.nanoTime();
                // Conversao do tempo em nanossegundos para segundos
                double tempoSegundos = (double) (tempoFinal - tempoInicial) / 1_000_000_000;
                dado.addTempo(tempoSegundos);
                System.out.println("tempo: " + tempoSegundos + " s");
//                System.out.println(Arrays.toString(vetor));
            }
            System.out.print("\n");
            dados.add(dado);
        }
        for(Dados dado : dados) {
            System.out.println("k: " + dado.getK());
            System.out.println("media: " + dado.getMediaTempo());
            System.out.println("desvio padrao: " + dado.getDesvioPadraoTempo());
        }
    }
}