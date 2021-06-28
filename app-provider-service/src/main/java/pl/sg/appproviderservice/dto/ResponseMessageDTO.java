package pl.sg.appproviderservice.dto;

import lombok.*;

/**
 * For notification/information message
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMessageDTO {
    private String message;
}
