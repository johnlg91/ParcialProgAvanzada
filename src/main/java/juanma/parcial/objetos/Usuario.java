package juanma.parcial.objetos;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String DNI;
    private String nombre;
    private String apellido;

    //constructor vacio usado por el newInstancexz
    public Usuario(){
        DNI = ""; nombre = ""; apellido = "";
    }

    public Usuario(String DNI, String nombre, String apellido) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
    }


    public String getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        return getDNI().equals(usuario.getDNI());
    }

    @Override
    public int hashCode() {
        return getDNI().hashCode();
    }
}
