package pl.sg.apigateway.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponseDTO implements Serializable {
    private String emailAddress;
    private Long id;
    private String password;
    private Integer loginAttempt;
    private Character status;
    private Date createdDate;
    private Date lastModifiedDate;
    private List<String> roles = new ArrayList<>();
}