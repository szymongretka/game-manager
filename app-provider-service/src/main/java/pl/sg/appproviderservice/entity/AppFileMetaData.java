package pl.sg.appproviderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppFileMetaData {

    private String id;
    private String name;
    private String username;

    public AppFileMetaData(String name, String username) {
        this.name = name;
        this.username = username;
    }

}
