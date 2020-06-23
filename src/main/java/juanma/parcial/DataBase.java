package juanma.parcial;


import juanma.parcial.objetos.*;
import juanma.parcial.persistencia.TextPersistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DataBase {

    //mapas y listas donde se guardan los datos
    //los mapas se guardan con un ID del objeto como key
    private List<Operacion> historial;
    private final SortedMap<String, Producto> productos = new TreeMap<>();
    private final SortedMap<String, Deposito> depositos = new TreeMap<>();
    private final SortedMap<String, Tienda> tiendas = new TreeMap<>();
    private final SortedMap<String, Stock> stock = new TreeMap<>();
    private final SortedMap<String, Usuario> usuarios = new TreeMap<>();


    // Nombres de los archivos

    private static final String FILE_DEPOSITOS = "depositos.txt";
    private static final String FILE_PRODUCTOS = "productos.txt";
    private static final String FILE_STOCK = "stock.txt";
    private static final String FILE_USUARIOS = "usuarios.txt";
    private static final String FILE_TIENDAS = "tiendas.txt";
    private static final String FILE_HISTORIAL = "historial.txt";

    private static final DataBase INSTANCE = new DataBase(); //instancia

    //constructor privado del singleton
    private DataBase() {
    }

    //inicializa el objeto singleton
    public static DataBase getInstance() {
        return INSTANCE;
    }

    //para cargar del disco

    public void loadData(File dataDirectory) {
        Thread[] threads = {
                new Thread(() -> loadProductos(dataDirectory)),
                new Thread(() -> loadDepositos(dataDirectory)),
                new Thread(() -> loadTiendas(dataDirectory)),
                new Thread(() -> loadStock(dataDirectory)),
                new Thread(() -> loadUsuario(dataDirectory)),
                new Thread(() -> loadHistorial(dataDirectory))
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
                new Thread(() -> saveDepositos(dataDirectory)),
                new Thread(() -> saveTiendas(dataDirectory)),
                new Thread(() -> saveStock(dataDirectory)),
                new Thread(() -> saveUsuarios(dataDirectory)),
                new Thread(() -> saveHistorial(dataDirectory))
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

    //recorre la lista q devuelve el readElements() y los guarda en el mapa

    private void loadDepositos(File dataDirectory) {
        for (Deposito deposito : TextPersistence.<Deposito>readElements(new File(dataDirectory, FILE_DEPOSITOS)))
            depositos.put(deposito.getId(), deposito);
    }

    private void loadTiendas(File file) {
        for (Tienda tienda : TextPersistence.<Tienda>readElements(new File(file, FILE_TIENDAS)))
            tiendas.put(tienda.getId(), tienda);
    }

    private void loadProductos(File dataDirectory) {
        for (Producto p : TextPersistence.<Producto>readElements(new File(dataDirectory, FILE_PRODUCTOS)))
            productos.put(p.getSku(), p);
    }

    private void loadStock(File file) {
        for (Stock s : TextPersistence.<Stock>readElements(new File(file, FILE_STOCK)))
            stock.put(s.getKey(), s);
    }

    private void loadUsuario(File dataDirectory) {
        for (Usuario usuario : TextPersistence.<Usuario>readElements(new File(dataDirectory, FILE_USUARIOS)))
            usuarios.put(usuario.getDNI(), usuario);
    }

    private void loadHistorial(File file) {
        historial.addAll(TextPersistence.readElements(new File(file, FILE_HISTORIAL)));
    }


    //devuelve los valores del mapa como lista para ser guardados

    private void saveProductos(File dataDirectory) {
        TextPersistence.writeElements(new File(dataDirectory, FILE_PRODUCTOS), new ArrayList<>(tiendas.values()));
    }

    private void saveDepositos(File dataDirectory) {
        TextPersistence.writeElements(new File(dataDirectory, FILE_DEPOSITOS), new ArrayList<>(depositos.values()));
    }


    private void saveTiendas(File dataDirectory) {
        TextPersistence.writeElements(new File(dataDirectory, FILE_TIENDAS), new ArrayList<>(tiendas.values()));
    }

    private void saveStock(File dataDirectory) {
        TextPersistence.writeElements(new File(dataDirectory, FILE_STOCK), new ArrayList<>(stock.values()));
    }

    private void saveUsuarios(File dataDirectory) {
        TextPersistence.writeElements(new File(dataDirectory, FILE_USUARIOS), new ArrayList<>(usuarios.values()));
    }

    private void saveHistorial(File dataDirectory) {
        TextPersistence.writeElements(new File(dataDirectory, FILE_HISTORIAL), historial);
    }


    //getters

    public Producto getProducto(String sku) {
        return productos.get(sku);
    }

    public Deposito getDeposito(String id) {
        return depositos.get(id);
    }

    public void add(Producto producto) {
        productos.put(producto.getSku(), producto);
    }
}

