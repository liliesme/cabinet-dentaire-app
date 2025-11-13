package ma.dentalTech.repository.modules.Consultation.impl.memoryBase;


import ma.dentalTech.entities.Consultation;
import ma.dentalTech.repository.modules.Consultation.api.ConsultationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    private final List<Consultation> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public ConsultationRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();


        data.add(Consultation.builder()
                .id(nextId.getAndIncrement()).patientId(101L).utilisateurId(1L)
                .dateConsultation(now.minusDays(5).withHour(10).withMinute(0))
                .diagnostic("Caries mineures sur Molaire 36 et 46").traitement("2 Obturations à réaliser").build());

        data.add(Consultation.builder()
                .id(nextId.getAndIncrement()).patientId(102L).utilisateurId(1L)
                .dateConsultation(now.minusDays(10).withHour(14).withMinute(30))
                .diagnostic("Gingivite légère").traitement("Détartrage et brossage plus fréquent").build());
    }



    @Override
    public List<Consultation> findAll() {
        return data.stream()
                .sorted(Comparator.comparing(Consultation::getDateConsultation).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Consultation findById(Long id) {
        return findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Consultation c) {
        if (c.getId() == null) {
            c.setId(nextId.getAndIncrement());
        }
        data.add(c);
    }

    @Override
    public void update(Consultation c) {
        deleteById(c.getId());
        data.add(c);
        data.sort(Comparator.comparing(Consultation::getId));
    }

    @Override
    public void delete(Consultation c) { data.removeIf(con -> con.getId().equals(c.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(con -> con.getId().equals(id)); }



    @Override
    public List<Consultation> findByPatientId(Long patientId) {
        return data.stream()
                .filter(c -> c.getPatientId().equals(patientId))
                .sorted(Comparator.comparing(Consultation::getDateConsultation).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Consultation> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        return data.stream()
                .filter(c -> !c.getDateConsultation().isBefore(start) && !c.getDateConsultation().isAfter(end))
                .sorted(Comparator.comparing(Consultation::getDateConsultation).reversed())
                .collect(Collectors.toList());
    }
}