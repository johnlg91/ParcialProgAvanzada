package juanma.parcial.exceptions;

public class DepositoNotFoundException extends RuntimeException {
    public DepositoNotFoundException(String depositoId) {
        super("Depósito inexistente:: " + depositoId);
    }
}
