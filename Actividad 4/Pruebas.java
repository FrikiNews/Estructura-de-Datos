package actividad4;

/* @author friki */

/**
 * Banco de pruebas automatizadas del arbol binario y del caso practico.
 * Se ejecuta desde la opcion 3 del menu principal.
 */
public class Pruebas {

    private static int totales = 0;
    private static int exitosas = 0;

    public static void ejecutar() {
        totales = 0;
        exitosas = 0;

        System.out.println("\n==========================================================");
        System.out.println("     CASOS DE PRUEBA - ACTIVIDAD 4");
        System.out.println("==========================================================");

        probarInsercion();
        probarBusqueda();
        probarRecorridos();
        probarEliminacion();
        probarMedidas();
        probarEmpleados();

        System.out.println("\n==========================================================");
        System.out.println("  RESUMEN: " + exitosas + " de " + totales + " pruebas exitosas.");
        if (exitosas == totales) {
            System.out.println("  RESULTADO: todas las pruebas pasaron correctamente.");
        } else {
            System.out.println("  RESULTADO: hay " + (totales - exitosas) + " prueba(s) con fallos.");
        }
        System.out.println("==========================================================");
    }

    /** Arbol de referencia usado por varias pruebas. */
    private static ArbolBinario<Integer> arbolEjemplo() {
        ArbolBinario<Integer> a = new ArbolBinario<>();
        int[] valores = {50, 30, 70, 20, 40, 60, 80};
        for (int i = 0; i < valores.length; i++) a.insertar(valores[i]);
        return a;
    }

    // ---------------- INSERCION ----------------

    private static void probarInsercion() {
        System.out.println("\n--- GRUPO 1: INSERCION ---");
        ArbolBinario<Integer> a = new ArbolBinario<>();

        verificar("Un arbol nuevo esta vacio", a.estaVacio());
        verificar("Un arbol vacio tiene 0 nodos", a.getCantidad() == 0);
        verificar("Un arbol vacio tiene altura 0", a.altura() == 0);

        verificar("insertar() devuelve true con un dato nuevo", a.insertar(50));
        verificar("Tras insertar ya no esta vacio", !a.estaVacio());
        verificar("El primer dato queda en la raiz", a.getRaiz().getDato() == 50);

        a.insertar(30);
        a.insertar(70);
        verificar("Tras 3 inserciones hay 3 nodos", a.getCantidad() == 3);
        verificar("El menor se coloca a la izquierda", a.getRaiz().getIzquierdo().getDato() == 30);
        verificar("El mayor se coloca a la derecha", a.getRaiz().getDerecho().getDato() == 70);

        verificar("insertar() devuelve false con un duplicado", !a.insertar(30));
        verificar("El duplicado no aumenta la cantidad", a.getCantidad() == 3);

        boolean rechazaNulo = false;
        try {
            a.insertar(null);
        } catch (IllegalArgumentException e) {
            rechazaNulo = true;
        }
        verificar("insertar(null) lanza una excepcion", rechazaNulo);
    }

    // ---------------- BUSQUEDA ----------------

    private static void probarBusqueda() {
        System.out.println("\n--- GRUPO 2: BUSQUEDA ---");
        ArbolBinario<Integer> a = arbolEjemplo();

        verificar("Encuentra la raiz", a.buscar(50) != null);
        verificar("Encontrar la raiz cuesta 1 comparacion", a.getComparaciones() == 1);

        verificar("Encuentra una hoja", a.buscar(20) != null);
        verificar("Llegar a una hoja del nivel 3 cuesta 3 comparaciones",
                a.getComparaciones() == 3);

        verificar("Devuelve null si el dato no existe", a.buscar(99) == null);
        verificar("contiene() es true con un dato presente", a.contiene(60));
        verificar("contiene() es false con un dato ausente", !a.contiene(99));
        verificar("buscar(null) devuelve null", a.buscar(null) == null);

        ArbolBinario<Integer> vacio = new ArbolBinario<>();
        verificar("Buscar en un arbol vacio devuelve null", vacio.buscar(10) == null);
        verificar("Buscar en un arbol vacio no compara nada", vacio.getComparaciones() == 0);

        // La busqueda nunca visita mas nodos que la altura del arbol
        boolean dentroDeLaAltura = true;
        for (int v = 10; v <= 90; v += 10) {
            a.buscar(v);
            if (a.getComparaciones() > a.altura()) dentroDeLaAltura = false;
        }
        verificar("Ninguna busqueda visita mas nodos que la altura", dentroDeLaAltura);
    }

    // ---------------- RECORRIDOS ----------------

    private static void probarRecorridos() {
        System.out.println("\n--- GRUPO 3: RECORRIDOS ---");
        ArbolBinario<Integer> a = arbolEjemplo();

        verificar("INORDEN devuelve los datos ordenados",
                a.inorden().equals("20, 30, 40, 50, 60, 70, 80"));
        verificar("PREORDEN empieza por la raiz",
                a.preorden().equals("50, 30, 20, 40, 70, 60, 80"));
        verificar("POSTORDEN termina en la raiz",
                a.postorden().equals("20, 40, 30, 60, 80, 70, 50"));

        ArbolBinario<Integer> vacio = new ArbolBinario<>();
        verificar("Los recorridos de un arbol vacio salen vacios",
                vacio.inorden().isEmpty() && vacio.preorden().isEmpty()
                && vacio.postorden().isEmpty());

        ArbolBinario<Integer> uno = new ArbolBinario<>();
        uno.insertar(7);
        verificar("Con un solo nodo los tres recorridos coinciden",
                uno.inorden().equals("7") && uno.preorden().equals("7")
                && uno.postorden().equals("7"));

        // El inorden sigue ordenado sin importar el orden de insercion
        ArbolBinario<Integer> desordenado = new ArbolBinario<>();
        int[] entrada = {40, 20, 60, 10, 50, 30, 70};
        for (int i = 0; i < entrada.length; i++) desordenado.insertar(entrada[i]);
        verificar("El inorden ordena aunque los datos entren desordenados",
                desordenado.inorden().equals("10, 20, 30, 40, 50, 60, 70"));
    }

    // ---------------- ELIMINACION ----------------

    private static void probarEliminacion() {
        System.out.println("\n--- GRUPO 4: ELIMINACION (LOS TRES CASOS) ---");

        // Caso 1: nodo hoja
        ArbolBinario<Integer> a = arbolEjemplo();
        verificar("Elimina una hoja", a.eliminar(20));
        verificar("Tras eliminar quedan 6 nodos", a.getCantidad() == 6);
        verificar("La hoja ya no esta", !a.contiene(20));
        verificar("El orden se conserva tras eliminar una hoja",
                a.inorden().equals("30, 40, 50, 60, 70, 80"));

        // Caso 2: nodo con un solo hijo
        ArbolBinario<Integer> b = new ArbolBinario<>();
        int[] conUnHijo = {50, 30, 70, 20};
        for (int i = 0; i < conUnHijo.length; i++) b.insertar(conUnHijo[i]);
        verificar("Elimina un nodo con un solo hijo", b.eliminar(30));
        verificar("El hijo ocupa el lugar del eliminado",
                b.getRaiz().getIzquierdo().getDato() == 20);
        verificar("El orden se conserva con un hijo",
                b.inorden().equals("20, 50, 70"));

        // Caso 3: nodo con dos hijos
        ArbolBinario<Integer> c = arbolEjemplo();
        verificar("Elimina un nodo con dos hijos", c.eliminar(30));
        verificar("El sucesor inorden sube a su lugar",
                c.getRaiz().getIzquierdo().getDato() == 40);
        verificar("El orden se conserva con dos hijos",
                c.inorden().equals("20, 40, 50, 60, 70, 80"));

        // Eliminar la raiz
        ArbolBinario<Integer> d = arbolEjemplo();
        verificar("Elimina la raiz", d.eliminar(50));
        verificar("El sucesor se vuelve la nueva raiz", d.getRaiz().getDato() == 60);
        verificar("El orden se conserva tras eliminar la raiz",
                d.inorden().equals("20, 30, 40, 60, 70, 80"));

        // Casos limite
        verificar("Eliminar un dato inexistente devuelve false", !d.eliminar(999));
        verificar("Un fallo al eliminar no cambia la cantidad", d.getCantidad() == 6);
        verificar("Eliminar null devuelve false", !d.eliminar(null));

        ArbolBinario<Integer> vacio = new ArbolBinario<>();
        verificar("Eliminar en un arbol vacio devuelve false", !vacio.eliminar(10));

        ArbolBinario<Integer> uno = new ArbolBinario<>();
        uno.insertar(7);
        verificar("Elimina el unico nodo", uno.eliminar(7));
        verificar("El arbol queda vacio", uno.estaVacio() && uno.getCantidad() == 0);

        // Vaciar el arbol nodo por nodo
        ArbolBinario<Integer> e = arbolEjemplo();
        int[] orden = {50, 30, 70, 20, 40, 60, 80};
        boolean todosEliminados = true;
        for (int i = 0; i < orden.length; i++) {
            if (!e.eliminar(orden[i])) todosEliminados = false;
        }
        verificar("Se puede vaciar el arbol eliminando uno por uno",
                todosEliminados && e.estaVacio());
    }

    /** Registra el resultado de una prueba e imprime OK o FALLO. */
    private static void verificar(String descripcion, boolean condicion) {
        totales++;
        if (condicion) {
            exitosas++;
            System.out.println("   [OK]    " + descripcion);
        } else {
            System.out.println("   [FALLO] " + descripcion);
        }
    }

    // ---------------- MEDIDAS ----------------

    private static void probarMedidas() {
        System.out.println("\n--- GRUPO 5: MEDIDAS DEL ARBOL ---");
        ArbolBinario<Integer> a = arbolEjemplo();

        verificar("La altura del arbol de ejemplo es 3", a.altura() == 3);
        verificar("Tiene 4 hojas", a.contarHojas() == 4);
        verificar("El minimo es 20", a.getMinimo() == 20);
        verificar("El maximo es 80", a.getMaximo() == 80);

        ArbolBinario<Integer> vacio = new ArbolBinario<>();
        verificar("El minimo de un arbol vacio es null", vacio.getMinimo() == null);
        verificar("El maximo de un arbol vacio es null", vacio.getMaximo() == null);
        verificar("Las hojas de un arbol vacio son 0", vacio.contarHojas() == 0);

        // Arbol degenerado: datos insertados ya ordenados
        ArbolBinario<Integer> degenerado = new ArbolBinario<>();
        for (int i = 1; i <= 10; i++) degenerado.insertar(i);
        verificar("Insertar datos ordenados degenera el arbol (altura = nodos)",
                degenerado.altura() == 10);
        verificar("Un arbol degenerado tiene una sola hoja",
                degenerado.contarHojas() == 1);
        verificar("La altura ideal de 10 nodos es 4", degenerado.alturaIdeal() == 4);

        a.vaciar();
        verificar("vaciar() deja el arbol vacio", a.estaVacio() && a.getCantidad() == 0);
    }

    // ---------------- CASO PRACTICO ----------------

    private static void probarEmpleados() {
        System.out.println("\n--- GRUPO 6: CASO PRACTICO (EMPLEADOS) ---");
        GestionEmpleados gestion = new GestionEmpleados();
        gestion.cargar(GestionEmpleados.plantillaDemo());

        verificar("Se cargaron los 12 empleados de la demo",
                gestion.getArbol().getCantidad() == 12);

        Empleado encontrado = gestion.buscarEnArbol(1035);
        verificar("Encuentra un empleado por su ID", encontrado != null);
        verificar("Devuelve el empleado completo, no solo el ID",
                encontrado != null && "Sofia Herrera".equals(encontrado.getNombre()));
        verificar("Devuelve null con un ID inexistente",
                gestion.buscarEnArbol(9999) == null);

        verificar("El arbol compara menos veces que la busqueda secuencial",
                comparacionesArbol(gestion, 1090) < comparacionesSecuencial(gestion, 1090));

        // La busqueda por clave encuentra al mismo empleado que la secuencial
        boolean coinciden = true;
        Empleado[] plantilla = gestion.getPlantilla();
        for (int i = 0; i < plantilla.length; i++) {
            int id = plantilla[i].getId();
            Empleado porArbol = gestion.buscarEnArbol(id);
            Empleado porLista = gestion.buscarSecuencial(id);
            if (porArbol == null || porLista == null
                    || porArbol.getId() != porLista.getId()) {
                coinciden = false;
            }
        }
        verificar("Los dos metodos encuentran al mismo empleado", coinciden);

        // Altas
        verificar("Contrata un empleado nuevo",
                gestion.contratar(new Empleado(1100, "Nuevo Empleado", "Auxiliar", "Ventas")));
        verificar("Tras contratar hay 13 empleados", gestion.getArbol().getCantidad() == 13);
        verificar("El nuevo empleado se puede encontrar",
                gestion.buscarEnArbol(1100) != null);
        verificar("Rechaza un ID duplicado",
                !gestion.contratar(new Empleado(1100, "Repetido", "Auxiliar", "Ventas")));

        // Bajas
        verificar("Da de baja a un empleado", gestion.darDeBaja(1050));
        verificar("Tras la baja hay 12 empleados", gestion.getArbol().getCantidad() == 12);
        verificar("El empleado dado de baja ya no aparece",
                gestion.buscarEnArbol(1050) == null);
        verificar("La baja tambien se refleja en la lista secuencial",
                gestion.buscarSecuencial(1050) == null);
        verificar("Dar de baja un ID inexistente devuelve false",
                !gestion.darDeBaja(9999));

        // El recorrido inorden entrega la plantilla ordenada por ID
        String inorden = gestion.getArbol().inorden();
        verificar("El inorden empieza por el ID mas pequeno",
                inorden.startsWith("1005"));

        // Eficiencia con una plantilla grande
        GestionEmpleados grande = new GestionEmpleados();
        grande.cargar(GestionEmpleados.generarPlantilla(1000));
        verificar("Se cargaron 1000 empleados", grande.getArbol().getCantidad() == 1000);
        verificar("La altura de 1000 empleados se mantiene baja",
                grande.getArbol().altura() < 40);
        verificar("Todos los empleados generados se pueden encontrar",
                todosEncontrados(grande));
    }

    private static int comparacionesArbol(GestionEmpleados gestion, int id) {
        gestion.buscarEnArbol(id);
        return gestion.getArbol().getComparaciones();
    }

    private static int comparacionesSecuencial(GestionEmpleados gestion, int id) {
        gestion.buscarSecuencial(id);
        return gestion.getComparacionesSecuencial();
    }

    private static boolean todosEncontrados(GestionEmpleados gestion) {
        Empleado[] plantilla = gestion.getPlantilla();
        for (int i = 0; i < plantilla.length; i++) {
            if (gestion.buscarEnArbol(plantilla[i].getId()) == null) return false;
        }
        return true;
    }
}
