package co.edu.uniquindio.rutaverde.model;

import java.util.List;

public abstract class Producto {
    protected String codigo;
    protected String nombre;

    public Producto(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract double getPrecio();
    public abstract int getDuracion();
    public abstract List<Actividad> obtenerActividades();
    public abstract boolean contieneActividadSuspendida();

    @Override
    public String toString() {
        return nombre + " [" + codigo + "] - $" + getPrecio() + " (" + getDuracion() + "h)";
    }
}