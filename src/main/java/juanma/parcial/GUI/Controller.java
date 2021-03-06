package juanma.parcial.GUI;

import juanma.parcial.objetos.*;
import juanma.parcial.persistencia.DataBase;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;


//Es el entre medio entre la GUI y la DataBase
//es la q se encarga de realizar las acciones pedidas
public class Controller {

    private final File dataDirectory;
    private DataBase dataBase;

    public Controller(String dataDirectory) {
        this.dataDirectory = new File(dataDirectory);
        dataBase = DataBase.getInstance();
    }

    //LLama a la data Base para q cargue los datos de los archivos
    public void start() {
        dataBase.loadData(dataDirectory);
    }

    //LLama a la data Base para guarde los datos en los archivos
    public void stop() {
        dataBase.saveData(dataDirectory);
    }


    //Finders, usados por los finders en User GUI
    public Producto findProduct(String productId) {
        return dataBase.getProducto(productId);
    }

    public Deposito findDeposito(String id) {
        return dataBase.getDeposito(id);
    }

    public Tienda findTienda(String idTienda) {
        return dataBase.getTienda(idTienda);
    }

    public Usuario findUsuario(String dni) {
        return dataBase.getUsuario(dni);
    }


    //Acciones

    public void transferir(Producto product, Ubicacion origen, Ubicacion destino, Usuario usuario, int cantidad) {
        Operacion operacion = new Operacion(usuario, LocalDate.now(), origen, destino, product, cantidad);// crea la operacion
        Stock desde = dataBase.getStock(origen, product); //pide ambos objetos para fabricar el ID del stock y pedirlo
        desde.setCantidad(desde.getCantidad() - cantidad);
        Stock hasta = dataBase.getStock(destino, product);
        hasta.setCantidad(hasta.getCantidad() + cantidad);
        dataBase.addHistorial(operacion); //guarda la operacion en el historial
    }

    //Similar a transferir pero solo le quita stock de la tienda
    public void vender(Producto product, Tienda tienda, Usuario usuario, int cantidad) {
        Operacion operacion = new Operacion(usuario, LocalDate.now(), tienda, product, cantidad);
        Stock desde = dataBase.getStock(tienda, product);
        desde.setCantidad(desde.getCantidad() - cantidad);
        dataBase.addHistorial(operacion);
    }

    //Similar a vender pero agrega en vez de quitar
    //nesecitaba un metodo para agregar sotck entonces lo agregue
    public void comprar(Producto product, Deposito deposito, Usuario usuario, int cantidad) {
        Operacion op = new Operacion(usuario, LocalDate.now(), deposito, product, cantidad);
        Stock desde = dataBase.getStock(deposito, product);
        desde.setCantidad(desde.getCantidad() + cantidad);
        dataBase.addHistorial(op);
    }

    //Se llama desde la GUI, agrega un Usuario a la dataBase
    public void addUsuario(Usuario usuario) {
        dataBase.addUsuario(usuario);
    }


    //Como los finders pero devuelven la coleccion entera, llaman a los getters del data base

    public Collection<Producto> getProductos() {
        return dataBase.getProductos();
    }

    public Collection<Deposito> getDepositos() {
        return dataBase.getDepositos();
    }

    public Collection<Tienda> getTiendas() {
        return dataBase.getTiendas();
    }
}
