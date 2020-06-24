package juanma.parcial.objetos;

public class Deposito extends Ubicacion{


    //constructor vacio usado por el newInstance
    private Deposito() {
        this("","", null);
    }

    public Deposito(String ID, String nombre, Provincia provincia) {
        super(ID, nombre, provincia);
    }


    @Override
    public String toString() {
        return "Deposito{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", provincia=" + provincia +
                '}';
    }
}
