package pl.sg.appproviderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("ApplicationFiles")
public class ApplicationFile {

    @Id
    private String id;
    private String thumbnailId;
    private String name;

    public ApplicationFile(String thumbnailId, String name) {
        this.thumbnailId = thumbnailId;
        this.name = name;
    }
}
