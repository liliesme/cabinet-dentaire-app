package ma.dentalTech.service.modules.auth.api;

import ma.dentalTech.entities.pat.Utilisateur;
import ma.dentalTech.common.exceptions.AuthException;
import ma.dentalTech.common.exceptions.ServiceException;

public interface AuthService {

    
    Utilisateur login(String login, String password) throws AuthException, ServiceException;

    
    void logout();

    
    Utilisateur getCurrentUser();

    
    boolean isAuthenticated();

    
    boolean hasRole(String roleName);
}