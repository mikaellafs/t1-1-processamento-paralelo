import java.lang.Math;

public class Main{
    public static void main(String[] args){
        int[] vetor = new int[50_000_000];
        // Gera valores aleatorios
        for(int i = 0; i < 50_000_000; i++) {
            // Valores entre 0 e 100_000_000
            int numero = (int) Math.round(Math.random() * 100_000_000);
            vetor[i] = numero;
        }
    }
}