package ma.dentalTech.entities.pat;

import java.time.LocalDate;
import java.time.LocalDateTime;



public class Medicament {

    
    private Long id; 
    private String dosage; 
    private String presentation; 

    
    
    private String nom;
    private String laboratoire;
    private String typeDosage;
    private String forme;
    private boolean remboursable;
    private String posologie;
    private String description;

    public Medicament() {}

    public Medicament(Long id, String nom, String laboratoire) {
        this.id = id;
        this.nom = nom;
        this.laboratoire = laboratoire;
    }

    

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getPresentation() { return presentation; }
    public void setPresentation(String presentation) { this.presentation = presentation; }

    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getLaboratoire() { return laboratoire; }
    public void setLaboratoire(String laboratoire) { this.laboratoire = laboratoire; }

    public String getTypeDosage() { return typeDosage; }
    public void setTypeDosage(String typeDosage) { this.typeDosage = typeDosage; }

    public String getForme() { return forme; }
    public void setForme(String forme) { this.forme = forme; }

    public boolean isRemboursable() { return remboursable; }
    public void setRemboursable(boolean remboursable) { this.remboursable = remboursable; }

    public String getPosologie() { return posologie; }
    public void setPosologie(String posologie) { this.posologie = posologie; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}