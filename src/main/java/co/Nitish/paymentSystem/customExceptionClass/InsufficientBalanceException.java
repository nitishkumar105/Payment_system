package co.Nitish.paymentSystem.customExceptionClass;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message) {
        super(message);
    }

}
