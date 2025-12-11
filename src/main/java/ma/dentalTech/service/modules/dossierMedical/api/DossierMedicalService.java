package ma.dentalTech.service.modules.dossierMedical.api;

import ma.dentalTech.entities.pat.DossierMedicale;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.util.Optional;

public interface DossierMedicalService {

    
    Optional<DossierMedicale> getDossierMedicalById(Long id) throws ServiceException;
    Optional<DossierMedicale> getDossierMedicalByPatient(Long patientId) throws ServiceException;
    void createDossierMedical(DossierMedicale dossier) throws ServiceException, ValidationException;
    void updateDossierMedical(DossierMedicale dossier) throws ServiceException, ValidationException;
    void deleteDossierMedical(Long id) throws ServiceException;

    
    boolean patientHasDossierMedical(Long patientId) throws ServiceException;

    
    byte[] exportDossierCompletPDF(Long patientId) throws ServiceException;
}