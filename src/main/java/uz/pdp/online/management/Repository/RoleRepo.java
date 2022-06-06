package uz.pdp.online.management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.management.Entity.Enums.RoleName;
import uz.pdp.online.management.Entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);
}
