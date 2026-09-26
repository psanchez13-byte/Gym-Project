
import vista.VistaLogin;
import vista.VistaRegistro;

import java.util.Scanner;

public class MainDeArranque {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VistaLogin menuPrincipal = new VistaLogin();
        menuPrincipal.inicio(sc);
    }
}