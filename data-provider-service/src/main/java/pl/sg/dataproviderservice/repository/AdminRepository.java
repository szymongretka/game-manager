package pl.sg.dataproviderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sg.dataproviderservice.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
