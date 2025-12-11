package ma.dentalTech.repository.modules.facture.api;

import ma.dentalTech.entities.pat.Facture;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FactureRepository extends CrudRepository<Facture, Long> {


    List<Facture> findByPatientId(Long patientId);
    List<Facture> findByEstPayee(boolean estPayee);
    List<Facture> findByDateBetween(LocalDateTime start, LocalDateTime end);
    Optional<Facture> findByNumeroFacture(String numeroFacture);

}