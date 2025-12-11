package ma.dentalTech.repository.modules.secretaire.api;

import ma.dentalTech.entities.pat.Patient;
import ma.dentalTech.entities.pat.medecin;
import ma.dentalTech.entities.pat.RDV;
import ma.dentalTech.entities.pat.Secretaire; 
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface SecretaireRepository extends CrudRepository<Secretaire, Long> {

    
    Optional<Secretaire> findByEmail(String email);
    Optional<Secretaire> findByTelephone(String telephone);
    List<Secretaire> searchByNomPrenom(String keyword); 
    boolean existsById(Long id);
    long count();
    List<Secretaire> findPage(int limit, int offset);

    
    List<medecin> findAllMedecins();
    Optional<medecin> findMedecinById(Long id);
    List<medecin> searchMedecinsByNomPrenom(String keyword);
    List<medecin> findMedecinsBySpecialite(String specialite);

    
    void createRendezVous(RDV rdv);
    void updateRendezVous(RDV rdv);
    void deleteRendezVous(Long id);
    Optional<RDV> findRendezVousById(Long id);
    List<RDV> findRendezVousByPatient(Long patientId);
    List<RDV> findRendezVousByMedecin(Long medecinId);
    List<RDV> findRendezVousByDate(String date);

    
    Optional<Patient> findPatientByEmail(String email); 
    Optional<Patient> findPatientByTelephone(String telephone); 
    List<Patient> searchPatientsByNomPrenom(String keyword); 
    List<Patient> findAllPatients(); 

    
    void assignMedecinToPatient(Long patientId, Long medecinId);
    void removeMedecinFromPatient(Long patientId, Long medecinId);
    List<medecin> getMedecinsOfPatient(Long patientId);
    List<Patient> getPatientsOfMedecin(Long medecinId);
}