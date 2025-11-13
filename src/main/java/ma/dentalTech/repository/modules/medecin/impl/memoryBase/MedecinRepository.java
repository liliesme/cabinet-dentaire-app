package ma.dentalTech.repository.modules.medecin.impl.memoryBase;

import ma.dentalTech.entities.medecin.Medecin;
import ma.dentalTech.repository.modules.medecin.api.MedecinRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MedecinRepositoryImpl implements MedecinRepository {

    private final List<Medecin> data = new ArrayList<>();

    public MedecinRepositoryImpl() {
        // Données d'exemple
        data.add(Medecin.builder()
                .id(1L).nom("Driss").prenom("A.")
                .email("driss@example.com").telephone("0655-555555")
                .specialite("Orthodontie")
                .build());

        data.add(Medecin.builder()
                .id(2L).nom("Leila").prenom("B.")
                .email("leila@example.com").telephone("0666-666666")
                .specialite("Parodontologie")
                .build());

        data.add(Medecin.builder()
                .id(3L).nom("Karim").prenom("C.")
                .email("karim@example.com").telephone("0677-777777")
                .specialite("Chirurgie Dentaire")
                .build());

        data.sort(Comparator.comparing(Medecin::getId));
    }

    @Override
    public List<Medecin> findAll() {
        return List.copyOf(data);
    }

    @Override
    public Medecin findById(Long id) {
        return findAll().stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void create(Medecin medecin) {
        data.add(medecin);
    }

    @Override
    public void update(Medecin medecin) {
        deleteById(medecin.getId());
        data.add(medecin);
    }

    @Override
    public void delete(Medecin medecin) {
        data.removeIf(m -> m.getId().equals(medecin.getId()));
    }

    @Override
    public void deleteById(Long id) {
        data.removeIf(m -> m.getId().equals(id));
    }

    @Override
    public Optional<Medecin> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Medecin> findByTelephone(String telephone) {
        return Optional.empty();
    }

    @Override
    public List<Medecin> searchByNomPrenom(String keyword) {
        return List.of();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Medecin> findPage(int limit, int offset) {
        return List.of();
    }

    @Override
    public List<Medecin> findBySpecialite(String specialite) {
        return List.of();
    }
}
