package vista;

import controlador.FuerzaControlador;
import modelo.LectorEjerciciosRutina.EjercicioRutina;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Permite consultar y seleccionar los ejercicios de la rutina del usuario.
 * Muestra las series y el objetivo indicado para cada ejercicio.
 */
public class VistaMiFuerza extends JFrame {

    private final FuerzaControlador controlador;

    private JComboBox<EjercicioRutina> comboEjercicios;
    private JLabel lblSeries;
    private JLabel lblObjetivo;
    private JLabel lblEstado;

    /**
     * Prepara el controlador con el usuario que inició sesión.
     */
    public VistaMiFuerza(Usuario usuario) {
        this.controlador = new FuerzaControlador(usuario);
    }

    /**
     * Construye la ventana y carga los ejercicios asignados.
     */
    public void mostrarVentana() {
        setTitle("Mi fuerza y progreso - Campus-Fit");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contenido = new JPanel(new BorderLayout(15, 20));
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );
        setContentPane(contenido);

        JLabel titulo = new JLabel(
                "Ejercicios de mi rutina",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(20, 70, 160));
        contenido.add(titulo, BorderLayout.NORTH);

        // Agrupa el selector y los datos del ejercicio elegido.
        JPanel panelEjercicio = new JPanel(new GridLayout(4, 1, 0, 12));
        panelEjercicio.setOpaque(false);

        panelEjercicio.add(new JLabel("Selecciona un ejercicio:"));

        comboEjercicios = new JComboBox<>();
        comboEjercicios.setEditable(false);
        comboEjercicios.setPreferredSize(new Dimension(420, 32));
        comboEjercicios.addActionListener(
                e -> mostrarDatosEjercicio()
        );
        panelEjercicio.add(comboEjercicios);

        lblSeries = new JLabel("Series: —");
        panelEjercicio.add(lblSeries);

        lblObjetivo = new JLabel("Objetivo por serie: —");
        panelEjercicio.add(lblObjetivo);

        contenido.add(panelEjercicio, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout(10, 15));
        panelInferior.setOpaque(false);

        lblEstado = new JLabel(" ");
        panelInferior.add(lblEstado, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelInferior.add(btnCerrar, BorderLayout.SOUTH);

        contenido.add(panelInferior, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        cargarEjercicios();
    }

    /**
     * Solicita los ejercicios al controlador y llena el desplegable.
     * Si ocurre un error, deshabilita el selector e informa el problema.
     */
    private void cargarEjercicios() {
        comboEjercicios.removeAllItems();

        try {
            List<EjercicioRutina> ejercicios =
                    controlador.obtenerEjerciciosAsignados();

            for (EjercicioRutina ejercicio : ejercicios) {
                comboEjercicios.addItem(ejercicio);
            }

            comboEjercicios.setEnabled(true);
            lblEstado.setText(
                    "Ejercicios disponibles: " + ejercicios.size()
            );

            mostrarDatosEjercicio();

        } catch (IllegalStateException | IllegalArgumentException ex) {
            comboEjercicios.setEnabled(false);
            lblEstado.setText("No se pudieron cargar los ejercicios.");

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Consulta de rutina",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Actualiza las etiquetas según el ejercicio seleccionado.
     * Conserva la unidad de medida definida en la rutina.
     */
    private void mostrarDatosEjercicio() {
        EjercicioRutina ejercicio =
                (EjercicioRutina) comboEjercicios.getSelectedItem();

        if (ejercicio == null) {
            lblSeries.setText("Series: —");
            lblObjetivo.setText("Objetivo por serie: —");
            return;
        }

        lblSeries.setText("Series: " + ejercicio.getSeries());

        lblObjetivo.setText(
                "Objetivo por serie: "
                        + ejercicio.getObjetivo()
                        + " "
                        + ejercicio.getUnidad()
        );
    }
}