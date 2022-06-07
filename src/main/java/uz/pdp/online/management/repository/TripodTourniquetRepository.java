package uz.pdp.online.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.management.entity.TripodTourniquet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TripodTourniquetRepository extends JpaRepository<TripodTourniquet,Integer> {
    Optional<TripodTourniquet> findByEmployeesIdAndExitTimeIsNull(UUID employees_id);

    List<TripodTourniquet> findByEmployeesId(UUID employees_id);

}
