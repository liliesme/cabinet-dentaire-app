package ma.dentalTech.repository.modules.utilisateur.api;

import ma.dentalTech.entities.Utilisateur;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByLogin(String login);
    Optional<Utilisateur> findByEmail(String email);
    boolean existsByLogin(String login);
}