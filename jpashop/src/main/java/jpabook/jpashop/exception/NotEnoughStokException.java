package jpabook.jpashop.exception;

public class NotEnoughStokException extends RuntimeException {

    public NotEnoughStokException() {
    }

    public NotEnoughStokException(String message) {
        super(message);
    }

    public NotEnoughStokException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStokException(Throwable cause) {
        super(cause);
    }
}
