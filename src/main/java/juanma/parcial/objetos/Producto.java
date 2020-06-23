package juanma.parcial.objetos;

import java.io.Serializable;

public class Producto implements Serializable{

    private String sku; //codigo ID
    private String nombre;
    private String descripcion;
    private double precio;

    public Producto() {
        this("", "", "", 0);
    }

    public Producto(String sku, String nombre, String descripcion, double precio) {
        this.sku = sku;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "sku='" + sku + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;

        Producto producto = (Producto) o;

        return sku.equals(producto.sku);
    }

    @Override
    public int hashCode() {
        return sku.hashCode();
    }

    public String getSku() {
        return sku;
    }
}
