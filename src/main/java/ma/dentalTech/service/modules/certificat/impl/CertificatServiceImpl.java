package ma.dentalTech.service.modules.certificat.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.Certificat;
import ma.dentalTech.repository.modules.Certificat.api.CertificatRepository;
import ma.dentalTech.service.modules.certificat.api.CertificatService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CertificatServiceImpl implements CertificatService {

    private CertificatRepository repository;

    @Override
    public List<Certificat> getAllCertificats() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des certificats", e);
        }
    }

    @Override
    public Optional<Certificat> getCertificatById(Long id) throws ServiceException {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw new ServiceException(
                    "Erreur lors de la recherche du certificat #" + id, e
            );
        }
    }


    @Override
    public void createCertificat(Certificat certificat) throws ServiceException, ValidationException {
        
        if (certificat.getPatientId() == null) {
            throw new ValidationException("Patient requis");
        }
        if (certificat.getUtilisateurId() == null) {
            throw new ValidationException("Médecin requis");
        }
        Validators.notBlank(certificat.getTypeCertificat(), "Type de certificat");

        if (certificat.getDureeEnJours() == null || certificat.getDureeEnJours() <= 0) {
            throw new ValidationException("Durée invalide");
        }

        
        if (certificat.getDateEmission() == null) {
            certificat.setDateEmission(LocalDateTime.now());
        }

        try {
            repository.create(certificat);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du certificat", e);
        }
    }

    @Override
    public void updateCertificat(Certificat certificat) throws ServiceException, ValidationException {
        if (certificat.getId() == null) {
            throw new ValidationException("ID requis pour la mise à jour");
        }

        try {
            repository.update(certificat);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du certificat", e);
        }
    }

    @Override
    public void deleteCertificat(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du certificat", e);
        }
    }

    @Override
    public List<Certificat> getCertificatsByPatient(Long patientId) throws ServiceException {
        try {
            return repository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des certificats du patient", e);
        }
    }

    @Override
    public List<Certificat> getCertificatsByMedecin(Long utilisateurId) throws ServiceException {
        try {
            return repository.findByUtilisateurId(utilisateurId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des certificats du médecin", e);
        }
    }

    @Override
    public List<Certificat> getCertificatsBetweenDates(LocalDateTime start, LocalDateTime end) throws ServiceException {
        try {
            return repository.findByDateBetween(start, end);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par dates", e);
        }
    }

    @Override
    public byte[] generateCertificatPDF(Long certificatId) throws ServiceException {
        
        throw new UnsupportedOperationException("Génération PDF à implémenter");
    }
}