package pl.sg.apigateway.constant;

public class ErrorMessageConstants {
    public interface TokenInvalid {
        String DEVELOPER_MESSAGE= "Request not authorized.";
        String MESSAGE ="Unmatched JWT token.";
    }
}
