package juanma.parcial.objetos;

public class Deposito extends Ubicacion{


    //Constructor vacio usado por el class.newInstance en el lector de texto
    public Deposito() {
        this("","", null);
    }

    public Deposito(String ID, String nombre, Provincia provincia) {
        super(ID, nombre, provincia);
    }
}
