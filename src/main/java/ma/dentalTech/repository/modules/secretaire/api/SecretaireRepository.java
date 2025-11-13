package ma.dentalTech.repository.modules.secretaire.api;

import ma.dentalTech.entities.patient.Patient;
import ma.dentalTech.entities.medecin.Medecin;
import ma.dentalTech.entities.rendezvous.RendezVous;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SecretaireRepository extends CrudRepository<Patient, Long> {

    // ---- Gestion Patients ----
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByTelephone(String telephone);
    List<Patient> searchByNomPrenom(String keyword); // LIKE %keyword%
    boolean existsById(Long id);
    long count();
    List<Patient> findPage(int limit, int offset);

    // ---- Gestion Médecins ----
    List<Medecin> findAllMedecins();
    Optional<Medecin> findMedecinById(Long id);
    List<Medecin> searchMedecinsByNomPrenom(String keyword);
    List<Medecin> findMedecinsBySpecialite(String specialite);

    // ---- Gestion Rendez-vous ----
    void createRendezVous(RendezVous rdv);
    void updateRendezVous(RendezVous rdv);
    void deleteRendezVous(Long id);
    Optional<RendezVous> findRendezVousById(Long id);
    List<RendezVous> findRendezVousByPatient(Long patientId);
    List<RendezVous> findRendezVousByMedecin(Long medecinId);
    List<RendezVous> findRendezVousByDate(String date);

    // ---- Liaison Patient ↔ Médecin (optionnel) ----
    void assignMedecinToPatient(Long patientId, Long medecinId);
    void removeMedecinFromPatient(Long patientId, Long medecinId);
    List<Medecin> getMedecinsOfPatient(Long patientId);
    List<Patient> getPatientsOfMedecin(Long medecinId);
}
