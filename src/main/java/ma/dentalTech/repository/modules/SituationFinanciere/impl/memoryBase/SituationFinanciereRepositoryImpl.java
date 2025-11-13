package ma.dentalTech.repository.modules.SituationFinanciere.impl.memoryBase;


import ma.dentalTech.entities.SituationFinanciere;
import ma.dentalTech.repository.modules.SituationFinanciere.api.SituationFinanciereRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class SituationFinanciereRepositoryImpl implements SituationFinanciereRepository {

    private final List<SituationFinanciere> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public SituationFinanciereRepositoryImpl() {

        // Données d'exemple
        // Patient 101: 1000 dû, 700 payé -> 300 restant
        SituationFinanciere sf1 = new SituationFinanciere();
        sf1.setId(nextId.getAndIncrement());
        sf1.setPatientId(101L);
        sf1.setTotalDesActes(1000.0);
        sf1.setTotalePaye(700.0);
        // Le crédit est calculé via le setter dans l'entité, mais ici on le force pour l'exemple
        sf1.setCredit(300.0);
        sf1.setObservation("Reste 300 à payer.");
        // sf1.setStatut(Statut.SOLDE_PARTIEL); // Nécessite l'enum Statut
        data.add(sf1);

        // Patient 102: 500 dû, 500 payé -> 0 restant
        SituationFinanciere sf2 = new SituationFinanciere();
        sf2.setId(nextId.getAndIncrement());
        sf2.setPatientId(102L);
        sf2.setTotalDesActes(500.0);
        sf2.setTotalePaye(500.0);
        sf2.setCredit(0.0);
        sf2.setObservation("Solde réglé.");
        // sf2.setStatut(Statut.SOLDE_COMPLET);
        data.add(sf2);
    }

    // ------------------------------------
    // -------- 1. CRUD STANDARD --------
    // ------------------------------------

    @Override
    public List<SituationFinanciere> findAll() {
        return List.copyOf(data);
    }

    @Override
    public SituationFinanciere findById(Long id) {
        return data.stream()
                .filter(sf -> sf.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(SituationFinanciere sf) {
        if (sf.getId() == null) {
            sf.setId(nextId.getAndIncrement());
        }
        // Recalculer le crédit avant l'enregistrement si l'entité ne le fait pas elle-même
        sf.setCredit(sf.getTotalDesActes() - sf.getTotalePaye());
        data.add(sf);
    }

    @Override
    public void update(SituationFinanciere sf) {
        deleteById(sf.getId());
        sf.setCredit(sf.getTotalDesActes() - sf.getTotalePaye());
        data.add(sf);
    }

    @Override
    public void delete(SituationFinanciere sf) { data.removeIf(s -> s.getId().equals(sf.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(s -> s.getId().equals(id)); }




    @Override
    public Optional<SituationFinanciere> findByPatientId(Long patientId) {
        return data.stream()
                .filter(sf -> sf.getPatientId().equals(patientId))
                .findFirst();
    }
}