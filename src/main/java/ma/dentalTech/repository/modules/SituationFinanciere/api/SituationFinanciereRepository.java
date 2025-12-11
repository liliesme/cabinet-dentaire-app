package ma.dentalTech.repository.modules.SituationFinanciere.api;


import ma.dentalTech.entities.pat.SituationFinanciere;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.Optional;

public interface SituationFinanciereRepository extends CrudRepository<SituationFinanciere, Long> {


    Optional<SituationFinanciere> findByPatientId(Long patientId);
}