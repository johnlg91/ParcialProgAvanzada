package juanma.parcial.exceptions;

public class ReadFailedException extends RuntimeException {
    public ReadFailedException(String fileName, Exception e) {
        super("No se pudo leer del archivo: " + fileName, e);
    }
}
