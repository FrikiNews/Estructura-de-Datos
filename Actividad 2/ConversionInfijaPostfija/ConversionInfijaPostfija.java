package conversioninfijapostfija;

/* @author friki */

/**
 * Conversion de una expresion en notacion infija a notacion postfija,
 * utilizando una pila propia y una lista ligada propia (sin java.util.*).
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
        Pila pila = new Pila();
        ListaPostfija postfija = new ListaPostfija();

        for (int i = 0; i < expresion.length; i++) {
            char c = expresion[i];

            if (c == ' ') {
                continue;
            }

            if (esOperando(c)) {
                postfija.agregar(c);

            } else if (c == '(') {
                pila.apilar(c);

            } else if (c == ')') {
                while (pila.verTope() != '(') {
                    postfija.agregar(pila.desapilar());
                }
                pila.desapilar(); // descarta el '('

            } else if (esOperador(c)) {
                if (pila.estaVacia()) {
                    pila.apilar(c);
                } else {
                    while (!pila.estaVacia() && pesoEntrada(c) <= pesoPila(pila.verTope())) {
                        postfija.agregar(pila.desapilar());
                    }
                    pila.apilar(c);
                }
            }
        }

        while (!pila.estaVacia()) {
            postfija.agregar(pila.desapilar());
        }

        return postfija;
    }

    public static void main(String[] args) {
        char[] expresion1 = {'2', '+', '3', '*', '4'};
        char[] expresion2 = {'(', '2', '+', '3', ')', '*', '4'};
        char[] expresion3 = {'2', '+', '3', '*', '(', '4', '-', '1', ')'};

        System.out.print("Prueba 1 - Postfija: ");
        convertir(expresion1).imprimir();

        System.out.print("Prueba 2 - Postfija: ");
        convertir(expresion2).imprimir();

        System.out.print("Prueba 3 - Postfija: ");
        convertir(expresion3).imprimir();
    }
}
