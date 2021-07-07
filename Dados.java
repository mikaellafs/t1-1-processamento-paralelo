import java.util.Arrays;

public class Dados {
    private final int k;
    private int tamanhoTempo = 0;
    private final double[] tempo = new double[10];

    public Dados(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }

    public void addTempo(double tempo) {
        this.tempo[tamanhoTempo++] = tempo;
    }

    public double getMediaTempo() {
        double soma = Arrays.stream(tempo).sum();
        return soma / tempo.length;
    }

    public double getDesvioPadraoTempo() {
        double media = getMediaTempo();
        double variancia = 0;
        for(double elemento : tempo) {
            variancia += Math.pow(elemento - media, 2);
        }
        variancia /= tempo.length;
        return Math.sqrt(variancia);
    }
}
