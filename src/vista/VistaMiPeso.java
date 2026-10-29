package vista;

import controlador.ProgresoControlador;
import modelo.HistorialProgreso;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VistaMiPeso extends JFrame {

    private final ProgresoControlador controlador;

    private JTextField cajaPeso;
    private DefaultTableModel modeloTabla;
    private JLabel lblDiferencia;
    private JLabel lblEstado;

    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public VistaMiPeso(Usuario usuario) {
        this.controlador = new ProgresoControlador(usuario);
    }

    public void mostrarVentana() {
        setTitle("Mi peso y progreso - Campus-Fit");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contenido = new JPanel(new BorderLayout(15, 15));
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        setContentPane(contenido);

        // Encabezado y formulario de registro.
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 15));
        panelSuperior.setOpaque(false);

        JLabel titulo = new JLabel(
                "Mi peso y progreso", SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(20, 70, 160));
        panelSuperior.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 10, 5
        ));
        formulario.setOpaque(false);

        formulario.add(new JLabel("Nuevo peso (kg):"));

        cajaPeso = new JTextField(10);
        formulario.add(cajaPeso);


        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarPeso());
        formulario.add(btnGuardar);

        panelSuperior.add(formulario, BorderLayout.CENTER);
        contenido.add(panelSuperior, BorderLayout.NORTH);

        // Recuadro con el historial. Las celdas son de solo lectura.
        modeloTabla = new DefaultTableModel(
                new Object[]{"Fecha", "Peso (kg)"}, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(440, 230));
        scroll.setBorder(
                BorderFactory.createTitledBorder("Historial de peso")
        );
        contenido.add(scroll, BorderLayout.CENTER);

        // Resumen y fecha del siguiente registro.
        JPanel panelInferior = new JPanel(new GridLayout(3, 1, 0, 8));
        panelInferior.setOpaque(false);

        lblDiferencia = new JLabel(" ");
        lblEstado = new JLabel(" ");

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panelInferior.add(lblDiferencia);
        panelInferior.add(lblEstado);
        panelInferior.add(btnCerrar);

        contenido.add(panelInferior, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnGuardar);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        cargarHistorial();
    }

    // Consulta las mediciones guardadas al abrir la ventana.
    private void cargarHistorial() {
        try {
            mostrarHistorial(controlador.obtenerHistorial());
        } catch (RuntimeException ex) {
            lblEstado.setText("No se pudo cargar el historial.");

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo leer el historial. Revisa progreso.json.",
                    "Error de lectura",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Solicita el guardado al controlador y actualiza la tabla.
    private void guardarPeso() {
        if (cajaPeso.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingresa el peso que deseas registrar.",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            HistorialProgreso historial =
                    controlador.registrarPeso(cajaPeso.getText());

            mostrarHistorial(historial);
            cajaPeso.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    "Peso guardado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Revisa el registro",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo completar el guardado del peso. "
                            + "Revisa el archivo de progreso.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Presenta los datos recibidos sin modificar el historial.
    private void mostrarHistorial(HistorialProgreso historial) {
        modeloTabla.setRowCount(0);

        for (HistorialProgreso.RegistroPeso registro
                : historial.getRegistros()) {

            modeloTabla.addRow(new Object[]{
                    registro.getFecha().format(formatoFecha),
                    registro.getPeso()
            });
        }

        Double diferencia = historial.calcularDiferencia();

        if (diferencia == null) {
            lblDiferencia.setText(
                    "Se necesitan dos mediciones para comparar."
            );
        } else {
            lblDiferencia.setText(String.format(
                    "Cambio respecto al registro anterior: %+.2f kg",
                    diferencia
            ));
        }

        LocalDate ultimaFecha = historial.getUltimaFecha();

        if (ultimaFecha == null) {
            lblEstado.setText(
                    "Sin mediciones: puedes registrar tu primer peso."
            );
        } else {
            LocalDate proximaFecha = ultimaFecha.plusMonths(1);

            if (LocalDate.now().isBefore(proximaFecha)) {
                lblEstado.setText(
                        "Próximo registro desde: "
                                + proximaFecha.format(formatoFecha)
                );
            } else {
                lblEstado.setText(
                        "Ya puedes registrar una nueva medición."
                );
            }
        }
    }
}