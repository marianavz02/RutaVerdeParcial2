package co.edu.uniquindio.rutaverde.model;

public class VoucherAsesor implements IVoucherGenerator {
    @Override
    public String generarVoucher(Reserva reserva, String rnt) {
        StringBuilder sb = new StringBuilder();
        sb.append("===================================================\n");
        sb.append("       RutaVerde Quindío - RNT: ").append(rnt).append("\n");
        sb.append("             VOUCHER DETALLADO DE RESERVA         \n");
        sb.append("===================================================\n");
        sb.append("Consecutivo: ").append(reserva.getConsecutivo()).append("\n");
        sb.append("Cliente: ").append(reserva.getCliente().getNombreCompleto()).append("\n");
        sb.append("Documento: ").append(reserva.getCliente().getDocumento()).append("\n");
        sb.append("Fecha Viaje: ").append(reserva.getFechaViaje()).append("\n");
        sb.append("Viajeros: ").append(reserva.getNumeroViajeros()).append("\n");
        sb.append("Producto: ").append(reserva.getProducto().getNombre()).append("\n");
        sb.append("\n-- Detalle de Actividades --\n");
        for (Actividad act : reserva.getProducto().obtenerActividades()) {
            sb.append(" * ").append(act.getNombre()).append(" (").append(act.getMunicipio()).append(")\n");
        }
        sb.append("\nGuía Asignado: ").append(reserva.getGuia() != null ? reserva.getGuia().getNombre() : "Sin guía").append("\n");
        sb.append("Servicios Opcionales: ")
                .append(reserva.isIncluyeTransporte() ? "[Transporte] " : "")
                .append(reserva.isIncluyeSeguro() ? "[Seguro] " : "").append("\n");
        sb.append("Observaciones: ").append(reserva.getObservaciones() != null ? reserva.getObservaciones() : "Ninguna").append("\n");
        sb.append("---------------------------------------------------\n");
        sb.append("VALOR TOTAL PAGADO: $").append(String.format("%,.2f", reserva.calcularValorTotal())).append("\n");
        sb.append("---------------------------------------------------\n");
        sb.append("Política de Cancelación: Sin costo hasta 48 horas antes del viaje.\n");
        sb.append("===================================================");
        return sb.toString();
    }
}