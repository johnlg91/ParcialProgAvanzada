package juanma.parcial;

import juanma.parcial.persistencia.BinaryPersistence;
import juanma.parcial.persistencia.TextPersistence;
import juanma.parcial.objetos.Producto;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TestPersistence {

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
        File file  = new File("test/Productos.txt");
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("1A", "ventilador", "", 2.0));
        productos.add(new Producto("TV1A", "televisor", "", 5.0));
        TextPersistence.writeElements(file, productos);
        List<Producto> readProductos = TextPersistence.readElements(file);
        assertEquals(productos.size(), readProductos.size());
        for (int i = 0; i < productos.size(); i++) {
            assertEquals(productos.get(i), readProductos.get(i));
        }

    }
}
