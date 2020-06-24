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

    //manejo de excepciones de stock se manejaran en la gui

    //transferir recibe el producto, las ubicaciones y el usuario, pide la instancia de la base de datos,
    // y en ella modifica el stock y le agrega la operacional historial
    public void transferir(Producto product, Ubicacion origen, Ubicacion destino, Usuario usuario, int cantidad) {
        DataBase dataBase = DataBase.getInstance();
        Operacion operacion = new Operacion(usuario, LocalDate.now(), origen, destino, product, cantidad);// crea la operacion
        Stock desde = dataBase.getStock(origen, product); //pide ambos objetos para fabricar el ID del stock y pedirlo
        desde.setCantidad(desde.getCantidad() - cantidad);
        Stock hasta = dataBase.getStock(destino, product);
        hasta.setCantidad(hasta.getCantidad() + cantidad);
        dataBase.addHistorial(operacion); //guarda la operacion en el historial
    }

    //similar a transferir pero solo le quita stock de la tienda
    public void vender(Producto product, Tienda tienda, Usuario usuario, int cantidad) {
        DataBase dataBase = DataBase.getInstance();
        Operacion operacion = new Operacion(usuario, LocalDate.now(), tienda, product, cantidad);
        Stock desde = dataBase.getStock(tienda, product);
        desde.setCantidad(desde.getCantidad() - cantidad);
        dataBase.addHistorial(operacion);
    }

    //similar a vender pero agrega en vez de quitar
    //nesecitaba un metodo para agregar sotck entonces lo agregue
    public void comprar(Producto product, Deposito deposito, Usuario usuario, int cantidad) {
        DataBase dataBase = DataBase.getInstance();
        Operacion op = new Operacion(usuario, LocalDate.now(), deposito, product, cantidad);
        Stock desde = dataBase.getStock(deposito, product);
        desde.setCantidad(desde.getCantidad() + cantidad);
        dataBase.addHistorial(op);
    }

}
