import java.util.Arrays;

public class Ordena implements Runnable{
    int[] vetorNum;
    int inicio;
    int fim;

    public Ordena(int[] vetor, int inicio, int fim){
        //System.out.println("inicio: " + inicio + " fim: " + fim);
        this.vetorNum = vetor;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
	public void run() {
        Arrays.sort(this.vetorNum, this.inicio, this.fim);
    }
}