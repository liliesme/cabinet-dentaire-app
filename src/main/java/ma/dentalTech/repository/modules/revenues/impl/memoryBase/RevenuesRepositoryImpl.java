package ma.dentalTech.repository.modules.revenues.impl.memoryBase;

import ma.dentalTech.entities.Revenues; // Utilisation de Revenues
import ma.dentalTech.repository.modules.revenues.api.RevenuRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class RevenuRepositoryImpl implements RevenuesRepository {

    private final List<Revenues> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public RevenuRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();

        Revenues r1 = new Revenues();
        r1.setId(nextId.getAndIncrement());
        r1.setTitre("Paiement Facture");
        r1.setMontant(1500.0);
        r1.setDate(now.minusHours(2));
        r1.setDescription("Paiement de la facture #101");

        data.add(r1);

        Revenues r2 = new Revenues();
        r2.setId(nextId.getAndIncrement());
        r2.setTitre("Consultation");
        r2.setMontant(500.0);
        r2.setDate(now.minusDays(1));
        r2.setDescription("Consultation initiale du patient X");
        data.add(r2);
    }

    // ------------------------------------
    // -------- 1. CRUD STANDARD --------
    // ------------------------------------

    @Override
    public List<Revenues> findAll() {
        // Trie par date descendante
        return data.stream()
                .sorted(Comparator.comparing(Revenues::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    @Override
    public Revenues findById(Long id) {
        return data.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Revenues revenu) {
        if (revenu.getId() == null) {
            revenu.setId(nextId.getAndIncrement());
        }
        data.add(revenu);
    }

    @Override
    public void update(Revenues revenu) {
        deleteById(revenu.getId());
        data.add(revenu);
        data.sort(Comparator.comparing(Revenues::getId));
    }

    @Override
    public void delete(Revenues revenu) { data.removeIf(r -> r.getId().equals(revenu.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(r -> r.getId().equals(id)); }


    // ------------------------------------
    // -------- 2. FINDERS SPÉCIFIQUES --------
    // ------------------------------------

    @Override
    public List<Revenues> findByTitre(String titre) {
        return data.stream()
                .filter(r -> r.getTitre().equalsIgnoreCase(titre))
                .sorted(Comparator.comparing(Revenues::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Revenues> findByDateBetween(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59, 999999999);

        return data.stream()
                .filter(r -> {
                    LocalDateTime revenuDate = r.getDate();
                    return revenuDate != null &&
                            !revenuDate.isBefore(startDateTime) &&
                            !revenuDate.isAfter(endDateTime);
                })
                .sorted(Comparator.comparing(Revenues::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }
}