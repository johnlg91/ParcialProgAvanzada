package juanma.parcial.objetos;


import java.io.Serializable;

public abstract class Ubicacion implements Serializable {

    protected String id;
    protected String nombre;
    protected Provincia provincia;

    public Ubicacion(String id, String nombre, Provincia provincia) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ubicacion)) return false;

        Ubicacion ubicacion = (Ubicacion) o;

        return nombre.equals(ubicacion.nombre);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id + ": " + nombre + " (" + provincia + ")";
    }

}
