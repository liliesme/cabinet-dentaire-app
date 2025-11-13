package ma.dentalTech.repository.modules.InterventionMedecin.impl.memoryBase;


import ma.dentalTech.entities.InterventionMedecin;
import ma.dentalTech.repository.modules.InterventionMedecin.api.InterventionMedecinRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InterventionMedecinRepositoryImpl implements InterventionMedecinRepository {

    private final List<InterventionMedecin> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public InterventionMedecinRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();


        data.add(InterventionMedecin.builder()
                .id(nextId.getAndIncrement()).patientId(101L).utilisateurId(1L)
                .dateIntervention(now.minusDays(3).withHour(11).withMinute(0))
                .typeIntervention("Obturation").description("Amalgame sur 36. Durée 30min.").build());

        data.add(InterventionMedecin.builder()
                .id(nextId.getAndIncrement()).patientId(102L).utilisateurId(2L)
                .dateIntervention(now.minusDays(5).withHour(9).withMinute(30))
                .typeIntervention("Extraction").description("Extraction de la molaire 18.").build());
    }

    // ------------------------------------
    // -------- 1. CRUD STANDARD --------
    // ------------------------------------

    @Override
    public List<InterventionMedecin> findAll() {
        return data.stream()
                .sorted(Comparator.comparing(InterventionMedecin::getDateIntervention).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public InterventionMedecin findById(Long id) {
        return findAll().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(InterventionMedecin i) {
        if (i.getId() == null) {
            i.setId(nextId.getAndIncrement());
        }
        data.add(i);
    }

    @Override
    public void update(InterventionMedecin i) {
        deleteById(i.getId());
        data.add(i);
        data.sort(Comparator.comparing(InterventionMedecin::getId));
    }

    @Override
    public void delete(InterventionMedecin i) { data.removeIf(inter -> inter.getId().equals(i.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(inter -> inter.getId().equals(id)); }


    // ------------------------------------
    // -------- 2. FINDERS SPÉCIFIQUES --------
    // ------------------------------------

    @Override
    public List<InterventionMedecin> findByPatientId(Long patientId) {
        return data.stream()
                .filter(i -> i.getPatientId().equals(patientId))
                .sorted(Comparator.comparing(InterventionMedecin::getDateIntervention).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<InterventionMedecin> findByUtilisateurId(Long utilisateurId) {
        return data.stream()
                .filter(i -> i.getUtilisateurId().equals(utilisateurId))
                .sorted(Comparator.comparing(InterventionMedecin::getDateIntervention).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<InterventionMedecin> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        return data.stream()
                .filter(i -> !i.getDateIntervention().isBefore(start) && !i.getDateIntervention().isAfter(end))
                .sorted(Comparator.comparing(InterventionMedecin::getDateIntervention))
                .collect(Collectors.toList());
    }
}