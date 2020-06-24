package juanma.parcial.persistencia;


import juanma.parcial.objetos.*;

import java.io.File;
import java.util.*;

public class DataBase {

    //Mapas y listas donde se guardan los datos
    //los mapas se guardan con un ID del objeto como key
    private final List<Operacion> historial = new ArrayList<>();
    private final SortedMap<String, Producto> productos = new TreeMap<>();
    private final SortedMap<String, Deposito> depositos = new TreeMap<>();
    private final SortedMap<String, Tienda> tiendas = new TreeMap<>();
    private final SortedMap<String, Stock> stock = new TreeMap<>();
    private final SortedMap<String, Usuario> usuarios = new TreeMap<>();


    //Nombres de los archivos

    private static final String FILE_DEPOSITOS = "depositos.txt";
    private static final String FILE_PRODUCTOS = "productos.txt";
    private static final String FILE_STOCK = "stock.txt";
    private static final String FILE_USUARIOS = "usuarios.txt";
    private static final String FILE_TIENDAS = "tiendas.txt";
    private static final String FILE_HISTORIAL = "historial.bin";

    private static final DataBase INSTANCE = new DataBase(); //Instancia

    //Constructor privado del singleton
    private DataBase() {
    }

    //Inicializa el objeto singleton
    public static DataBase getInstance() {
        return INSTANCE;
    }

    //Para cargar del disco
    //crea un thread por cada loader y los junta al final, el join se queda esperando a que terminen todos los threads juntos
    public void loadData(File dataDirectory) {
        //Array de threads
        Thread[] threads = {
                new Thread(() -> loadProductos(dataDirectory)),
                new Thread(() -> loadDepositos(dataDirectory)),
                new Thread(() -> loadTiendas(dataDirectory)),
                new Thread(() -> loadStock(dataDirectory)),
                new Thread(() -> loadUsuario(dataDirectory)),
                new Thread(() -> loadHistorial(dataDirectory))
        };
        //Se empezan
        for (Thread t : threads) t.start();
        //Join
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignore) {
            }
        }
    }


    //Para grabar al disco, con threads como loadData

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


    //Recorre la lista q devuelve el readElements() en las clases de persistencia cuando leen los archivos
    //y los guarda en el mapa de la data base

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
        historial.addAll(BinaryPersistence.readElements(new File(file, FILE_HISTORIAL)));
    }


    //Devuelve los valores del mapa como lista para ser guardados en los persisitance

    private void saveProductos(File dataDirectory) {
        TextPersistence.writeElements(new File(dataDirectory, FILE_PRODUCTOS), new ArrayList<>(productos.values()));
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
        BinaryPersistence.writeElements(new File(dataDirectory, FILE_HISTORIAL), historial);
    }


    //Getters reciven un ID para buscar el elemento en el mapa y devolverlo

    public Producto getProducto(String sku) {
        return productos.get(sku);
    }

    public Deposito getDeposito(String id) {
        return depositos.get(id);
    }

    public Tienda getTienda(String idTienda) {
        return tiendas.get(idTienda);
    }

    public Usuario getUsuario(String dni) {
        return usuarios.get(dni);
    }

    //Adders

    public void addProducto(Producto producto) {
        productos.put(producto.getSku(), producto);
    }

    public void addHistorial(Operacion operacion) {
        historial.add(operacion);
    }

    public void addUsuario(Usuario usuario) {
        usuarios.put(usuario.getDNI(), usuario);
    }

    public Stock getStock(Ubicacion ubicacion, Producto producto) {
        String id = ubicacion.getId();
        String sku = producto.getSku();
        return stock.computeIfAbsent(id + ':' + sku, k -> new Stock(sku, id, 0));
    }

    //Getters pero devuelven la coleccion entera en vez de solo un elemento
    //se usan en casos como cuando se quiere listar todos los debidos elementos para q elija

    public List<Operacion> getOperaciones() {
        return historial;
    }

    public Collection<Producto> getProductos() {
        return productos.values();
    }

    public Collection<Deposito> getDepositos() {
        return depositos.values();
    }

    public Collection<Tienda> getTiendas() {
        return tiendas.values();
    }
}

