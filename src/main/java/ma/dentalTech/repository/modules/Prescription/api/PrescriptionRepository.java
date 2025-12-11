package ma.dentalTech.repository.modules.Prescription.api;


import ma.dentalTech.entities.pat.Prescription;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {


    List<Prescription> findByPatientId(Long patientId);


    List<Prescription> findByUtilisateurId(Long utilisateurId);


    List<Prescription> findByDateBetween(LocalDateTime start, LocalDateTime end);
}