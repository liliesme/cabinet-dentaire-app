package ma.dentalTech.entities.pat;



public class Role {

    
    private Long id; 
    private String nom; 
    private String description; 


    public Role() {}

    public Role(Long id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    
    public Long getIdRole() { return id; }
    public void setIdRole(Long idRole) { this.id = idRole; }
    public String getLibelle() { return nom; }
    public void setLibelle(String libelle) { this.nom = libelle; }
}