package ma.dentalTech.repository.modules.Certificat.impl.memoryBase;


import ma.dentalTech.entities.Certificat;
import ma.dentalTech.repository.modules.Certificat.api.CertificatRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class CertificatRepositoryImpl implements CertificatRepository {

    private final List<Certificat> data = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public CertificatRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();


        data.add(Certificat.builder()
                .id(nextId.getAndIncrement()).patientId(101L).utilisateurId(1L)
                .dateEmission(now.minusDays(1))
                .typeCertificat("Arrêt de travail").dureeEnJours(3)
                .motif("Post-extraction dentaire complexe.").build());

        data.add(Certificat.builder()
                .id(nextId.getAndIncrement()).patientId(102L).utilisateurId(1L)
                .dateEmission(now.minusMonths(2))
                .typeCertificat("Aptitude sportive").dureeEnJours(0)
                .motif("Aptitude dentaire pour la compétition.").build());
    }



    @Override
    public List<Certificat> findAll() {
        return data.stream()
                .sorted(Comparator.comparing(Certificat::getDateEmission).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Certificat findById(Long id) {
        return findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void create(Certificat cert) {
        if (cert.getId() == null) {
            cert.setId(nextId.getAndIncrement());
        }
        data.add(cert);
    }

    @Override
    public void update(Certificat cert) {
        deleteById(cert.getId());
        data.add(cert);
        data.sort(Comparator.comparing(Certificat::getId));
    }

    @Override
    public void delete(Certificat cert) { data.removeIf(c -> c.getId().equals(cert.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(c -> c.getId().equals(id)); }




    @Override
    public List<Certificat> findByPatientId(Long patientId) {
        return data.stream()
                .filter(c -> c.getPatientId().equals(patientId))
                .sorted(Comparator.comparing(Certificat::getDateEmission).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Certificat> findByUtilisateurId(Long utilisateurId) {
        return data.stream()
                .filter(c -> c.getUtilisateurId().equals(utilisateurId))
                .sorted(Comparator.comparing(Certificat::getDateEmission).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Certificat> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        return data.stream()
                .filter(c -> !c.getDateEmission().isBefore(start) && !c.getDateEmission().isAfter(end))
                .sorted(Comparator.comparing(Certificat::getDateEmission).reversed())
                .collect(Collectors.toList());
    }
}