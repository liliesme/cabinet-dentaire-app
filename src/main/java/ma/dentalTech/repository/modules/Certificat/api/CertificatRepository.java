package ma.dentalTech.repository.modules.Certificat.api;


import ma.dentalTech.entities.pat.Certificat;
import ma.dentalTech.repository.common.CrudRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface CertificatRepository extends CrudRepository<Certificat, Long> {


    List<Certificat> findByPatientId(Long patientId);


    List<Certificat> findByUtilisateurId(Long utilisateurId);


    List<Certificat> findByDateBetween(LocalDateTime start, LocalDateTime end);
}