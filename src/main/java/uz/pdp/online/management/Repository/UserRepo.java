package uz.pdp.online.management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.management.Entity.Enums.RoleName;
import uz.pdp.online.management.Entity.Role;
import uz.pdp.online.management.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User>findByEmailAndEmailCode(String email, String emailCode);

    Optional<User> findByEmail(String email);

    List<Object> findAllByRoles(RoleName roleEnum);

//    User findByRoles(Set<Role> roles);
//    boolean existsByRoles(Set<Role> roles);

}
