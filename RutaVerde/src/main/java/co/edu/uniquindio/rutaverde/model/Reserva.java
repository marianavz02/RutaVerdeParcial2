package co.edu.uniquindio.rutaverde.model;

import java.time.LocalDate;

public class Reserva {
    private String consecutivo;
    private Cliente cliente;
    private Producto producto;
    private LocalDate fechaViaje;
    private int numeroViajeros;
    private Guia guia;
    private boolean incluyeTransporte;
    private boolean incluyeSeguro;
    private String observaciones;
    private EstadoReserva estado;

    public Reserva(Cliente cliente, Producto producto, LocalDate fechaViaje, int numeroViajeros,
                   Guia guia, boolean incluyeTransporte, boolean incluyeSeguro, String observaciones) {
        this.cliente = cliente;
        this.producto = producto;
        this.fechaViaje = fechaViaje;
        this.numeroViajeros = numeroViajeros;
        this.guia = guia;
        this.incluyeTransporte = incluyeTransporte;
        this.incluyeSeguro = incluyeSeguro;
        this.observaciones = observaciones;
        this.estado = EstadoReserva.PENDIENTE;
    }

    public double calcularValorTotal() {
        double precioBase = producto.getPrecio() * numeroViajeros;
        double costoTransporte = incluyeTransporte ? 35000.0 * numeroViajeros : 0.0;
        double costoSeguro = incluyeSeguro ? 15000.0 * numeroViajeros : 0.0;

        double costoGuia = 0.0;
        if (guia != null) {
            int duracionHoras = producto.getDuracion();
            int diasGuia = (int) Math.ceil((double) duracionHoras / 8.0);
            costoGuia = guia.getTarifaDia() * diasGuia;
        }

        return precioBase + costoTransporte + costoSeguro + costoGuia;
    }

    public String getConsecutivo() { return consecutivo; }
    public void setConsecutivo(String consecutivo) { this.consecutivo = consecutivo; }
    public Cliente getCliente() { return cliente; }
    public Producto getProducto() { return producto; }
    public LocalDate getFechaViaje() { return fechaViaje; }
    public int getNumeroViajeros() { return numeroViajeros; }
    public Guia getGuia() { return guia; }
    public boolean isIncluyeTransporte() { return incluyeTransporte; }
    public boolean isIncluyeSeguro() { return incluyeSeguro; }
    public String getObservaciones() { return observaciones; }
    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }
}