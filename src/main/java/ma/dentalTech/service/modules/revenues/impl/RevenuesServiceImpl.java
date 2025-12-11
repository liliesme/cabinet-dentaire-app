package ma.dentalTech.service.modules.revenues.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.Revenues;
import ma.dentalTech.repository.modules.revenues.api.RevenuesRepository;
import ma.dentalTech.service.modules.revenues.api.RevenuesService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RevenuesServiceImpl implements RevenuesService {

    private RevenuesRepository repository;

    @Override
    public List<Revenues> getAllRevenues() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des revenus", e);
        }
    }

    @Override
    public Optional<Revenues> getRevenueById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(repository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche du revenu #" + id, e);
        }
    }

    @Override
    public void createRevenue(Revenues revenue) throws ServiceException, ValidationException {
        
        Validators.notBlank(revenue.getTitre(), "Titre");

        if (revenue.getMontant() == null || revenue.getMontant() <= 0) {
            throw new ValidationException("Montant invalide");
        }

        if (revenue.getUtilisateurId() == null) {
            throw new ValidationException("Utilisateur requis");
        }

        try {
            repository.create(revenue);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du revenu", e);
        }
    }

    @Override
    public void updateRevenue(Revenues revenue) throws ServiceException, ValidationException {
        if (revenue.getId() == null) {
            throw new ValidationException("ID requis pour la mise à jour");
        }

        Validators.notBlank(revenue.getTitre(), "Titre");

        try {
            repository.update(revenue);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du revenu", e);
        }
    }

    @Override
    public void deleteRevenue(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du revenu", e);
        }
    }

    @Override
    public List<Revenues> getRevenuesByTitre(String titre) throws ServiceException {
        try {
            return repository.findByTitre(titre);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par titre", e);
        }
    }

    @Override
    public List<Revenues> getRevenuesBetweenDates(LocalDate start, LocalDate end) throws ServiceException {
        try {
            return repository.findByDateBetween(start, end);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par dates", e);
        }
    }

    @Override
    public List<Revenues> getRevenuesOfMonth(int year, int month) throws ServiceException {
        try {
            LocalDate start = LocalDate.of(year, month, 1);
            LocalDate end = start.plusMonths(1).minusDays(1);
            return repository.findByDateBetween(start, end);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par mois", e);
        }
    }

    @Override
    public Double getTotalRevenues() throws ServiceException {
        try {
            return repository.findAll().stream()
                    .mapToDouble(Revenues::getMontant)
                    .sum();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul du total", e);
        }
    }

    @Override
    public Double getTotalRevenuesOfMonth(int year, int month) throws ServiceException {
        try {
            return getRevenuesOfMonth(year, month).stream()
                    .mapToDouble(Revenues::getMontant)
                    .sum();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul du total mensuel", e);
        }
    }
}