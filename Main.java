import java.lang.Math;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args){

        final int TAMANHO_VETOR = 50_000_000;
        int[] vetorReferencia = new int[TAMANHO_VETOR];

        // Gera valores aleatorios
        for(int i = 0; i < vetorReferencia.length; i++) {
            // Math.random() retorna valores de 0 a 1 em ponto flutuante
            // Queremos valores no intervalo [ 0; 2*TAMANHO-VETOR ]
            double numeroAleatorioDouble = Math.random() * (2 * TAMANHO_VETOR);
            int numeroAleatorioInt = (int) Math.round(numeroAleatorioDouble);
            vetorReferencia[i] = numeroAleatorioInt;
        }

        // Valores para o numero de threads inicial em cada ordenacao completa
        int[] k = {8, 4, 2, 1};

        ArrayList<Dados> dados = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();

        // kInicial sera' o primeiro numero de threads executando sobre o vetor inicial
        // antes de um k = k/2
        for(int kInicial : k) {

            Dados dado = new Dados(kInicial);
            System.out.println("Ordenando vetor com k inicial = " + kInicial);

            // Executa 10 vezes a ordenacao completa para um determinado valor de k inicial
            for(int i = 1; i <= 10; i++) {

                int[] vetor = vetorReferencia.clone(); // Recupera vetor de referencia

                // kEfetivo sera' o numero decrescente de threads executando sobre o vetor
                double kEfetivo = kInicial; // Recupera numero de threads inicial
                long tempoInicial = System.nanoTime();

                while (kEfetivo >= 1) {

                    // Porcentagem do vetor que uma thread deve ordenar
                    double fracao = 1.0 / kEfetivo;

                    // Inicia todas as k threads
                    for (int j = 0; j < kEfetivo; j++) {

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
                    kEfetivo /= 2;  // Atualiza o valor de k
                }

                long tempoFinal = System.nanoTime();

                // Conversao do tempo em nanossegundos para segundos
                double tempoSegundos = (double) (tempoFinal - tempoInicial) / 1_000_000_000;
                dado.addTempo(tempoSegundos);

                System.out.print("Execucao " + i + " finalizada. Tempo decorrido: ");
                System.out.print(String.format("%.5f", tempoSegundos) + " s\n");
            }

            System.out.print("\n");
            dados.add(dado);
        }

        System.out.println("Relatorio");
        for(Dados dado : dados) {
            System.out.println("k: " + dado.getK());
            System.out.println("media: " + String.format("%.5f", dado.getMediaTempo()) + " s");
            System.out.println("desvio padrao: " + String.format("%.5f", dado.getDesvioPadraoTempo()) + " s");
        }
    }
}