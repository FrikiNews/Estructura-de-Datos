package avancereto;

/* @author friki */

/**
 * Banco de pruebas automatizadas del sistema.
 *
 * Ejecuta y verifica todas las operaciones de las tres estructuras,
 * incluyendo los casos limite (estructura vacia, posicion invalida,
 * elemento inexistente). Cada prueba imprime OK o FALLO y al final se
 * muestra un resumen.
 *
 * Se corre aparte del programa principal: clic derecho sobre este archivo
 * en NetBeans y "Run File" (Shift+F6).
 */
public class PruebasAvanceReto {

    private static int totales = 0;
    private static int exitosas = 0;

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("     PRUEBAS DEL SISTEMA DE GESTION DE TAREAS");
        System.out.println("==========================================================");

        probarPila();
        probarCola();
        probarLista();
        probarOrdenamiento();

        System.out.println("\n==========================================================");
        System.out.println("  RESUMEN: " + exitosas + " de " + totales + " pruebas exitosas.");
        if (exitosas == totales) {
            System.out.println("  RESULTADO: todas las pruebas pasaron correctamente.");
        } else {
            System.out.println("  RESULTADO: hay " + (totales - exitosas) + " prueba(s) con fallos.");
        }
        System.out.println("==========================================================");
    }

    // ---------- PRUEBAS DE LA PILA ----------

    private static void probarPila() {
        System.out.println("\n--- GRUPO 1: PILA (LIFO) - push, pop, peek ---");
        Pila<Tarea> pila = new Pila<>();

        verificar("Pila recien creada esta vacia", pila.isEmpty());
        verificar("Pila vacia tiene tamano 0", pila.getSize() == 0);
        verificar("pop() en pila vacia devuelve null", pila.pop() == null);
        verificar("peek() en pila vacia devuelve null", pila.peek() == null);

        pila.push(new Tarea("Tarea A", "Sistemas", 5));
        pila.push(new Tarea("Tarea B", "Ventas", 3));
        pila.push(new Tarea("Tarea C", "Almacen", 4));

        verificar("Tras 3 push el tamano es 3", pila.getSize() == 3);
        verificar("Pila con elementos ya no esta vacia", !pila.isEmpty());
        verificar("peek() devuelve la ultima apilada (Tarea C)",
                pila.peek().getDescripcion().equals("Tarea C"));
        verificar("peek() NO elimina el elemento", pila.getSize() == 3);

        verificar("pop() 1 devuelve Tarea C (LIFO)",
                pila.pop().getDescripcion().equals("Tarea C"));
        verificar("pop() 2 devuelve Tarea B (LIFO)",
                pila.pop().getDescripcion().equals("Tarea B"));
        verificar("pop() 3 devuelve Tarea A (LIFO)",
                pila.pop().getDescripcion().equals("Tarea A"));
        verificar("Tras vaciarla la pila esta vacia", pila.isEmpty());
        verificar("pop() extra tras vaciarla devuelve null", pila.pop() == null);
    }

    // ---------- PRUEBAS DE LA COLA ----------

    private static void probarCola() {
        System.out.println("\n--- GRUPO 2: COLA (FIFO) - enqueue, dequeue, front ---");
        Cola<Tarea> cola = new Cola<>();

        verificar("Cola recien creada esta vacia", cola.isEmpty());
        verificar("dequeue() en cola vacia devuelve null", cola.dequeue() == null);
        verificar("front() en cola vacia devuelve null", cola.front() == null);

        cola.enqueue(new Tarea("Respaldo", "Sistemas", 2));
        cola.enqueue(new Tarea("Reporte", "Contabilidad", 3));
        cola.enqueue(new Tarea("Inventario", "Almacen", 1));

        verificar("Tras 3 enqueue el tamano es 3", cola.getSize() == 3);
        verificar("front() devuelve la primera formada (Respaldo)",
                cola.front().getDescripcion().equals("Respaldo"));
        verificar("front() NO elimina el elemento", cola.getSize() == 3);

        verificar("dequeue() 1 devuelve Respaldo (FIFO)",
                cola.dequeue().getDescripcion().equals("Respaldo"));
        verificar("dequeue() 2 devuelve Reporte (FIFO)",
                cola.dequeue().getDescripcion().equals("Reporte"));
        verificar("dequeue() 3 devuelve Inventario (FIFO)",
                cola.dequeue().getDescripcion().equals("Inventario"));
        verificar("Tras vaciarla la cola esta vacia", cola.isEmpty());
        verificar("dequeue() extra tras vaciarla devuelve null", cola.dequeue() == null);
    }

    // ---------- PRUEBAS DE LA LISTA ----------

    private static void probarLista() {
        System.out.println("\n--- GRUPO 3: LISTA - insert, delete, find, get ---");
        ListaTareas lista = new ListaTareas();

        verificar("Lista recien creada esta vacia", lista.isEmpty());
        verificar("delete() en lista vacia devuelve false", !lista.delete("X"));
        verificar("get() en lista vacia devuelve null", lista.get(0) == null);
        verificar("find() en lista vacia no encuentra nada", lista.find("Sistemas").isEmpty());

        lista.insert(new Tarea("Inventario", "Almacen", 2));
        lista.insert(new Tarea("Capacitacion", "Recursos Humanos", 1));
        lista.insert(new Tarea("Auditoria", "Contabilidad", 4));

        verificar("Tras 3 insert el tamano es 3", lista.getSize() == 3);
        verificar("insert() agrega al final: pos 0 es Inventario",
                lista.get(0).getDescripcion().equals("Inventario"));
        verificar("insert() agrega al final: pos 2 es Auditoria",
                lista.get(2).getDescripcion().equals("Auditoria"));

        // Insercion en posicion arbitraria (acceso aleatorio)
        lista.insert(new Tarea("Nomina", "Contabilidad", 3), 1);
        verificar("insert(pos 1) coloca Nomina en la posicion 1",
                lista.get(1).getDescripcion().equals("Nomina"));
        verificar("insert(pos 1) recorre Capacitacion a la posicion 2",
                lista.get(2).getDescripcion().equals("Capacitacion"));
        verificar("Tras insertar en posicion el tamano es 4", lista.getSize() == 4);

        lista.insert(new Tarea("AlInicio", "Ventas", 1), 0);
        verificar("insert(pos 0) coloca la tarea al inicio",
                lista.get(0).getDescripcion().equals("AlInicio"));

        lista.insert(new Tarea("FueraRango", "Ventas", 1), 99);
        verificar("insert() con posicion invalida la agrega al final",
                lista.get(lista.getSize() - 1).getDescripcion().equals("FueraRango"));

        // Acceso por posicion invalida
        verificar("get(-1) devuelve null", lista.get(-1) == null);
        verificar("get(999) devuelve null", lista.get(999) == null);

        // Busqueda por departamento
        ListaEnlazada<Tarea> conta = lista.find("Contabilidad");
        verificar("find('Contabilidad') encuentra 2 tareas", conta.getSize() == 2);
        ListaEnlazada<Tarea> minusculas = lista.find("contabilidad");
        verificar("find() ignora mayusculas/minusculas", minusculas.getSize() == 2);
        verificar("find() de un depto inexistente devuelve vacio",
                lista.find("Marketing").isEmpty());
        verificar("find() NO modifica la lista original", lista.getSize() == 6);

        // Eliminacion
        int antes = lista.getSize();
        verificar("delete() de una tarea intermedia devuelve true", lista.delete("Nomina"));
        verificar("delete() reduce el tamano en 1", lista.getSize() == antes - 1);
        verificar("delete() de una tarea inexistente devuelve false",
                !lista.delete("NoExiste"));
        verificar("delete() ignora mayusculas/minusculas", lista.delete("alinicio"));
        verificar("delete() del primer elemento reacomoda la cabeza",
                lista.get(0).getDescripcion().equals("Inventario"));
    }

    // ---------- PRUEBAS DEL ORDENAMIENTO ----------

    private static void probarOrdenamiento() {
        System.out.println("\n--- GRUPO 4: ORDEN por urgencia y departamento ---");
        ListaTareas orden = new ListaTareas();

        orden.insertOrdenado(new Tarea("Baja", "Ventas", 1));
        orden.insertOrdenado(new Tarea("Critica", "Sistemas", 5));
        orden.insertOrdenado(new Tarea("Media", "Almacen", 3));
        orden.insertOrdenado(new Tarea("Alta-Ventas", "Ventas", 4));
        orden.insertOrdenado(new Tarea("Alta-Almacen", "Almacen", 4));

        verificar("La urgencia 5 queda en primer lugar",
                orden.get(0).getUrgencia() == 5);
        verificar("La urgencia 1 queda en ultimo lugar",
                orden.get(orden.getSize() - 1).getUrgencia() == 1);

        boolean descendente = true;
        for (int i = 0; i < orden.getSize() - 1; i++) {
            if (orden.get(i).getUrgencia() < orden.get(i + 1).getUrgencia()) {
                descendente = false;
            }
        }
        verificar("Las urgencias quedan en orden descendente", descendente);

        verificar("A igual urgencia, Almacen va antes que Ventas",
                orden.get(1).getDepartamento().equals("Almacen")
                && orden.get(2).getDepartamento().equals("Ventas"));

        System.out.println("   Lista ordenada resultante:");
        orden.mostrar();
    }

    // ---------- AUXILIAR ----------

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
}
