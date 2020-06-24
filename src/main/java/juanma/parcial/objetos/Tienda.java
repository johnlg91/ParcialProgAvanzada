package juanma.parcial.objetos;

public class Tienda extends Ubicacion{


    //constructor vacio usado por el newInstance
    private Tienda() {
        this("","", null);
    }

    public Tienda(String ID, String nombre, Provincia provincia) {
        super(ID, nombre, provincia);
    }
}
