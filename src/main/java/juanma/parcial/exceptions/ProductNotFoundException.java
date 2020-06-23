package juanma.parcial.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super("Producto inexistente: " + productId);
    }
}
