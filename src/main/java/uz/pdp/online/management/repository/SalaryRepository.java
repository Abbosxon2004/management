package uz.pdp.online.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.online.management.entity.Salary;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RepositoryRestResource(path = "salary")
public interface SalaryRepository extends JpaRepository<Salary,Integer> {

    List<Salary> findByEmployeesId(UUID employees_id);

    List<Salary> findByDateEquals(Timestamp date);
}
