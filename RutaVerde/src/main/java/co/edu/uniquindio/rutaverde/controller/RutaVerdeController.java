package co.edu.uniquindio.rutaverde.controller;

import co.edu.uniquindio.rutaverde.model.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class RutaVerdeController {

    private final Agencia agencia;

    public RutaVerdeController() {
        // Usamos el patrón Singleton para la Agencia, como vimos en la solución anterior
        this.agencia = Agencia.getInstancia();
        cargarDatosPrueba();
    }

    /**
     * 1. CREAR CLIENTE
     */
    public void crearCliente(String documento, String nombreCompleto, String telefono, String correo, String paisOrigen, String idiomaPreferido) throws Exception {
        // Validaciones
        if (documento == null || documento.trim().isEmpty()) {
            throw new Exception("La identificación es obligatoria.");
        }
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new Exception("El nombre es obligatorio.");
        }
        if (correo == null || !correo.contains("@") || !correo.contains(".")) {
            throw new Exception("Debe ingresar un correo electrónico válido.");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new Exception("El teléfono es obligatorio.");
        }

        Cliente nuevoCliente = new Cliente(documento, nombreCompleto, telefono, correo, paisOrigen, idiomaPreferido);
        agencia.agregarCliente(nuevoCliente);
    }

    /**
     * 2. CREAR GUÍA
     */
    public void crearGuia(String identificacion, String nombre, List<String> idiomas, String telefono, double tarifaDia) throws Exception {
        // Validaciones
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new Exception("La identificación del guía es obligatoria.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del guía es obligatorio.");
        }
        if (idiomas == null || idiomas.isEmpty()) {
            throw new Exception("Debe especificar al menos un idioma.");
        }


        Guia nuevoGuia = new Guia(identificacion, nombre, idiomas, telefono, tarifaDia);
        agencia.agregarGuia(nuevoGuia);
    }

    /**
     * 3. CREAR ACTIVIDAD
     */
    public void crearActividad(String codigo, String nombre, String municipio, int duracionHoras,
                               double precioPersona, int cupoMaximoDia, NivelDificultad dificultad, EstadoActividad estado) throws Exception {
        // Validaciones
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new Exception("El codigo de la actividad es obligatorio.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("La nombre no puede estar vacío.");
        }
        if (precioPersona <= 0) {
            throw new Exception("El precio de la actividad debe ser mayor a cero.");
        }
        if (dificultad == null) {
            throw new Exception("Debe seleccionar un nivel de dificultad.");
        }

        Actividad nuevaActividad = new Actividad(codigo, nombre, municipio, duracionHoras, precioPersona, cupoMaximoDia, dificultad, estado);
        agencia.agregarProducto(nuevaActividad);
    }

    /**
     * 4. CREAR PAQUETE TURÍSTICO
     */
    public void crearPaqueteTuristico(String codigo, String nombre, double porcentajeDescuento) throws Exception {
        // Validaciones
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new Exception("El codigo de la actividad es obligatorio.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del paquete es obligatorio.");
        }


        PaqueteTuristico nuevoPaquete = new PaqueteTuristico(codigo, nombre, porcentajeDescuento);
        agencia.agregarProducto(nuevoPaquete);
    }

    /**
     * 5. CREAR RESERVA
     */
    public void crearReserva(Cliente cliente, Producto producto, LocalDate fechaViaje, int numeroViajeros,
                             Guia guia, boolean incluyeTransporte, boolean incluyeSeguro, String observaciones)  throws Exception {

        // 1. Validaciones básicas
        if (cliente == null) {
            throw new Exception("Debe seleccionar un cliente para la reserva.");
        }
        if (producto == null) {
            throw new Exception("Debe seleccionar un producto para la reserva.");
        }
        if (numeroViajeros <= 0) {
            throw new Exception("La reserva debe ser para al menos 1 persona.");
        }
        if (fechaViaje == null || fechaViaje.isBefore(LocalDate.now())) {
            throw new Exception("La fecha de viaje no puede estar en el pasado.");
        }

        // 2. Lógica de negocio con CASIFICACIÓN (Type Casting)
        // Verificamos si el producto seleccionado es específicamente una Actividad
        if (producto instanceof Actividad) {
            // Transformamos temporalmente el Producto a Actividad
            Actividad actividad = (Actividad) producto;

            // Ahora sí podemos usar los métodos específicos sin que marque error
            if (!actividad.hayCupoDisponible(fechaViaje, numeroViajeros)) {
                throw new Exception("No hay suficiente cupo en esta actividad para la fecha seleccionada.");
            }

            // Si hay cupo, lo reservamos
            actividad.reservarCupo(fechaViaje, numeroViajeros);
        }


        // 3. Crear la reserva y agregarla a la agencia
        Reserva nuevaReserva = new Reserva(cliente, producto, fechaViaje, numeroViajeros, guia, incluyeTransporte, incluyeSeguro, observaciones);
        agencia.agregarReserva(nuevaReserva);
    }


    /**
     * MÉTODO PARA CARGAR DATOS QUEMADOS DE PRUEBA
     */
    public void cargarDatosPrueba() {
        try {
            // 1. Crear Clientes
            crearCliente("1094000111", "Ana Gomez", "3001234567", "ana.gomez@gmail.com", "Colombia", "Español");
            crearCliente("1094000222", "John Smith", "3119876543", "john.smith@hotmail.com", "Estados Unidos", "Inglés");
            crearCliente("1094000333", "Marie Dubois", "3205559988", "marie.d@yahoo.fr", "Francia", "Francés");

            // 2. Crear Guías
            crearGuia("99001", "Carlos Perez", Arrays.asList("Español", "Inglés"), "3154445566", 150000.0);
            crearGuia("99002", "Laura Londoño", Arrays.asList("Español", "Francés"), "3109998877", 180000.0);

            // 3. Crear Actividades
            // IMPORTANTE: Verifica que "MEDIO", "BAJO" y "ACTIVA" coincidan con los nombres exactos en tus Enums
            crearActividad("ACT-01", "Caminata Valle de Cocora", "Salento", 4, 50000.0, 20, NivelDificultad.valueOf("MEDIA"), EstadoActividad.valueOf("DISPONIBLE"));
            crearActividad("ACT-02", "Tour del Café Tradicional", "Buenavista", 3, 80000.0, 15, NivelDificultad.valueOf("BAJA"), EstadoActividad.valueOf("DISPONIBLE"));
            crearActividad("ACT-03", "Balsaje Río La Vieja", "Quimbaya", 6, 120000.0, 10, NivelDificultad.valueOf("MEDIA"), EstadoActividad.valueOf("DISPONIBLE"));
            crearActividad("ACT-04", "Escalada Nevado del Tolima", "Salento", 12, 250000.0, 5, NivelDificultad.valueOf("ALTA"), EstadoActividad.valueOf("DISPONIBLE"));

            // 4. Crear Paquetes Turísticos
            crearPaqueteTuristico("PAQ-01", "Descubre el Quindío Mágico", 10.0);
            crearPaqueteTuristico("PAQ-02", "Aventura Cafetera Extrema", 15.0);

            System.out.println("✅ Datos de prueba cargados correctamente en la Agencia.");

        } catch (Exception e) {
            System.err.println("❌ Error al cargar datos de prueba: " + e.getMessage());
        }
    }




}