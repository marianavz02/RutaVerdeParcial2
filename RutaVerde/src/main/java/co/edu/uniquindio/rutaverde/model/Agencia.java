package co.edu.uniquindio.rutaverde.model;

import java.time.LocalDate;
import java.util.*;

public class Agencia {
    private static Agencia instancia;

    private String nombreComercial = "RutaVerde Quindío";
    private String nit = "900.123.456-7";
    private String rnt = "RNT-45892";
    private String direccion = "Calle 10 # 15-22, Armenia, Quindío";
    private String telefono = "+57 310 456 7890";
    private String correo = "info@rutaverdequindio.com";
    private String paginaWeb = "www.rutaverdequindio.com";

    private List<Cliente> clientes = new ArrayList<>();
    private List<Guia> guias = new ArrayList<>();
    private List<Producto> inventario = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();
    private int contadorConsecutivo = 1000;

    private Agencia() {}

    public static synchronized Agencia getInstancia() {
        if (instancia == null) {
            instancia = new Agencia();
        }
        return instancia;
    }



    public synchronized String generarSiguienteConsecutivo() {
        contadorConsecutivo++;
        return "RV-" + contadorConsecutivo;
    }

    // Funcionalidades de reportes
    public double calcularIngresosEnPeriodo(LocalDate inicio, LocalDate fin) {
        return reservas.stream()
                .filter(r -> r.getEstado() == EstadoReserva.CONFIRMADA)
                .filter(r -> !r.getFechaViaje().isBefore(inicio) && !r.getFechaViaje().isAfter(fin))
                .mapToDouble(Reserva::calcularValorTotal)
                .sum();
    }

    public Producto obtenerProductoMasVendido() {
        Map<Producto, Integer> mapaViajeros = new HashMap<>();
        for (Reserva r : reservas) {
            if (r.getEstado() == EstadoReserva.CONFIRMADA) {
                Producto p = r.getProducto();
                mapaViajeros.put(p, mapaViajeros.getOrDefault(p, 0) + r.getNumeroViajeros());
            }
        }
        return mapaViajeros.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public String getNombreComercial() { return nombreComercial; }
    public String getRnt() { return rnt; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Guia> getGuias() { return guias; }
    public List<Producto> getInventario() { return inventario; }
    public List<Reserva> getReservas() { return reservas; }

    public void agregarCliente(Cliente c) { clientes.add(c); }
    public void agregarGuia(Guia g) { guias.add(g); }
    public void agregarProducto(Producto p) { inventario.add(p); }
    public void agregarReserva(Reserva r) { reservas.add(r); }



}