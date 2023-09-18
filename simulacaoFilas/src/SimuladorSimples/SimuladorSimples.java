package SimuladorSimples;

import java.util.PriorityQueue;
import java.util.Random;

public class SimuladorSimples {

    private final int TAMANHO_FILA;
    private final int SERVIDORES;
    private final Random random;
    private final double CHEGADA_MIN;
    private final double CHEGADA_MAX;
    private final double ATENDIMENTO_MIN;
    private final double ATENDIMENTO_MAX;

    private int aleatoriosGerados = 0;
    private double totalTempo = 0;
    private int totalClientes = 0;

    public SimuladorSimples(int tamanhoFila, int servidores, long seed, double chegadaMin, double chegadaMax, double atendimentoMin, double atendimentoMax) {
        this.TAMANHO_FILA = tamanhoFila;
        this.SERVIDORES = servidores;
        this.random = new Random(seed);
        this.CHEGADA_MIN = chegadaMin;
        this.CHEGADA_MAX = chegadaMax;
        this.ATENDIMENTO_MIN = atendimentoMin;
        this.ATENDIMENTO_MAX = atendimentoMax;
    }

    private double rnd(double min, double max) {
        aleatoriosGerados++;
        return min + (max - min) * random.nextDouble();
    }

    private class Evento implements Comparable<Evento> {
        double tempo;
        TipoEvento tipo;

        Evento(double tempo, TipoEvento tipo) {
            this.tempo = tempo;
            this.tipo = tipo;
        }

        @Override
        public int compareTo(Evento o) {
            return Double.compare(this.tempo, o.tempo);
        }
    }


    public void simular(int maxAleatorios) {
        PriorityQueue<Evento> eventos = new PriorityQueue<>();
        eventos.add(new Evento(3.0, TipoEvento.CHEGADA));

        int fila = 0;
        double lastEventTime = 0;
        while (!eventos.isEmpty() && aleatoriosGerados < maxAleatorios) {
            Evento e = eventos.poll();
            double TEMPO = e.tempo;

            totalTempo += (TEMPO - lastEventTime) * fila;
            lastEventTime = TEMPO;

            if (e.tipo == TipoEvento.CHEGADA) {
                System.out.println("Chegada no tempo: " + TEMPO);
                if (fila < TAMANHO_FILA) {
                    fila++;
                    if (fila <= SERVIDORES) {
                        eventos.add(new Evento(TEMPO + rnd(ATENDIMENTO_MIN, ATENDIMENTO_MAX), TipoEvento.SAIDA));
                    }
                    eventos.add(new Evento(TEMPO + rnd(CHEGADA_MIN, CHEGADA_MAX), TipoEvento.CHEGADA));
                }
            } else {
                System.out.println("Saída no tempo: " + TEMPO);
                fila--;
                if (fila >= SERVIDORES) {
                    eventos.add(new Evento(TEMPO + rnd(ATENDIMENTO_MIN, ATENDIMENTO_MAX), TipoEvento.SAIDA));
                }
            }

            totalClientes++;
        }
    }

    public double getMediaTempoPorCliente() {
        return totalClientes == 0 ? 0 : totalTempo / totalClientes;
    }

}
