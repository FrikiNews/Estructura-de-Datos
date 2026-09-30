package retofinal;

public class ContadorReto {
    private final String algoritmo;
    private final String estructura;
    private final int coleccionesCompletadas;
    private final double tiempoPromedioMs;

    public ContadorReto(String algoritmo, String estructura, int coleccionesCompletadas, double tiempoPromedioMs) {
        this.algoritmo = algoritmo;
        this.estructura = estructura;
        this.coleccionesCompletadas = coleccionesCompletadas;
        this.tiempoPromedioMs = tiempoPromedioMs;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public String getEstructura() {
        return estructura;
    }

    public int getColeccionesCompletadas() {
        return coleccionesCompletadas;
    }

    public double getTiempoPromedioMs() {
        return tiempoPromedioMs;
    }
}
