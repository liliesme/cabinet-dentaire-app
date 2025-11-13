package ma.dentalTech.repository.modules.secretaire.impl.memoryBase;

import ma.dentalTech.entities.patient.Patient;
import ma.dentalTech.entities.medecin.Medecin;
import ma.dentalTech.entities.rendezvous.RendezVous;
import ma.dentalTech.repository.modules.secretaire.api.SecretaireRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SecretaireRepositoryImpl implements SecretaireRepository {

    private final List<Patient> patients = new ArrayList<>();
    private final List<Medecin> medecins = new ArrayList<>();
    private final List<RendezVous> rendezVous = new ArrayList<>();

    public SecretaireRepositoryImpl() {
        // --- Patients exemple ---
        patients.add(Patient.builder().id(1L).nom("Amal").prenom("Z.").build());
        patients.add(Patient.builder().id(2L).nom("Hassan").prenom("B.").build());

        // --- Médecins exemple ---
        medecins.add(Medecin.builder().id(1L).nom("Driss").prenom("A.").specialite("Orthodontie").build());
        medecins.add(Medecin.builder().id(2L).nom("Leila").prenom("B.").specialite("Parodontologie").build());

        // --- Rendez-vous exemple ---
        rendezVous.add(RendezVous.builder().id(1L).patientId(1L).medecinId(1L).dateTime(LocalDateTime.now().plusDays(1)).build());
        rendezVous.add(RendezVous.builder().id(2L).patientId(2L).medecinId(2L).dateTime(LocalDateTime.now().plusDays(2)).build());
    }

    // -------- CRUD Patients --------
    @Override
    public List<Patient> findAll() { return List.copyOf(patients); }

    @Override
    public Optional<Patient> findByEmail(String email) { return patients.stream().filter(p -> email.equals(p.getEmail())).findFirst(); }

    @Override
    public Optional<Patient> findByTelephone(String telephone) { return patients.stream().filter(p -> telephone.equals(p.getTelephone())).findFirst(); }

    @Override
    public List<Patient> searchByNomPrenom(String keyword) {
        String k = keyword.toLowerCase();
        List<Patient> out = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getNom().toLowerCase().contains(k) || p.getPrenom().toLowerCase().contains(k)) out.add(p);
        }
        return out;
    }

    @Override
    public boolean existsById(Long id) { return patients.stream().anyMatch(p -> p.getId().equals(id)); }

    @Override
    public long count() { return patients.size(); }

    @Override
    public List<Patient> findPage(int limit, int offset) {
        int start = Math.min(offset, patients.size());
        int end = Math.min(offset + limit, patients.size());
        return patients.subList(start, end);
    }

    @Override
    public void create(Patient patient) { patients.add(patient); }

    @Override
    public void update(Patient patient) {
        patients.removeIf(p -> p.getId().equals(patient.getId()));
        patients.add(patient);
    }

    @Override
    public void delete(Patient patient) { patients.removeIf(p -> p.getId().equals(patient.getId())); }

    @Override
    public void deleteById(Long id) { patients.removeIf(p -> p.getId().equals(id)); }

    // -------- CRUD Médecins --------
    @Override
    public List<Medecin> findAllMedecins() { return List.copyOf(medecins); }

    @Override
    public Optional<Medecin> findMedecinById(Long id) { return medecins.stream().filter(m -> m.getId().equals(id)).findFirst(); }

    @Override
    public List<Medecin> searchMedecinsByNomPrenom(String keyword) {
        String k = keyword.toLowerCase();
        List<Medecin> out = new ArrayList<>();
        for (Medecin m : medecins) {
            if (m.getNom().toLowerCase().contains(k) || m.getPrenom().toLowerCase().contains(k)) out.add(m);
        }
        return out;
    }

    @Override
    public List<Medecin> findMedecinsBySpecialite(String specialite) {
        String s = specialite.toLowerCase();
        List<Medecin> out = new ArrayList<>();
        for (Medecin m : medecins) {
            if (m.getSpecialite().toLowerCase().contains(s)) out.add(m);
        }
        return out;
    }

    // -------- CRUD Rendez-vous --------
    @Override
    public void createRendezVous(RendezVous rdv) { rendezVous.add(rdv); }

    @Override
    public void updateRendezVous(RendezVous rdv) {
        rendezVous.removeIf(r -> r.getId().equals(rdv.getId()));
        rendezVous.add(rdv);
    }

    @Override
    public void deleteRendezVous(Long id) { rendezVous.removeIf(r -> r.getId().equals(id)); }

    @Override
    public Optional<RendezVous> findRendezVousById(Long id) {
        return rendezVous.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    @Override
    public List<RendezVous> findRendezVousByPatient(Long patientId) {
        List<RendezVous> out = new ArrayList<>();
        for (RendezVous r : rendezVous) if (r.getPatientId().equals(patientId)) out.add(r);
        return out;
    }

    @Override
    public List<RendezVous> findRendezVousByMedecin(Long medecinId) {
        List<RendezVous> out = new ArrayList<>();
        for (RendezVous r : rendezVous) if (r.getMedecinId().equals(medecinId)) out.add(r);
        return out;
    }

    @Override
    public List<RendezVous> findRendezVousByDate(String date) {
        List<RendezVous> out = new ArrayList<>();
        for (RendezVous r : rendezVous) if (r.getDateTime().toLocalDate().toString().equals(date)) out.add(r);
        return out;
    }

    // -------- Liaison Patient ↔ Médecin --------
    @Override
    public void assignMedecinToPatient(Long patientId, Long medecinId) {
        Patient p = findAll().stream().filter(pa -> pa.getId().equals(patientId)).findFirst().orElse(null);
        Medecin m = findAllMedecins().stream().filter(me -> me.getId().equals(medecinId)).findFirst().orElse(null);
        if (p != null && m != null) p.getMedecins().add(m);
    }

    @Override
    public void removeMedecinFromPatient(Long patientId, Long medecinId) {
        Patient p = findAll().stream().filter(pa -> pa.getId().equals(patientId)).findFirst().orElse(null);
        if (p != null) p.getMedecins().removeIf(m -> m.getId().equals(medecinId));
    }

    @Override
    public List<Medecin> getMedecinsOfPatient(Long patientId) {
        Patient p = findAll().stream().filter(pa -> pa.getId().equals(patientId)).findFirst().orElse(null);
        return p != null ? p.getMedecins() : List.of();
    }

    @Override
    public List<Patient> getPatientsOfMedecin(Long medecinId) {
        List<Patient> out = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getMedecins().stream().anyMatch(m -> m.getId().equals(medecinId))) out.add(p);
        }
        return out;
    }
}
