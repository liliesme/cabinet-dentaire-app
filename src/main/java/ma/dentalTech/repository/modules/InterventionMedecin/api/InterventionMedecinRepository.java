package ma.dentalTech.repository.modules.InterventionMedecin.api;


import ma.dentalTech.entities.pat.InterventionMedecin;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface InterventionMedecinRepository extends CrudRepository<InterventionMedecin, Long> {


    List<InterventionMedecin> findByPatientId(Long patientId);


    List<InterventionMedecin> findByUtilisateurId(Long utilisateurId);


    List<InterventionMedecin> findByDateBetween(LocalDateTime start, LocalDateTime end);
}