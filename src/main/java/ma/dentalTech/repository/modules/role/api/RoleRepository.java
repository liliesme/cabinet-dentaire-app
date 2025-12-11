package ma.dentalTech.repository.modules.role.api;

import ma.dentalTech.entities.pat.Role;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    
    Optional<Role> findByNom(String nom);

    
    boolean existsByNom(String nom);
}