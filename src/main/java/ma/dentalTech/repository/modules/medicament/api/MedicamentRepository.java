package ma.dentalTech.repository.modules.medicament.api;

import ma.dentalTech.entities.pat.Medicament;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface MedicamentRepository extends CrudRepository<Medicament, Long> {

    Optional<Medicament> findByNom(String nom);

    
    List<Medicament> searchByNom(String keyword);
}