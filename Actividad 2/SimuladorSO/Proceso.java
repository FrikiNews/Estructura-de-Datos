package listaligada;

/* @author friki */

public class Proceso {
    private int pid;
    private String nombre;
    private int prioridad;

    public Proceso(int pid, String nombre, int prioridad) {
        this.pid = pid;
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    public int getPid() { return pid; }
    public String getNombre() { return nombre; }
    public int getPrioridad() { return prioridad; }

    @Override
    public String toString() {
        return "PID " + pid + " - " + nombre + " (prioridad " + prioridad + ")";
    }
}
