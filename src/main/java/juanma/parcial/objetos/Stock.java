package juanma.parcial.objetos;

public class Stock {

    private String productoID;
    private String ubicacionID;
    private int cantidad;

    public Stock(String productoID, String ubicacionID, int cantidad) {
        this.productoID = productoID;
        this.ubicacionID = ubicacionID;
        this.cantidad = cantidad;
    }

    public String getKey() {
        return ubicacionID + ':' + productoID;
    }

    public String getProductoID() {
        return productoID;
    }

    public String getUbicacionID() {
        return ubicacionID;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
