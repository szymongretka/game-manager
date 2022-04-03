package pl.sg.appproviderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.stereotype.Component;

@Component
public class GridFsConfig {

    @Bean
    public ReactiveGridFsTemplate reactiveGridFsTemplate(ReactiveMongoDatabaseFactory reactiveMongoDbFactory,
                                                         MappingMongoConverter mappingMongoConverter) throws Exception {
        return new ReactiveGridFsTemplate(reactiveMongoDbFactory, mappingMongoConverter);
    }

}
