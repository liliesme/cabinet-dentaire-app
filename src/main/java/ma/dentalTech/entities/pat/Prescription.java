package ma.dentalTech.entities.pat;

import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.List;
import ma.dentalTech.entities.pat.Medicament; 

public class Prescription {

    
    private Long id; 
    private Long patientId; 
    private Long utilisateurId; 
    private LocalDateTime datePrescription; 
    private List<String> lignesMedicaments; 

    
    
    private String description;
    private Integer frequence;
    private Integer dureeEnJours;
    private Medicament medicament;

    public Prescription() {
        this.lignesMedicaments = new ArrayList<>();
    }

    public Prescription(Long id, String description, Medicament medicament) {
        this();
        this.id = id;
        this.description = description;
        this.medicament = medicament;
    }

    

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    
    public LocalDateTime getDatePrescription() { return datePrescription; }
    public void setDatePrescription(LocalDateTime datePrescription) { this.datePrescription = datePrescription; }

    
    public List<String> getLignesMedicaments() { return lignesMedicaments; }
    public void setLignesMedicaments(List<String> lignesMedicaments) { this.lignesMedicaments = lignesMedicaments; }

    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getFrequence() { return frequence; }
    public void setFrequence(Integer frequence) { this.frequence = frequence; }

    public Integer getDureeEnJours() { return dureeEnJours; }
    public void setDureeEnJours(Integer dureeEnJours) { this.dureeEnJours = dureeEnJours; }

    public Medicament getMedicament() { return medicament; }
    public void setMedicament(Medicament medicament) { this.medicament = medicament; }
}