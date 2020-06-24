package juanma.parcial;

import juanma.parcial.objetos.Deposito;
import juanma.parcial.objetos.Provincia;
import juanma.parcial.persistencia.BinaryPersistence;
import juanma.parcial.persistencia.TextPersistence;
import juanma.parcial.objetos.Producto;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static juanma.parcial.objetos.Provincia.*;
import static juanma.parcial.objetos.Provincia.CABA;
import static org.junit.Assert.assertEquals;


public class TestPersistence {
    static {
        //noinspection ResultOfMethodCallIgnored
        new File("test").mkdir();
    }

    @Test
    public void testBinaryPersistence() {
        File file  = new File("test/Productos.bin");
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("1A", "ventilador", "", 2.0));
        BinaryPersistence.writeElements(file, productos);
        List<Producto> readProductos = BinaryPersistence.readElements(file);
        assertEquals(productos.size(), readProductos.size());
        for (int i = 0; i < productos.size(); i++) {
            assertEquals(productos.get(i), readProductos.get(i));
        }

    }

    @Test
    public void testTextPersistence() {
        ArrayList<Producto> productos = createProductos();
        List<Producto> readProductos = TextPersistence.readElements(new File("test/Productos.txt"));
        assertEquals(productos.size(), readProductos.size());
        for (int i = 0; i < productos.size(); i++) {
            assertEquals(productos.get(i), readProductos.get(i));
        }

    }
    @Test
    public void testDepositoPersistence() {
        ArrayList<Deposito> depositos = createDepositos();
        List<Deposito> readDepositos = TextPersistence.readElements(new File("test/Depositos.txt"));
        assertEquals(depositos.size(), readDepositos.size());
        for (int i = 0; i < depositos.size(); i++) {
            assertEquals(depositos.get(i), readDepositos.get(i));
        }

    }

    private ArrayList<Producto> createProductos() {
        File file  = new File("test/Productos.txt");
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("1A", "ventilador", "", 2.0));
        productos.add(new Producto("TV1A", "televisor", "", 5.0));
        TextPersistence.writeElements(file, productos);
        return productos;
    }
    private ArrayList<Deposito> createDepositos() {
        File file  = new File("test/Depositos.txt");
        ArrayList<Deposito> depositos = new ArrayList<>();
        depositos.add(new Deposito("A", "Deposito Pilar", BUENOS_AIRES));
        depositos.add(new Deposito("B", "Deposito Belgrano", CABA));
        TextPersistence.writeElements(file, depositos);
        return depositos;
    }

    @Test
    public void testDataBase() {
        createProductos();
        DataBase db = DataBase.getInstance();
        File directory = new File("test");
        db.loadData(directory);
        assertEquals("1A", db.getProducto("1A").getSku());
        assertEquals(CABA, db.getDeposito("B").getProvincia());

        
        db.addProducto(new Producto("XX", "XBOX", "La ultima Xbox", 1000));
        db.saveData(directory);
        db.loadData(directory);

        assertEquals("XBOX", db.getProducto("XX").getNombre());
    }
}
