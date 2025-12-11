package ma.dentalTech.service.modules.auth.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.Utilisateur;
import ma.dentalTech.repository.modules.utilisateur.api.UtilisateurRepository;
import ma.dentalTech.service.modules.auth.api.AuthService;
import ma.dentalTech.common.exceptions.AuthException;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.utilitaire.Crypto;

@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UtilisateurRepository repository;
    private Utilisateur currentUser; 

    @Override
    public Utilisateur login(String login, String password) throws AuthException, ServiceException {
        try {
            var userOpt = repository.findByLogin(login);
            if (userOpt.isEmpty()) {
                throw new AuthException("Login ou mot de passe incorrect");
            }

            Utilisateur user = userOpt.get();

            
            if (!Crypto.matches(password, user.getPassword())) {
                throw new AuthException("Login ou mot de passe incorrect");
            }

            
            this.currentUser = user;

            return user;

        } catch (AuthException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de l'authentification", e);
        }
    }

    @Override
    public void logout() {
        this.currentUser = null;
    }

    @Override
    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    @Override
    public boolean isAuthenticated() {
        return currentUser != null;
    }

    @Override
    public boolean hasRole(String roleName) {
        if (currentUser == null || currentUser.getRole() == null) {
            return false;
        }
        return currentUser.getRole().getNom().equalsIgnoreCase(roleName);
    }
}