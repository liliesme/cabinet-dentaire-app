package ma.dentalTech.entities.Pat;

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

class Prescription {
    private Long idPr;
    private String description;
    private Integer frequence;
    private Integer dureeEnJours;
    private Medicament medicament;
    
    public Prescription() {}
    
    public Prescription(Long idPr, String description, Medicament medicament) {
        this.idPr = idPr;
        this.description = description;
        this.medicament = medicament;
    }
    
    public Long getIdPr() { return idPr; }
    public void setIdPr(Long idPr) { this.idPr = idPr; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getFrequence() { return frequence; }
    public void setFrequence(Integer frequence) { this.frequence = frequence; }
    
    public Integer getDureeEnJours() { return dureeEnJours; }
    public void setDureeEnJours(Integer dureeEnJours) { this.dureeEnJours = dureeEnJours; }
    
    public Medicament getMedicament() { return medicament; }
    public void setMedicament(Medicament medicament) { this.medicament = medicament; }
}