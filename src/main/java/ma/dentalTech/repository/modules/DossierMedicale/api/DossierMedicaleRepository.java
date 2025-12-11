package ma.dentalTech.repository.modules.DossierMedicale.api;

import ma.dentalTech.entities.pat.DossierMedicale;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.Optional;

public interface DossierMedicaleRepository extends CrudRepository<DossierMedicale, Long> {


    Optional<DossierMedicale> findByPatientId(Long patientId);
}