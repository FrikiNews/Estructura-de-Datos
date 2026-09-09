package actividad3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/* @author friki */

/**
 * PROBLEMA 3: Resolucion de Sudoku mediante backtracking.
 *
 * El backtracking prueba un numero en la primera celda vacia y sigue
 * recursivamente; si esa via no lleva a ninguna solucion, deshace la
 * jugada y prueba con el siguiente numero.
 */
public class Sudoku {

    /** Tamano del tablero. */
    public static final int TAM = 9;

    /** Valor que representa una celda vacia. */
    public static final int VACIA = 0;

    /** Cuenta las llamadas recursivas para evidenciar el costo. */
    private static long llamadas = 0;

    /** Cuenta cuantas veces se tuvo que deshacer una jugada. */
    private static long retrocesos = 0;

    // ---------------- ALGORITMO PRINCIPAL ----------------

    /**
     * Resuelve el tablero modificandolo directamente.
     *
     * @param tablero matriz 9x9, donde 0 representa una celda vacia
     * @return true si el Sudoku tiene solucion
     */
    public static boolean resolver(int[][] tablero) {
        llamadas++;

        for (int fila = 0; fila < TAM; fila++) {
            for (int columna = 0; columna < TAM; columna++) {

                if (tablero[fila][columna] == VACIA) {

                    // ----- CASO RECURSIVO -----
                    for (int numero = 1; numero <= TAM; numero++) {

                        if (esValido(tablero, fila, columna, numero)) {
                            tablero[fila][columna] = numero;

                            if (resolver(tablero)) {
                                return true;
                            }

                            // ----- VUELTA ATRAS (BACKTRACKING) -----
                            tablero[fila][columna] = VACIA;
                            retrocesos++;
                        }
                    }

                    // Ningun numero funciono: el error viene de una
                    // decision anterior, se avisa al nivel de arriba.
                    return false;
                }
            }
        }

        // ----- CASO BASE: no quedan celdas vacias -----
        return true;
    }

    /**
     * Indica si colocar un numero en una celda respeta las tres reglas
     * del Sudoku: no repetirse en la fila, ni en la columna, ni en el
     * subcuadro de 3x3.
     *
     * @param tablero tablero actual
     * @param fila fila de la celda, de 0 a 8
     * @param columna columna de la celda, de 0 a 8
     * @param numero valor que se quiere colocar, de 1 a 9
     * @return true si el movimiento es legal
     */
    public static boolean esValido(int[][] tablero, int fila, int columna, int numero) {

        // Regla 1: sin repetir en la fila
        for (int c = 0; c < TAM; c++) {
            if (tablero[fila][c] == numero) return false;
        }

        // Regla 2: sin repetir en la columna
        for (int f = 0; f < TAM; f++) {
            if (tablero[f][columna] == numero) return false;
        }

        // Regla 3: sin repetir en el subcuadro 3x3. La division entera
        // localiza la esquina superior izquierda del subcuadro.
        int inicioFila = (fila / 3) * 3;
        int inicioColumna = (columna / 3) * 3;
        for (int f = inicioFila; f < inicioFila + 3; f++) {
            for (int c = inicioColumna; c < inicioColumna + 3; c++) {
                if (tablero[f][c] == numero) return false;
            }
        }

        return true;
    }

    /**
     * Revisa que ningun numero ya colocado se repita en su fila, columna
     * o subcuadro.
     *
     * @param tablero tablero a revisar
     * @return true si el tablero inicial es coherente
     */
    public static boolean tableroInicialValido(int[][] tablero) {
        for (int fila = 0; fila < TAM; fila++) {
            for (int columna = 0; columna < TAM; columna++) {
                int numero = tablero[fila][columna];
                if (numero != VACIA) {
                    // Se retira para no compararlo consigo mismo
                    tablero[fila][columna] = VACIA;
                    boolean ok = esValido(tablero, fila, columna, numero);
                    tablero[fila][columna] = numero;
                    if (!ok) return false;
                }
            }
        }
        return true;
    }

    /** Verifica que no queden celdas vacias y que se respeten las reglas. */
    public static boolean estaResuelto(int[][] tablero) {
        for (int fila = 0; fila < TAM; fila++) {
            for (int columna = 0; columna < TAM; columna++) {
                if (tablero[fila][columna] == VACIA) return false;
            }
        }
        return tableroInicialValido(tablero);
    }

    // ---------------- APOYO ----------------

    public static void reiniciarContadores() {
        llamadas = 0;
        retrocesos = 0;
    }

    public static long getLlamadas() {
        return llamadas;
    }

    public static long getRetrocesos() {
        return retrocesos;
    }

    /** Devuelve una copia independiente del tablero. */
    public static int[][] copiar(int[][] tablero) {
        int[][] copia = new int[TAM][TAM];
        for (int f = 0; f < TAM; f++) {
            for (int c = 0; c < TAM; c++) {
                copia[f][c] = tablero[f][c];
            }
        }
        return copia;
    }

    /** Cuenta cuantas celdas siguen vacias. */
    public static int contarVacias(int[][] tablero) {
        int vacias = 0;
        for (int f = 0; f < TAM; f++) {
            for (int c = 0; c < TAM; c++) {
                if (tablero[f][c] == VACIA) vacias++;
            }
        }
        return vacias;
    }

    /** Imprime el tablero con separadores entre los subcuadros de 3x3. */
    public static void imprimir(int[][] tablero) {
        System.out.println("   +-------+-------+-------+");
        for (int fila = 0; fila < TAM; fila++) {
            StringBuilder sb = new StringBuilder("   |");
            for (int columna = 0; columna < TAM; columna++) {
                int valor = tablero[fila][columna];
                sb.append(" ").append(valor == VACIA ? "." : String.valueOf(valor));
                if ((columna + 1) % 3 == 0) sb.append(" |");
            }
            System.out.println(sb.toString());
            if ((fila + 1) % 3 == 0) {
                System.out.println("   +-------+-------+-------+");
            }
        }
    }

    // ---------------- CARGA DESDE ARCHIVO ----------------

    /**
     * Lee un Sudoku desde un archivo de texto (.txt) o de Simple Sudoku (.sdk).
     *
     * @param ruta ubicacion del archivo
     * @return el tablero leido
     * @throws IOException si el archivo no existe o no se puede leer
     * @throws IllegalArgumentException si el contenido no forma un Sudoku valido
     */
    public static int[][] cargarDesdeArchivo(String ruta) throws IOException {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            throw new IOException("No se encontro el archivo: " + archivo.getAbsolutePath());
        }
        if (!archivo.canRead()) {
            throw new IOException("No hay permiso para leer el archivo: " + ruta);
        }

        StringBuilder contenido = new StringBuilder();
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append('\n');
            }
        } finally {
            if (lector != null) lector.close();
        }

        return parsearTablero(contenido.toString());
    }

    /**
     * Interpreta el contenido de un archivo y arma el tablero.
     *
     * Acepta 9 lineas de 9 digitos, los 81 digitos seguidos, o separados
     * por espacios y separadores como | + - (que se ignoran). Una celda
     * vacia puede ser 0, punto, guion bajo o asterisco, y las lineas que
     * empiezan con # o // son comentarios.
     *
     * @param contenido texto completo del archivo
     * @return el tablero interpretado
     * @throws IllegalArgumentException si no hay exactamente 81 celdas o si
     *         el tablero de partida viola las reglas del Sudoku
     */
    public static int[][] parsearTablero(String contenido) {
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser nulo");
        }

        StringBuilder celdas = new StringBuilder();
        String[] lineas = contenido.split("\n");

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();

            // Lineas vacias y comentarios del formato .sdk
            if (linea.isEmpty()) continue;
            if (linea.startsWith("#") || linea.startsWith("//")) continue;

            for (int j = 0; j < linea.length(); j++) {
                char ch = linea.charAt(j);
                if (ch >= '1' && ch <= '9') {
                    celdas.append(ch);
                } else if (ch == '0' || ch == '.' || ch == '_' || ch == '*') {
                    celdas.append('0');
                }
                // El resto de caracteres se ignora
            }
        }

        if (celdas.length() != TAM * TAM) {
            throw new IllegalArgumentException(
                    "El archivo debe contener 81 celdas, pero se encontraron "
                    + celdas.length() + ". Revisa que el tablero este completo.");
        }

        int[][] tablero = new int[TAM][TAM];
        for (int i = 0; i < TAM * TAM; i++) {
            tablero[i / TAM][i % TAM] = celdas.charAt(i) - '0';
        }

        if (!tableroInicialValido(tablero)) {
            throw new IllegalArgumentException(
                    "El Sudoku del archivo ya viola las reglas: hay un numero repetido "
                    + "en alguna fila, columna o subcuadro.");
        }
        return tablero;
    }

    // ---------------- GENERACION ALEATORIA ----------------

    /** Celdas que se vacian en un Sudoku aleatorio normal. */
    public static final int MEDIO = 48;

    /** Celdas que se vacian en un Sudoku aleatorio dificil. */
    public static final int DIFICIL = 54;

    /**
     * Genera un Sudoku aleatorio con solucion unica: primero llena un
     * tablero completo al azar y luego vacia celdas, verificando en cada
     * una que la solucion siga siendo unica.
     *
     * @param celdasAQuitar cuantas celdas se intentaran vaciar
     * @return un Sudoku con solucion unica
     */
    public static int[][] generarAleatorio(int celdasAQuitar) {
        Random rnd = new Random();

        // Fase 1: tablero completo generado al azar
        int[][] tablero = new int[TAM][TAM];
        llenarAleatorio(tablero, rnd);

        // Fase 2: vaciar celdas sin perder la unicidad
        int[] posiciones = posicionesMezcladas(rnd);
        int quitadas = 0;
        for (int i = 0; i < posiciones.length && quitadas < celdasAQuitar; i++) {
            int fila = posiciones[i] / TAM;
            int columna = posiciones[i] % TAM;

            int respaldo = tablero[fila][columna];
            tablero[fila][columna] = VACIA;

            // Si aparece mas de una solucion, se regresa el numero
            if (contarSoluciones(tablero, 2) != 1) {
                tablero[fila][columna] = respaldo;
            } else {
                quitadas++;
            }
        }
        return tablero;
    }

    /** Igual que resolver(), pero probando los numeros en orden aleatorio. */
    private static boolean llenarAleatorio(int[][] tablero, Random rnd) {
        for (int fila = 0; fila < TAM; fila++) {
            for (int columna = 0; columna < TAM; columna++) {
                if (tablero[fila][columna] == VACIA) {

                    int[] numeros = numerosMezclados(rnd);
                    for (int i = 0; i < numeros.length; i++) {
                        int numero = numeros[i];
                        if (esValido(tablero, fila, columna, numero)) {
                            tablero[fila][columna] = numero;
                            if (llenarAleatorio(tablero, rnd)) {
                                return true;
                            }
                            tablero[fila][columna] = VACIA;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Cuenta las soluciones del tablero, deteniendose al llegar al limite:
     * para validar un Sudoku basta distinguir entre una y mas de una.
     * El tablero se deja como estaba al terminar.
     *
     * @param tablero tablero a evaluar
     * @param limite numero de soluciones a partir del cual ya no se sigue buscando
     * @return cuantas soluciones se encontraron, como maximo el limite
     */
    public static int contarSoluciones(int[][] tablero, int limite) {
        for (int fila = 0; fila < TAM; fila++) {
            for (int columna = 0; columna < TAM; columna++) {
                if (tablero[fila][columna] == VACIA) {

                    int total = 0;
                    for (int numero = 1; numero <= TAM; numero++) {
                        if (esValido(tablero, fila, columna, numero)) {
                            tablero[fila][columna] = numero;
                            total += contarSoluciones(tablero, limite - total);
                            tablero[fila][columna] = VACIA;

                            if (total >= limite) return total;
                        }
                    }
                    return total;
                }
            }
        }
        return 1; // no quedan celdas vacias: es una solucion completa
    }

    /** Devuelve los numeros del 1 al 9 en orden aleatorio. */
    private static int[] numerosMezclados(Random rnd) {
        int[] numeros = new int[TAM];
        for (int i = 0; i < TAM; i++) {
            numeros[i] = i + 1;
        }
        mezclar(numeros, rnd);
        return numeros;
    }

    /** Devuelve las 81 posiciones del tablero en orden aleatorio. */
    private static int[] posicionesMezcladas(Random rnd) {
        int[] posiciones = new int[TAM * TAM];
        for (int i = 0; i < posiciones.length; i++) {
            posiciones[i] = i;
        }
        mezclar(posiciones, rnd);
        return posiciones;
    }

    /** Mezcla un arreglo en su lugar con el algoritmo de Fisher-Yates. */
    private static void mezclar(int[] valores, Random rnd) {
        for (int i = valores.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int temporal = valores[i];
            valores[i] = valores[j];
            valores[j] = temporal;
        }
    }

    // ---------------- DEMOSTRACION ----------------

    /** Menu interactivo del problema 3. */
    public static void demo(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n---- PROBLEMA 3: SUDOKU CON BACKTRACKING ----");
            System.out.println("  1) Generar un Sudoku aleatorio");
            System.out.println("  2) Generar un Sudoku aleatorio dificil");
            System.out.println("  3) Cargar un Sudoku desde archivo (.txt o .sdk)");
            System.out.println("  4) Ejecutar casos de prueba");
            System.out.println("  0) Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            switch (Main.leerEntero(sc)) {
                case 1: aleatorio(MEDIO, "Sudoku aleatorio"); break;
                case 2: aleatorio(DIFICIL, "Sudoku aleatorio dificil"); break;
                case 3: cargarArchivo(sc); break;
                case 4: pruebas(); break;
                case 0: volver = true; break;
                default: System.out.println(">> Opcion invalida. Elige un numero del 0 al 4.");
            }
        }
    }

    /** Resuelve un tablero mostrando el antes, el despues y las estadisticas. */
    private static void resolverYMostrar(int[][] tablero, String titulo) {
        System.out.println("\n   " + titulo.toUpperCase() + " - TABLERO INICIAL ("
                + contarVacias(tablero) + " celdas vacias)");
        imprimir(tablero);

        if (!tableroInicialValido(tablero)) {
            System.out.println("   >> El tablero de partida ya viola las reglas del Sudoku.");
            return;
        }

        reiniciarContadores();
        long inicio = System.currentTimeMillis();
        boolean resuelto = resolver(tablero);
        long ms = System.currentTimeMillis() - inicio;

        if (resuelto) {
            System.out.println("\n   TABLERO RESUELTO");
            imprimir(tablero);
            System.out.println("   >> Solucion valida: " + estaResuelto(tablero));
        } else {
            System.out.println("\n   >> Este Sudoku NO tiene solucion.");
        }
        System.out.println("   >> Llamadas recursivas: " + getLlamadas());
        System.out.println("   >> Retrocesos (jugadas deshechas): " + getRetrocesos());
        System.out.println("   >> Tiempo: " + ms + " ms");
    }

    /** Pide la ruta de un archivo y resuelve el Sudoku que contenga. */
    private static void cargarArchivo(Scanner sc) {
        System.out.println("\n   Escribe la ruta del archivo (.txt o .sdk).");
        System.out.println("   Puedes arrastrar el archivo a la terminal para pegar su ruta.");
        System.out.print("   Ruta: ");

        String ruta = sc.nextLine().trim();
        // Al arrastrar, algunos sistemas pegan la ruta entre comillas
        if (ruta.length() > 1
                && ((ruta.startsWith("\"") && ruta.endsWith("\""))
                 || (ruta.startsWith("'") && ruta.endsWith("'")))) {
            ruta = ruta.substring(1, ruta.length() - 1);
        }
        if (ruta.isEmpty()) {
            System.out.println("   >> No escribiste ninguna ruta.");
            return;
        }

        try {
            int[][] tablero = cargarDesdeArchivo(ruta);
            System.out.println("   >> Archivo leido correctamente.");
            resolverYMostrar(tablero, "Sudoku del archivo");
        } catch (IOException e) {
            System.out.println("   >> No se pudo leer el archivo: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("   >> El archivo no contiene un Sudoku valido: " + e.getMessage());
        }
    }

    /**
     * Genera un Sudoku aleatorio y lo resuelve mostrando estadisticas.
     *
     * @param celdasAQuitar cuantas celdas se vacian (a mas celdas, mas dificil)
     * @param nombre titulo que se muestra en pantalla
     */
    private static void aleatorio(int celdasAQuitar, String nombre) {
        System.out.println("\n   Generando... (se verifica que tenga una sola solucion)");
        long inicio = System.currentTimeMillis();
        int[][] tablero = generarAleatorio(celdasAQuitar);
        long ms = System.currentTimeMillis() - inicio;

        System.out.println("   >> Generado en " + ms + " ms con "
                + contarVacias(tablero) + " celdas vacias.");
        System.out.println("   >> Soluciones posibles: " + contarSoluciones(copiar(tablero), 2)
                + " (debe ser 1)");
        resolverYMostrar(tablero, nombre);
    }


    // ---------------- CASOS DE PRUEBA ----------------

    /** Tablero de prueba en formato de nueve lineas (solo lo usan las pruebas). */
    private static final String PRUEBA_EN_LINEAS =
              "53..7....\n"
            + "6..195...\n"
            + ".98....6.\n"
            + "8...6...3\n"
            + "4..8.3..1\n"
            + "7...2...6\n"
            + ".6....28.\n"
            + "...419..5\n"
            + "....8..79\n";

    /** El mismo tablero, escrito como 81 digitos seguidos. */
    private static final String PRUEBA_EN_UNA_LINEA =
              "530070000600195000098000060800060003400803001"
            + "700020006060000280000419005000080079";

    /** El mismo tablero, dibujado con separadores y comentarios de .sdk. */
    private static final String PRUEBA_CON_SEPARADORES =
              "#A Sudoku de prueba\n"
            + "#D Generado para la actividad 3\n"
            + "+-------+-------+-------+\n"
            + "| 5 3 . | . 7 . | . . . |\n"
            + "| 6 . . | 1 9 5 | . . . |\n"
            + "| . 9 8 | . . . | . 6 . |\n"
            + "+-------+-------+-------+\n"
            + "| 8 . . | . 6 . | . . 3 |\n"
            + "| 4 . . | 8 . 3 | . . 1 |\n"
            + "| 7 . . | . 2 . | . . 6 |\n"
            + "+-------+-------+-------+\n"
            + "| . 6 . | . . . | 2 8 . |\n"
            + "| . . . | 4 1 9 | . . 5 |\n"
            + "| . . . | . 8 . | . 7 9 |\n"
            + "+-------+-------+-------+\n";

    /** Casos de prueba del lector de archivos y del algoritmo de backtracking. */
    public static void pruebas() {
        System.out.println("\n   CASOS DE PRUEBA - SUDOKU");
        System.out.println("   ---------------------------------------------");

        int exitosas = 0;
        int totales = 0;

        // ----- LECTURA DE ARCHIVOS -----
        // Los tres formatos describen el mismo Sudoku

        int[][] base = parsearTablero(PRUEBA_EN_LINEAS);

        totales++; exitosas += revisar("Lee el formato de 9 lineas con puntos",
                tableroInicialValido(base), true);
        totales++; exitosas += revisar("Los 81 digitos en una linea dan el mismo tablero",
                mismoTablero(parsearTablero(PRUEBA_EN_UNA_LINEA), base), true);
        totales++; exitosas += revisar("Un tablero con separadores y comentarios da el mismo tablero",
                mismoTablero(parsearTablero(PRUEBA_CON_SEPARADORES), base), true);
        totales++; exitosas += revisar("Acepta el 0 como celda vacia igual que el punto",
                mismoTablero(parsearTablero(PRUEBA_EN_LINEAS.replace('.', '0')), base), true);

        boolean rechazaCorto = false;
        try {
            parsearTablero("53..7....\n6..195...\n");
        } catch (IllegalArgumentException e) {
            rechazaCorto = true;
        }
        totales++; exitosas += revisar("Rechaza un archivo con menos de 81 celdas", rechazaCorto, true);

        boolean rechazaInvalido = false;
        try {
            parsearTablero("55.......\n.........\n.........\n.........\n.........\n"
                         + ".........\n.........\n.........\n.........\n");
        } catch (IllegalArgumentException e) {
            rechazaInvalido = true;
        }
        totales++; exitosas += revisar("Rechaza un archivo con numeros repetidos", rechazaInvalido, true);

        // ----- VALIDACION DE MOVIMIENTOS -----

        totales++; exitosas += revisar("Rechaza un numero repetido en la fila",
                esValido(base, 0, 2, 5), false);
        totales++; exitosas += revisar("Rechaza un numero repetido en la columna",
                esValido(base, 2, 0, 6), false);
        totales++; exitosas += revisar("Rechaza un numero repetido en el subcuadro 3x3",
                esValido(base, 1, 1, 8), false);
        totales++; exitosas += revisar("Acepta un movimiento legal",
                esValido(base, 0, 2, 1), true);

        // ----- RESOLUCION -----

        int[][] resuelto = copiar(base);
        totales++; exitosas += revisar("Resuelve el Sudoku leido del archivo",
                resolver(resuelto), true);
        totales++; exitosas += revisar("La solucion no deja celdas vacias",
                contarVacias(resuelto) == 0, true);
        totales++; exitosas += revisar("La solucion respeta todas las reglas",
                estaResuelto(resuelto), true);

        boolean pistasIntactas = true;
        for (int f = 0; f < TAM; f++) {
            for (int c = 0; c < TAM; c++) {
                if (base[f][c] != VACIA && base[f][c] != resuelto[f][c]) {
                    pistasIntactas = false;
                }
            }
        }
        totales++; exitosas += revisar("Conserva las pistas originales del archivo",
                pistasIntactas, true);

        int[][] vacio = new int[TAM][TAM];
        totales++; exitosas += revisar("Llena un tablero completamente vacio",
                resolver(vacio), true);
        totales++; exitosas += revisar("El tablero llenado desde cero es valido",
                estaResuelto(vacio), true);

        // ----- GENERACION ALEATORIA -----

        int[][] generado = generarAleatorio(MEDIO);
        totales++; exitosas += revisar("El Sudoku aleatorio parte de un tablero valido",
                tableroInicialValido(generado), true);
        totales++; exitosas += revisar("El Sudoku aleatorio tiene solucion unica",
                contarSoluciones(copiar(generado), 2) == 1, true);
        totales++; exitosas += revisar("El Sudoku aleatorio deja celdas por llenar",
                contarVacias(generado) > 0, true);

        int[][] copiaGenerada = copiar(generado);
        totales++; exitosas += revisar("El Sudoku aleatorio se puede resolver",
                resolver(copiaGenerada), true);
        totales++; exitosas += revisar("La solucion del aleatorio es valida",
                estaResuelto(copiaGenerada), true);
        totales++; exitosas += revisar("Dos generaciones producen tableros distintos",
                mismoTablero(generarAleatorio(MEDIO), generarAleatorio(MEDIO)), false);

        int[][] generadoDificil = generarAleatorio(DIFICIL);
        totales++; exitosas += revisar("El aleatorio dificil deja mas celdas vacias que el normal",
                contarVacias(generadoDificil) > contarVacias(generado), true);
        totales++; exitosas += revisar("El aleatorio dificil tambien tiene solucion unica",
                contarSoluciones(copiar(generadoDificil), 2) == 1, true);

        // ----- CONTEO DE SOLUCIONES -----

        totales++; exitosas += revisar("Un Sudoku bien planteado tiene exactamente 1 solucion",
                contarSoluciones(copiar(base), 2) == 1, true);
        totales++; exitosas += revisar("Un tablero vacio admite multiples soluciones",
                contarSoluciones(new int[TAM][TAM], 2) >= 2, true);

        System.out.println("   ---------------------------------------------");
        System.out.println("   Resultado: " + exitosas + " de " + totales + " pruebas exitosas.");
    }

    /** Indica si dos tableros tienen exactamente los mismos valores. */
    private static boolean mismoTablero(int[][] a, int[][] b) {
        for (int f = 0; f < TAM; f++) {
            for (int c = 0; c < TAM; c++) {
                if (a[f][c] != b[f][c]) return false;
            }
        }
        return true;
    }

    /** Compara el resultado obtenido contra el esperado e imprime el veredicto. */
    private static int revisar(String descripcion, boolean obtenido, boolean esperado) {
        boolean ok = (obtenido == esperado);
        System.out.println("   " + (ok ? "[OK]   " : "[FALLO]") + " " + descripcion
                + "  ->  " + obtenido);
        return ok ? 1 : 0;
    }
}
