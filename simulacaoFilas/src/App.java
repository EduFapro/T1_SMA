import SimuladorSimples.SimuladorSimples;

public class App {
    public static void main(String[] args) {
        final int NUM_EXECUCOES = 5;
        final int MAX_ALEATORIOS = 100000;

        double totalMedia1 = 0;
        double totalMedia2 = 0;

        for (int i = 0; i < NUM_EXECUCOES; i++) {
            SimuladorSimples simulador1 = new SimuladorSimples(5, 1, i, 1, 3, 2, 4);
            simulador1.simular(MAX_ALEATORIOS);
            totalMedia1 += simulador1.getMediaTempoPorCliente();

            SimuladorSimples simulador2 = new SimuladorSimples(5, 2, i, 1, 3, 2, 4);
            simulador2.simular(MAX_ALEATORIOS);
            totalMedia2 += simulador2.getMediaTempoPorCliente();
        }

        System.out.println("Média G/G/1/5: " + (totalMedia1 / NUM_EXECUCOES));
        System.out.println("Média G/G/2/5: " + (totalMedia2 / NUM_EXECUCOES));
    }
}
