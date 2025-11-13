package ma.dentalTech.repository.modules.Acte.impl.memoryBase;


import ma.dentalTech.entities.Acte;
import ma.dentalTech.repository.modules.Acte.api.ActeRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ActeRepositoryImpl implements ActeRepository {

    private final List<Acte> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public ActeRepositoryImpl() {


        data.add(Acte.builder()
                .id(nextId.getAndIncrement()).nom("Consultation simple")
                .description("Examen de routine").typeActe("Consultation")
                .tarifBase(200.00).build());

        data.add(Acte.builder()
                .id(nextId.getAndIncrement()).nom("Détartrage complet")
                .description("Nettoyage des plaques").typeActe("Prévention")
                .tarifBase(450.00).build());

        data.add(Acte.builder()
                .id(nextId.getAndIncrement()).nom("Extraction molaire")
                .description("Enlèvement d'une molaire").typeActe("Chirurgie")
                .tarifBase(800.00).build());
    }



    @Override
    public List<Acte> findAll() {
        return data.stream()
                .sorted(Comparator.comparing(Acte::getNom))
                .collect(Collectors.toList());
    }

    @Override
    public Acte findById(Long id) {
        return findAll().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Acte a) {
        if (a.getId() == null) {
            a.setId(nextId.getAndIncrement());
        }
        data.add(a);
    }

    @Override
    public void update(Acte a) {
        deleteById(a.getId());
        data.add(a);
        data.sort(Comparator.comparing(Acte::getId));
    }

    @Override
    public void delete(Acte a) { data.removeIf(acte -> acte.getId().equals(a.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(acte -> acte.getId().equals(id)); }




    @Override
    public Optional<Acte> findByNom(String nom) {
        return data.stream()
                .filter(a -> a.getNom().equalsIgnoreCase(nom))
                .findFirst();
    }

    @Override
    public List<Acte> findByType(String type) {
        return data.stream()
                .filter(a -> a.getTypeActe().equalsIgnoreCase(type))
                .sorted(Comparator.comparing(Acte::getNom))
                .collect(Collectors.toList());
    }
}