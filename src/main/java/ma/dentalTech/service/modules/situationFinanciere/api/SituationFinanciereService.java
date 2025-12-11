package ma.dentalTech.service.modules.situationFinanciere.api;

import ma.dentalTech.entities.pat.SituationFinanciere;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.util.List;
import java.util.Optional;

public interface SituationFinanciereService {

    
    List<SituationFinanciere> getAllSituations() throws ServiceException;
    Optional<SituationFinanciere> getSituationById(Long id) throws ServiceException;
    Optional<SituationFinanciere> getSituationByPatient(Long patientId) throws ServiceException;
    void createSituation(SituationFinanciere situation) throws ServiceException, ValidationException;
    void updateSituation(SituationFinanciere situation) throws ServiceException, ValidationException;
    void deleteSituation(Long id) throws ServiceException;

    
    void recalculerSituation(Long patientId) throws ServiceException;
    void ajouterActe(Long patientId, Double montant) throws ServiceException;
    void enregistrerPaiement(Long patientId, Double montant) throws ServiceException;

    
    List<SituationFinanciere> getSituationsEnAttente() throws ServiceException;
    List<SituationFinanciere> getSituationsImpayees() throws ServiceException;
}