package juanma.parcial.objetos;

public class Tienda extends Ubicacion{

    public Tienda(String nombre) {
        super(nombre);
    }

    public Tienda(String ID, String nombre, Provincia provincia) {
        super(ID, nombre, provincia);
    }
}
