package avancereto;

import java.util.Scanner;

/* @author friki */

/**
 * SISTEMA DE GESTION DE TAREAS EMPRESARIALES
 *
 * Administra las tareas de una empresa con tres estructuras de datos,
 * cada una elegida por el tipo de acceso que exige el problema:
 *
 *   PILA  (LIFO) -> Tareas urgentes.
 *                   Lo ultimo que se reporta es lo primero que se atiende.
 *                   Operaciones: push, pop, peek.
 *
 *   COLA  (FIFO) -> Tareas programadas.
 *                   Se respeta el orden de llegada, nadie se adelanta.
 *                   Operaciones: enqueue, dequeue, front.
 *
 *   LISTA        -> Tareas por departamento.
 *                   Requiere acceso aleatorio: buscar, consultar por
 *                   posicion y eliminar elementos intermedios.
 *                   Operaciones: insert, delete, find.
 *
 * RESTRICCION DE DISENO: no se usan arreglos ni colecciones de java.util.
 * Todo el almacenamiento es dinamico, con nodos enlazados (ver Nodo.java).
 *
 * @author friki
 */
public class AvanceReto {

    private static Pila<Tarea> urgentes = new Pila<>();
    private static Cola<Tarea> programadas = new Cola<>();
    private static ListaTareas porDepartamento = new ListaTareas();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        cargarEjemplos();
        System.out.println("\n==================================================");
        System.out.println("   SISTEMA DE GESTION DE TAREAS EMPRESARIALES");
        System.out.println("==================================================");
        System.out.println("Se cargaron tareas de ejemplo para comenzar.");

        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            switch (leerEntero()) {
                case 1: menuPila(); break;
                case 2: menuCola(); break;
                case 3: menuLista(); break;
                case 4: verTodas(); break;
                case 0:
                    salir = true;
                    System.out.println("\nSaliendo del sistema. Hasta luego.");
                    break;
                default:
                    System.out.println(">> Opcion invalida. Elige un numero del 0 al 4.");
            }
        }
        sc.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=============== MENU PRINCIPAL ===============");
        System.out.println(" Pendientes: " + urgentes.getSize() + " urgentes | "
                + programadas.getSize() + " programadas | "
                + porDepartamento.getSize() + " en lista");
        System.out.println("----------------------------------------------");
        System.out.println("  1) Tareas URGENTES          (Pila  - LIFO)");
        System.out.println("  2) Tareas PROGRAMADAS       (Cola  - FIFO)");
        System.out.println("  3) Tareas POR DEPARTAMENTO  (Lista)");
        System.out.println("  4) Ver TODAS las pendientes (urgencia y depto.)");
        System.out.println("  0) Salir");
        System.out.println("----------------------------------------------");
        System.out.print("Selecciona una opcion: ");
    }

    // ============ PILA: TAREAS URGENTES ============

    private static void menuPila() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n---- TAREAS URGENTES (Pila / LIFO) ----");
            System.out.println("En la pila (" + urgentes.getSize() + "): se atiende primero la mas reciente.");
            System.out.println("  1) Agregar tarea urgente        (push)");
            System.out.println("  2) Atender la tarea de la cima  (pop)");
            System.out.println("  3) Ver la cima sin atenderla    (peek)");
            System.out.println("  4) Ver toda la pila");
            System.out.println("  0) Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            switch (leerEntero()) {
                case 1:
                    Tarea nueva = leerTarea();
                    urgentes.push(nueva);
                    System.out.println(">> Agregada a la cima: " + nueva);
                    break;
                case 2:
                    if (urgentes.isEmpty()) {
                        System.out.println(">> La pila esta vacia, no hay nada que atender.");
                    } else {
                        System.out.println(">> Tarea atendida: " + urgentes.pop());
                        System.out.println(">> Quedan " + urgentes.getSize() + " tareas urgentes.");
                    }
                    break;
                case 3:
                    if (urgentes.isEmpty()) {
                        System.out.println(">> La pila esta vacia.");
                    } else {
                        System.out.println(">> Siguiente a atender: " + urgentes.peek());
                    }
                    break;
                case 4:
                    System.out.println("Contenido de la pila (1 = cima, se atiende primero):");
                    urgentes.mostrar();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println(">> Opcion invalida. Elige un numero del 0 al 4.");
            }
        }
    }

    // ============ COLA: TAREAS PROGRAMADAS ============

    private static void menuCola() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n---- TAREAS PROGRAMADAS (Cola / FIFO) ----");
            System.out.println("En la cola (" + programadas.getSize() + "): se atiende en orden de llegada.");
            System.out.println("  1) Agregar tarea programada      (enqueue)");
            System.out.println("  2) Atender la primera de la fila (dequeue)");
            System.out.println("  3) Ver la primera sin atenderla  (front)");
            System.out.println("  4) Ver toda la cola");
            System.out.println("  0) Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            switch (leerEntero()) {
                case 1:
                    Tarea nueva = leerTarea();
                    programadas.enqueue(nueva);
                    System.out.println(">> Formada al final de la cola: " + nueva);
                    break;
                case 2:
                    if (programadas.isEmpty()) {
                        System.out.println(">> La cola esta vacia, no hay nada que atender.");
                    } else {
                        System.out.println(">> Tarea atendida: " + programadas.dequeue());
                        System.out.println(">> Quedan " + programadas.getSize() + " tareas programadas.");
                    }
                    break;
                case 3:
                    if (programadas.isEmpty()) {
                        System.out.println(">> La cola esta vacia.");
                    } else {
                        System.out.println(">> Primera de la fila: " + programadas.front());
                    }
                    break;
                case 4:
                    System.out.println("Contenido de la cola (1 = frente, se atiende primero):");
                    programadas.mostrar();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println(">> Opcion invalida. Elige un numero del 0 al 4.");
            }
        }
    }

    // ============ LISTA: TAREAS POR DEPARTAMENTO ============

    private static void menuLista() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n---- TAREAS POR DEPARTAMENTO (Lista) ----");
            System.out.println("En la lista (" + porDepartamento.getSize() + "): permite acceso aleatorio.");
            System.out.println("  1) Agregar tarea al final          (insert)");
            System.out.println("  2) Agregar tarea en una posicion   (insert)");
            System.out.println("  3) Eliminar tarea por descripcion  (delete)");
            System.out.println("  4) Buscar tareas por departamento  (find)");
            System.out.println("  5) Consultar tarea por posicion");
            System.out.println("  6) Ver toda la lista");
            System.out.println("  0) Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            switch (leerEntero()) {
                case 1:
                    Tarea alFinal = leerTarea();
                    porDepartamento.insert(alFinal);
                    System.out.println(">> Agregada al final: " + alFinal);
                    break;
                case 2:
                    Tarea enPos = leerTarea();
                    System.out.print("Posicion donde insertarla (0 a " + porDepartamento.getSize() + "): ");
                    int pos = leerEntero();
                    porDepartamento.insert(enPos, pos);
                    System.out.println(">> Insertada: " + enPos);
                    System.out.println("Lista actualizada:");
                    porDepartamento.mostrar();
                    break;
                case 3:
                    if (porDepartamento.isEmpty()) {
                        System.out.println(">> La lista esta vacia.");
                        break;
                    }
                    System.out.print("Descripcion exacta de la tarea a eliminar: ");
                    String desc = sc.nextLine();
                    if (porDepartamento.delete(desc)) {
                        System.out.println(">> Tarea eliminada. Quedan " + porDepartamento.getSize() + ".");
                    } else {
                        System.out.println(">> No se encontro una tarea con esa descripcion.");
                    }
                    break;
                case 4:
                    System.out.print("Departamento a buscar: ");
                    String depto = sc.nextLine();
                    ListaEnlazada<Tarea> encontradas = porDepartamento.find(depto);
                    if (encontradas.isEmpty()) {
                        System.out.println(">> No hay tareas registradas para '" + depto + "'.");
                    } else {
                        System.out.println(">> " + encontradas.getSize() + " tarea(s) de '" + depto + "':");
                        encontradas.mostrar();
                    }
                    break;
                case 5:
                    if (porDepartamento.isEmpty()) {
                        System.out.println(">> La lista esta vacia.");
                        break;
                    }
                    System.out.print("Posicion a consultar (0 a " + (porDepartamento.getSize() - 1) + "): ");
                    Tarea t = porDepartamento.get(leerEntero());
                    if (t == null) {
                        System.out.println(">> Esa posicion no existe en la lista.");
                    } else {
                        System.out.println(">> Tarea encontrada: " + t);
                    }
                    break;
                case 6:
                    System.out.println("Contenido de la lista:");
                    porDepartamento.mostrar();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println(">> Opcion invalida. Elige un numero del 0 al 6.");
            }
        }
    }

    // ============ VISTA COMBINADA ============

    /**
     * Reune las tareas de las tres estructuras en una lista nueva, ordenada
     * por urgencia (mayor primero) y, a igual urgencia, por departamento
     * alfabetico.
     *
     * Se recorren las tres estructuras y cada tarea se inserta ya en su
     * lugar con insertOrdenado(), asi no hace falta un ordenamiento aparte.
     * Las estructuras originales NO se modifican: la vista se puede
     * consultar cuantas veces se quiera sin perder tareas.
     */
    private static void verTodas() {
        ListaTareas todas = new ListaTareas();

        copiarOrdenado(todas, urgentes.getHead(), "Pila/Urgente");
        copiarOrdenado(todas, programadas.getHead(), "Cola/Programada");
        copiarOrdenado(todas, porDepartamento.getHead(), "Lista/Departamento");

        if (todas.isEmpty()) {
            System.out.println("\n>> No hay tareas pendientes en ninguna estructura.");
            return;
        }

        System.out.println("\n===== TODAS LAS TAREAS PENDIENTES (" + todas.getSize() + ") =====");
        System.out.println("Orden: urgencia de mayor a menor, luego departamento (A-Z).");
        System.out.println("----------------------------------------------------------");

        // Se recorre a mano en vez de usar mostrar() porque esta vista si
        // necesita indicar de que estructura viene cada tarea.
        Nodo<Tarea> actual = todas.getHead();
        int i = 1;
        while (actual != null) {
            System.out.println("   " + i + ". " + actual.data.toStringConOrigen());
            actual = actual.next;
            i++;
        }

        System.out.println("----------------------------------------------------------");
        System.out.println("Resumen: " + urgentes.getSize() + " en pila, "
                + programadas.getSize() + " en cola, "
                + porDepartamento.getSize() + " en lista.");
    }

    /**
     * Recorre una estructura y copia cada tarea a la lista destino en su
     * posicion ordenada, marcando de que estructura proviene.
     */
    private static void copiarOrdenado(ListaTareas destino, Nodo<Tarea> origen, String etiqueta) {
        Nodo<Tarea> actual = origen;
        while (actual != null) {
            actual.data.setOrigen(etiqueta);
            destino.insertOrdenado(actual.data);
            actual = actual.next;
        }
    }

    // ============ AUXILIARES DE ENTRADA ============

    /** Pide al usuario los datos de una tarea, validando la urgencia. */
    private static Tarea leerTarea() {
        String descripcion = "";
        while (descripcion.trim().isEmpty()) {
            System.out.print("Descripcion de la tarea: ");
            descripcion = sc.nextLine();
            if (descripcion.trim().isEmpty()) {
                System.out.println(">> La descripcion no puede quedar vacia.");
            }
        }

        String departamento = "";
        while (departamento.trim().isEmpty()) {
            System.out.print("Departamento responsable: ");
            departamento = sc.nextLine();
            if (departamento.trim().isEmpty()) {
                System.out.println(">> El departamento no puede quedar vacio.");
            }
        }

        int urgencia = 0;
        while (urgencia < 1 || urgencia > 5) {
            System.out.print("Urgencia (1=minima, 2=baja, 3=media, 4=alta, 5=critica): ");
            urgencia = leerEntero();
            if (urgencia < 1 || urgencia > 5) {
                System.out.println(">> Debe ser un numero entero entre 1 y 5.");
            }
        }
        return new Tarea(descripcion.trim(), departamento.trim(), urgencia);
    }

    /**
     * Lee un entero de la consola. Si el usuario escribe algo que no es
     * numero devuelve -1, que ningun menu acepta como opcion valida, para
     * que el programa no truene con una excepcion.
     */
    private static int leerEntero() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Datos de ejemplo para poder probar el sistema desde el arranque. */
    private static void cargarEjemplos() {
        urgentes.push(new Tarea("Reparar servidor caido", "Sistemas", 5));
        urgentes.push(new Tarea("Atender queja de cliente VIP", "Ventas", 4));

        programadas.enqueue(new Tarea("Respaldo semanal", "Sistemas", 2));
        programadas.enqueue(new Tarea("Reporte mensual", "Contabilidad", 3));

        porDepartamento.insert(new Tarea("Actualizar inventario", "Almacen", 2));
        porDepartamento.insert(new Tarea("Capacitacion de personal", "Recursos Humanos", 1));
        porDepartamento.insert(new Tarea("Auditoria interna", "Contabilidad", 4));
    }
}
