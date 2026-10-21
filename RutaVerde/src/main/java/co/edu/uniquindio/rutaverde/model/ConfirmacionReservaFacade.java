package co.edu.uniquindio.rutaverde.model;

import java.time.LocalDate;
import java.util.List;

public class ConfirmacionReservaFacade {
    private final IPagoPasarela pasarelaPago;

    public ConfirmacionReservaFacade(IPagoPasarela pasarelaPago) {
        this.pasarelaPago = pasarelaPago;
    }

    public boolean confirmarReserva(Reserva reserva, String numeroTarjeta, CanalVenta canal) {
        Cliente cliente = reserva.getCliente();
        Producto producto = reserva.getProducto();
        LocalDate fecha = reserva.getFechaViaje();
        int viajeros = reserva.getNumeroViajeros();

        // Validaciones previas de reglas de negocio
        if (cliente == null || producto == null) {
            notificarCorreo(cliente, "Fallo en la reserva: Cliente o producto faltante.");
            return false;
        }
        if (fecha.isBefore(LocalDate.now())) {
            notificarCorreo(cliente, "Fallo en la reserva: La fecha del viaje no puede estar en el pasado.");
            return false;
        }
        if (viajeros < 1 || viajeros > 15) {
            notificarCorreo(cliente, "Fallo en la reserva: El grupo debe tener entre 1 y 15 viajeros.");
            return false;
        }
        if (reserva.getGuia() != null && !reserva.getGuia().hablaIdioma(cliente.getIdiomaPreferido())) {
            notificarCorreo(cliente, "Fallo en la reserva: El guía asignado no habla el idioma preferido del cliente.");
            return false;
        }
        if (producto.contieneActividadSuspendida()) {
            notificarCorreo(cliente, "Fallo en la reserva: El producto seleccionado incluye actividades suspendidas.");
            return false;
        }

        // 1. Verificar cupo en la fecha del viaje
        List<Actividad> actividades = producto.obtenerActividades();
        for (Actividad act : actividades) {
            if (!act.hayCupoDisponible(fecha, viajeros)) {
                notificarCorreo(cliente, "Fallo en la reserva: Cupo agotado para la actividad '" + act.getNombre() + "' el " + fecha);
                return false;
            }
        }

        // 2. Validar tarjeta mediante algoritmo de Luhn
        if (!UtilidadLuhn.validarTarjeta(numeroTarjeta)) {
            notificarCorreo(cliente, "Fallo en la reserva: El número de tarjeta es inválido según el algoritmo de Luhn.");
            return false;
        }

        // 3. Cobrar en la pasarela de pagos
        double valorTotal = reserva.calcularValorTotal();
        String referencia = "REF-" + System.currentTimeMillis();
        ResultadoPago resultadoPago = pasarelaPago.procesarPago(numeroTarjeta, valorTotal, referencia);

        if (!resultadoPago.isAprobado()) {
            notificarCorreo(cliente, "Fallo en la reserva: " + resultadoPago.getMensaje());
            return false;
        }

        // 4. Descontar cupos
        for (Actividad act : actividades) {
            act.reservarCupo(fecha, viajeros);
        }

        // 5. Registrar la reserva con número consecutivo
        Agencia agencia = Agencia.getInstancia();
        String consecutivo = agencia.generarSiguienteConsecutivo();
        reserva.setConsecutivo(consecutivo);
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        agencia.agregarReserva(reserva);

        // 6. Emitir voucher según canal
        IVoucherGenerator voucherGen = VoucherFactory.obtenerGenerador(canal);
        String voucherTexto = voucherGen.generarVoucher(reserva, agencia.getRnt());

        // 7. Notificar al cliente
        notificarCorreo(cliente, "¡RESERVA CONFIRMADA EXITOSAMENTE!\n\n" + voucherTexto);

        return true;
    }

    private void notificarCorreo(Cliente cliente, String mensaje) {
        String correoDestino = cliente != null ? cliente.getCorreo() : "desconocido@correo.com";
        System.out.println("\n--- [SIMULACIÓN ENVÍO EMAIL] ---");
        System.out.println("Para: " + correoDestino);
        System.out.println("Mensaje:\n" + mensaje);
        System.out.println("----------------------------------\n");
    }
}