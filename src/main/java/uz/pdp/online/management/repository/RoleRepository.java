package uz.pdp.online.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.online.management.entity.RoleNames;
import uz.pdp.online.management.entity.Roles;

@RepositoryRestResource(path = "role")
public interface RoleRepository extends JpaRepository<Roles,Integer> {
    Roles findByRoleNames(RoleNames roleNames);
}
