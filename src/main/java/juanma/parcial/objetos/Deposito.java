package juanma.parcial.objetos;

public class Deposito extends Ubicacion{


    //constructor vacio usado por el newInstance
    public Deposito() {
        this("","", null);
    }

    public Deposito(String ID, String nombre, Provincia provincia) {
        super(ID, nombre, provincia);
    }
}
