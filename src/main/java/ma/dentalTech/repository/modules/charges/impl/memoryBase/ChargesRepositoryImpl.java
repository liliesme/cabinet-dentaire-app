package ma.dentalTech.repository.modules.charges.impl.memoryBase;

import ma.dentalTech.entities.Charges;
import ma.dentalTech.repository.modules.charges.api.ChargeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime; // Utilisé pour le champ 'date'
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ChargeRepositoryImpl implements ChargesRepository {

    private final List<Charges> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public ChargeRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();

        // Données d'exemple
        data.add(Charges.builder()
                .id(nextId.getAndIncrement()).titre("Loyer").montant(5000.0)
                .date(now.minusDays(30).withHour(10).withMinute(0))
                .description("Loyer mensuel du cabinet").utilisateurId(1L).build());

        data.add(Charges.builder()
                .id(nextId.getAndIncrement()).titre("Fournitures").montant(850.50)
                .date(now.minusDays(5).withHour(15).withMinute(30))
                .description("Achat de gants et masques").utilisateurId(2L).build());

        data.add(Charges.builder()
                .id(nextId.getAndIncrement()).titre("Salaires").montant(12000.0)
                .date(now.withDayOfMonth(2).withHour(11).withMinute(0))
                .description("Salaires du personnel").utilisateurId(1L).build());
    }

    // ------------------------------------
    // -------- 1. CRUD STANDARD --------
    // ------------------------------------

    @Override
    public List<Charges> findAll() {
        // Trie par date descendante
        return data.stream()
                .sorted(Comparator.comparing(Charges::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    @Override
    public Charges findById(Long id) {
        return data.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Charges charge) {
        if (charge.getId() == null) {
            charge.setId(nextId.getAndIncrement());
        }
        data.add(charge);
    }

    @Override
    public void update(Charges charge) {
        deleteById(charge.getId());
        data.add(charge);
        data.sort(Comparator.comparing(Charge::getId));
    }

    @Override
    public void delete(Charges charge) { data.removeIf(c -> c.getId().equals(charge.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(c -> c.getId().equals(id)); }


    // ------------------------------------
    // -------- 2. FINDERS SPÉCIFIQUES --------
    // ------------------------------------

    @Override
    public List<Charges> findByTitre(String titre) {
        return data.stream()
                .filter(c -> c.getTitre().equalsIgnoreCase(titre))
                .sorted(Comparator.comparing(Charge::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Charges> findByDateBetween(LocalDate start, LocalDate end) {
        // Convertit les LocalDate en limites LocalDateTime pour la comparaison
        LocalDateTime startDateTime = start.atStartOfDay();
        // Prend la fin de la journée 'end' (23:59:59.999...) pour inclure toute la journée
        LocalDateTime endDateTime = end.atTime(23, 59, 59, 999999999);

        return data.stream()
                .filter(c -> {
                    LocalDateTime chargeDate = c.getDate();
                    return chargeDate != null &&
                            !chargeDate.isBefore(startDateTime) &&
                            !chargeDate.isAfter(endDateTime);
                })
                .sorted(Comparator.comparing(Charges::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }
}