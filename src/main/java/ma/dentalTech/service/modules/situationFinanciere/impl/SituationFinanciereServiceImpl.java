package ma.dentalTech.service.modules.situationFinanciere.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.SituationFinanciere;
import ma.dentalTech.entities.enums.StatutFinancier;
import ma.dentalTech.repository.modules.SituationFinanciere.api.SituationFinanciereRepository;
import ma.dentalTech.service.modules.situationFinanciere.api.SituationFinanciereService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SituationFinanciereServiceImpl implements SituationFinanciereService {

    private SituationFinanciereRepository repository;

    @Override
    public List<SituationFinanciere> getAllSituations() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des situations financières", e);
        }
    }

    @Override
    public Optional<SituationFinanciere> getSituationById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(repository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de la situation #" + id, e);
        }
    }

    @Override
    public Optional<SituationFinanciere> getSituationByPatient(Long patientId) throws ServiceException {
        try {
            return repository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de la situation du patient", e);
        }
    }

    @Override
    public void createSituation(SituationFinanciere situation) throws ServiceException, ValidationException {
        
        if (situation.getPatientId() == null) {
            throw new ValidationException("Patient requis");
        }

        
        if (repository.findByPatientId(situation.getPatientId()).isPresent()) {
            throw new ValidationException("Une situation financière existe déjà pour ce patient");
        }

        
        if (situation.getTotalDesActes() == null) {
            situation.setTotalDesActes(0.0);
        }
        if (situation.getTotalePaye() == null) {
            situation.setTotalePaye(0.0);
        }

        
        situation.setStatut(determinerStatut(situation));

        try {
            repository.create(situation);
        } catch (Exception e) {
            throw new ServiceException("Errlors de la création de la situation financière", e);
        }
    }
    @Override
    public void updateSituation(SituationFinanciere situation) throws ServiceException, ValidationException {
        if (situation.getId() == null) {
            throw new ValidationException("ID requis pour la mise à jour");
        }

        
        situation.setStatut(determinerStatut(situation));

        try {
            repository.update(situation);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la situation financière", e);
        }
    }

    @Override
    public void deleteSituation(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la situation financière", e);
        }
    }

    @Override
    public void recalculerSituation(Long patientId) throws ServiceException {
        try {
            Optional<SituationFinanciere> optSituation = repository.findByPatientId(patientId);

            if (optSituation.isEmpty()) {
                throw new ServiceException("Aucune situation financière trouvée pour ce patient");
            }

            SituationFinanciere situation = optSituation.get();

            
            
            situation.setStatut(determinerStatut(situation));

            repository.update(situation);

        } catch (Exception e) {
            throw new ServiceException("Erreur lors du recalcul de la situation", e);
        }
    }

    @Override
    public void ajouterActe(Long patientId, Double montant) throws ServiceException {
        try {
            Optional<SituationFinanciere> optSituation = repository.findByPatientId(patientId);
            SituationFinanciere situation;

            if (optSituation.isEmpty()) {
                
                situation = new SituationFinanciere();
                situation.setPatientId(patientId);
                situation.setTotalDesActes(montant);
                situation.setTotalePaye(0.0);
                situation.setStatut(StatutFinancier.NON_PAYE);
                repository.create(situation);
            } else {
                situation = optSituation.get();
                situation.setTotalDesActes(situation.getTotalDesActes() + montant);
                situation.setStatut(determinerStatut(situation));
                repository.update(situation);
            }

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de l'ajout de l'acte", e);
        }
    }

    @Override
    public void enregistrerPaiement(Long patientId, Double montant) throws ServiceException {
        try {
            Optional<SituationFinanciere> optSituation = repository.findByPatientId(patientId);

            if (optSituation.isEmpty()) {
                throw new ServiceException("Aucune situation financière trouvée");
            }

            SituationFinanciere situation = optSituation.get();
            situation.setTotalePaye(situation.getTotalePaye() + montant);
            situation.setStatut(determinerStatut(situation));

            repository.update(situation);

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de l'enregistrement du paiement", e);
        }
    }

    @Override
    public List<SituationFinanciere> getSituationsEnAttente() throws ServiceException {
        try {
            return repository.findAll().stream()
                    .filter(s -> s.getStatut() == StatutFinancier.EN_ATTENTE_VALIDATION)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des situations en attente", e);
        }
    }

    @Override
    public List<SituationFinanciere> getSituationsImpayees() throws ServiceException {
        try {
            return repository.findAll().stream()
                    .filter(s -> s.getStatut() == StatutFinancier.NON_PAYE)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des situations impayées", e);
        }
    }

    
    private StatutFinancier determinerStatut(SituationFinanciere situation) {
        Double totalActes = situation.getTotalDesActes();
        Double totalPaye = situation.getTotalePaye();

        if (totalActes == null || totalPaye == null) {
            return StatutFinancier.EN_ATTENTE_VALIDATION;
        }

        if (totalPaye.equals(totalActes)) {
            return StatutFinancier.SOLDE_ACTUEL;
        } else if (totalPaye > 0 && totalPaye < totalActes) {
            return StatutFinancier.AVANCE_PARTIELLE;
        } else if (totalPaye == 0) {
            return StatutFinancier.NON_PAYE;
        }

        return StatutFinancier.EN_ATTENTE_VALIDATION;
    }
}