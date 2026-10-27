package controlador;

import datos.GestorProgresoJSON;
import modelo.HistorialProgreso;
import modelo.SeguimientoMensual;
import modelo.Usuario;

import java.time.LocalDate;

public class ProgresoControlador {

    private final Usuario usuario;
    private final GestorProgresoJSON gestor;
    private final SeguimientoMensual seguimiento;

    public ProgresoControlador(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Debe existir un usuario con sesión iniciada."
            );
        }

        this.usuario = usuario;
        this.gestor = new GestorProgresoJSON();
        this.seguimiento = new SeguimientoMensual();
    }
    // Recupera solamente el historial del usuario autenticado.
    public HistorialProgreso obtenerHistorial() {
        return gestor.leerProgreso(usuario.getMatricula());
    }
    // Guarda una medición respetando el plazo mensual.
    public HistorialProgreso registrarPeso(String textoPeso) {
        double peso;
        try {
            peso = Double.parseDouble(
                    textoPeso.trim().replace(',', '.')
            );
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    "Ingresar un peso numérico. Por ejemplo: 85,5."
            );
        }

        if (!Double.isFinite(peso) || peso <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe ser un número positivo y válido."
            );
        }
        // Cargamos los datos nuevamente antes de incorporar la medición.
        HistorialProgreso historial = obtenerHistorial();
        boolean registrado = seguimiento.registrarPesoMensual(
                historial, peso, LocalDate.now()
        );

        if (!registrado) {
            throw new IllegalArgumentException(
                    "Todavía no corresponde registrar otro peso."
            );
        }

        // Solo devolvemos el historial actualizado si se guardó correctamente.
        gestor.guardarProgreso(historial);

        return historial;
    }
}