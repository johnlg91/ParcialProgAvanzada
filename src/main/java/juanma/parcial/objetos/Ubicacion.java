package juanma.parcial.objetos;

import java.util.TreeMap;

public abstract class Ubicacion {

    public enum Provincia{
        BUENOS_AIRES, CORDOVA
    }

    protected String ID;
    protected String nombre;
    protected Provincia provincia;

    public Ubicacion(String ID, String nombre, Provincia provincia) {
        this.ID = ID;
        this.nombre = nombre;
        this.provincia = provincia;
    }


}
