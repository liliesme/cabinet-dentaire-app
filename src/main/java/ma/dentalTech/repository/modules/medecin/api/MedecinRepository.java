package ma.dentalTech.repository.modules.medecin.api;

import ma.dentalTech.entities.pat.medecin;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface MedecinRepository extends CrudRepository<medecin, Long> {

    Optional<medecin> findByEmail(String email);
    Optional<medecin> findByTelephone(String telephone);
    List<medecin> searchByNomPrenom(String keyword); 
    boolean existsById(Long id);
    long count();
    List<medecin> findPage(int limit, int offset);


    List<medecin> findBySpecialite(String specialite);
}
