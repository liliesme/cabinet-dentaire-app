package ma.dentalTech.repository.modules.patient.api;


import ma.dentalTech.entities.pat.Patient;
import ma.dentalTech.repository.common.CrudRepository;
import ma.dentalTech.entities.pat.Antecedent; 

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Long> {

    Optional<Patient> findById(Long id);

    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByTelephone(String telephone);
    List<Patient> searchByNomPrenom(String keyword);
    boolean existsById(Long id);
    long count();
    List<Patient> findPage(int limit, int offset);

    
    void addAntecedentToPatient(Long patientId, Long antecedentId);
    void removeAntecedentFromPatient(Long patientId, Long antecedentId);
    void removeAllAntecedentsFromPatient(Long patientId);
    List<Antecedent> getAntecedentsOfPatient(Long patientId); 
    List<Patient> getPatientsByAntecedent(Long antecedentId);

}