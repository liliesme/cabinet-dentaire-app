package ma.dentalTech.repository.modules.rdv.api;

import ma.dentalTech.entities.RDV;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RdvRepository extends CrudRepository<RDV, Long> {

    // Méthodes de recherche spécifiques
    List<RDV> findByPatientId(Long patientId);
    List<RDV> findByMedecinId(Long medecinId); // Supposons un champ medecin_id
    List<RDV> findByDateBetween(LocalDateTime start, LocalDateTime end);

    List<RDV> findTodayRdv();

}