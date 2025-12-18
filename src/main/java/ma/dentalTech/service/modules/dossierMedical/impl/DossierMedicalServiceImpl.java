package ma.dentalTech.service.modules.dossierMedical.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.DossierMedicale;
import ma.dentalTech.repository.modules.DossierMedicale.api.DossierMedicaleRepository;
import ma.dentalTech.service.modules.dossierMedical.api.DossierMedicalService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class DossierMedicalServiceImpl implements DossierMedicalService {

    private DossierMedicaleRepository repository;

    @Override
    public Optional<DossierMedicale> getDossierMedicalById(Long id) throws ServiceException {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw new ServiceException(
                    "Erreur lors de la recherche du dossier médical #" + id, e
            );
        }
    }


    @Override
    public Optional<DossierMedicale> getDossierMedicalByPatient(Long patientId) throws ServiceException {
        try {
            return repository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche du dossier du patient", e);
        }
    }

    @Override
    public void createDossierMedical(DossierMedicale dossier) throws ServiceException, ValidationException {
        
        if (dossier.getPatientId() == null) {
            throw new ValidationException("Patient requis");
        }

        
        if (repository.findByPatientId(dossier.getPatientId()).isPresent()) {
            throw new ValidationException("Un dossier médical existe déjà pour ce patient");
        }

        
        if (dossier.getDateCreation() == null) {
            dossier.setDateCreation(LocalDateTime.now());
        }

        try {
            repository.create(dossier);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du dossier médical", e);
        }
    }

    @Override
    public void updateDossierMedical(DossierMedicale dossier) throws ServiceException, ValidationException {
        if (dossier.getId() == null) {
            throw new ValidationException("ID requis pour la mise à jour");
        }

        try {
            repository.update(dossier);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du dossier médical", e);
        }
    }

    @Override
    public void deleteDossierMedical(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du dossier médical", e);
        }
    }

    @Override
    public boolean patientHasDossierMedical(Long patientId) throws ServiceException {
        try {
            return repository.findByPatientId(patientId).isPresent();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification du dossier", e);
        }
    }

    @Override
    public byte[] exportDossierCompletPDF(Long patientId) throws ServiceException {
        
        throw new UnsupportedOperationException("Export PDF complet à implémenter");
    }
}