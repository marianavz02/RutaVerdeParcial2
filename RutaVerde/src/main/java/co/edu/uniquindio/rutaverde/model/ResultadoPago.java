package co.edu.uniquindio.rutaverde.model;

public class ResultadoPago {
    private final boolean aprobado;
    private final String mensaje;
    private final String codigoAutorizacion;

    public ResultadoPago(boolean aprobado, String mensaje, String codigoAutorizacion) {
        this.aprobado = aprobado;
        this.mensaje = mensaje;
        this.codigoAutorizacion = codigoAutorizacion;
    }

    public boolean isAprobado() { return aprobado; }
    public String getMensaje() { return mensaje; }
    public String getCodigoAutorizacion() { return codigoAutorizacion; }
}