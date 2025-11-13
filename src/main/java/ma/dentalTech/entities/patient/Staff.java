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

class Staff extends Utilisateur {
    private Double salaire;
    private Double prime;
    private LocalDate dateRecrutement;
    private LocalDate dateDepart;
    
    public Staff() {
        super();
    }
    
    public Staff(Long idUser, String nom, String email, Double salaire) {
        super(idUser, nom, email);
        this.salaire = salaire;
    }
    
    public Double getSalaire() { return salaire; }
    public void setSalaire(Double salaire) { this.salaire = salaire; }
    
    public Double getPrime() { return prime; }
    public void setPrime(Double prime) { this.prime = prime; }
    
    public LocalDate getDateRecrutement() { return dateRecrutement; }
    public void setDateRecrutement(LocalDate dateRecrutement) { this.dateRecrutement = dateRecrutement; }
    
    public LocalDate getDateDepart() { return dateDepart; }
    public void setDateDepart(LocalDate dateDepart) { this.dateDepart = dateDepart; }
}




