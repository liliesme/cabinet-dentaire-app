package ma.dentalTech.repository.modules.Acte.api;

import ma.dentalTech.entities.pat.Acte;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface ActeRepository extends CrudRepository<Acte, Long> {

    Optional<Acte> findByNom(String nom);

    List<Acte> findByTypeActe(String typeActe);
}