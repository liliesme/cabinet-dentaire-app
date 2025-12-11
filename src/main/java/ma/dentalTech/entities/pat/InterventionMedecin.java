package ma.dentalTech.entities.pat;

import java.time.LocalDateTime;

public class InterventionMedecin {


    private Long id; 
    private Long patientId;
    private Long utilisateurId;
    private LocalDateTime dateIntervention;
    private String typeIntervention;
    private String description;


    private Double prixDePatient;
    private Integer nombDelit; 

    public InterventionMedecin() {}

    
    public InterventionMedecin(Long id, Double prixDePatient) {
        this.id = id;
        this.prixDePatient = prixDePatient;
    }

    

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    
    public LocalDateTime getDateIntervention() { return dateIntervention; }
    public void setDateIntervention(LocalDateTime dateIntervention) { this.dateIntervention = dateIntervention; }

    
    public String getTypeIntervention() { return typeIntervention; }
    public void setTypeIntervention(String typeIntervention) { this.typeIntervention = typeIntervention; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    
    public Double getPrixDePatient() { return prixDePatient; }
    public void setPrixDePatient(Double prixDePatient) { this.prixDePatient = prixDePatient; }

    public Integer getNombDelit() { return nombDelit; }
    public void setNombDelit(Integer nombDelit) { this.nombDelit = nombDelit; }

    
    public Long getIdIM() { return id; }
    public void setIdIM(Long idIM) { this.id = idIM; }
}