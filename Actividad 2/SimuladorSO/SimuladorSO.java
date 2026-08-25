package listaligada;

/* @author friki */

/**
 * Modulo de gestion de sistema operativo simulado.
 *
 * Integra las dos estructuras de esta actividad:
 * - Cola<Proceso>: procesos listos en espera de CPU (planificacion FCFS,
 *   el primero que llega es el primero en ejecutarse).
 * - Pila<Proceso>: procesos suspendidos por una interrupcion. El ultimo
 *   proceso interrumpido es el primero en reanudarse, igual que una
 *   pila de llamadas.
 */
public class SimuladorSO {

    public static void main(String[] args) {
        Cola<Proceso> colaListos = new Cola<>();
        Pila<Proceso> pilaSuspendidos = new Pila<>();

        System.out.println("=== Llegan procesos al sistema (encolar) ===");
        colaListos.enqueue(new Proceso(1, "Editor de texto", 2));
        colaListos.enqueue(new Proceso(2, "Navegador", 1));
        colaListos.enqueue(new Proceso(3, "Compilador", 3));
        colaListos.enqueue(new Proceso(4, "Reproductor de musica", 1));
        System.out.println("Cola de procesos listos:");
        colaListos.mostrar();
        System.out.println("Procesos en espera: " + colaListos.getSize());

        System.out.println("\n=== El planificador despacha el primer proceso ===");
        Proceso enEjecucion = colaListos.dequeue();
        System.out.println("Ejecutando: " + enEjecucion);
        System.out.println("Siguiente en la cola (peek, sin sacarlo): " + colaListos.peek());

        System.out.println("\n=== Ocurre una interrupcion: se suspende el proceso actual ===");
        pilaSuspendidos.push(enEjecucion);
        System.out.println("Proceso suspendido y apilado: " + enEjecucion);
        System.out.println("Tope de la pila de suspendidos (peek): " + pilaSuspendidos.peek());

        System.out.println("\n=== El planificador despacha el siguiente proceso de la cola ===");
        enEjecucion = colaListos.dequeue();
        System.out.println("Ejecutando: " + enEjecucion);

        System.out.println("\n=== Segunda interrupcion mientras se ejecuta ese proceso ===");
        pilaSuspendidos.push(enEjecucion);
        System.out.println("Proceso suspendido y apilado: " + enEjecucion);
        System.out.println("Pila de procesos suspendidos:");
        pilaSuspendidos.mostrar();
        System.out.println("Total de procesos suspendidos: " + pilaSuspendidos.getSize());

        System.out.println("\n=== Se resuelven las interrupciones: se reanudan en orden LIFO ===");
        while (!pilaSuspendidos.isEmpty()) {
            Proceso reanudado = pilaSuspendidos.pop();
            System.out.println("Reanudando: " + reanudado);
        }
        System.out.println("Pila de suspendidos vacia? " + pilaSuspendidos.isEmpty());

        System.out.println("\n=== Se atienden los procesos restantes de la cola ===");
        while (!colaListos.isEmpty()) {
            Proceso siguiente = colaListos.dequeue();
            System.out.println("Ejecutando: " + siguiente);
        }
        System.out.println("Cola de listos vacia? " + colaListos.isEmpty());

        System.out.println("\n=== Prueba de estructuras vacias ===");
        System.out.println("pop() en pila vacia devuelve: " + pilaSuspendidos.pop());
        System.out.println("dequeue() en cola vacia devuelve: " + colaListos.dequeue());
        System.out.println("peek() en pila vacia devuelve: " + pilaSuspendidos.peek());
        System.out.println("peek() en cola vacia devuelve: " + colaListos.peek());
    }
}
