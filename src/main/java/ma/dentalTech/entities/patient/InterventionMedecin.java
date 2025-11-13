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



class InterventionMedecin {
    private Long idIM;
    private Double prixDePatient;
    private Integer nombDelit;
    
    public InterventionMedecin() {}
    
    public InterventionMedecin(Long idIM, Double prixDePatient) {
        this.idIM = idIM;
        this.prixDePatient = prixDePatient;
    }
    
    public Long getIdIM() { return idIM; }
    public void setIdIM(Long idIM) { this.idIM = idIM; }
    
    public Double getPrixDePatient() { return prixDePatient; }
    public void setPrixDePatient(Double prixDePatient) { this.prixDePatient = prixDePatient; }
    
    public Integer getNombDelit() { return nombDelit; }
    public void setNombDelit(Integer nombDelit) { this.nombDelit = nombDelit; }
}
