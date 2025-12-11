package ma.dentalTech.service.modules.consultation.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.Consultation;
import ma.dentalTech.entities.enums.StatutRendezVous;
import ma.dentalTech.repository.modules.Consultation.api.ConsultationRepository;
import ma.dentalTech.service.modules.consultation.api.ConsultationService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private ConsultationRepository repository;

    @Override
    public List<Consultation> getAllConsultations() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des consultations", e);
        }
    }

    @Override
    public Optional<Consultation> getConsultationById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(repository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de la consultation #" + id, e);
        }
    }

    @Override
    public void createConsultation(Consultation consultation) throws ServiceException, ValidationException {
        
        if (consultation.getPatientId() == null) {
            throw new ValidationException("Patient requis");
        }
        if (consultation.getUtilisateurId() == null) {
            throw new ValidationException("Médecin requis");
        }
        if (consultation.getDateConsultation() == null) {
            consultation.setDateConsultation(LocalDateTime.now());
        }

        Validators.notBlank(consultation.getDiagnostic(), "Diagnostic");
        Validators.notBlank(consultation.getTraitement(), "Traitement");

        
        if (consultation.getStatut() == null) {
            consultation.setStatut(StatutRendezVous.ANNULE);
        }

        try {
            repository.create(consultation);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la consultation", e);
        }
    }

    @Override
    public void updateConsultation(Consultation consultation) throws ServiceException, ValidationException {
        if (consultation.getId() == null) {
            throw new ValidationException("ID requis pour la mise à jour");
        }

        Validators.notBlank(consultation.getDiagnostic(), "Diagnostic");
        Validators.notBlank(consultation.getTraitement(), "Traitement");

        try {
            repository.update(consultation);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la consultation", e);
        }
    }

    @Override
    public void deleteConsultation(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la consultation", e);
        }
    }

    @Override
    public List<Consultation> getConsultationsByPatient(Long patientId) throws ServiceException {
        try {
            return repository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des consultations du patient", e);
        }
    }

    @Override
    public List<Consultation> getConsultationsBetweenDates(LocalDateTime start, LocalDateTime end) throws ServiceException {
        try {
            return repository.findByDateBetween(start, end);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par dates", e);
        }
    }

    @Override
    public Optional<Consultation> getLastConsultationOfPatient(Long patientId) throws ServiceException {
        try {
            List<Consultation> consultations = repository.findByPatientId(patientId);
            return consultations.stream()
                    .max(Comparator.comparing(Consultation::getDateConsultation));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de la dernière consultation", e);
        }
    }

    @Override
    public long countConsultationsByMedecin(Long medecinId) throws ServiceException {
        try {
            return repository.findAll().stream()
                    .filter(c -> c.getUtilisateurId().equals(medecinId))
                    .count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des consultations", e);
        }
    }

    @Override
    public long countConsultationsToday() throws ServiceException {
        try {
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay().minusNanos(1);
            return repository.findByDateBetween(startOfDay, endOfDay).size();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des consultations du jour", e);
        }
    }
}