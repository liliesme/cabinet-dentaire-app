package ma.dentalTech.entities.pat;

import ma.dentalTech.entities.enums.StatutRendezVous;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Consultation {

    
    private Long id;
    private Long patientId;
    private Long utilisateurId;
    private LocalDateTime dateConsultation;
    private String diagnostic;
    private String traitement;

    
    private StatutRendezVous statut;
    private String observationMedecin;

    
    private InterventionMedecin interventionMedecin;
    private List<Ordonnance> ordonnances = new ArrayList<>();
    private List<Certificat> certificats = new ArrayList<>();


    public Consultation() {}

    
    public Consultation(Long id, Long patientId, Long utilisateurId, LocalDateTime dateConsultation, String diagnostic, String traitement, StatutRendezVous statut, String observationMedecin) {
        this.id = id;
        this.patientId = patientId;
        this.utilisateurId = utilisateurId;
        this.dateConsultation = dateConsultation;
        this.diagnostic = diagnostic;
        this.traitement = traitement;
        this.statut = statut;
        this.observationMedecin = observationMedecin;
    }

    

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    public LocalDateTime getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(LocalDateTime dateConsultation) { this.dateConsultation = dateConsultation; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public String getTraitement() { return traitement; }
    public void setTraitement(String traitement) { this.traitement = traitement; }

    
    public StatutRendezVous getStatut() { return statut; }
    public void setStatut(StatutRendezVous statut) { this.statut = statut; }

    
    public String getObservationMedecin() { return observationMedecin; }
    public void setObservationMedecin(String observationMedecin) { this.observationMedecin = observationMedecin; }

    public InterventionMedecin getInterventionMedecin() { return interventionMedecin; }
    public void setInterventionMedecin(InterventionMedecin interventionMedecin) { this.interventionMedecin = interventionMedecin; }

    public List<Ordonnance> getOrdonnances() { return ordonnances; }
    public void setOrdonnances(List<Ordonnance> ordonnances) { this.ordonnances = ordonnances; }

    public List<Certificat> getCertificats() { return certificats; }
    public void setCertificats(List<Certificat> certificats) { this.certificats = certificats; }
}