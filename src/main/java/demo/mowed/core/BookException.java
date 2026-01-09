package demo.mowed.core;

/*
General Book Application exception for invalid user message data
*/
public class BookException extends RuntimeException {
    public BookException(String message) {
        super(message);
    }
}
