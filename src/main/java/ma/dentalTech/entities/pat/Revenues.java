package ma.dentalTech.entities.pat;

import java.time.LocalDateTime;



public class Revenues {

    
    private Long id; 
    private Long factureId; 
    private Long utilisateurId; 

    
    private String titre;
    private String description;
    private Double montant;
    private LocalDateTime date;

    
    public Revenues() {}

    public Revenues(String titre, String description, Double montant, LocalDateTime date) {
        this.titre = titre;
        this.description = description;
        this.montant = montant;
        this.date = date;
    }

    

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    
    public Long getFactureId() { return factureId; }
    public void setFactureId(Long factureId) { this.factureId = factureId; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}