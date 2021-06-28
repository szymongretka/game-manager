package pl.sg.appproviderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sg.appproviderservice.entity.ApplicationFile;

public interface ApplicationFileRepository extends JpaRepository<ApplicationFile, Long> {
}
