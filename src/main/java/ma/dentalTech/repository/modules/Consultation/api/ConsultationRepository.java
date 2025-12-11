package ma.dentalTech.repository.modules.Consultation.api;


import ma.dentalTech.entities.pat.Consultation;
import ma.dentalTech.repository.common.CrudRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends CrudRepository<Consultation, Long> {


    List<Consultation> findByPatientId(Long patientId);


    List<Consultation> findByDateBetween(LocalDateTime start, LocalDateTime end);
}