package juanma.parcial;

import juanma.parcial.objetos.*;

import java.time.LocalDate;

public class Acciones {

    //constructor privado
    private Acciones() {
    }

    private static final Acciones INSTANCE = new Acciones();

    public static Acciones getInstance() {
        return INSTANCE;
    }

    public void transferir(Producto product, Ubicacion origen, Ubicacion destino, Usuario usuario, int cantidad) {
        DataBase db = DataBase.getInstance();
        Operacion op = new Operacion(usuario, LocalDate.now(), origen, destino, product, cantidad);
        Stock desde = db.getStock(origen, product);
        desde.setCantidad(desde.getCantidad() - cantidad);
        Stock hasta = db.getStock(destino, product);
        hasta.setCantidad(hasta.getCantidad() + cantidad);
        db.add(op);
    }

    public void vender(Producto product, Tienda tienda, Usuario usuario, int cantidad) {
        DataBase db = DataBase.getInstance();
        Operacion op = new Operacion(usuario, LocalDate.now(), tienda, product, cantidad);
        Stock desde = db.getStock(tienda, product);
        desde.setCantidad(desde.getCantidad() - cantidad);
        db.add(op);
    }

}
