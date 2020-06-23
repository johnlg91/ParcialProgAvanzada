package juanma.parcial;

import juanma.parcial.persistencia.BinaryPersistence;
import juanma.parcial.persistencia.TextPersistence;
import juanma.parcial.objetos.Producto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TestPersistence {

    @Test
    public void testBinaryPersistence() {
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("1A", "ventilador", "", 2.0));
        BinaryPersistence.writeElements(productos, "Productos.bytes");
        List<Producto> readProductos = BinaryPersistence.readElements("Productos.bytes");
        assertEquals(productos.size(), readProductos.size());
        for (int i = 0; i < productos.size(); i++) {
            assertEquals(productos.get(i), readProductos.get(i));
        }

    }

    @Test
    public void testTextPersistence() {
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("1A", "ventilador", "", 2.0));
        productos.add(new Producto("TV1A", "televisor", "", 5.0));
        TextPersistence.writeElements(productos, "Productos.txt");
        List<Producto> readProductos = TextPersistence.readElements("Productos.txt");
        assertEquals(productos.size(), readProductos.size());
        for (int i = 0; i < productos.size(); i++) {
            assertEquals(productos.get(i), readProductos.get(i));
        }

    }
}
