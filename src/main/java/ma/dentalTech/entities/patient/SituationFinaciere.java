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

class SituationFinanciere {
    private Long id;
    private Double totalDesActes;
    private Double totalePaye;
    private Double credit;
    private Statut statut;
    private String observation;
    
    public SituationFinanciere() {}
    
    public SituationFinanciere(Long id, Double totalDesActes, Double totalePaye) {
        this.id = id;
        this.totalDesActes = totalDesActes;
        this.totalePaye = totalePaye;
        this.credit = totalDesActes - totalePaye;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Double getTotalDesActes() { return totalDesActes; }
    public void setTotalDesActes(Double totalDesActes) { 
        this.totalDesActes = totalDesActes;
        calculerCredit();
    }
    
    public Double getTotalePaye() { return totalePaye; }
    public void setTotalePaye(Double totalePaye) { 
        this.totalePaye = totalePaye;
        calculerCredit();
    }
    
    public Double getCredit() { return credit; }
    
    private void calculerCredit() {
        if (totalDesActes != null && totalePaye != null) {
            this.credit = totalDesActes - totalePaye;
        }
    }
    
    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
    
    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
}
