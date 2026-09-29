package retofinal;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Una de las 12 implementaciones, lista para correr en su propio Thread.
 *
 * Cada tarea ya recibe su propia copia de los datos (arreglo o lista),
 * hecha antes de crear los hilos, asi que nunca comparte una coleccion
 * modificable con otra tarea. Lo unico que los 12 hilos si comparten es el
 * arreglo "esperado" (los datos ya ordenados que sirven de referencia para
 * verificar) y el mapa de resultados; el primero solo se lee y el segundo
 * es un ConcurrentHashMap, asi que ninguno necesita bloqueos.
 *
 * Dentro de run() solo se mide el tiempo del ordenamiento en si: ni la
 * generacion de numeros, ni la copia, ni la creacion del hilo, ni la
 * verificacion posterior entran en el tiempo medido.
 *
 * @author friki
 */
public class TareaOrdenamiento implements Runnable {

    private final String algoritmo;
    private final String estructura;
    private final int[] arreglo;
    private final List<Integer> lista;
    private final int[] esperado;
    private final OrdenadorArreglo ordenadorArreglo;
    private final OrdenadorLista ordenadorLista;
    private final ConcurrentHashMap<String, ResultadoOrden> resultados;

    public TareaOrdenamiento(String algoritmo, int[] arreglo, int[] esperado, OrdenadorArreglo ordenador,
            ConcurrentHashMap<String, ResultadoOrden> resultados) {
        this.algoritmo = algoritmo;
        this.estructura = "Arreglo";
        this.arreglo = arreglo;
        this.lista = null;
        this.esperado = esperado;
        this.ordenadorArreglo = ordenador;
        this.ordenadorLista = null;
        this.resultados = resultados;
    }

    public TareaOrdenamiento(String algoritmo, List<Integer> lista, int[] esperado, OrdenadorLista ordenador,
            ConcurrentHashMap<String, ResultadoOrden> resultados) {
        this.algoritmo = algoritmo;
        this.estructura = "ArrayList";
        this.arreglo = null;
        this.lista = lista;
        this.esperado = esperado;
        this.ordenadorArreglo = null;
        this.ordenadorLista = ordenador;
        this.resultados = resultados;
    }

    @Override
    public void run() {
        long inicio;
        long fin;
        boolean ordenadoCorrectamente;

        if (arreglo != null) {
            inicio = System.nanoTime();
            ordenadorArreglo.ordenar(arreglo);
            fin = System.nanoTime();
            ordenadoCorrectamente = Verificador.arregloCoincide(arreglo, esperado);
        } else {
            inicio = System.nanoTime();
            ordenadorLista.ordenar(lista);
            fin = System.nanoTime();
            ordenadoCorrectamente = Verificador.listaCoincide(lista, esperado);
        }

        double tiempoMs = (fin - inicio) / 1_000_000.0;
        String clave = algoritmo + "_" + estructura;
        resultados.put(clave, new ResultadoOrden(algoritmo, estructura, tiempoMs, ordenadoCorrectamente));
    }
}
