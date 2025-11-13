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

class Secretaire extends Staff {
    private String numCNSS;
    private Double commission;
    
    public Secretaire() {
        super();
    }
    
    public Secretaire(Long idUser, String nom, String email, Double salaire) {
        super(idUser, nom, email, salaire);
    }
    
    public String getNumCNSS() { return numCNSS; }
    public void setNumCNSS(String numCNSS) { this.numCNSS = numCNSS; }
    
    public Double getCommission() { return commission; }
    public void setCommission(Double commission) { this.commission = commission; }
}