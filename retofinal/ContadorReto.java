package retofinal;

/**
 * Resultado de una implementacion en el modo opcional de "reto por tiempo":
 * cuantas colecciones alcanzo a ordenar dentro del limite y su tiempo
 * promedio por coleccion.
 *
 * @author friki
 */
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
