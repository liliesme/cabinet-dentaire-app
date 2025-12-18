package ma.dentalTech.service.modules.facture.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.entities.pat.Facture;
import ma.dentalTech.repository.modules.facture.api.FactureRepository;
import ma.dentalTech.service.modules.facture.api.FactureService;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FactureServiceImpl implements FactureService {

    private FactureRepository repository;

    @Override
    public List<Facture> getAllFactures() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des factures", e);
        }
    }

    @Override
    public Optional<Facture> getFactureById(Long id) throws ServiceException {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw new ServiceException(
                    "Erreur lors de la recherche de la facture #" + id, e
            );
        }
    }


    @Override
    public void createFacture(Facture facture) throws ServiceException, ValidationException {
        
        if (facture.getPatientId() == null) {
            throw new ValidationException("Patient requis");
        }
        if (facture.getMontantTotal() == null || facture.getMontantTotal() <= 0) {
            throw new ValidationException("Montant total invalide");
        }

        
        if (facture.getDateFacture() == null) {
            facture.setDateFacture(LocalDateTime.now());
        }

        
        if (facture.getNumeroFacture() == null || facture.getNumeroFacture().isEmpty()) {
            facture.setNumeroFacture(generateNumeroFacture());
        }

        
        if (facture.getTotalPaye() == null) {
            facture.setTotalPaye(0.0);
        }

        try {
            repository.create(facture);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la facture", e);
        }
    }

    @Override
    public void updateFacture(Facture facture) throws ServiceException, ValidationException {
        if (facture.getId() == null) {
            throw new ValidationException("ID requis pour la mise à jour");
        }

        try {
            repository.update(facture);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la facture", e);
        }
    }

    @Override
    public void deleteFacture(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la facture", e);
        }
    }

    @Override
    public void enregistrerPaiement(Long factureId, Double montant) throws ServiceException, ValidationException {
        if (montant == null || montant <= 0) {
            throw new ValidationException("Montant de paiement invalide");
        }

        try {
            Facture facture = repository.findById(factureId)
                    .orElseThrow(() -> new ServiceException("Facture introuvable"));

            Double nouveauTotal = facture.getTotalPaye() + montant;

            if (nouveauTotal > facture.getMontantTotal()) {
                throw new ValidationException("Le paiement dépasse le montant total de la facture");
            }

            facture.setTotalPaye(nouveauTotal);

            if (nouveauTotal.equals(facture.getMontantTotal())) {
                facture.setEstPayee(true);
            }

            repository.update(facture);

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de l'enregistrement du paiement", e);
        }
    }

    @Override
    public void marquerCommePayee(Long factureId) throws ServiceException {
        try {
            Facture facture = repository.findById(factureId)
                    .orElseThrow(() -> new ServiceException("Facture introuvable"));

            facture.setEstPayee(true);
            facture.setTotalPaye(facture.getMontantTotal());
            repository.update(facture);

        } catch (ServiceException e) {
            throw e; // نعيد نفس الاستثناء
        } catch (Exception e) {
            throw new ServiceException(
                    "Erreur lors du marquage de la facture comme payée", e
            );
        }
    }


    @Override
    public List<Facture> getFacturesByPatient(Long patientId) throws ServiceException {
        try {
            return repository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des factures du patient", e);
        }
    }

    @Override
    public List<Facture> getFacturesImpayees() throws ServiceException {
        try {
            return repository.findByEstPayee(false);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des factures impayées", e);
        }
    }

    @Override
    public List<Facture> getFacturesPayees() throws ServiceException {
        try {
            return repository.findByEstPayee(true);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche des factures payées", e);
        }
    }

    @Override
    public List<Facture> getFacturesBetweenDates(LocalDateTime start, LocalDateTime end) throws ServiceException {
        try {
            return repository.findByDateBetween(start, end);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par dates", e);
        }
    }

    @Override
    public Optional<Facture> getFactureByNumero(String numeroFacture) throws ServiceException {
        try {
            return repository.findByNumeroFacture(numeroFacture);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par numéro", e);
        }
    }

    @Override
    public Double getTotalFacturesPayees() throws ServiceException {
        try {
            return repository.findByEstPayee(true).stream()
                    .mapToDouble(Facture::getMontantTotal)
                    .sum();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul du total payé", e);
        }
    }

    @Override
    public Double getTotalFacturesImpayees() throws ServiceException {
        try {
            return repository.findByEstPayee(false).stream()
                    .mapToDouble(f -> f.getMontantTotal() - f.getTotalPaye())
                    .sum();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul du total impayé", e);
        }
    }

    @Override
    public long countFacturesImpayees() throws ServiceException {
        try {
            return repository.findByEstPayee(false).size();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des factures impayées", e);
        }
    }

    @Override
    public byte[] generateFacturePDF(Long factureId) throws ServiceException {
        
        throw new UnsupportedOperationException("Génération PDF à implémenter");
    }

    
    private String generateNumeroFacture() throws ServiceException {
        try {
            int year = LocalDateTime.now().getYear();
            long count = repository.findAll().stream()
                    .filter(f -> f.getDateFacture().getYear() == year)
                    .count();

            return String.format("FACT-%d-%04d", year, count + 1);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la génération du numéro de facture", e);
        }
    }
}