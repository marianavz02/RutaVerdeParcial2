package co.edu.uniquindio.rutaverde.model;

public class VoucherFactory {
    public static IVoucherGenerator obtenerGenerador(CanalVenta canal) {
        switch (canal) {
            case KIOSCO:
                return new VoucherKiosco();
            case ASESOR:
            case MOVIL:
            default:
                return new VoucherAsesor();
        }
    }
}
