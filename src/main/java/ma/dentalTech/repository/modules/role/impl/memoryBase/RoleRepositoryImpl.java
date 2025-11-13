package ma.dentalTech.repository.modules.role.impl.memoryBase;

import ma.dentalTech.entities.Role;
import ma.dentalTech.repository.modules.role.api.RoleRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class RoleRepositoryImpl implements RoleRepository {

    private final List<Role> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public RoleRepositoryImpl() {
        // Données d'exemple
        data.add(Role.builder()
                .id(nextId.getAndIncrement()).nom("Administrateur")
                .description("Accès complet au système").build());

        data.add(Role.builder()
                .id(nextId.getAndIncrement()).nom("Médecin")
                .description("Gestion des consultations et traitements").build());

        data.add(Role.builder()
                .id(nextId.getAndIncrement()).nom("Secrétaire")
                .description("Gestion des rendez-vous et facturation").build());
    }



    @Override
    public List<Role> findAll() { return List.copyOf(data); }

    @Override
    public Role findById(Long id) {
        return findAll().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Role role) {
        if (role.getId() == null) {
            role.setId(nextId.getAndIncrement());
        }
        data.add(role);
    }

    @Override
    public void update(Role role) {
        deleteById(role.getId());
        data.add(role);
        data.sort(Comparator.comparing(Role::getId));
    }

    @Override
    public void delete(Role role) { data.removeIf(r -> r.getId().equals(role.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(r -> r.getId().equals(id)); }


    // ------------------------------------
    // -------- 2. FINDERS SPÉCIFIQUES --------
    // ------------------------------------

    @Override
    public Optional<Role> findByNom(String nom) {
        return findAll().stream()
                .filter(r -> r.getNom().equalsIgnoreCase(nom))
                .findFirst();
    }

    @Override
    public boolean existsByNom(String nom) {
        return findAll().stream()
                .anyMatch(r -> r.getNom().equalsIgnoreCase(nom));
    }
}