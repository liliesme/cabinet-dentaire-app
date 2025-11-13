package ma.dentalTech.repository.modules.staff.api;

import ma.dentalTech.entities.Staff;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface StaffRepository extends CrudRepository<Staff, Long> {

    // Méthodes de recherche spécifiques
    Optional<Staff> findByCin(String cin);
    List<Staff> findByRole(String role); // Ex: "Médecin", "Secrétaire", "Assistant"
    Optional<Staff> findByTelephone(String telephone);
}