package exceptions;

public class PendingOperationsException extends Exception {

    /**
     * Creates a new instance of <code>PendingOperationsException</code> without
     * detail message.
     */
    public PendingOperationsException() {
    }

    /**
     * Constructs an instance of <code>PendingOperationsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PendingOperationsException(String msg) {
        super(msg);
    }
}
