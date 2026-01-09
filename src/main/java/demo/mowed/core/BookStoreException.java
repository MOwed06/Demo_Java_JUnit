package demo.mowed.core;

/*
General Book Application exception for invalid user message data
*/
public class BookStoreException extends RuntimeException {
    public BookStoreException(String message) {
        super(message);
    }
}
