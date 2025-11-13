package ma.dentalTech.repository.modules.facture.impl.memoryBase;

import ma.dentalTech.entities.Facture;
import ma.dentalTech.repository.modules.facture.api.FactureRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FactureRepositoryImpl implements FactureRepository {

    private final List<Facture> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public FactureRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();

        data.add(Facture.builder()
                .id(nextId.getAndIncrement()).patientId(1L).numeroFacture("F2025-001")
                .dateFacture(now.minusDays(5)).montantTotal(1250.00).estPayee(true)
                .build());

        data.add(Facture.builder()
                .id(nextId.getAndIncrement()).patientId(2L).numeroFacture("F2025-002")
                .dateFacture(now.minusDays(2)).montantTotal(890.50).estPayee(false)
                .build());

        data.add(Facture.builder()
                .id(nextId.getAndIncrement()).patientId(1L).numeroFacture("F2025-003")
                .dateFacture(now.minusHours(10)).montantTotal(350.00).estPayee(false)
                .build());
    }

    // -------- CRUD STANDARD --------
    @Override
    public List<Facture> findAll() { return List.copyOf(data); }

    @Override
    public Facture findById(Long id) {
        return findAll().stream()
                .filter(f -> f.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Facture facture) {
        if (facture.getId() == null) {
            facture.setId(nextId.getAndIncrement()); // Assigner un ID unique
        }
        data.add(facture);
    }

    @Override
    public void update(Facture facture) {
        deleteById(facture.getId());
        data.add(facture);
        data.sort(Comparator.comparing(Facture::getId));
    }

    @Override
    public void delete(Facture facture) { data.removeIf(f -> f.getId().equals(facture.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(f -> f.getId().equals(id)); }


    // -------- MÉTHODES SPÉCIFIQUES --------

    @Override
    public List<Facture> findByPatientId(Long patientId) {
        return findAll().stream()
                .filter(f -> f.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Facture> findByEstPayee(boolean estPayee) {
        return findAll().stream()
                .filter(f -> f.isEstPayee() == estPayee)
                .collect(Collectors.toList());
    }

    @Override
    public List<Facture> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        return findAll().stream()
                .filter(f -> f.getDateFacture().isAfter(start) && f.getDateFacture().isBefore(end))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Facture> findByNumeroFacture(String numeroFacture) {
        return findAll().stream()
                .filter(f -> f.getNumeroFacture() != null && f.getNumeroFacture().equals(numeroFacture))
                .findFirst();
    }
}