package juanma.parcial.objetos;

public class Deposito extends Ubicacion{

    public Deposito(String nombre) {
        super(nombre);
    }

    public Deposito(String ID, String nombre, Provincia provincia) {
        super(ID, nombre, provincia);
    }
}
