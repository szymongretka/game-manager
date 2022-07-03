package pl.sg.appproviderservice.exception.advice;

public class InvalidExtensionException extends RuntimeException {
    public InvalidExtensionException() {
        super("Invalid extension!");
    }
}