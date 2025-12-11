package ma.dentalTech.entities.pat;

import java.time.LocalDateTime;

public class Charges {

    
    private Long id; 
    private String titre;
    private String description;
    private Double montant;
    private LocalDateTime date;
    private Long utilisateurId; 

    
    public Charges() {
    }

    
    public Charges(String titre, String description, Double montant, LocalDateTime date, Long utilisateurId) {
        this(null, titre, description, montant, date, utilisateurId);
    }

    
    public Charges(Long id, String titre, String description, Double montant, LocalDateTime date, Long utilisateurId) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.montant = montant;
        this.date = date;
        this.utilisateurId = utilisateurId;
    }

    

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }
}