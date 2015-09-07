package chbachman.api.nbt;

public class SerializationException extends RuntimeException {

    public SerializationException() {
        super();
    }

    public SerializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = -8183140670675549506L;

}
