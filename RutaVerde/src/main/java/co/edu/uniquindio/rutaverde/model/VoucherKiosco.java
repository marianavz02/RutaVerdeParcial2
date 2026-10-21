package co.edu.uniquindio.rutaverde.model;

public class VoucherKiosco implements IVoucherGenerator {
    @Override
    public String generarVoucher(Reserva reserva, String rnt) {
        StringBuilder sb = new StringBuilder();
        sb.append("RutaVerde Quindío (RNT: ").append(rnt).append(")\n");
        sb.append("TIQUETE RÁPIDO DE ENTRADA | REF: ").append(reserva.getConsecutivo())
                .append(" | ").append(reserva.getCliente().getNombreCompleto())
                .append(" | ").append(reserva.getProducto().getNombre())
                .append(" | VIAJEROS: ").append(reserva.getNumeroViajeros())
                .append(" | TOTAL: $").append(String.format("%,.0f", reserva.calcularValorTotal())).append("\n");
        sb.append("Política: Cancelación sin costo hasta 48 horas antes del viaje.");
        return sb.toString();
    }
}