package juanma.parcial.exceptions;

public class WriteFailedException extends RuntimeException {
    public WriteFailedException(String fileName, Exception e) {
        super("No se encuentra el archivo: " + fileName, e);
    }
}
