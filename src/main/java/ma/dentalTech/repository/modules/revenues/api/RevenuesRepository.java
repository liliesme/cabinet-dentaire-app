package ma.dentalTech.repository.modules.revenues.api;

import ma.dentalTech.entities.finance.Revenu;
import ma.dentalTech.repository.common.CrudRepository;
import java.time.LocalDate;
import java.util.List;

public interface RevenuRepository extends CrudRepository<Revenues, Long> {


    List<Revenues> findByTitre(String titre);


    List<Revenues> findByDateBetween(LocalDate start, LocalDate end);
}