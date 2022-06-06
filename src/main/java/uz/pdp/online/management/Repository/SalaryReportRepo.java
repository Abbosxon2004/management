package uz.pdp.online.management.Repository;

import uz.pdp.online.management.Entity.SalaryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryReportRepo extends JpaRepository<SalaryReport,Integer> {
}
