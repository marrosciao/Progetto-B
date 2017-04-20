package exceptions;

public class LastPhaseException extends Exception {

    /**
     * Creates a new instance of <code>LastPhaseException</code> without detail
     * message.
     */
    public LastPhaseException() {
    }

    /**
     * Constructs an instance of <code>LastPhaseException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LastPhaseException(String msg) {
        super(msg);
    }
}
