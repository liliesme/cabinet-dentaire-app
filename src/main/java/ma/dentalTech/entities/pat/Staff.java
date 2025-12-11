package ma.dentalTech.entities.pat;

import java.time.LocalDate;

public class Staff extends Utilisateur {

    private String cin;
    
    private Double salaire;
    private Double prime;
    private LocalDate dateEmbauche;
    private LocalDate dateDepart;

    public Staff() {
        super();
    }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    

    public LocalDate getDateEmbauche() { return dateEmbauche; }
    public void setDateEmbauche(LocalDate dateEmbauche) { this.dateEmbauche = dateEmbauche; }

    public Double getSalaire() { return salaire; }
    public void setSalaire(Double salaire) { this.salaire = salaire; }

    public Double getPrime() { return prime; }
    public void setPrime(Double prime) { this.prime = prime; }

    public LocalDate getDateDepart() { return dateDepart; }
    public void setDateDepart(LocalDate dateDepart) { this.dateDepart = dateDepart; }
}