package ma.dentalTech.service.modules.rdv.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.RDV;
import ma.dentalTech.entities.enums.StatutRendezVous;
import ma.dentalTech.repository.modules.rdv.api.RdvRepository;
import ma.dentalTech.service.modules.rdv.api.RdvService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RdvServiceImpl implements RdvService {

    private RdvRepository repository;

    @Override
    public List<RDV> getAllRdv() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des RDV", e);
        }
    }

    @Override
    public Optional<RDV> getRdvById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(repository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche du RDV", e);
        }
    }

    @Override
    public void createRdv(RDV rdv) throws ServiceException, ValidationException {
        
        if (rdv.getPatientId() == null) {
            throw new ValidationException("Patient requis");
        }
        if (rdv.getMedecinId() == null) {
            throw new ValidationException("Médecin requis");
        }
        if (rdv.getDateHeure() == null) {
            throw new ValidationException("Date et heure requises");
        }
        Validators.notBlank(rdv.getMotif(), "Motif");

        
        if (!isSlotAvailable(rdv.getMedecinId(), rdv.getDateHeure())) {
            throw new ValidationException("Ce créneau n'est pas disponible");
        }

        
        if (rdv.getStatut() == null) {
            rdv.setStatut(StatutRendezVous.PLANIFIE);
        }

        try {
            repository.create(rdv);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du RDV", e);
        }
    }

    @Override
    public void updateRdv(RDV rdv) throws ServiceException, ValidationException {
        if (rdv.getId() == null) {
            throw new ValidationException("ID requis pour la mise à jour");
        }

        try {
            repository.update(rdv);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du RDV", e);
        }
    }

    @Override
    public void deleteRdv(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du RDV", e);
        }
    }

    @Override
    public void cancelRdv(Long id) throws ServiceException {
        try {
            RDV rdv = repository.findById(id);
            if (rdv == null) {
                throw new ServiceException("RDV introuvable");
            }
            rdv.setStatut(StatutRendezVous.ANNULE);
            repository.update(rdv);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de l'annulation du RDV", e);
        }
    }

    @Override
    public List<RDV> getRdvByPatient(Long patientId) throws ServiceException {
        try {
            return repository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des RDV du patient", e);
        }
    }

    @Override
    public List<RDV> getRdvByMedecin(Long medecinId) throws ServiceException {
        try {
            return repository.findByMedecinId(medecinId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des RDV du médecin", e);
        }
    }

    @Override
    public List<RDV> getRdvByDate(LocalDate date) throws ServiceException {
        try {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);
            return repository.findByDateBetween(start, end);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par date", e);
        }
    }

    @Override
    public List<RDV> getRdvBetweenDates(LocalDateTime start, LocalDateTime end) throws ServiceException {
        try {
            return repository.findByDateBetween(start, end);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche entre dates", e);
        }
    }

    @Override
    public List<RDV> getTodayRdv() throws ServiceException {
        try {
            return repository.findTodayRdv();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des RDV du jour", e);
        }
    }

    @Override
    public boolean isSlotAvailable(Long medecinId, LocalDateTime dateHeure) throws ServiceException {
        try {
            List<RDV> rdvs = repository.findByMedecinId(medecinId);
            return rdvs.stream()
                    .filter(r -> r.getStatut() == StatutRendezVous.PLANIFIE)
                    .noneMatch(r -> r.getDateHeure().equals(dateHeure));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification de disponibilité", e);
        }
    }

    @Override
    public List<LocalDateTime> getAvailableSlots(Long medecinId, LocalDate date) throws ServiceException {

        throw new UnsupportedOperationException("À implémenter");
    }
}