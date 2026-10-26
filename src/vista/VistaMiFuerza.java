package vista;

import controlador.FuerzaControlador;
import modelo.LectorEjerciciosRutina.EjercicioRutina;
import modelo.RegistroFuerza;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Permite seleccionar un ejercicio de la rutina, registrar sus series
 * y consultar el historial de entrenamientos del usuario.
 */
public class VistaMiFuerza extends JFrame {

    private final FuerzaControlador controlador;

    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JComboBox<EjercicioRutina> comboEjercicios;

    private JLabel lblObjetivo;
    private JLabel lblEstado;

    private JTable tablaSeries;
    private JTable tablaHistorial;

    private DefaultTableModel modeloSeries;
    private DefaultTableModel modeloHistorial;

    private JButton btnGuardar;

    private EjercicioRutina ejercicioActual;

    // Evita ejecutar acciones mientras se preparan los componentes.
    private boolean actualizando;

    /**
     * Prepara el controlador con el usuario que inició sesión.
     */
    public VistaMiFuerza(Usuario usuario) {
        controlador = new FuerzaControlador(usuario);
    }

    /**
     * Construye la ventana, configura el cierre y carga los ejercicios.
     */
    public void mostrarVentana() {
        setTitle("Mi fuerza y progreso - Campus-Fit");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel contenido = new JPanel(new BorderLayout(12, 15));
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        setContentPane(contenido);

        contenido.add(crearEncabezado(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 15));
        centro.setOpaque(false);
        centro.add(crearFormularioSeries(), BorderLayout.NORTH);
        centro.add(crearPanelHistorial(), BorderLayout.CENTER);

        contenido.add(centro, BorderLayout.CENTER);
        contenido.add(crearPie(), BorderLayout.SOUTH);

        // La X y el botón Cerrar comprueban si hay datos pendientes.
        addWindowListener(new WindowAdapter() {
            /**
             * Solicita el cierre sin descartar datos silenciosamente.
             */
            @Override
            public void windowClosing(WindowEvent evento) {
                cerrarVentana();
            }
        });

        setMinimumSize(new Dimension(650, 580));
        setSize(760, 680);
        setLocationRelativeTo(null);

        cargarEjercicios();
        setVisible(true);
    }

    /**
     * Crea el título, el selector de ejercicios y su objetivo.
     */
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout(10, 12));
        panel.setOpaque(false);

        JLabel titulo = new JLabel(
                "Mi fuerza y progreso",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(20, 70, 160));

        panel.add(titulo, BorderLayout.NORTH);

        JPanel selector = new JPanel(new BorderLayout(10, 0));
        selector.setOpaque(false);

        selector.add(new JLabel("Ejercicio:"), BorderLayout.WEST);

        comboEjercicios = new JComboBox<>();
        comboEjercicios.setEditable(false);
        comboEjercicios.addActionListener(e -> cambiarEjercicio());

        selector.add(comboEjercicios, BorderLayout.CENTER);
        panel.add(selector, BorderLayout.CENTER);

        lblObjetivo = new JLabel("Selecciona un ejercicio.");
        panel.add(lblObjetivo, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea la tabla editable para ingresar los resultados de las series.
     * El número de serie se mantiene como dato de solo lectura.
     */
    private JPanel crearFormularioSeries() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setOpaque(false);
        panel.setBorder(
                BorderFactory.createTitledBorder("Registrar entrenamiento de hoy")
        );

        modeloSeries = new DefaultTableModel(
                new Object[]{"Serie", "Peso (kg)", "Repeticiones"},
                0
        ) {
            /**
             * Permite editar únicamente el peso y el resultado.
             */
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return columna > 0;
            }
        };

        tablaSeries = new JTable(modeloSeries);
        tablaSeries.setRowHeight(30);
        tablaSeries.getTableHeader().setReorderingAllowed(false);
        tablaSeries.putClientProperty(
                "terminateEditOnFocusLost", Boolean.TRUE
        );

        JScrollPane scroll = new JScrollPane(tablaSeries);
        scroll.setPreferredSize(new Dimension(600, 130));
        panel.add(scroll, BorderLayout.CENTER);

        JPanel acciones = new JPanel(new BorderLayout(10, 5));
        acciones.setOpaque(false);

        JLabel ayuda = new JLabel(
                "Ingresa lo realizado. Usa 0 kg si no utilizaste carga adicional."
        );
        acciones.add(ayuda, BorderLayout.NORTH);

        btnGuardar = new JButton("Guardar entrenamiento");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(e -> guardarEntrenamiento());

        acciones.add(btnGuardar, BorderLayout.SOUTH);
        panel.add(acciones, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea una tabla de solo lectura para consultar entrenamientos anteriores.
     */
    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Historial del ejercicio seleccionado"
                )
        );

        modeloHistorial = new DefaultTableModel(
                new Object[]{
                        "Fecha", "Registro", "Serie",
                        "Peso (kg)", "Resultado", "Unidad"
                },
                0
        ) {
            /**
             * Impide modificar los registros guardados desde la tabla.
             */
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloHistorial);
        tablaHistorial.setRowHeight(26);
        tablaHistorial.setFillsViewportHeight(true);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);

        panel.add(
                new JScrollPane(tablaHistorial),
                BorderLayout.CENTER
        );

        return panel;
    }

    /**
     * Crea el mensaje de estado y el botón para cerrar la ventana.
     */
    private JPanel crearPie() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);

        lblEstado = new JLabel(" ");
        panel.add(lblEstado, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> cerrarVentana());
        panel.add(btnCerrar, BorderLayout.EAST);

        return panel;
    }

    /**
     * Recupera los ejercicios asignados y selecciona el primero disponible.
     */
    private void cargarEjercicios() {
        actualizando = true;

        try {
            List<EjercicioRutina> ejercicios =
                    controlador.obtenerEjerciciosAsignados();

            comboEjercicios.removeAllItems();

            for (EjercicioRutina ejercicio : ejercicios) {
                comboEjercicios.addItem(ejercicio);
            }

            comboEjercicios.setEnabled(true);
            ejercicioActual =
                    (EjercicioRutina) comboEjercicios.getSelectedItem();

        } catch (RuntimeException ex) {
            comboEjercicios.setEnabled(false);
            btnGuardar.setEnabled(false);
            lblEstado.setText("No se pudieron cargar los ejercicios.");

            mostrarError(
                    "No se pudieron cargar los ejercicios de tu rutina.",
                    ex
            );

        } finally {
            actualizando = false;
        }

        if (ejercicioActual != null) {
            prepararSeries();
            cargarHistorial();
        }
    }

    /**
     * Cambia el ejercicio y comprueba si hay resultados pendientes.
     * Si se cancela el cambio, restaura la selección anterior.
     */
    private void cambiarEjercicio() {
        if (actualizando) {
            return;
        }

        EjercicioRutina seleccionado =
                (EjercicioRutina) comboEjercicios.getSelectedItem();

        if (seleccionado == null || seleccionado == ejercicioActual) {
            return;
        }

        if (!confirmarDescarte()) {
            actualizando = true;
            comboEjercicios.setSelectedItem(ejercicioActual);
            actualizando = false;
            return;
        }

        ejercicioActual = seleccionado;

        prepararSeries();
        cargarHistorial();
    }

    /**
     * Genera una fila vacía por cada serie asignada.
     * Adapta la última columna a repeticiones o segundos.
     */
    private void prepararSeries() {
        if (tablaSeries.isEditing()) {
            tablaSeries.getCellEditor().cancelCellEditing();
        }

        String resultado = ejercicioActual.esPorTiempo()
                ? "Segundos realizados"
                : "Repeticiones realizadas";

        modeloSeries.setRowCount(0);
        modeloSeries.setColumnIdentifiers(
                new Object[]{"Serie", "Peso (kg)", resultado}
        );

        for (int i = 1; i <= ejercicioActual.getSeries(); i++) {
            modeloSeries.addRow(new Object[]{i, "", ""});
        }

        lblObjetivo.setText(
                "Rutina: " + ejercicioActual.getSeries()
                        + " series de " + ejercicioActual.getObjetivo()
                        + " " + ejercicioActual.getUnidad() + "."
        );

        btnGuardar.setEnabled(true);
    }

    /**
     * Lee las celdas, solicita el guardado y actualiza el historial.
     * Conserva los datos ingresados si falla la validación o escritura.
     */
    private void guardarEntrenamiento() {
        if (ejercicioActual == null || !finalizarEdicion()) {
            return;
        }

        List<String> pesos = new ArrayList<>();
        List<String> cantidades = new ArrayList<>();

        for (int fila = 0; fila < modeloSeries.getRowCount(); fila++) {
            pesos.add(textoCelda(fila, 1));
            cantidades.add(textoCelda(fila, 2));
        }

        btnGuardar.setEnabled(false);

        try {
            controlador.guardarEntrenamiento(
                    ejercicioActual.getIdentificador(),
                    pesos,
                    cantidades
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Revisa las series",
                    JOptionPane.WARNING_MESSAGE
            );
            return;

        } catch (RuntimeException ex) {
            mostrarError("No se pudo guardar el entrenamiento.", ex);
            return;

        } finally {
            btnGuardar.setEnabled(true);
        }

        // Limpia los campos solo después de un guardado exitoso.
        prepararSeries();

        boolean historialCargado = cargarHistorial();

        JOptionPane.showMessageDialog(
                this,
                historialCargado
                        ? "Entrenamiento guardado correctamente."
                        : "El entrenamiento se guardó, pero no se pudo "
                        + "actualizar el historial en pantalla.",
                "Entrenamiento guardado",
                historialCargado
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Recupera el historial y muestra una fila por cada serie registrada.
     * El número de registro diferencia entrenamientos de una misma fecha.
     */
    private boolean cargarHistorial() {
        modeloHistorial.setRowCount(0);

        try {
            List<RegistroFuerza> historial =
                    controlador.obtenerHistorial(
                            ejercicioActual.getIdentificador()
                    );

            int numeroRegistro = 1;

            for (RegistroFuerza registro : historial) {
                int numeroSerie = 1;

                for (RegistroFuerza.Serie serie : registro.getSeries()) {
                    modeloHistorial.addRow(new Object[]{
                            registro.getFecha().format(formatoFecha),
                            numeroRegistro,
                            numeroSerie,
                            serie.getPeso(),
                            serie.getCantidad(),
                            registro.getUnidad()
                    });

                    numeroSerie++;
                }

                numeroRegistro++;
            }

            lblEstado.setText(
                    historial.isEmpty()
                            ? "Este ejercicio todavía no tiene registros."
                            : "Entrenamientos guardados: " + historial.size()
            );

            return true;

        } catch (RuntimeException ex) {
            lblEstado.setText("No se pudo cargar el historial.");
            mostrarError("No se pudo consultar el historial de fuerza.", ex);
            return false;
        }
    }

    /**
     * Confirma la edición de la celda activa antes de leer la tabla.
     */
    private boolean finalizarEdicion() {
        return !tablaSeries.isEditing()
                || tablaSeries.getCellEditor().stopCellEditing();
    }

    /**
     * Obtiene el contenido de una celda como texto sin espacios externos.
     */
    private String textoCelda(int fila, int columna) {
        Object valor = modeloSeries.getValueAt(fila, columna);
        return valor == null ? "" : valor.toString().trim();
    }

    /**
     * Detecta si el usuario ingresó algún peso o resultado sin guardar.
     */
    private boolean hayDatosPendientes() {
        for (int fila = 0; fila < modeloSeries.getRowCount(); fila++) {
            if (!textoCelda(fila, 1).isEmpty()
                    || !textoCelda(fila, 2).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Pide confirmación antes de descartar resultados ingresados.
     */
    private boolean confirmarDescarte() {
        if (!finalizarEdicion()) {
            return false;
        }

        if (!hayDatosPendientes()) {
            return true;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "Hay datos sin guardar. ¿Quieres descartarlos?",
                "Datos pendientes",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Cierra la ventana después de comprobar los datos pendientes.
     */
    private void cerrarVentana() {
        if (confirmarDescarte()) {
            dispose();
        }
    }

    /**
     * Presenta el error con una descripción del problema ocurrido.
     */
    private void mostrarError(String mensaje, RuntimeException ex) {
        String detalle = ex.getMessage();

        JOptionPane.showMessageDialog(
                this,
                mensaje + (detalle == null ? "" : "\n" + detalle),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}