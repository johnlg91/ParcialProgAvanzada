package juanma.parcial;


import juanma.parcial.objetos.Deposito;
import juanma.parcial.objetos.Operacion;
import juanma.parcial.objetos.Producto;
import juanma.parcial.objetos.Tienda;
import juanma.parcial.persistencia.TextPersistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DataBase {

    private static final String FILE_DEPOSITOS = "depositos.txt";
    private static final String FILE_PRODUCTOS = "productos.txt";
    private static final String FILE_STOCK = "stock.txt";

    private final List<Operacion> historial = new ArrayList<>();
    private final SortedMap<String, Producto> productos = new TreeMap<>();
    private final SortedMap<String, Deposito> depositos = new TreeMap<>();
    private final SortedMap<String, Tienda> tiendas = new TreeMap<>();


    //constructor privado
    private DataBase() {
    }

    //para cargar del disco
    public void loadData(File dataDirectory) {
        Thread[] threads = {
                new Thread(() -> loadProductos(dataDirectory)),
                new Thread(() -> loadDepositos(dataDirectory))
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
        Thread[] threads = {
                new Thread(() -> saveProductos(dataDirectory)),
                new Thread(() -> saveDepositos(dataDirectory))
                // save tiendas
                // save  stock
                // save usuarios
        };
        for (Thread t : threads) t.start();
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignore) {
            }
        }
        ;
    }

    public Producto getProducto(String sku) {
        return productos.get(sku);
    }

    public Deposito getDeposito(String id) {
        return depositos.get(id);
    }

    private void loadDepositos(File dataDirectory) {
        for (Deposito deposito : TextPersistence.<Deposito>readElements(new File(dataDirectory, FILE_DEPOSITOS)))
            depositos.put(deposito.getId(), deposito);
    }

    private void loadProductos(File dataDirectory) {
        for (Producto p : TextPersistence.<Producto>readElements(new File(dataDirectory, FILE_PRODUCTOS)))
            productos.put(p.getSku(), p);
    }

    private void saveProductos(File dataDirectory) {
        TextPersistence.writeElements(new File(dataDirectory, FILE_PRODUCTOS), new ArrayList<>(tiendas.values()));
    }
    private void saveDepositos(File dataDirectory) {
        TextPersistence.writeElements(new File(dataDirectory, FILE_DEPOSITOS), new ArrayList<>(depositos.values()));
    }

    private static final DataBase INSTANCE = new DataBase();

    public static DataBase getInstance() {
        return INSTANCE;
    }


}

