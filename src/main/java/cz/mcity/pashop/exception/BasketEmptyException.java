package cz.mcity.pashop.exception;

public class BasketEmptyException extends RuntimeException {
    public BasketEmptyException(String message) {
            super(message);
        }
    }