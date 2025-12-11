package ma.dentalTech.service.modules.ordonnance.api;

import ma.dentalTech.entities.pat.Ordonnance;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrdonnanceService {


    List<Ordonnance> getAllOrdonnances() throws ServiceException;
    Optional<Ordonnance> getOrdonnanceById(Long id) throws ServiceException;
    void createOrdonnance(Ordonnance ordonnance) throws ServiceException, ValidationException;
    void updateOrdonnance(Ordonnance ordonnance) throws ServiceException, ValidationException;
    void deleteOrdonnance(Long id) throws ServiceException;


    List<Ordonnance> getOrdonnancesByPatient(Long patientId) throws ServiceException;
    List<Ordonnance> getOrdonnancesByMedecin(Long utilisateurId) throws ServiceException;
    List<Ordonnance> getOrdonnancesBetweenDates(LocalDateTime start, LocalDateTime end) throws ServiceException;


    byte[] generateOrdonnancePDF(Long ordonnanceId) throws ServiceException;
}