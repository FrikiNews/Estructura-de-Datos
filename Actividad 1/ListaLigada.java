package listaligada;

/* @author friki */

import java.util.Scanner;

public class ListaLigada {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1. Operar con una lista enlazada");
            System.out.println("2. Gestion de contactos");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: menuLista(); break;
                case 2: menuContactos(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private static void menuLista() {
        System.out.println("\nSeleccione el tipo de lista:");
        System.out.println("1. Simplemente enlazada");
        System.out.println("2. Doblemente enlazada");
        System.out.println("3. Circular");
        System.out.print("Opcion: ");
        int t = leerEntero();

        switch (t) {
            case 1: operarSimple(); break;
            case 2: operarDoble(); break;
            case 3: operarCircular(); break;
            default: System.out.println("Tipo invalido.");
        }
    }

    private static void operarSimple() {
        ListaSimple<Integer> lista = new ListaSimple<>();
        int op;
        do {
            System.out.println("\n--- Lista SIMPLE (tamano: " + lista.getSize() + ") ---");
            System.out.println("1. Insertar  2. Eliminar  3. Buscar  4. Mostrar  0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();
            switch (op) {
                case 1:
                    System.out.print("Numero a insertar: ");
                    lista.insertar(leerEntero());
                    System.out.println("Insertado.");
                    break;
                case 2:
                    System.out.print("Numero a eliminar: ");
                    System.out.println(lista.eliminar(leerEntero()) ? "Eliminado." : "No encontrado.");
                    break;
                case 3:
                    System.out.print("Numero a buscar: ");
                    int pos = lista.buscar(leerEntero());
                    System.out.println(pos >= 0 ? "Encontrado en la posicion " + pos : "No encontrado.");
                    break;
                case 4: lista.mostrar(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (op != 0);
    }

    private static void operarDoble() {
        ListaDoble<Integer> lista = new ListaDoble<>();
        int op;
        do {
            System.out.println("\n--- Lista DOBLE (tamano: " + lista.getSize() + ") ---");
            System.out.println("1. Insertar  2. Eliminar  3. Buscar  4. Mostrar  5. Mostrar inverso  0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();
            switch (op) {
                case 1:
                    System.out.print("Numero a insertar: ");
                    lista.insertar(leerEntero());
                    System.out.println("Insertado.");
                    break;
                case 2:
                    System.out.print("Numero a eliminar: ");
                    System.out.println(lista.eliminar(leerEntero()) ? "Eliminado." : "No encontrado.");
                    break;
                case 3:
                    System.out.print("Numero a buscar: ");
                    int pos = lista.buscar(leerEntero());
                    System.out.println(pos >= 0 ? "Encontrado en la posicion " + pos : "No encontrado.");
                    break;
                case 4: lista.mostrar(); break;
                case 5: lista.mostrarInverso(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (op != 0);
    }

    private static void operarCircular() {
        ListaCircular<Integer> lista = new ListaCircular<>();
        int op;
        do {
            System.out.println("\n--- Lista CIRCULAR (tamano: " + lista.getSize() + ") ---");
            System.out.println("1. Insertar  2. Eliminar  3. Buscar  4. Mostrar  0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();
            switch (op) {
                case 1:
                    System.out.print("Numero a insertar: ");
                    lista.insertar(leerEntero());
                    System.out.println("Insertado.");
                    break;
                case 2:
                    System.out.print("Numero a eliminar: ");
                    System.out.println(lista.eliminar(leerEntero()) ? "Eliminado." : "No encontrado.");
                    break;
                case 3:
                    System.out.print("Numero a buscar: ");
                    int pos = lista.buscar(leerEntero());
                    System.out.println(pos >= 0 ? "Encontrado en la posicion " + pos : "No encontrado.");
                    break;
                case 4: lista.mostrar(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (op != 0);
    }

    private static void menuContactos() {
        ListaDoble<Contacto> agenda = new ListaDoble<>();
        int op;
        do {
            System.out.println("\n--- GESTION DE CONTACTOS (" + agenda.getSize() + ") ---");
            System.out.println("1. Agregar  2. Eliminar  3. Buscar  4. Mostrar  0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();
            switch (op) {
                case 1:
                    System.out.print("Nombre: ");
                    String nom = sc.nextLine();
                    System.out.print("Direccion: ");
                    String dir = sc.nextLine();
                    System.out.print("Telefono: ");
                    String tel = sc.nextLine();
                    agenda.insertar(new Contacto(nom, dir, tel));
                    System.out.println("Contacto agregado.");
                    break;
                case 2:
                    System.out.print("Nombre a eliminar: ");
                    String elim = sc.nextLine();
                    System.out.println(agenda.eliminar(new Contacto(elim, "", "")) ? "Eliminado." : "No encontrado.");
                    break;
                case 3:
                    System.out.print("Nombre a buscar: ");
                    String bus = sc.nextLine();
                    int p = agenda.buscar(new Contacto(bus, "", ""));
                    System.out.println(p >= 0 ? "Encontrado en la posicion " + p : "No encontrado.");
                    break;
                case 4: agenda.mostrar(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (op != 0);
    }

    private static int leerEntero() {
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            sc.next();
        }
        int n = sc.nextInt();
        sc.nextLine();
        return n;
    }
}