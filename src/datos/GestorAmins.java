package datos;
import modelo.Administrador;

import java.util.List;
import java.util.ArrayList;

public class GestorAmins {

    private final List<Administrador> admins = new ArrayList<>();

    public GestorAmins(){
        admins.add(new Administrador("123456789","nure67"));
        admins.add(new Administrador("21984678024","lasde12"));
        admins.add(new Administrador("123456789","meir69"));
        admins.add(new Administrador("21910067124","lia1515"));
    }

    public boolean validarCredenciales(String a, String b){

        for(Administrador ad: admins){
            if (ad.getMatricula().equals(a) && ad.getContraseña().equals(b)){
                return true;
            }
        }
        return false;
    }

}
