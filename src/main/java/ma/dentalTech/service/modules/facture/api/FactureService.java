package ma.dentalTech.service.modules.facture.api;

import ma.dentalTech.entities.pat.Facture;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FactureService {

    
    List<Facture> getAllFactures() throws ServiceException;
    Optional<Facture> getFactureById(Long id) throws ServiceException;
    void createFacture(Facture facture) throws ServiceException, ValidationException;
    void updateFacture(Facture facture) throws ServiceException, ValidationException;
    void deleteFacture(Long id) throws ServiceException;

    
    void enregistrerPaiement(Long factureId, Double montant) throws ServiceException, ValidationException;
    void marquerCommePayee(Long factureId) throws ServiceException;

    
    List<Facture> getFacturesByPatient(Long patientId) throws ServiceException;
    List<Facture> getFacturesImpayees() throws ServiceException;
    List<Facture> getFacturesPayees() throws ServiceException;
    List<Facture> getFacturesBetweenDates(LocalDateTime start, LocalDateTime end) throws ServiceException;
    Optional<Facture> getFactureByNumero(String numeroFacture) throws ServiceException;

    
    Double getTotalFacturesPayees() throws ServiceException;
    Double getTotalFacturesImpayees() throws ServiceException;
    long countFacturesImpayees() throws ServiceException;

    
    byte[] generateFacturePDF(Long factureId) throws ServiceException;
}