package ma.dentalTech.entities.pat;

import java.time.LocalDateTime;
import ma.dentalTech.entities.enums.Sexe;

public class Secretaire extends Staff {

    
    private Long id; 
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private LocalDateTime dateCreation;
    private Sexe sexe; 

    private String numCNSS;
    private Double commission;

    public Secretaire() {
        super();
    }

    

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public Sexe getSexe() { return sexe; }
    public void setSexe(Sexe sexe) { this.sexe = sexe; }

    
    public String getNumCNSS() { return numCNSS; }
    public void setNumCNSS(String numCNSS) { this.numCNSS = numCNSS; }

    public Double getCommission() { return commission; }
    public void setCommission(Double commission) { this.commission = commission; }
}