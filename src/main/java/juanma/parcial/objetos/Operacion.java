package juanma.parcial.objetos;

import java.io.Serializable;
import java.time.LocalDate;

public class Operacion implements Serializable {
    private final Usuario usuario;
    private final LocalDate fecha;
    private final Ubicacion origen;
    private final Ubicacion destino;
    private final Producto producto;
    private final int cantidad;

    private final TipoOperacion tipo;

    public Operacion(Usuario usuario, LocalDate fecha, Ubicacion origen, Ubicacion destino, Producto producto, int cantidad) {
        assert usuario != null && fecha != null && origen != null && destino != null && producto != null;
        this.usuario = usuario;
        this.fecha = fecha;
        this.origen = origen;
        this.destino = destino;
        this.producto = producto;
        this.cantidad = cantidad;
        this.tipo = origen instanceof Deposito && destino instanceof Deposito ? TipoOperacion.TRANSFERENCIA_DEPOSITOS
                : TipoOperacion.TRANSFERENCIA_TIENDA;
    }

    public Operacion(Usuario usuario, LocalDate fecha, Ubicacion ubicacion, Producto producto, int cantidad) {
        assert usuario != null && fecha != null && ubicacion != null && producto != null;
        this.usuario = usuario;
        this.fecha = fecha;
        this.origen = ubicacion;
        this.destino = null;
        this.producto = producto;
        this.cantidad = cantidad;
        this.tipo = ubicacion instanceof Tienda ? TipoOperacion.VENTA : TipoOperacion.COMPRA;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Ubicacion getOrigen() {
        return origen;
    }

    public Ubicacion getDestino() {
        return destino;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }
    public TipoOperacion getTipo() {
        return tipo;
    }

}
