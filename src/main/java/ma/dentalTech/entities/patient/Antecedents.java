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

class Antecedents {
    private Long id_Antecedent;
    private String nom;
    private String categorie;
    private LocalDateTime niveauDeBase;
    
    public Antecedents() {}
    
    public Antecedents(Long id, String nom, String categorie) {
        this.id_Antecedent = id;
        this.nom = nom;
        this.categorie = categorie;
    }
    
    public Long getId_Antecedent() { return id_Antecedent; }
    public void setId_Antecedent(Long id_Antecedent) { this.id_Antecedent = id_Antecedent; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    
    public LocalDateTime getNiveauDeBase() { return niveauDeBase; }
    public void setNiveauDeBase(LocalDateTime niveauDeBase) { this.niveauDeBase = niveauDeBase; }
}