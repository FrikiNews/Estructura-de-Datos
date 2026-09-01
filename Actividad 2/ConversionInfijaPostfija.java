package listaligada;

/* @author friki */

/**
 * Conversion de una expresion en notacion infija a notacion postfija.
 * Reutiliza la Pila generica de esta actividad como pila de operadores
 * (el mismo Pila<T> que usa el simulador de sistema operativo para los
 * procesos suspendidos) y ListaPostfija para acumular el resultado,
 * sin usar String, StringBuilder ni colecciones de java.util.
 */
public class ConversionInfijaPostfija {

    static boolean esOperando(char c) {
        return c >= '0' && c <= '9';
    }

    static boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Peso que tiene el caracter cuando llega desde la expresion de entrada
    static int pesoEntrada(char c) {
        switch (c) {
            case '^':
                return 4;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            case '(':
                return 5;
            default:
                return -1;
        }
    }

    // Peso que tiene el caracter cuando ya esta dentro de la pila
    static int pesoPila(char c) {
        switch (c) {
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            case '(':
                return 0;
            default:
                return -1;
        }
    }

    static ListaPostfija convertir(char[] expresion) {
        Pila<Character> pila = new Pila<>();
        ListaPostfija postfija = new ListaPostfija();

        for (int i = 0; i < expresion.length; i++) {
            char c = expresion[i];

            if (c == ' ') {
                continue;
            }

            if (esOperando(c)) {
                postfija.agregar(c);

            } else if (c == '(') {
                pila.push(c);

            } else if (c == ')') {
                while (pila.peek() != '(') {
                    postfija.agregar(pila.pop());
                }
                pila.pop(); // descarta el '('

            } else if (esOperador(c)) {
                if (pila.isEmpty()) {
                    pila.push(c);
                } else {
                    while (!pila.isEmpty() && pesoEntrada(c) <= pesoPila(pila.peek())) {
                        postfija.agregar(pila.pop());
                    }
                    pila.push(c);
                }
            }
        }

        while (!pila.isEmpty()) {
            postfija.agregar(pila.pop());
        }

        return postfija;
    }
}
