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

import java.util.ArrayList;
import java.util.List;

class Medicament {
    private Long idMed;
    private String nom;
    private String laboratoire;
    private String typeDosage;
    private Forme forme;
    private boolean remboursable;
    private String posologie;
    private String description;
    
    public Medicament() {}
    
    public Medicament(Long idMed, String nom, String laboratoire) {
        this.idMed = idMed;
        this.nom = nom;
        this.laboratoire = laboratoire;
    }
    
    public Long getIdMed() { return idMed; }
    public void setIdMed(Long idMed) { this.idMed = idMed; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getLaboratoire() { return laboratoire; }
    public void setLaboratoire(String laboratoire) { this.laboratoire = laboratoire; }
    
    public String getTypeDosage() { return typeDosage; }
    public void setTypeDosage(String typeDosage) { this.typeDosage = typeDosage; }
    
    public Forme getForme() { return forme; }
    public void setForme(Forme forme) { this.forme = forme; }
    
    public boolean isRemboursable() { return remboursable; }
    public void setRemboursable(boolean remboursable) { this.remboursable = remboursable; }
    
    public String getPosologie() { return posologie; }
    public void setPosologie(String posologie) { this.posologie = posologie; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
