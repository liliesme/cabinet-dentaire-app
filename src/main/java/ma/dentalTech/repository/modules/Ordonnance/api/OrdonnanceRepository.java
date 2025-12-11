package ma.dentalTech.repository.modules.Ordonnance.api;


import ma.dentalTech.entities.pat.Ordonnance;
import ma.dentalTech.repository.common.CrudRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface OrdonnanceRepository extends CrudRepository<Ordonnance, Long> {


    List<Ordonnance> findByPatientId(Long patientId);


    List<Ordonnance> findByUtilisateurId(Long utilisateurId);


    List<Ordonnance> findByDateBetween(LocalDateTime start, LocalDateTime end);
}