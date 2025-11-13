package ma.dentalTech.repository.modules.medical.impl.memoryBase;

import ma.dentalTech.entities.Medicament;
import ma.dentalTech.repository.modules.medicament.api.MedicamentRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MedicamentRepositoryImpl implements MedicamentRepository {

    private final List<Medicament> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public MedicamentRepositoryImpl() {

        data.add(Medicament.builder()
                .id(nextId.getAndIncrement()).nom("Amoxicilline").dosage("500mg")
                .presentation("Comprimés").build());

        data.add(Medicament.builder()
                .id(nextId.getAndIncrement()).nom("Ibuprofène").dosage("400mg")
                .presentation("Gélules").build());
    }


    @Override
    public List<Medicament> findAll() { return List.copyOf(data); }

    @Override
    public Medicament findById(Long id) {
        return findAll().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Medicament medicament) {
        if (medicament.getId() == null) {
            medicament.setId(nextId.getAndIncrement());
        }
        data.add(medicament);
    }

    @Override
    public void update(Medicament medicament) {
        deleteById(medicament.getId());
        data.add(medicament);
        data.sort(Comparator.comparing(Medicament::getId));
    }

    @Override
    public void delete(Medicament medicament) { data.removeIf(m -> m.getId().equals(medicament.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(m -> m.getId().equals(id)); }



    @Override
    public Optional<Medicament> findByNom(String nom) {
        // Recherche par nom exact (insensible à la casse)
        return findAll().stream()
                .filter(m -> m.getNom().equalsIgnoreCase(nom))
                .findFirst();
    }


}