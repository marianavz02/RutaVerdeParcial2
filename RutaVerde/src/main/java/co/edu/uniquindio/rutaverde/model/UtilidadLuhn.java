package co.edu.uniquindio.rutaverde.model;

public class UtilidadLuhn {
    public static boolean validarTarjeta(String numeroTarjeta) {
        if (numeroTarjeta == null) return false;
        String limpia = numeroTarjeta.replaceAll("\\s+", "");

        if (!limpia.matches("\\d{13,19}")) {
            return false;
        }

        int suma = 0;
        boolean duplicar = false;

        for (int i = limpia.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(limpia.charAt(i));
            if (duplicar) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            suma += digito;
            duplicar = !duplicar;
        }

        return (suma % 10 == 0);
    }
}