package ma.dentalTech.repository.modules.Acte.api;


import ma.dentalTech.finance.Acte;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface ActeRepository extends CrudRepository<Acte, Long> {


    Optional<Acte> findByNom(String nom);


    List<Acte> findByType(String type);
}