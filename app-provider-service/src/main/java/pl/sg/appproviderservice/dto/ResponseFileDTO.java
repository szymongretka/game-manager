package pl.sg.appproviderservice.dto;

import lombok.*;

/**
 * Contains name, url, type and size
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseFileDTO {
    private Long id;
    private String name;
    private String url;
    private String type;
    private long size;
}
