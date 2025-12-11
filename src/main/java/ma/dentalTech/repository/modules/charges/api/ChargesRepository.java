package ma.dentalTech.repository.modules.charges.api;

import ma.dentalTech.entities.pat.Charges;
import ma.dentalTech.repository.common.CrudRepository;
import java.time.LocalDate;
import java.util.List;

public interface ChargesRepository extends CrudRepository<Charges, Long> {

    List<Charges> findByTitre(String titre); 

    List<Charges> findByDateBetween(LocalDate start, LocalDate end);
}