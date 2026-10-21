package co.edu.uniquindio.rutaverde.model;

import java.time.LocalDate;
import java.util.*;


public class Actividad extends Producto {
    private String municipio;
    private int duracionHoras;
    private double precioPersona;
    private static int cupoMaximoDia;
    private NivelDificultad dificultad;
    private EstadoActividad estado;
    private static Map<LocalDate, Integer> cuposOcupados;

    public Actividad(String codigo, String nombre, String municipio, int duracionHoras,
                     double precioPersona, int cupoMaximoDia, NivelDificultad dificultad, EstadoActividad estado) {
        super(codigo, nombre);
        this.municipio = municipio;
        this.duracionHoras = duracionHoras;
        this.precioPersona = precioPersona;
        this.cupoMaximoDia = cupoMaximoDia;
        this.dificultad = dificultad;
        this.estado = estado;
        this.cuposOcupados = new HashMap<>();
    }


    @Override
    public double getPrecio() {
        return precioPersona;
    }

    @Override
    public int getDuracion() {
        return duracionHoras;
    }

    @Override
    public List<Actividad> obtenerActividades() {
        return Collections.singletonList(this);
    }

    @Override
    public boolean contieneActividadSuspendida() {
        return estado == EstadoActividad.SUSPENDIDA;
    }

    public static boolean hayCupoDisponible(LocalDate fecha, int viajeros) {
        int ocupados = cuposOcupados.getOrDefault(fecha, 0);
        if (ocupados + viajeros < cupoMaximoDia) {
            return true;
        }
        return false;
    }

    public void reservarCupo(LocalDate fecha, int viajeros) {
        int ocupados = cuposOcupados.getOrDefault(fecha, 0);
        cuposOcupados.put(fecha, ocupados + viajeros);
    }

    public String getMunicipio() { return municipio; }
    public EstadoActividad getEstado() { return estado; }
    public void setEstado(EstadoActividad estado) { this.estado = estado; }
}