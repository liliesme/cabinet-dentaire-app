package ma.dentalTech.repository.modules.Ordonnance.impl.memoryBase;


import ma.dentalTech.entities.Ordonnance;
import ma.dentalTech.repository.modules.Ordonnance.api.OrdonnanceRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class OrdonnanceRepositoryImpl implements OrdonnanceRepository {

    private final List<Ordonnance> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public OrdonnanceRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();


        List<String> lignes1 = Arrays.asList("Amoxicilline 500mg, 3x/jour, 7 jours", "Ibuprofène 400mg, si douleur");
        List<String> lignes2 = Arrays.asList("Bain de bouche antiseptique, 2x/jour");

        data.add(Ordonnance.builder()
                .id(nextId.getAndIncrement()).patientId(101L).utilisateurId(1L)
                .dateOrdonnance(now.minusDays(2))
                .lignesMedicaments(lignes1).build());

        data.add(Ordonnance.builder()
                .id(nextId.getAndIncrement()).patientId(103L).utilisateurId(2L)
                .dateOrdonnance(now.minusDays(5))
                .lignesMedicaments(lignes2).build());
    }




    @Override
    public List<Ordonnance> findAll() {
        return data.stream()
                .sorted(Comparator.comparing(Ordonnance::getDateOrdonnance).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Ordonnance findById(Long id) {
        return findAll().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Ordonnance o) {
        if (o.getId() == null) {
            o.setId(nextId.getAndIncrement());
        }
        data.add(o);
    }

    @Override
    public void update(Ordonnance o) {
        deleteById(o.getId());
        data.add(o);
        data.sort(Comparator.comparing(Ordonnance::getId));
    }

    @Override
    public void delete(Ordonnance o) { data.removeIf(ord -> ord.getId().equals(o.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(ord -> ord.getId().equals(id)); }




    @Override
    public List<Ordonnance> findByPatientId(Long patientId) {
        return data.stream()
                .filter(o -> o.getPatientId().equals(patientId))
                .sorted(Comparator.comparing(Ordonnance::getDateOrdonnance).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Ordonnance> findByUtilisateurId(Long utilisateurId) {
        return data.stream()
                .filter(o -> o.getUtilisateurId().equals(utilisateurId))
                .sorted(Comparator.comparing(Ordonnance::getDateOrdonnance).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Ordonnance> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        return data.stream()
                .filter(o -> !o.getDateOrdonnance().isBefore(start) && !o.getDateOrdonnance().isAfter(end))
                .sorted(Comparator.comparing(Ordonnance::getDateOrdonnance).reversed())
                .collect(Collectors.toList());
    }
}