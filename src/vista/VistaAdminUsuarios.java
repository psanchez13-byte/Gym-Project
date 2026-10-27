package vista;

import controlador.AdminUsuariosControlador;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaAdminUsuarios extends JFrame{

    private JButton btnRevisarUsuarioDsp;
    private JButton btnEliminarUsuario;
    private AdminUsuariosControlador controlador;

    public void mostrarVentana(){
        setTitle("Menú De administración - Gestion de usuarios ");
        setSize(500, 550);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // centrado

        controlador = new AdminUsuariosControlador();

        Color negroAdmin = new Color(30, 30, 30); // Gris muy oscuro
        Color doradoAdmin = new Color(212, 175, 55); // Tono oro elegante

        //. Fondo de la ventana oscuro
        getContentPane().setBackground(negroAdmin);
        setVisible(true);
        JPanel barraSuperior = Herramientas.crearPanel(doradoAdmin, 0, 0, 500, 60);
        JLabel lblTitulo = Herramientas.crearLabels(18, "Panel de Gestion de usuarios registrados ", 120, 10, 300, 40);
        lblTitulo.setForeground(Color.BLACK); // Texto negro sobre barra dorada
        barraSuperior.add(lblTitulo);
        add(barraSuperior);

        btnRevisarUsuarioDsp = Herramientas.crearBoton("Revisar usuarios registrados",150,260,200,40,e -> btnRevisarUsuarioDspAccion());
        btnEliminarUsuario = Herramientas.crearBoton("Eliminar Usuarios",150,320,200,40,e -> btnEliminarUsuarioAccion());
        add(btnEliminarUsuario);
        add(btnRevisarUsuarioDsp);
    }
    public void btnRevisarUsuarioDspAccion(){
        List<Usuario> lista = controlador.obtenerTodosLosUsuarios();
        StringBuilder reporte = new StringBuilder("Usuarios Registrados en el CSV:\n\n");
        // 2. Mientras preparamos el JTable, podemos agrupar los datos
        // en un texto largo para comprobar inmediatamente que el CSV se está leyendo bien.
        for (Usuario u : lista){
            reporte.append("Nombre: ").append(u.getNombre())
                    .append(" | Matrícula: ").append(u.getMatricula())
                    .append("\n");
        }
        // 3. Metemos el texto en un panel con "Scroll" por si tienes 50 usuarios y no caben en pantalla
        JTextArea areaTexto = new JTextArea(reporte.toString());
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setPreferredSize(new Dimension(300, 250));

        JOptionPane.showMessageDialog(this, scroll, "Visor Rápido de Usuarios", JOptionPane.INFORMATION_MESSAGE);

    }
    public void btnEliminarUsuarioAccion(){
        // 1. La Vista le pide un dato al administrador mediante un cuadro de texto emergente
        String matricula = JOptionPane.showInputDialog(this, "Ingrese la matrícula del usuario a eliminar:", "Eliminar Usuario", JOptionPane.QUESTION_MESSAGE);

        // 2. Validamos que no haya presionado "Cancelar" o dejado vacío
        if (matricula != null && !matricula.trim().isEmpty()) {
            // 3. Enviamos el dato al Controlador para que haga el trabajo
            // esperamos TRUE o FALSE.
            boolean exito = controlador.eliminarUsuario(matricula.trim());
            if (exito) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente del sistema.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un usuario con la matrícula: " + matricula, "Error de búsqueda", JOptionPane.ERROR_MESSAGE);
            }
        }

    }



}
