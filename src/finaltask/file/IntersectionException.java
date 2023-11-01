package finaltask.file;

public class IntersectionException extends IllegalArgumentException {

    public IntersectionException() {
    }

    public IntersectionException(String s) {
        super(s);
    }

    public IntersectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntersectionException(Throwable cause) {
        super(cause);
    }
}
