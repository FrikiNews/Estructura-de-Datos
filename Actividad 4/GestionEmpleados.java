package actividad4;

import java.util.Random;
import java.util.Scanner;

/* @author friki */

/**
 * CASO PRACTICO: gestion de empleados con un arbol binario de busqueda.
 *
 * Los empleados se guardan en un ArbolBinario ordenado por ID. La clase
 * mantiene ademas la misma plantilla en un arreglo, para poder comparar
 * la busqueda en el arbol contra la busqueda secuencial sobre los mismos
 * datos y medir la diferencia real.
 */
public class GestionEmpleados {

    private ArbolBinario<Empleado> arbol;
    private Empleado[] plantilla;

    /** Comparaciones de la ultima busqueda secuencial. */
    private int comparacionesSecuencial;

    public GestionEmpleados() {
        arbol = new ArbolBinario<>();
        plantilla = new Empleado[0];
    }

    public ArbolBinario<Empleado> getArbol() { return arbol; }
    public Empleado[] getPlantilla() { return plantilla; }
    public int getComparacionesSecuencial() { return comparacionesSecuencial; }

    /**
     * Carga una plantilla en el arbol y en el arreglo.
     *
     * @param empleados empleados a registrar
     */
    public void cargar(Empleado[] empleados) {
        arbol.vaciar();
        plantilla = empleados;
        for (int i = 0; i < empleados.length; i++) {
            arbol.insertar(empleados[i]);
        }
    }

    // ---------------- BUSQUEDAS ----------------

    /**
     * Busca un empleado en el arbol. En cada nodo se descarta la mitad de
     * los empleados que quedaban, por lo que el costo es O(log n).
     *
     * @param id identificador buscado
     * @return el empleado, o null si no existe
     */
    public Empleado buscarEnArbol(int id) {
        return arbol.buscar(Empleado.clave(id));
    }

    /**
     * Busca un empleado recorriendo el arreglo uno por uno, que es el
     * metodo secuencial contra el que se compara. Costo O(n).
     *
     * @param id identificador buscado
     * @return el empleado, o null si no existe
     */
    public Empleado buscarSecuencial(int id) {
        comparacionesSecuencial = 0;
        for (int i = 0; i < plantilla.length; i++) {
            comparacionesSecuencial++;
            if (plantilla[i].getId() == id) {
                return plantilla[i];
            }
        }
        return null;
    }

    // ---------------- ALTAS Y BAJAS ----------------

    /**
     * Registra un empleado nuevo en el arbol.
     *
     * @return true si se dio de alta, false si el ID ya existia
     */
    public boolean contratar(Empleado empleado) {
        if (!arbol.insertar(empleado)) {
            return false;
        }
        Empleado[] ampliada = new Empleado[plantilla.length + 1];
        for (int i = 0; i < plantilla.length; i++) {
            ampliada[i] = plantilla[i];
        }
        ampliada[plantilla.length] = empleado;
        plantilla = ampliada;
        return true;
    }

    /**
     * Da de baja al empleado con ese ID.
     *
     * @return true si se elimino, false si no estaba registrado
     */
    public boolean darDeBaja(int id) {
        if (!arbol.eliminar(Empleado.clave(id))) {
            return false;
        }
        Empleado[] reducida = new Empleado[plantilla.length - 1];
        int j = 0;
        for (int i = 0; i < plantilla.length; i++) {
            if (plantilla[i].getId() != id) {
                reducida[j] = plantilla[i];
                j++;
            }
        }
        plantilla = reducida;
        return true;
    }

    // ---------------- PLANTILLAS DE EJEMPLO ----------------

    /** Plantilla pequena, pensada para que el arbol se pueda ver dibujado. */
    public static Empleado[] plantillaDemo() {
        return new Empleado[] {
            new Empleado(1050, "Ana Ramirez",      "Gerente",        "Direccion"),
            new Empleado(1020, "Luis Torres",      "Analista",       "Sistemas"),
            new Empleado(1080, "Maria Solis",      "Contadora",      "Contabilidad"),
            new Empleado(1010, "Jorge Medina",     "Programador",    "Sistemas"),
            new Empleado(1035, "Sofia Herrera",    "Disenadora",     "Marketing"),
            new Empleado(1065, "Pedro Alvarez",    "Vendedor",       "Ventas"),
            new Empleado(1095, "Carmen Rios",      "Auditora",       "Contabilidad"),
            new Empleado(1005, "Diego Navarro",    "Becario",        "Sistemas"),
            new Empleado(1015, "Elena Vega",       "Programadora",   "Sistemas"),
            new Empleado(1040, "Raul Castillo",    "Community",      "Marketing"),
            new Empleado(1070, "Laura Mendoza",    "Vendedora",      "Ventas"),
            new Empleado(1090, "Hugo Delgado",     "Almacenista",    "Almacen")
        };
    }

    /**
     * Genera una plantilla grande para la prueba de eficiencia.
     *
     * Los empleados se insertan en orden aleatorio a proposito: si los IDs
     * entraran ya ordenados, el arbol degeneraria en una lista y perderia
     * toda su ventaja.
     *
     * @param cantidad cuantos empleados generar
     * @return la plantilla en orden aleatorio
     */
    public static Empleado[] generarPlantilla(int cantidad) {
        String[] nombres = {"Ana", "Luis", "Maria", "Jorge", "Sofia", "Pedro",
                            "Carmen", "Diego", "Elena", "Raul", "Laura", "Hugo"};
        String[] apellidos = {"Ramirez", "Torres", "Solis", "Medina", "Herrera",
                              "Alvarez", "Rios", "Navarro", "Vega", "Castillo"};
        String[] puestos = {"Programador", "Analista", "Vendedor", "Contador",
                            "Disenador", "Almacenista"};
        String[] departamentos = {"Sistemas", "Ventas", "Contabilidad",
                                  "Marketing", "Almacen"};

        Empleado[] lista = new Empleado[cantidad];
        for (int i = 0; i < cantidad; i++) {
            int id = 1000 + i;
            String nombre = nombres[i % nombres.length] + " "
                          + apellidos[(i / nombres.length) % apellidos.length];
            lista[i] = new Empleado(id, nombre,
                    puestos[i % puestos.length],
                    departamentos[i % departamentos.length]);
        }
        mezclar(lista, new Random());
        return lista;
    }

    /** Mezcla la plantilla con el algoritmo de Fisher-Yates. */
    private static void mezclar(Empleado[] lista, Random rnd) {
        for (int i = lista.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            Empleado temporal = lista[i];
            lista[i] = lista[j];
            lista[j] = temporal;
        }
    }

    // ---------------- COMPARACION DE EFICIENCIA ----------------

    /**
     * Busca TODOS los empleados con los dos metodos y compara el trabajo
     * que costo cada uno.
     *
     * Se buscan todos y no uno solo para que el resultado sea justo: si se
     * midiera una sola busqueda, el resultado dependeria de la suerte de
     * si ese empleado estaba al principio o al final del arreglo.
     *
     * @param cantidad tamano de la plantilla a probar
     */
    public static void compararEficiencia(int cantidad) {
        GestionEmpleados gestion = new GestionEmpleados();
        Empleado[] lista = generarPlantilla(cantidad);
        gestion.cargar(lista);

        ArbolBinario<Empleado> arbol = gestion.getArbol();

        System.out.println("\n   COMPARACION DE EFICIENCIA CON " + cantidad + " EMPLEADOS");
        System.out.println("   ------------------------------------------------------");
        System.out.println("   Altura del arbol: " + arbol.altura()
                + "  (la ideal equilibrada seria " + arbol.alturaIdeal() + ")");
        System.out.println();

        long totalArbol = 0;
        int maximoArbol = 0;
        long inicioArbol = System.nanoTime();
        for (int i = 0; i < lista.length; i++) {
            gestion.buscarEnArbol(lista[i].getId());
            int c = arbol.getComparaciones();
            totalArbol += c;
            if (c > maximoArbol) maximoArbol = c;
        }
        long nanosArbol = System.nanoTime() - inicioArbol;

        long totalSecuencial = 0;
        int maximoSecuencial = 0;
        long inicioSecuencial = System.nanoTime();
        for (int i = 0; i < lista.length; i++) {
            gestion.buscarSecuencial(lista[i].getId());
            int c = gestion.getComparacionesSecuencial();
            totalSecuencial += c;
            if (c > maximoSecuencial) maximoSecuencial = c;
        }
        long nanosSecuencial = System.nanoTime() - inicioSecuencial;

        double promedioArbol = (double) totalArbol / lista.length;
        double promedioSecuencial = (double) totalSecuencial / lista.length;

        System.out.println("   Metodo                 Promedio      Peor caso        Total");
        System.out.println("   ------------------------------------------------------------");
        System.out.printf("   Arbol binario      %10.2f     %10d   %10d%n",
                promedioArbol, maximoArbol, totalArbol);
        System.out.printf("   Busqueda secuencial%10.2f     %10d   %10d%n",
                promedioSecuencial, maximoSecuencial, totalSecuencial);
        System.out.println("   ------------------------------------------------------------");

        if (promedioArbol > 0) {
            System.out.printf("   El arbol necesito %.1f veces menos comparaciones.%n",
                    promedioSecuencial / promedioArbol);
        }
        System.out.printf("   Tiempo total: arbol %.2f ms | secuencial %.2f ms%n",
                nanosArbol / 1_000_000.0, nanosSecuencial / 1_000_000.0);
    }

    /**
     * Demuestra por que el orden de insercion importa.
     *
     * Con los mismos datos, insertarlos ya ordenados produce un arbol
     * degenerado (una lista disfrazada) que pierde toda la ventaja, y
     * mezclarlos produce un arbol razonablemente equilibrado.
     *
     * @param cantidad tamano de la plantilla a probar
     */
    public static void compararOrdenDeInsercion(int cantidad) {
        Empleado[] mezclada = generarPlantilla(cantidad);

        // La misma plantilla, pero ordenada por ID de menor a mayor
        Empleado[] ordenada = new Empleado[cantidad];
        for (int i = 0; i < cantidad; i++) {
            ordenada[i] = new Empleado(1000 + i, "Empleado " + i, "Puesto", "Depto");
        }

        ArbolBinario<Empleado> arbolMezclado = new ArbolBinario<>();
        for (int i = 0; i < mezclada.length; i++) arbolMezclado.insertar(mezclada[i]);

        ArbolBinario<Empleado> arbolOrdenado = new ArbolBinario<>();
        for (int i = 0; i < ordenada.length; i++) arbolOrdenado.insertar(ordenada[i]);

        System.out.println("\n   EFECTO DEL ORDEN DE INSERCION (" + cantidad + " empleados)");
        System.out.println("   ------------------------------------------------------");

        long totalMezclado = 0;
        for (int i = 0; i < mezclada.length; i++) {
            arbolMezclado.buscar(Empleado.clave(mezclada[i].getId()));
            totalMezclado += arbolMezclado.getComparaciones();
        }

        long totalOrdenado = 0;
        for (int i = 0; i < ordenada.length; i++) {
            arbolOrdenado.buscar(Empleado.clave(ordenada[i].getId()));
            totalOrdenado += arbolOrdenado.getComparaciones();
        }

        System.out.println("   Orden de insercion     Altura     Comparaciones promedio");
        System.out.println("   ------------------------------------------------------");
        System.out.printf("   Aleatorio          %10d     %18.2f%n",
                arbolMezclado.altura(), (double) totalMezclado / cantidad);
        System.out.printf("   Ya ordenado por ID %10d     %18.2f%n",
                arbolOrdenado.altura(), (double) totalOrdenado / cantidad);
        System.out.println("   ------------------------------------------------------");
        System.out.println("   Insertar datos ya ordenados degenera el arbol en una lista:");
        System.out.println("   su altura pasa a ser igual a la cantidad de empleados y la");
        System.out.println("   busqueda se vuelve secuencial, O(n) en lugar de O(log n).");
    }

    // ---------------- MENU ----------------

    /** Menu interactivo del caso practico. */
    public static void demo(Scanner sc) {
        GestionEmpleados gestion = new GestionEmpleados();
        gestion.cargar(plantillaDemo());

        boolean volver = false;
        while (!volver) {
            System.out.println("\n---- CASO PRACTICO: GESTION DE EMPLEADOS ----");
            System.out.println("Empleados registrados: " + gestion.getArbol().getCantidad());
            System.out.println("  1) Ver la plantilla ordenada por ID (recorrido inorden)");
            System.out.println("  2) Ver el arbol dibujado");
            System.out.println("  3) Buscar un empleado por ID");
            System.out.println("  4) Contratar un empleado");
            System.out.println("  5) Dar de baja un empleado");
            System.out.println("  6) Comparar arbol contra busqueda secuencial");
            System.out.println("  7) Ver el efecto del orden de insercion");
            System.out.println("  0) Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            switch (Main.leerEntero(sc)) {
                case 1: listar(gestion); break;
                case 2:
                    System.out.println("\n   ARBOL DE EMPLEADOS (raiz a la izquierda)");
                    System.out.print(gestion.getArbol().dibujar());
                    break;
                case 3: buscar(sc, gestion); break;
                case 4: contratar(sc, gestion); break;
                case 5: darDeBaja(sc, gestion); break;
                case 6:
                    compararEficiencia(Main.pedirEntero(sc,
                            "   Cuantos empleados generar (100 a 100000): ", 100, 100000));
                    break;
                case 7:
                    compararOrdenDeInsercion(Main.pedirEntero(sc,
                            "   Cuantos empleados generar (100 a 5000): ", 100, 5000));
                    break;
                case 0: volver = true; break;
                default: System.out.println(">> Opcion invalida. Elige un numero del 0 al 7.");
            }
        }
    }

    private static void listar(GestionEmpleados gestion) {
        ArbolBinario<Empleado> arbol = gestion.getArbol();
        if (arbol.estaVacio()) {
            System.out.println("   >> No hay empleados registrados.");
            return;
        }
        System.out.println("\n   PLANTILLA ORDENADA POR ID (" + arbol.getCantidad() + " empleados)");
        System.out.println("   ------------------------------------------------------");
        Nodo<Empleado> raiz = arbol.getRaiz();
        imprimirFichas(raiz);
        System.out.println("   ------------------------------------------------------");
        System.out.println("   El recorrido inorden los entrega ya ordenados, sin ordenarlos aparte.");
    }

    /** Recorre el arbol en inorden imprimiendo la ficha de cada empleado. */
    private static void imprimirFichas(Nodo<Empleado> nodo) {
        if (nodo == null) return;
        imprimirFichas(nodo.getIzquierdo());
        System.out.println("   " + nodo.getDato().ficha());
        imprimirFichas(nodo.getDerecho());
    }

    private static void buscar(Scanner sc, GestionEmpleados gestion) {
        int id = Main.pedirEntero(sc, "   ID del empleado: ", 0, Integer.MAX_VALUE);

        Empleado enArbol = gestion.buscarEnArbol(id);
        int compArbol = gestion.getArbol().getComparaciones();

        gestion.buscarSecuencial(id);
        int compSecuencial = gestion.getComparacionesSecuencial();

        if (enArbol == null) {
            System.out.println("   >> No existe ningun empleado con el ID " + id + ".");
        } else {
            System.out.println("   >> Empleado encontrado: " + enArbol.ficha());
        }
        System.out.println("   >> Comparaciones en el arbol: " + compArbol);
        System.out.println("   >> Comparaciones secuenciales: " + compSecuencial);
    }

    private static void contratar(Scanner sc, GestionEmpleados gestion) {
        int id = Main.pedirEntero(sc, "   ID del nuevo empleado: ", 0, Integer.MAX_VALUE);
        System.out.print("   Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("   Puesto: ");
        String puesto = sc.nextLine().trim();
        System.out.print("   Departamento: ");
        String departamento = sc.nextLine().trim();

        if (nombre.isEmpty()) nombre = "Sin nombre";
        if (puesto.isEmpty()) puesto = "Sin puesto";
        if (departamento.isEmpty()) departamento = "Sin departamento";

        if (gestion.contratar(new Empleado(id, nombre, puesto, departamento))) {
            System.out.println("   >> Empleado registrado. Total: "
                    + gestion.getArbol().getCantidad());
        } else {
            System.out.println("   >> Ya existe un empleado con el ID " + id + ".");
        }
    }

    private static void darDeBaja(Scanner sc, GestionEmpleados gestion) {
        int id = Main.pedirEntero(sc, "   ID del empleado a dar de baja: ", 0, Integer.MAX_VALUE);
        Empleado empleado = gestion.buscarEnArbol(id);
        if (empleado == null) {
            System.out.println("   >> No existe ningun empleado con el ID " + id + ".");
            return;
        }
        System.out.println("   Se dara de baja a: " + empleado.ficha());
        if (gestion.darDeBaja(id)) {
            System.out.println("   >> Empleado dado de baja. Quedan "
                    + gestion.getArbol().getCantidad() + ".");
            System.out.println("   Plantilla tras la baja: " + gestion.getArbol().inorden());
        }
    }
}
