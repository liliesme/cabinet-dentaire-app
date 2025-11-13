package ma.dentalTech.repository.modules.DossierMedicale.impl.memoryBase;


import ma.dentalTech.entities.DossierMedical;
import ma.dentalTech.repository.modules.DossierMedicale.api.DossierMedicalRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class DossierMedicalRepositoryImpl implements DossierMedicalRepository {

    private final List<DossierMedical> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public DossierMedicalRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();


        data.add(DossierMedical.builder()
                .id(nextId.getAndIncrement()).patientId(101L)
                .dateCreation(now.minusMonths(6))
                .notesInitiales("Patient avec historique de sensibilité élevée.").build());

        data.add(DossierMedical.builder()
                .id(nextId.getAndIncrement()).patientId(102L)
                .dateCreation(now.minusDays(5))
                .notesInitiales("Nouvelle patiente, aucun antécédent dentaire grave connu.").build());
    }



    @Override
    public List<DossierMedical> findAll() {
        return data.stream()
                .sorted(Comparator.comparing(DossierMedical::getDateCreation).reversed())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public DossierMedical findById(Long id) {
        return findAll().stream()
                .filter(dm -> dm.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(DossierMedical dm) {
        if (dm.getId() == null) {
            dm.setId(nextId.getAndIncrement());
        }
        data.add(dm);
    }

    @Override
    public void update(DossierMedical dm) {
        deleteById(dm.getId());
        data.add(dm);
        data.sort(Comparator.comparing(DossierMedical::getId));
    }

    @Override
    public void delete(DossierMedical dm) { data.removeIf(d -> d.getId().equals(dm.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(d -> d.getId().equals(id)); }




    @Override
    public Optional<DossierMedical> findByPatientId(Long patientId) {
        return data.stream()
                .filter(dm -> dm.getPatientId().equals(patientId))
                .findFirst();
    }
}