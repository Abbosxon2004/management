package uz.pdp.online.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.management.entity.TaskStatus;
import uz.pdp.online.management.entity.TaskStatusNames;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
    TaskStatus findByTaskStatus(TaskStatusNames taskStatus);
}
