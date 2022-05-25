package pl.sg.appproviderservice.exception.advice;

public class AppNotFoundException extends RuntimeException {
    public AppNotFoundException() {
        super("App not found or you don't have privileges!");
    }
}
