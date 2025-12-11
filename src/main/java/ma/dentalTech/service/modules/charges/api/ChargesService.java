package ma.dentalTech.service.modules.charges.api;

import ma.dentalTech.entities.pat.Charges;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChargesService {

    
    List<Charges> getAllCharges() throws ServiceException;
    Optional<Charges> getChargeById(Long id) throws ServiceException;
    void createCharge(Charges charge) throws ServiceException, ValidationException;
    void updateCharge(Charges charge) throws ServiceException, ValidationException;
    void deleteCharge(Long id) throws ServiceException;

    
    List<Charges> getChargesByTitre(String titre) throws ServiceException;
    List<Charges> getChargesBetweenDates(LocalDate start, LocalDate end) throws ServiceException;
    List<Charges> getChargesOfMonth(int year, int month) throws ServiceException;

    
    Double getTotalCharges() throws ServiceException;
    Double getTotalChargesOfMonth(int year, int month) throws ServiceException;
}