package co.Nitish.paymentSystem.customExceptionClass;

public class AccountNotFoundException extends  RuntimeException{
    public AccountNotFoundException(String message) {
        super(message);
    }

}
