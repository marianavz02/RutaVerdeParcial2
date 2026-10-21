package co.edu.uniquindio.rutaverde.model;

import java.util.ArrayList;
import java.util.List;

public class PaqueteTuristico extends Producto {
    private double porcentajeDescuento;
    private List<Producto> componentes;

    public PaqueteTuristico(String codigo, String nombre, double porcentajeDescuento) {
        super(codigo, nombre);
        if (porcentajeDescuento < 0 || porcentajeDescuento > 20) {
            throw new IllegalArgumentException("El descuento debe estar entre 0% y 20%");
        }
        this.porcentajeDescuento = porcentajeDescuento;
        this.componentes = new ArrayList<>();
    }

    public void agregarComponente(Producto producto) {
        if (producto == this) {
            throw new IllegalArgumentException("Un paquete no puede contenerse a sí mismo.");
        }

        // Evitar duplicar la misma actividad
        List<Actividad> actividadesExistentes = obtenerActividades();
        List<Actividad> actividadesNuevas = producto.obtenerActividades();
        for (Actividad act : actividadesNuevas) {
            if (actividadesExistentes.contains(act)) {
                throw new IllegalArgumentException("El paquete ya contiene la actividad: " + act.getNombre());
            }
        }

        componentes.add(producto);
    }

    @Override
    public double getPrecio() {
        double suma = componentes.stream().mapToDouble(Producto::getPrecio).sum();
        return suma * (1.0 - (porcentajeDescuento / 100.0));
    }

    @Override
    public int getDuracion() {
        return componentes.stream().mapToInt(Producto::getDuracion).sum();
    }

    @Override
    public List<Actividad> obtenerActividades() {
        List<Actividad> lista = new ArrayList<>();
        for (Producto p : componentes) {
            lista.addAll(p.obtenerActividades());
        }
        return lista;
    }

    @Override
    public boolean contieneActividadSuspendida() {
        return componentes.stream().anyMatch(Producto::contieneActividadSuspendida);
    }

    public double getPorcentajeDescuento() { return porcentajeDescuento; }
    public List<Producto> getComponentes() { return componentes; }
}