package ma.dentalTech.entities.pat;


 import java.time.LocalDate;
import java.time.LocalDateTime;
import ma.dentalTech.entities.enums.Assurance;
import ma.dentalTech.entities.enums.Sexe;
import ma.dentalTech.entities.enums.UtilisateurRole;
 import ma.dentalTech.entities.enums.StatutRendezVous;

public class Acte {

    
    private Long id;
    private String nom;
    private String description;
    private String typeActe;
    private Double tarifBase;



    public Acte() {
    }

    
    public Acte(Long id, String nom, String description, String typeActe, Double tarifBase) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.typeActe = typeActe;
        this.tarifBase = tarifBase;
    }


    
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getTypeActe() {
        return typeActe;
    }

    public Double getTarifBase() {
        return tarifBase;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeActe(String typeActe) {
        this.typeActe = typeActe;
    }

    public void setTarifBase(Double tarifBase) {
        this.tarifBase = tarifBase;
    }
}