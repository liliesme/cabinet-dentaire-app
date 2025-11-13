package ma.dentalTech.repository.modules.rdv.impl.memoryBase;

import ma.dentalTech.entities.RDV;
import ma.dentalTech.entities.enums.RdvStatut;
import ma.dentalTech.repository.modules.rdv.api.RdvRepository;
import ma.dentalTech.repository.modules.rdv.api.RdvRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class RdvRepositoryImpl implements RdvRepository {

    private final List<RDV> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public RdvRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();

        // Données d'exemple
        data.add(RDV.builder()
                .id(nextId.getAndIncrement()).patientId(1L).medecinId(10L)
                .dateHeure(now.plusHours(1)).motif("Contrôle annuel")
                .statut(RDVStatut.CONFIRME)
                .build());

        data.add(RDV.builder()
                .id(nextId.getAndIncrement()).patientId(3L).medecinId(10L)
                .dateHeure(now.plusHours(2)).motif("Extraction")
                .statut(RDVStatut.EN_ATTENTE)
                .build());

        data.add(RDV.builder()
                .id(nextId.getAndIncrement()).patientId(2L).medecinId(11L)
                .dateHeure(now.minusDays(1)).motif("Nettoyage")
                .statut(RDVStatut.TERMINE)
                .build());
    }

    // -------- CRUD STANDARD --------
    @Override
    public List<RDV> findAll() { return List.copyOf(data); }

    @Override
    public RDV findById(Long id) {
        return findAll().stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void create(RDV rdv) {
        if (rdv.getId() == null) {
            rdv.setId(nextId.getAndIncrement());
        }
        data.add(Rdv);
    }

    @Override
    public void update(RDV rdv) {
        deleteById(rdv.getId());
        data.add(rdv);
        data.sort(Comparator.comparing(RDV::getId));
    }

    @Override
    public void delete(RDV rdv) { data.removeIf(r -> r.getId().equals(rdv.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(r -> r.getId().equals(id)); }


    // -------- MÉTHODES SPÉCIFIQUES --------

    @Override
    public List<RDV> findByPatientId(Long patientId) {
        return findAll().stream()
                .filter(r -> r.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    @Override
    public List<RDV> findByMedecinId(Long medecinId) {
        return findAll().stream()
                .filter(r -> r.getMedecinId().equals(medecinId))
                .collect(Collectors.toList());
    }

    @Override
    public List<RDV> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        return findAll().stream()
                .filter(r -> r.getDateHeure().isAfter(start) && r.getDateHeure().isBefore(end))
                .collect(Collectors.toList());
    }



    @Override
    public List<RDV> findTodayRdv() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();
        return findByDateBetween(start, end);
    }
}