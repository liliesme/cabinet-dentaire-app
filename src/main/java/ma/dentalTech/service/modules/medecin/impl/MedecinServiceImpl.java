package ma.dentalTech.service.modules.medecin.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.medecin;
import ma.dentalTech.repository.modules.medecin.api.MedecinRepository;
import ma.dentalTech.service.modules.medecin.api.MedecinService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MedecinServiceImpl implements MedecinService {

    private MedecinRepository repository;

    @Override
    public List<medecin> getAllMedecins() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des médecins", e);
        }
    }

    @Override
    public Optional<medecin> getMedecinById(Long id) throws ServiceException {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw new ServiceException(
                    "Erreur lors de la recherche du médecin #" + id, e
            );
        }
    }


    @Override
    public void createMedecin(medecin medecin) throws ServiceException, ValidationException {
        
        Validators.notBlank(medecin.getNom(), "Nom");
        Validators.notBlank(medecin.getPrenom(), "Prénom");
        Validators.email(medecin.getEmail());
        Validators.phone(medecin.getTelephone());

        
        if (repository.findByEmail(medecin.getEmail()).isPresent()) {
            throw new ValidationException("Email déjà utilisé");
        }

        
        if (medecin.getDateCreation() == null) {
            medecin.setDateCreation(LocalDateTime.now());
        }

        try {
            repository.create(medecin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du médecin", e);
        }
    }

    @Override
    public void updateMedecin(medecin medecin) throws ServiceException, ValidationException {
        Validators.notBlank(medecin.getNom(), "Nom");
        Validators.notBlank(medecin.getPrenom(), "Prénom");
        Validators.email(medecin.getEmail());

        try {
            repository.update(medecin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du médecin", e);
        }
    }

    @Override
    public void deleteMedecin(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du médecin", e);
        }
    }

    @Override
    public List<medecin> searchMedecinsByNom(String keyword) throws ServiceException {
        try {
            return repository.searchByNomPrenom(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche", e);
        }
    }

    @Override
    public List<medecin> getMedecinsBySpecialite(String specialite) throws ServiceException {
        try {
            return repository.findBySpecialite(specialite);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par spécialité", e);
        }
    }

    @Override
    public Optional<medecin> getMedecinByEmail(String email) throws ServiceException {
        try {
            return repository.findByEmail(email);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par email", e);
        }
    }

    @Override
    public long countMedecins() throws ServiceException {
        try {
            return repository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage", e);
        }
    }

    @Override
    public boolean medecinExists(Long id) throws ServiceException {
        try {
            return repository.existsById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification", e);
        }
    }

    @Override
    public List<medecin> getMedecinsPaginated(int page, int size) throws ServiceException {
        try {
            int offset = page * size;
            return repository.findPage(size, offset);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la pagination", e);
        }
    }
}