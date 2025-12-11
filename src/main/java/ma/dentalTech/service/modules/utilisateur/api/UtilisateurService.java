package ma.dentalTech.service.modules.utilisateur.api;

import ma.dentalTech.entities.pat.Utilisateur;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    
    List<Utilisateur> getAllUtilisateurs() throws ServiceException;
    Optional<Utilisateur> getUtilisateurById(Long id) throws ServiceException;
    void createUtilisateur
            (Utilisateur utilisateur, String plainPassword) throws ServiceException, ValidationException;
    void updateUtilisateur(Utilisateur utilisateur) throws ServiceException, ValidationException;
    void deleteUtilisateur(Long id) throws ServiceException;
    
    Optional<Utilisateur> getUtilisateurByLogin(String login) throws ServiceException;
    Optional<Utilisateur> getUtilisateurByEmail(String email) throws ServiceException;

    
    void changePassword(Long userId, String oldPassword, String newPassword) throws ServiceException, ValidationException;
    void resetPassword(Long userId, String newPassword) throws ServiceException, ValidationException;

    
    boolean loginExists(String login) throws ServiceException;
    boolean emailExists(String email) throws ServiceException;
}