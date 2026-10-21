package co.edu.uniquindio.rutaverde.model;

import java.util.List;

public class Guia {
    private String identificacion;
    private String nombre;
    private List<String> idiomas;
    private String telefono;
    private double tarifaDia;

    public Guia(String identificacion, String nombre, List<String> idiomas, String telefono, double tarifaDia) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.idiomas = idiomas;
        this.telefono = telefono;
        this.tarifaDia = tarifaDia;
    }

    public boolean hablaIdioma(String idioma) {
        if (idioma == null) return false;
        return idiomas.stream().anyMatch(i -> i.equalsIgnoreCase(idioma));
    }

    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public List<String> getIdiomas() { return idiomas; }
    public String getTelefono() { return telefono; }
    public double getTarifaDia() { return tarifaDia; }

    @Override
    public String toString() {
        return nombre + " - Tarifa: $" + tarifaDia + "/día";
    }
}