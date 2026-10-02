package modelo;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PlanSemanalTest {

    @Test
    public void testElegirDias_EntradaValida() {
        // 1. Simulamos que un usuario teclea "1", "2", "3" y "4" y presiona Enter
        String entradaSimulada = "b\n1\n2\n3\n4\n";
        //  lea nuestro String en lugar del teclado real
        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

        PlanSemanal plan = new PlanSemanal();

        // 2. Ejecutar: Llamamos al método. El Scanner leerá los números simulados automáticamente.
        List<Integer> diasElegidos = plan.elegirDias();

        // 3. Afirmar: Verificamos que guardó exactamente 4 días y que son los correctos
        assertEquals(4, diasElegidos.size(), "Debe retornar exactamente 4 días");
        assertTrue(diasElegidos.contains(1));
        assertTrue(diasElegidos.contains(4));
    }
} ///sin problemas