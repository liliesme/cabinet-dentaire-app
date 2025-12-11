package ma.dentalTech.service.modules.medecin.api;

import ma.dentalTech.entities.pat.medecin;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.util.List;
import java.util.Optional;

public interface MedecinService {

    
    List<medecin> getAllMedecins() throws ServiceException;
    Optional<medecin> getMedecinById(Long id) throws ServiceException;
    void createMedecin(medecin medecin) throws ServiceException, ValidationException;
    void updateMedecin(medecin medecin) throws ServiceException, ValidationException;
    void deleteMedecin(Long id) throws ServiceException;

    
    List<medecin> searchMedecinsByNom(String keyword) throws ServiceException;
    List<medecin> getMedecinsBySpecialite(String specialite) throws ServiceException;
    Optional<medecin> getMedecinByEmail(String email) throws ServiceException;

    
    long countMedecins() throws ServiceException;
    boolean medecinExists(Long id) throws ServiceException;

    
    List<medecin> getMedecinsPaginated(int page, int size) throws ServiceException;
}