package ma.dentalTech.service.modules.certificat.api;

import ma.dentalTech.entities.pat.Certificat;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CertificatService {

    
    List<Certificat> getAllCertificats() throws ServiceException;
    Optional<Certificat> getCertificatById(Long id) throws ServiceException;
    void createCertificat(Certificat certificat) throws ServiceException, ValidationException;
    void updateCertificat(Certificat certificat) throws ServiceException, ValidationException;
    void deleteCertificat(Long id) throws ServiceException;

    
    List<Certificat> getCertificatsByPatient(Long patientId) throws ServiceException;
    List<Certificat> getCertificatsByMedecin(Long utilisateurId) throws ServiceException;
    List<Certificat> getCertificatsBetweenDates(LocalDateTime start, LocalDateTime end) throws ServiceException;

    
    byte[] generateCertificatPDF(Long certificatId) throws ServiceException;
}