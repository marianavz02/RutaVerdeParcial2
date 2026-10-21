package co.edu.uniquindio.rutaverde.model;

public class PagoAndinoAdapter implements IPagoPasarela {
    private final PagoAndinoAPI apiExterna;

    public PagoAndinoAdapter() {
        this.apiExterna = new PagoAndinoAPI();
    }

    @Override
    public ResultadoPago procesarPago(String numeroTarjeta, double valorPesos, String referencia) {
        long valorCentavos = (long) Math.round(valorPesos * 100.0);
        int codigoRespuesta = apiExterna.procesarCobro(numeroTarjeta, valorCentavos, referencia);

        switch (codigoRespuesta) {
            case 0:
                return new ResultadoPago(true, "Aprobado", apiExterna.ultimaAutorizacion());
            case 51:
                return new ResultadoPago(false, "Rechazado: Fondos insuficientes.", null);
            case 14:
                return new ResultadoPago(false, "Rechazado: Tarjeta inválida.", null);
            case 91:
                return new ResultadoPago(false, "Rechazado: Pasarela no disponible.", null);
            default:
                return new ResultadoPago(false, "Error desconocido en pasarela (" + codigoRespuesta + ")", null);
        }
    }
}