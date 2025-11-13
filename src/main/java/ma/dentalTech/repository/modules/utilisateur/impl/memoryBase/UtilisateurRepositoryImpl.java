package ma.dentalTech.repository.modules.utilisateur.impl.memoryBase;

import ma.dentalTech.entities.Utilisateur;
import ma.dentalTech.repository.modules.utilisateur.api.UtilisateurRepository;
import ma.dentalTech.entities.enums.RoleType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UtilisateurRepositoryImpl implements UtilisateurRepository {

    private final List<Utilisateur> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public UtilisateurRepositoryImpl() {

        data.add(Utilisateur.builder()
                .id(nextId.getAndIncrement()).nom("Ahmed").prenom("Karimi").login("akarimi").password("hashedpass1")
                .email("akarimi@dental.ma").roleId(10L).build()); // Rôle Médecin

        data.add(Utilisateur.builder()
                .id(nextId.getAndIncrement()).nom("Fatima").prenom("Alaoui").login("falaoui").password("hashedpass2")
                .email("falaoui@dental.ma").roleId(20L).build()); // Rôle Secrétaire
    }

    // ------------------------------------
    // -------- 1. CRUD STANDARD --------
    // ------------------------------------

    @Override
    public List<Utilisateur> findAll() { return List.copyOf(data); }

    @Override
    public Utilisateur findById(Long id) {
        return findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Utilisateur utilisateur) {
        if (utilisateur.getId() == null) {
            utilisateur.setId(nextId.getAndIncrement());
        }
        data.add(utilisateur);
    }

    @Override
    public void update(Utilisateur utilisateur) {
        deleteById(utilisateur.getId());
        data.add(utilisateur);
        data.sort(Comparator.comparing(Utilisateur::getId));
    }

    @Override
    public void delete(Utilisateur utilisateur) { data.removeIf(u -> u.getId().equals(utilisateur.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(u -> u.getId().equals(id)); }


    // ------------------------------------
    // -------- 2. FINDERS SPÉCIFIQUES --------
    // ------------------------------------

    @Override
    public Optional<Utilisateur> findByLogin(String login) {
        return findAll().stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .findFirst();
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        return findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public boolean existsByLogin(String login) {
        return findAll().stream()
                .anyMatch(u -> u.getLogin().equalsIgnoreCase(login));
    }
}