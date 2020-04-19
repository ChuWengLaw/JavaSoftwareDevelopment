package Main;

/* A trivial exception class for the Movie List program. */
@SuppressWarnings("serial")
public class BillboardException extends Exception {

    public BillboardException() {
        super();
    }

    public BillboardException(String message) {
        super(message);
    }
}
