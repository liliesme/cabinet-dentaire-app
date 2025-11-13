package ma.dentalTech.entities.pat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dentalTech.entities.enums.Assurance;
import ma.dentalTech.entities.enums.Sexe;
import ma.dentalTech.entities.enums.UtilisateurRole;
import ma.dentalTech.entities.enums.StatutRendezVous;

import java.util.ArrayList;
import java.util.List;
class Consultation {
    private Long idConsultation;
    private LocalDate date;
    private Statut statut;
    private String observationMedecin;
    
    private InterventionMedecin interventionMedecin;
    private List<Ordonnance> ordonnances;
    private List<Certificat> certificats;
    
    public Consultation() {
        this.ordonnances = new ArrayList<>();
        this.certificats = new ArrayList<>();
    }
    
    public Consultation(Long idConsultation, LocalDate date) {
        this();
        this.idConsultation = idConsultation;
        this.date = date;
    }
    
    public Long getIdConsultation() { return idConsultation; }
    public void setIdConsultation(Long idConsultation) { this.idConsultation = idConsultation; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
    
    public String getObservationMedecin() { return observationMedecin; }
    public void setObservationMedecin(String observationMedecin) { this.observationMedecin = observationMedecin; }
    
    public InterventionMedecin getInterventionMedecin() { return interventionMedecin; }
    public void setInterventionMedecin(InterventionMedecin interventionMedecin) { this.interventionMedecin = interventionMedecin; }
    
    public List<Ordonnance> getOrdonnances() { return ordonnances; }
    public void addOrdonnance(Ordonnance ordonnance) { this.ordonnances.add(ordonnance); }
    
    public List<Certificat> getCertificats() { return certificats; }
    public void addCertificat(Certificat certificat) { this.certificats.add(certificat); }
}