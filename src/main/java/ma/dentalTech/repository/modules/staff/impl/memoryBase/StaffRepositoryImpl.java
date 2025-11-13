package ma.dentalTech.repository.modules.staff.impl.memoryBase;

import ma.dentalTech.entities.Staff;
import ma.dentalTech.repository.modules.staff.api.StaffRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class StaffRepositoryImpl implements StaffRepository {

    private final List<Staff> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public StaffRepositoryImpl() {
        LocalDate today = LocalDate.now();

        // Données d'exemple
        data.add(Staff.builder()
                .id(nextId.getAndIncrement()).nom("Ahmadi").prenom("Driss").cin("H111111").role("Médecin")
                .telephone("0600112233").dateEmbauche(today.minusYears(5)).build());

        data.add(Staff.builder()
                .id(nextId.getAndIncrement()).nom("Zahraoui").prenom("Nadia").cin("H222222").role("Secrétaire")
                .telephone("0699887766").dateEmbauche(today.minusYears(2)).build());
    }

    // ------------------------------------
    // -------- 1. CRUD STANDARD --------
    // ------------------------------------

    @Override
    public List<Staff> findAll() { return List.copyOf(data); }

    @Override
    public Staff findById(Long id) {
        return findAll().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Staff staff) {
        if (staff.getId() == null) {
            staff.setId(nextId.getAndIncrement());
        }
        data.add(staff);
    }

    @Override
    public void update(Staff staff) {
        deleteById(staff.getId());
        data.add(staff);
        data.sort(Comparator.comparing(Staff::getId));
    }

    @Override
    public void delete(Staff staff) { data.removeIf(s -> s.getId().equals(staff.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(s -> s.getId().equals(id)); }


    // ------------------------------------
    // -------- 2. FINDERS SPÉCIFIQUES --------
    // ------------------------------------

    @Override
    public Optional<Staff> findByCin(String cin) {
        return findAll().stream()
                .filter(s -> s.getCin().equalsIgnoreCase(cin))
                .findFirst();
    }

    @Override
    public List<Staff> findByRole(String role) {
        return findAll().stream()
                .filter(s -> s.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Staff> findByTelephone(String telephone) {
        return findAll().stream()
                .filter(s -> s.getTelephone().equals(telephone))
                .findFirst();
    }
}