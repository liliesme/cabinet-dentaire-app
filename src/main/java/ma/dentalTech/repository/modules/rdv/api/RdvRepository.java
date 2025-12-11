package ma.dentalTech.repository.modules.rdv.api;

import ma.dentalTech.entities.pat.RDV;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RdvRepository extends CrudRepository<RDV, Long> {

    
    List<RDV> findByPatientId(Long patientId);
    List<RDV> findByMedecinId(Long medecinId); 
    List<RDV> findByDateBetween(LocalDateTime start, LocalDateTime end);

    List<RDV> findTodayRdv();

}