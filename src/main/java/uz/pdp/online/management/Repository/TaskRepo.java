package uz.pdp.online.management.Repository;

import uz.pdp.online.management.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task,Integer> {
//    List<Task> findAllByCompletedAtBetweenAndEmployee_Email(Timestamp fromDate, Timestamp toDate, String email);
}
