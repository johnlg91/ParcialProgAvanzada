package juanma.parcial;


import juanma.parcial.objetos.Deposito;
import juanma.parcial.objetos.Operacion;
import juanma.parcial.objetos.Producto;
import juanma.parcial.persistencia.TextPersistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DataBase {

    private List<Operacion> historial;
    private SortedMap<String, Producto> productos = new TreeMap<>();
    private SortedMap<String, Deposito> depositos = new TreeMap<>();
    private SortedMap<String, Integer> stock = new TreeMap<>();


    //constructor privado
    private DataBase() {
    }

    //para cargar del disco
    public void loadData(File dataDirectory) {
        Thread[] threads = {
            new Thread(() -> loadProductos(new File(dataDirectory, "productos.txt"))),
            new Thread(() -> loadDepositos(new File(dataDirectory, "depositos.txt")))
                    // load tiendas
                    // load stock
                    // load usuarios
        };
        for (Thread t : threads) t.start();
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignore) {
            }
        }
    }

    //para grabar al disco
    public void saveData(File dataDirectory) {
        saveProductos(new File(dataDirectory, "productos.txt"));
        // saveDepositos(new File(dataDirectory, "depositos.txt"));
        // save tiendas
        // save  stock
        // save usuarios
    }

    public Producto getProducto(String sku) {
        return productos.get(sku);
    }

    public Deposito getDeposito(String id) {
        return depositos.get(id);
    }

    private void loadDepositos(File file) {
        for (Deposito deposito : TextPersistence.<Deposito>readElements(file))
            depositos.put(deposito.getId(), deposito);
    }

    private void loadProductos(File file) {
        for (Producto p : TextPersistence.<Producto>readElements(file))
            productos.put(p.getSku(), p);
    }

    private void saveProductos(File file) {
        TextPersistence.writeElements(file, new ArrayList<>(productos.values()));
    }

    private static final DataBase INSTANCE = new DataBase();

    public static DataBase getInstance() {
        return INSTANCE;
    }


}

