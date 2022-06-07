package uz.pdp.online.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.management.entity.Task;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findByEmployeeId(UUID employee_id);
}
