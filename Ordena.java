import java.util.Arrays;

public class Ordena implements Runnable{
    int[] vetorNum;
    int inicio;
    int fim;

    public Ordena(int[] vetor, int inicio, int fim){
        this.vetorNum = vetor;
    }

    @Override
	public void run() {
        Arrays.sort(this.vetorNum, this.inicio, this.fim);
    }
}