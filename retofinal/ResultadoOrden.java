package retofinal;

public class ResultadoOrden {
    private final String algoritmo;
    private final String estructura;
    private final double tiempoMs;
    private final boolean ordenadoCorrectamente;

    public ResultadoOrden(String algoritmo, String estructura, double tiempoMs, boolean ordenadoCorrectamente) {
        this.algoritmo = algoritmo;
        this.estructura = estructura;
        this.tiempoMs = tiempoMs;
        this.ordenadoCorrectamente = ordenadoCorrectamente;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public String getEstructura() {
        return estructura;
    }

    public double getTiempoMs() {
        return tiempoMs;
    }

    public boolean isOrdenadoCorrectamente() {
        return ordenadoCorrectamente;
    }
}
