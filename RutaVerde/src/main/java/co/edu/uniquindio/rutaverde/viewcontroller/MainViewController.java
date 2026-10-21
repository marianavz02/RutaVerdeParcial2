package co.edu.uniquindio.rutaverde.viewcontroller;

import co.edu.uniquindio.rutaverde.controller.RutaVerdeController;
import co.edu.uniquindio.rutaverde.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class MainViewController {

    private RutaVerdeController controller;

    // ================== COMPONENTES FXML ==================
    // Cliente
    @FXML private TextField txtDocCliente;
    @FXML private TextField txtNombreCliente;
    @FXML private TextField txtTelCliente;
    @FXML private TextField txtCorreoCliente;
    @FXML private TextField txtPaisCliente;
    @FXML private TextField txtIdiomaCliente;

    // Guía
    @FXML private TextField txtIdGuia;
    @FXML private TextField txtNombreGuia;
    @FXML private TextField txtIdiomasGuia;
    @FXML private TextField txtTelGuia;
    @FXML private TextField txtTarifaGuia;

    // Actividad
    @FXML private TextField txtCodActividad;
    @FXML private TextField txtNombreActividad;
    @FXML private TextField txtMunActividad;
    @FXML private TextField txtDuracionActividad;
    @FXML private TextField txtPrecioActividad;
    @FXML private TextField txtCupoActividad;
    @FXML private ComboBox<NivelDificultad> cbDificultadActividad;
    @FXML private ComboBox<EstadoActividad> cbEstadoActividad;

    // Paquete Turístico
    @FXML private TextField txtCodPaquete;
    @FXML private TextField txtNombrePaquete;
    @FXML private TextField txtDescuentoPaquete;

    // Reserva
    @FXML private ComboBox<Cliente> cbClienteReserva;
    @FXML private ComboBox<Producto> cbProductoReserva;
    @FXML private ComboBox<Guia> cbGuiaReserva;
    @FXML private DatePicker dpFechaReserva;
    @FXML private TextField txtViajerosReserva;
    @FXML private CheckBox chkTransporteReserva;
    @FXML private CheckBox chkSeguroReserva;
    @FXML private TextArea txtObservacionesReserva;

    @FXML private Label lblMensajeGlobal;

    // ================== INICIALIZACIÓN ==================
    @FXML
    public void initialize() {
        controller = new RutaVerdeController();

        // 1. Llenar los ComboBox de los Enums
        if (cbDificultadActividad != null) {
            cbDificultadActividad.getItems().setAll(NivelDificultad.values());
        }
        if (cbEstadoActividad != null) {
            cbEstadoActividad.getItems().setAll(EstadoActividad.values());
        }
        actualizarListasDesplegables();
    }

    // ================== ACCIONES DE BOTONES ==================

    @FXML
    public void registrarClienteAction(ActionEvent event) {
        try {
            controller.crearCliente(
                    txtDocCliente.getText(),
                    txtNombreCliente.getText(),
                    txtTelCliente.getText(),
                    txtCorreoCliente.getText(),
                    txtPaisCliente.getText(),
                    txtIdiomaCliente.getText()
            );
            mostrarMensaje("Éxito", "Cliente registrado exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCamposCliente();
            actualizarListasDesplegables(); // Refresca el ComboBox de clientes en la pestaña de reservas
        } catch (Exception e) {
            mostrarMensaje("Error en Cliente", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void registrarGuiaAction(ActionEvent event) {
        try {
            List<String> idiomas = Arrays.asList(txtIdiomasGuia.getText().split("\\s*,\\s*"));
            double tarifa = Double.parseDouble(txtTarifaGuia.getText());

            controller.crearGuia(
                    txtIdGuia.getText(),
                    txtNombreGuia.getText(),
                    idiomas,
                    txtTelGuia.getText(),
                    tarifa
            );
            mostrarMensaje("Éxito", "Guía registrado exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCamposGuia();
            actualizarListasDesplegables();
        } catch (NumberFormatException e) {
            mostrarMensaje("Error de formato", "La tarifa debe ser un valor numérico.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarMensaje("Error en Guía", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void registrarActividadAction(ActionEvent event) {
        try {
            int duracion = Integer.parseInt(txtDuracionActividad.getText());
            double precio = Double.parseDouble(txtPrecioActividad.getText());
            int cupo = Integer.parseInt(txtCupoActividad.getText());

            controller.crearActividad(
                    txtCodActividad.getText(),
                    txtNombreActividad.getText(),
                    txtMunActividad.getText(),
                    duracion,
                    precio,
                    cupo,
                    cbDificultadActividad.getValue(),
                    cbEstadoActividad.getValue()
            );
            mostrarMensaje("Éxito", "Actividad registrada exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCamposActividad();
            actualizarListasDesplegables();
        } catch (NumberFormatException e) {
            mostrarMensaje("Error de formato", "Duración, precio y cupo deben ser valores numéricos enteros o decimales según corresponda.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarMensaje("Error en Actividad", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void registrarPaqueteAction(ActionEvent event) {
        try {
            double descuento = Double.parseDouble(txtDescuentoPaquete.getText());

            controller.crearPaqueteTuristico(
                    txtCodPaquete.getText(),
                    txtNombrePaquete.getText(),
                    descuento
            );
            mostrarMensaje("Éxito", "Paquete turístico registrado exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCamposPaquete();
            actualizarListasDesplegables();
        } catch (NumberFormatException e) {
            mostrarMensaje("Error de formato", "El descuento debe ser numérico.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarMensaje("Error en Paquete", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void registrarReservaAction(ActionEvent event) {
        try {
            int viajeros = Integer.parseInt(txtViajerosReserva.getText());
            LocalDate fecha = dpFechaReserva.getValue();

            controller.crearReserva(
                    cbClienteReserva.getValue(),
                    cbProductoReserva.getValue(),
                    fecha,
                    viajeros,
                    cbGuiaReserva.getValue(),
                    chkTransporteReserva.isSelected(),
                    chkSeguroReserva.isSelected(),
                    txtObservacionesReserva.getText()
            );
            mostrarMensaje("Éxito", "Reserva creada exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCamposReserva();
        } catch (NumberFormatException e) {
            mostrarMensaje("Error de formato", "El número de viajeros debe ser numérico.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarMensaje("Error en Reserva", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    public void onGenerarReporte(ActionEvent event) {
        mostrarMensaje("Reporte", "Funcionalidad de reportes en construcción.", Alert.AlertType.INFORMATION);
    }

    // ================== MÉTODOS DE UTILIDAD ==================

    private void mostrarMensaje(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void actualizarListasDesplegables() {

        //if (cbClienteReserva != null) cbClienteReserva.getItems().setAll(controller.obtenerClientes());
        //if (cbProductoReserva != null) cbProductoReserva.getItems().setAll(controller.obtenerProductos());
        //if (cbGuiaReserva != null) cbGuiaReserva.getItems().setAll(controller.obtenerGuias());
    }

    private void limpiarCamposCliente() {
        txtDocCliente.clear(); txtNombreCliente.clear(); txtTelCliente.clear();
        txtCorreoCliente.clear(); txtPaisCliente.clear(); txtIdiomaCliente.clear();
    }

    private void limpiarCamposGuia() {
        txtIdGuia.clear(); txtNombreGuia.clear(); txtIdiomasGuia.clear();
        txtTelGuia.clear(); txtTarifaGuia.clear();
    }

    private void limpiarCamposActividad() {
        txtCodActividad.clear(); txtNombreActividad.clear(); txtMunActividad.clear();
        txtDuracionActividad.clear(); txtPrecioActividad.clear(); txtCupoActividad.clear();
        if(cbDificultadActividad != null) cbDificultadActividad.getSelectionModel().clearSelection();
        if(cbEstadoActividad != null) cbEstadoActividad.getSelectionModel().clearSelection();
    }

    private void limpiarCamposPaquete() {
        txtCodPaquete.clear(); txtNombrePaquete.clear(); txtDescuentoPaquete.clear();
    }

    private void limpiarCamposReserva() {
        if(cbClienteReserva != null) cbClienteReserva.getSelectionModel().clearSelection();
        if(cbProductoReserva != null) cbProductoReserva.getSelectionModel().clearSelection();
        if(cbGuiaReserva != null) cbGuiaReserva.getSelectionModel().clearSelection();
        if(dpFechaReserva != null) dpFechaReserva.setValue(null);
        txtViajerosReserva.clear();
        if(chkTransporteReserva != null) chkTransporteReserva.setSelected(false);
        if(chkSeguroReserva != null) chkSeguroReserva.setSelected(false);
        txtObservacionesReserva.clear();
    }
}