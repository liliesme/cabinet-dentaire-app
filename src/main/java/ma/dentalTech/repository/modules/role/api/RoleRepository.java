package ma.dentalTech.repository.modules.role.api;

import ma.dentalTech.entities.Role;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    // Méthode de recherche spécifique
    Optional<Role> findByNom(String nom);

    // Vérifier l'existence d'un rôle par son nom
    boolean existsByNom(String nom);
}