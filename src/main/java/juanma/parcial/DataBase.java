package juanma.parcial;


import juanma.parcial.objetos.Operacion;

import java.util.List;
import java.util.TreeMap;

public class DataBase {

    private TreeMap<String, Integer> stock; // String = ID de ubicacion + sku del producto, Integer= cantidad
    private List<Operacion> historial;
    private Map<String, Producto> productos;


    //constructor privado
    private DataBase() {
    }

    //para cargar del disco
    public void loadData(String dataDirectory) {
        for (Producto p: TextPersistence.readElements(new File(dataDirectory, "productos.txt"))) {
            productos.put(p)
        }
    }

    private static DataBase INSTANCE = new DataBase();

    public DataBase getInstance() {
        return INSTANCE;
    }

    
}
