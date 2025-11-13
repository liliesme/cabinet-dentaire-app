package ma.dentalTech.repository.modules.Agenda.impl.memoryBase;


import ma.dentalTech.entities.Agenda;
import ma.dentalTech.repository.modules.Agenda.api.AgendaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class AgendaRepositoryImpl implements AgendaRepository {

    private final List<Agenda> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public AgendaRepositoryImpl() {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        // Configuration de l'agenda pour le mois en cours
        List<LocalDate> joursOuvrables = new ArrayList<>();
        joursOuvrables.add(today.withDayOfMonth(1));
        joursOuvrables.add(today.withDayOfMonth(2));

        List<LocalDate> joursFeries = new ArrayList<>();
        joursFeries.add(today.withDayOfMonth(11));

        Agenda configMoisCourant = new Agenda();
        configMoisCourant.setIdAgenda(nextId.getAndIncrement());
        configMoisCourant.setAnnee(currentYear);
        configMoisCourant.setMois(currentMonth);
        configMoisCourant.setJoursOuvrables(joursOuvrables);
        configMoisCourant.setJoursFeries(joursFeries);
        configMoisCourant.setHeureDebut(LocalTime.of(9, 0));
        configMoisCourant.setHeureFin(LocalTime.of(18, 0));
        configMoisCourant.setDureeConsultation(30);

        data.add(configMoisCourant);
    }

    // ------------------------------------
    // -------- 1. CRUD STANDARD --------
    // ------------------------------------

    @Override
    public List<Agenda> findAll() {
        return List.copyOf(data);
    }

    @Override
    public Agenda findById(Long id) {
        return data.stream()
                .filter(a -> a.getIdAgenda().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Agenda a) {
        if (a.getIdAgenda() == null) {
            a.setIdAgenda(nextId.getAndIncrement());
        }
        data.add(a);
    }

    @Override
    public void update(Agenda a) {
        deleteById(a.getIdAgenda());
        data.add(a);
        data.sort(Comparator.comparing(Agenda::getIdAgenda));
    }

    @Override
    public void delete(Agenda a) { data.removeIf(ag -> ag.getIdAgenda().equals(a.getIdAgenda())); }

    @Override
    public void deleteById(Long id) { data.removeIf(ag -> ag.getIdAgenda().equals(id)); }


    // ------------------------------------
    // -------- 2. FINDERS SPÉCIFIQUES --------
    // ------------------------------------

    @Override
    public Optional<Agenda> findByAnneeAndMois(Integer annee, Integer mois) {
        return data.stream()
                .filter(a -> a.getAnnee().equals(annee) && a.getMois().equals(mois))
                .findFirst();
    }
}