package ma.dentalTech.repository.modules.medecin.api;

import ma.dentalTech.entities.medecin.Medecin;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface MedecinRepository extends CrudRepository<Medecin, Long> {

    Optional<Medecin> findByEmail(String email);
    Optional<Medecin> findByTelephone(String telephone);
    List<Medecin> searchByNomPrenom(String keyword); // LIKE %keyword%
    boolean existsById(Long id);
    long count();
    List<Medecin> findPage(int limit, int offset);

    // ---- Relations ----
    List<Medecin> findBySpecialite(String specialite);
}
