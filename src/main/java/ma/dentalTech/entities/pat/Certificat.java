package ma.dentalTech.entities.pat;

import java.time.LocalDateTime;



public class Certificat {


    private Long id;
    private Long patientId;
    private Long utilisateurId;
    private LocalDateTime dateEmission;
    private String typeCertificat;
    private Integer dureeEnJours;
    private String motif;



    public Certificat() {
    }


    public Certificat(Long id, Long patientId, Long utilisateurId, LocalDateTime dateEmission, String typeCertificat, Integer dureeEnJours, String motif) {
        this.id = id;
        this.patientId = patientId;
        this.utilisateurId = utilisateurId;
        this.dateEmission = dateEmission;
        this.typeCertificat = typeCertificat;
        this.dureeEnJours = dureeEnJours;
        this.motif = motif;
    }

    

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    public LocalDateTime getDateEmission() { return dateEmission; }
    public void setDateEmission(LocalDateTime dateEmission) { this.dateEmission = dateEmission; }

    public String getTypeCertificat() { return typeCertificat; }
    public void setTypeCertificat(String typeCertificat) { this.typeCertificat = typeCertificat; }

    public Integer getDureeEnJours() { return dureeEnJours; }
    public void setDureeEnJours(Integer dureeEnJours) { this.dureeEnJours = dureeEnJours; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
}