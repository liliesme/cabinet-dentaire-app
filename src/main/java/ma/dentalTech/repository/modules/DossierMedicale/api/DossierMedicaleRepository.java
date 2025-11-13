package ma.dentalTech.repository.modules.DossierMedicale.api;

import ma.dentalTech.entities.DossierMedical;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.Optional;

public interface DossierMedicalRepository extends CrudRepository<DossierMedical, Long> {


    Optional<DossierMedical> findByPatientId(Long patientId);
}