package ma.dentalTech.repository.modules.Prescription.impl.memoryBase;


import ma.dentalTech.entities.Prescription;
import ma.dentalTech.repository.modules.Prescription.api.PrescriptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    private final List<Prescription> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public PrescriptionRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();


        List<String> lignes1 = Arrays.asList("Clindamycine 300mg, 4x/jour, 5 jours", "Paracétamol 1000mg, si nécessaire");
        List<String> lignes2 = Arrays.asList("Recommandation d'hygiène buccale renforcée");

        data.add(Prescription.builder()
                .id(nextId.getAndIncrement()).patientId(101L).utilisateurId(1L)
                .datePrescription(now.minusDays(1))
                .lignesMedicaments(lignes1).build());

        data.add(Prescription.builder()
                .id(nextId.getAndIncrement()).patientId(103L).utilisateurId(2L)
                .datePrescription(now.minusDays(4))
                .lignesMedicaments(lignes2).build());
    }



    @Override
    public List<Prescription> findAll() {
        return data.stream()
                .sorted(Comparator.comparing(Prescription::getDatePrescription).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Prescription findById(Long id) {
        return findAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Prescription p) {
        if (p.getId() == null) {
            p.setId(nextId.getAndIncrement());
        }
        data.add(p);
    }

    @Override
    public void update(Prescription p) {
        deleteById(p.getId());
        data.add(p);
        data.sort(Comparator.comparing(Prescription::getId));
    }

    @Override
    public void delete(Prescription p) { data.removeIf(pres -> pres.getId().equals(p.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(pres -> pres.getId().equals(id)); }




    @Override
    public List<Prescription> findByPatientId(Long patientId) {
        return data.stream()
                .filter(p -> p.getPatientId().equals(patientId))
                .sorted(Comparator.comparing(Prescription::getDatePrescription).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Prescription> findByUtilisateurId(Long utilisateurId) {
        return data.stream()
                .filter(p -> p.getUtilisateurId().equals(utilisateurId))
                .sorted(Comparator.comparing(Prescription::getDatePrescription).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Prescription> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        return data.stream()
                .filter(p -> !p.getDatePrescription().isBefore(start) && !p.getDatePrescription().isAfter(end))
                .sorted(Comparator.comparing(Prescription::getDatePrescription).reversed())
                .collect(Collectors.toList());
    }
}