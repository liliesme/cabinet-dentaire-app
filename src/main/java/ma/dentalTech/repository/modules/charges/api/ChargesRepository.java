package ma.dentalTech.repository.modules.charges.api;

import ma.dentalTech.entities.Charges;
import ma.dentalTech.repository.common.CrudRepository;
import java.time.LocalDate;
import java.util.List;

public interface ChargesRepository extends CrudRepository<Charges, Long> {

    // Méthodes de recherche spécifiques


    List<Charges> findByTitre(String titre); // Renommé de findByType à findByTitre


    List<Charges> findByDateBetween(LocalDate start, LocalDate end);


}