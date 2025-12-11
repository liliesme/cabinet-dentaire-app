package ma.dentalTech.service.modules.utilisateur.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.Utilisateur;
import ma.dentalTech.repository.modules.utilisateur.api.UtilisateurRepository;
import ma.dentalTech.service.modules.utilisateur.api.UtilisateurService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;
import ma.dentalTech.common.utilitaire.Crypto;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository repository;

    @Override
    public List<Utilisateur> getAllUtilisateurs() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des utilisateurs", e);
        }
    }

    @Override
    public Optional<Utilisateur> getUtilisateurById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(repository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de l'utilisateur #" + id, e);
        }
    }

    @Override
    public void createUtilisateur(Utilisateur utilisateur, String plainPassword) throws ServiceException, ValidationException {
        
        Validators.notBlank(utilisateur.getNom(), "Nom");
        Validators.notBlank(utilisateur.getPrenom(), "Prénom");
        Validators.notBlank(utilisateur.getLogin(), "Login");
        Validators.minLen(plainPassword, 6, "Mot de passe");
        Validators.email(utilisateur.getEmail());

        
        if (repository.existsByLogin(utilisateur.getLogin())) {
            throw new ValidationException("Ce login est déjà utilisé");
        }

        if (repository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new ValidationException("Cet email est déjà utilisé");
        }

        if (utilisateur.getRoleId() == null) {
            throw new ValidationException("Rôle requis");
        }

        
        String hashedPassword = Crypto.hash(plainPassword);
        utilisateur.setPassword(hashedPassword);

        try {
            repository.create(utilisateur);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de l'utilisateur", e);
        }
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) throws ServiceException, ValidationException {
        if (utilisateur.getId() == null) {
            throw new ValidationException("ID requis pour la mise à jour");
        }

        Validators.notBlank(utilisateur.getNom(), "Nom");
        Validators.notBlank(utilisateur.getPrenom(), "Prénom");
        Validators.email(utilisateur.getEmail());

        try {
            repository.update(utilisateur);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de l'utilisateur", e);
        }
    }

    @Override
    public void deleteUtilisateur(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de l'utilisateur", e);
        }
    }

    @Override
    public Optional<Utilisateur> getUtilisateurByLogin(String login) throws ServiceException {
        try {
            return repository.findByLogin(login);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par login", e);
        }
    }

    @Override
    public Optional<Utilisateur> getUtilisateurByEmail(String email) throws ServiceException {
        try {
            return repository.findByEmail(email);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par email", e);
        }
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) throws ServiceException, ValidationException {
        Validators.minLen(newPassword, 6, "Nouveau mot de passe");

        try {
            Utilisateur user = repository.findById(userId);
            if (user == null) {
                throw new ServiceException("Utilisateur introuvable");
            }

            
            if (!Crypto.matches(oldPassword, user.getPassword())) {
                throw new ValidationException("Ancien mot de passe incorrect");
            }

            
            user.setPassword(Crypto.hash(newPassword));
            repository.update(user);

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du changement de mot de passe", e);
        }
    }

    @Override
    public void resetPassword(Long userId, String newPassword) throws ServiceException, ValidationException {
        Validators.minLen(newPassword, 6, "Mot de passe");

        try {
            Utilisateur user = repository.findById(userId);
            if (user == null) {
                throw new ServiceException("Utilisateur introuvable");
            }

            user.setPassword(Crypto.hash(newPassword));
            repository.update(user);

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la réinitialisation du mot de passe", e);
        }
    }

    @Override
    public boolean loginExists(String login) throws ServiceException {
        try {
            return repository.existsByLogin(login);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification du login", e);
        }
    }

    @Override
    public boolean emailExists(String email) throws ServiceException {
        try {
            return repository.findByEmail(email).isPresent();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification de l'email", e);
        }
    }
}