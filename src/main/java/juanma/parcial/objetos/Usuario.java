package juanma.parcial.objetos;

public class Usuario {

    private String DNI;
    private String nombre;
    private String apellido;

    public Usuario(String DNI, String nombre, String apellido) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
    }


    public String getDNI() {
        return DNI;
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
