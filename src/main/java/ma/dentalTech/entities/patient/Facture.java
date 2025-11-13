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

class Facture {
    private Long idFacture;
    private Double totalFacture;
    private Double totalPaye;
    private Double reste;
    private Statut statut;
    private LocalDateTime dateFacture;
    
    public Facture() {}
    
    public Facture(Long idFacture, Double totalFacture, Double totalPaye) {
        this.idFacture = idFacture;
        this.totalFacture = totalFacture;
        this.totalPaye = totalPaye;
        this.reste = totalFacture - totalPaye;
    }
    
    public Long getIdFacture() { return idFacture; }
    public void setIdFacture(Long idFacture) { this.idFacture = idFacture; }
    
    public Double getTotalFacture() { return totalFacture; }
    public void setTotalFacture(Double totalFacture) { 
        this.totalFacture = totalFacture;
        calculerReste();
    }
    
    public Double getTotalPaye() { return totalPaye; }
    public void setTotalPaye(Double totalPaye) { 
        this.totalPaye = totalPaye;
        calculerReste();
    }
    
    public Double getReste() { return reste; }
    
    private void calculerReste() {
        if (totalFacture != null && totalPaye != null) {
            this.reste = totalFacture - totalPaye;
        }
    }
    
    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
    
    public LocalDateTime getDateFacture() { return dateFacture; }
    public void setDateFacture(LocalDateTime dateFacture) { this.dateFacture = dateFacture; }
}
