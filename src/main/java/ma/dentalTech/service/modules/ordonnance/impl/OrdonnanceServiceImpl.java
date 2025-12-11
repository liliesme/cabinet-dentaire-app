package ma.dentalTech.service.modules.ordonnance.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.Ordonnance;
import ma.dentalTech.repository.modules.Ordonnance.api.OrdonnanceRepository;
import ma.dentalTech.service.modules.ordonnance.api.OrdonnanceService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class OrdonnanceServiceImpl implements OrdonnanceService {

    private OrdonnanceRepository repository;

    @Override
    public List<Ordonnance> getAllOrdonnances() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des ordonnances", e);
        }
    }

    @Override
    public Optional<Ordonnance> getOrdonnanceById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(repository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de l'ordonnance #" + id, e);
        }
    }

    @Override
    public void createOrdonnance(Ordonnance ordonnance) throws ServiceException, ValidationException {
        
        if (ordonnance.getPatientId() == null) {
            throw new ValidationException("Patient requis");
        }
        if (ordonnance.getUtilisateurId() == null) {
            throw new ValidationException("Médecin requis");
        }
        if (ordonnance.getLignesMedicaments() == null || ordonnance.getLignesMedicaments().isEmpty()) {
            throw new ValidationException("Au moins un médicament requis");
        }

        
        if (ordonnance.getDateOrdonnance() == null) {
            ordonnance.setDateOrdonnance(LocalDateTime.now());
        }

        try {
            repository.create(ordonnance);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de l'ordonnance", e);
        }
    }

    @Override
    public void updateOrdonnance(Ordonnance ordonnance) throws ServiceException, ValidationException {
        if (ordonnance.getId() == null) {
            throw new ValidationException("ID requis pour la mise à jour");
        }

        try {
            repository.update(ordonnance);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de l'ordonnance", e);
        }
    }

    @Override
    public void deleteOrdonnance(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de l'ordonnance", e);
        }
    }

    @Override
    public List<Ordonnance> getOrdonnancesByPatient(Long patientId) throws ServiceException {
        try {
            return repository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des ordonnances du patient", e);
        }
    }

    @Override
    public List<Ordonnance> getOrdonnancesByMedecin(Long utilisateurId) throws ServiceException {
        try {
            return repository.findByUtilisateurId(utilisateurId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des ordonnances du médecin", e);
        }
    }

    @Override
    public List<Ordonnance> getOrdonnancesBetweenDates(LocalDateTime start, LocalDateTime end) throws ServiceException {
        try {
            return repository.findByDateBetween(start, end);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par dates", e);
        }
    }

    @Override
    public byte[] generateOrdonnancePDF(Long ordonnanceId) throws ServiceException {
        
        throw new UnsupportedOperationException("Génération PDF à implémenter");
    }
}