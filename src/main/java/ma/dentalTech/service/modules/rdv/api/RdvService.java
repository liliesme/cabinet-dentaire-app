package ma.dentalTech.service.modules.rdv.api;

import ma.dentalTech.entities.pat.RDV;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RdvService {


    List<RDV> getAllRdv() throws ServiceException;
    Optional<RDV> getRdvById(Long id) throws ServiceException;
    void createRdv(RDV rdv) throws ServiceException, ValidationException;
    void updateRdv(RDV rdv) throws ServiceException, ValidationException;
    void deleteRdv(Long id) throws ServiceException;
    void cancelRdv(Long id) throws ServiceException;


    List<RDV> getRdvByPatient(Long patientId) throws ServiceException;
    List<RDV> getRdvByMedecin(Long medecinId) throws ServiceException;
    List<RDV> getRdvByDate(LocalDate date) throws ServiceException;
    List<RDV> getRdvBetweenDates(LocalDateTime start, LocalDateTime end) throws ServiceException;
    List<RDV> getTodayRdv() throws ServiceException;


    boolean isSlotAvailable(Long medecinId, LocalDateTime dateHeure) throws ServiceException;
    List<LocalDateTime> getAvailableSlots(Long medecinId, LocalDate date) throws ServiceException;
}