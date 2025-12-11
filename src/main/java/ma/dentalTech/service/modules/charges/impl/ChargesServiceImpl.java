package ma.dentalTech.service.modules.charges.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.Charges;
import ma.dentalTech.repository.modules.charges.api.ChargesRepository;
import ma.dentalTech.service.modules.charges.api.ChargesService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ChargesServiceImpl implements ChargesService {

    private ChargesRepository repository;

    @Override
    public List<Charges> getAllCharges() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des charges", e);
        }
    }

    @Override
    public Optional<Charges> getChargeById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(repository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de la charge #" + id, e);
        }
    }

    @Override
    public void createCharge(Charges charge) throws ServiceException, ValidationException {
        
        Validators.notBlank(charge.getTitre(), "Titre");

        if (charge.getMontant() == null || charge.getMontant() <= 0) {
            throw new ValidationException("Montant invalide");
        }

        if (charge.getUtilisateurId() == null) {
            throw new ValidationException("Utilisateur requis");
        }

        try {
            repository.create(charge);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la charge", e);
        }
    }

    @Override
    public void updateCharge(Charges charge) throws ServiceException, ValidationException {
        if (charge.getId() == null) {
            throw new ValidationException("ID requis pour la mise à jour");
        }

        Validators.notBlank(charge.getTitre(), "Titre");

        try {
            repository.update(charge);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la charge", e);
        }
    }

    @Override
    public void deleteCharge(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la charge", e);
        }
    }

    @Override
    public List<Charges> getChargesByTitre(String titre) throws ServiceException {
        try {
            return repository.findByTitre(titre);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par titre", e);
        }
    }

    @Override
    public List<Charges> getChargesBetweenDates(LocalDate start, LocalDate end) throws ServiceException {
        try {
            return repository.findByDateBetween(start, end);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par dates", e);
        }
    }

    @Override
    public List<Charges> getChargesOfMonth(int year, int month) throws ServiceException {
        try {
            LocalDate start = LocalDate.of(year, month, 1);
            LocalDate end = start.plusMonths(1).minusDays(1);
            return repository.findByDateBetween(start, end);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par mois", e);
        }
    }

    @Override
    public Double getTotalCharges() throws ServiceException {
        try {
            return repository.findAll().stream()
                    .mapToDouble(Charges::getMontant)
                    .sum();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul du total", e);
        }
    }

    @Override
    public Double getTotalChargesOfMonth(int year, int month) throws ServiceException {
        try {
            return getChargesOfMonth(year, month).stream()
                    .mapToDouble(Charges::getMontant)
                    .sum();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul du total mensuel", e);
        }
    }
}