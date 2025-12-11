package ma.dentalTech.entities.pat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import ma.dentalTech.entities.enums.Assurance;
import ma.dentalTech.entities.enums.Sexe;

public class Patient {

    
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private LocalDate dateNaissance;
    private LocalDateTime dateCreation;
    private Sexe sexe;
    private Assurance assurance;

    
    
    public Patient() {
    }

    
    public Patient(Long id, String nom, String prenom, String adresse, String telephone, String email, LocalDate dateNaissance, LocalDateTime dateCreation, Sexe sexe, Assurance assurance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.dateCreation = dateCreation;
        this.sexe = sexe;
        this.assurance = assurance;
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

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public Sexe getSexe() { return sexe; }
    public void setSexe(Sexe sexe) { this.sexe = sexe; }

    public Assurance getAssurance() { return assurance; }
    public void setAssurance(Assurance assurance) { this.assurance = assurance; }
}