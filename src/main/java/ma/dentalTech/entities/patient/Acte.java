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


class Acte {
    private Long idActe;
    private String libelle;
    private String categorie;
    private Double prixDeBase;
    
    public Acte() {}
    
    public Acte(Long idActe, String libelle, Double prixDeBase) {
        this.idActe = idActe;
        this.libelle = libelle;
        this.prixDeBase = prixDeBase;
    }
    
    public Long getIdActe() { return idActe; }
    public void setIdActe(Long idActe) { this.idActe = idActe; }
    
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    
    public Double getPrixDeBase() { return prixDeBase; }
    public void setPrixDeBase(Double prixDeBase) { this.prixDeBase = prixDeBase; }
}