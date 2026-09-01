package listaligada;

import java.util.Scanner;

/* @author friki */

/**
 * Programa principal de la Actividad 2: un menu interactivo para operar
 * el simulador de sistema operativo (Cola de procesos listos + Pila de
 * procesos suspendidos), con opciones para correr ademas la bateria de
 * pruebas automaticas de ambas estructuras y del conversor de
 * expresiones infijas a postfijas.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Cola<Proceso> colaListos = new Cola<>();
    private static final Pila<Proceso> pilaSuspendidos = new Pila<>();
    private static Proceso enEjecucion = null;
    private static int siguientePid = 1;

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero();
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== Simulador de sistema operativo =====");
        System.out.println("Proceso en ejecucion: " + (enEjecucion == null ? "(ninguno)" : enEjecucion));
        System.out.println(" 1. Agregar proceso a la cola de listos");
        System.out.println(" 2. Ver el proceso al frente de la cola (peek)");
        System.out.println(" 3. Despachar el proceso al frente de la cola (dequeue)");
        System.out.println(" 4. Suspender el proceso en ejecucion (push a la pila)");
        System.out.println(" 5. Reanudar el ultimo proceso suspendido (pop)");
        System.out.println(" 6. Ver el tope de la pila de suspendidos (peek)");
        System.out.println(" 7. Eliminar un proceso de la cola por PID");
        System.out.println(" 8. Ver el estado completo (cola y pila)");
        System.out.println(" 9. Ejecutar pruebas automaticas del simulador");
        System.out.println("10. Ejecutar pruebas de conversion infija a postfija");
        System.out.println(" 0. Salir");
        System.out.print("Elige una opcion: ");
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarProceso();
            case 2 -> verFrenteCola();
            case 3 -> despacharProceso();
            case 4 -> suspenderProceso();
            case 5 -> reanudarProceso();
            case 6 -> verTopePila();
            case 7 -> eliminarProcesoPorPid();
            case 8 -> verEstadoCompleto();
            case 9 -> pruebasAutomaticasSimulador();
            case 10 -> pruebasConversion();
            case 0 -> System.out.println("Saliendo...");
            default -> System.out.println("Opcion invalida.");
        }
    }

    private static int leerEntero() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void agregarProceso() {
        System.out.print("Nombre del proceso: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Prioridad (numero entero): ");
        int prioridad = leerEntero();
        Proceso nuevo = new Proceso(siguientePid++, nombre, prioridad);
        colaListos.enqueue(nuevo);
        System.out.println("Agregado: " + nuevo);
    }

    private static void verFrenteCola() {
        Proceso frente = colaListos.peek();
        System.out.println(frente == null ? "La cola esta vacia." : "Al frente: " + frente);
    }

    private static void despacharProceso() {
        if (enEjecucion != null) {
            System.out.println("Ya hay un proceso en ejecucion (" + enEjecucion + "). Suspendelo o reanudalo antes de despachar otro.");
            return;
        }
        Proceso siguiente = colaListos.dequeue();
        if (siguiente == null) {
            System.out.println("La cola esta vacia, no hay nada que despachar.");
            return;
        }
        enEjecucion = siguiente;
        System.out.println("Ejecutando: " + enEjecucion);
    }

    private static void suspenderProceso() {
        if (enEjecucion == null) {
            System.out.println("No hay ningun proceso en ejecucion para suspender.");
            return;
        }
        pilaSuspendidos.push(enEjecucion);
        System.out.println("Suspendido y apilado: " + enEjecucion);
        enEjecucion = null;
    }

    private static void reanudarProceso() {
        if (enEjecucion != null) {
            System.out.println("Ya hay un proceso en ejecucion (" + enEjecucion + "). Suspendelo antes de reanudar otro.");
            return;
        }
        Proceso reanudado = pilaSuspendidos.pop();
        if (reanudado == null) {
            System.out.println("No hay procesos suspendidos.");
            return;
        }
        enEjecucion = reanudado;
        System.out.println("Reanudando: " + enEjecucion);
    }

    private static void verTopePila() {
        Proceso tope = pilaSuspendidos.peek();
        System.out.println(tope == null ? "La pila de suspendidos esta vacia." : "Tope de la pila: " + tope);
    }

    private static void eliminarProcesoPorPid() {
        System.out.print("PID del proceso a eliminar de la cola: ");
        int pid = leerEntero();
        boolean eliminado = colaListos.eliminar(new Proceso(pid, "", 0));
        System.out.println(eliminado
                ? "Proceso con PID " + pid + " eliminado de la cola."
                : "No se encontro un proceso con ese PID en la cola.");
    }

    private static void verEstadoCompleto() {
        System.out.println("Cola de procesos listos:");
        colaListos.mostrar();
        System.out.println("Pila de procesos suspendidos:");
        pilaSuspendidos.mostrar();
    }

    private static void pruebasAutomaticasSimulador() {
        System.out.println("\n--- Pruebas automaticas del simulador (estructuras propias, no afectan tu sesion interactiva) ---");
        Cola<Proceso> cola = new Cola<>();
        Pila<Proceso> pila = new Pila<>();

        System.out.println("=== Llegan procesos al sistema (encolar) ===");
        cola.enqueue(new Proceso(101, "Editor de texto", 2));
        cola.enqueue(new Proceso(102, "Navegador", 1));
        cola.enqueue(new Proceso(103, "Compilador", 3));
        cola.enqueue(new Proceso(104, "Reproductor de musica", 1));
        System.out.println("Cola de procesos listos:");
        cola.mostrar();
        System.out.println("Procesos en espera: " + cola.getSize());

        System.out.println("\n=== El planificador despacha el primer proceso ===");
        Proceso p = cola.dequeue();
        System.out.println("Ejecutando: " + p);
        System.out.println("Siguiente en la cola (peek, sin sacarlo): " + cola.peek());

        System.out.println("\n=== Ocurre una interrupcion: se suspende el proceso actual ===");
        pila.push(p);
        System.out.println("Proceso suspendido y apilado: " + p);
        System.out.println("Tope de la pila de suspendidos (peek): " + pila.peek());

        System.out.println("\n=== El planificador despacha el siguiente proceso de la cola ===");
        p = cola.dequeue();
        System.out.println("Ejecutando: " + p);

        System.out.println("\n=== Segunda interrupcion mientras se ejecuta ese proceso ===");
        pila.push(p);
        System.out.println("Proceso suspendido y apilado: " + p);
        System.out.println("Pila de procesos suspendidos:");
        pila.mostrar();
        System.out.println("Total de procesos suspendidos: " + pila.getSize());

        System.out.println("\n=== Se resuelven las interrupciones: se reanudan en orden LIFO ===");
        while (!pila.isEmpty()) {
            System.out.println("Reanudando: " + pila.pop());
        }
        System.out.println("Pila de suspendidos vacia? " + pila.isEmpty());

        System.out.println("\n=== Se atienden los procesos restantes de la cola ===");
        while (!cola.isEmpty()) {
            System.out.println("Ejecutando: " + cola.dequeue());
        }
        System.out.println("Cola de listos vacia? " + cola.isEmpty());

        System.out.println("\n=== Prueba de estructuras vacias ===");
        System.out.println("pop() en pila vacia devuelve: " + pila.pop());
        System.out.println("dequeue() en cola vacia devuelve: " + cola.dequeue());
        System.out.println("peek() en pila vacia devuelve: " + pila.peek());
        System.out.println("peek() en cola vacia devuelve: " + cola.peek());
    }

    private static void pruebasConversion() {
        System.out.println("\n--- Pruebas de conversion infija a postfija ---");
        char[] expresion1 = {'2', '+', '3', '*', '4'};
        char[] expresion2 = {'(', '2', '+', '3', ')', '*', '4'};
        char[] expresion3 = {'2', '+', '3', '*', '(', '4', '-', '1', ')'};

        System.out.print("Prueba 1 - Postfija: ");
        ConversionInfijaPostfija.convertir(expresion1).imprimir();

        System.out.print("Prueba 2 - Postfija: ");
        ConversionInfijaPostfija.convertir(expresion2).imprimir();

        System.out.print("Prueba 3 - Postfija: ");
        ConversionInfijaPostfija.convertir(expresion3).imprimir();
    }
}
