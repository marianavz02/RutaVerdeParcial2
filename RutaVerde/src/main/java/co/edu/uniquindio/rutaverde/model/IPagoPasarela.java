package co.edu.uniquindio.rutaverde.model;

public interface IPagoPasarela {
    ResultadoPago procesarPago(String numeroTarjeta, double valorPesos, String referencia);
}
