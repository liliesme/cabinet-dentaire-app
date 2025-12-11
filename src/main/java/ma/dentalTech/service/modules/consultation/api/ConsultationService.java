package ma.dentalTech.service.modules.consultation.api;

import ma.dentalTech.entities.pat.Consultation;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConsultationService {

    
    List<Consultation> getAllConsultations() throws ServiceException;
    Optional<Consultation> getConsultationById(Long id) throws ServiceException;
    void createConsultation(Consultation consultation) throws ServiceException, ValidationException;
    void updateConsultation(Consultation consultation) throws ServiceException, ValidationException;
    void deleteConsultation(Long id) throws ServiceException;

    
    List<Consultation> getConsultationsByPatient(Long patientId) throws ServiceException;
    List<Consultation> getConsultationsBetweenDates(LocalDateTime start, LocalDateTime end) throws ServiceException;
    Optional<Consultation> getLastConsultationOfPatient(Long patientId) throws ServiceException;

    
    long countConsultationsByMedecin(Long medecinId) throws ServiceException;
    long countConsultationsToday() throws ServiceException;
}