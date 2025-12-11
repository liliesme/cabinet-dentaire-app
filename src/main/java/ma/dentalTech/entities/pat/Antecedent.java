package ma.dentalTech.entities.pat;

import ma.dentalTech.entities.enums.CategorieAntecedent;
import ma.dentalTech.entities.enums.NiveauRisque;

public class Antecedent {

    private Long id;
    private Long patientId; 
    private String nom;
    private CategorieAntecedent categorie;
    private NiveauRisque niveauRisque;

    public Antecedent() {}

    public Antecedent(Long id, Long patientId, String nom, CategorieAntecedent categorie, NiveauRisque niveauRisque) {
        this.id = id;
        this.patientId = patientId;
        this.nom = nom;
        this.categorie = categorie;
        this.niveauRisque = niveauRisque;
    }

    

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public CategorieAntecedent getCategorie() { return categorie; }
    public void setCategorie(CategorieAntecedent categorie) { this.categorie = categorie; }

    public NiveauRisque getNiveauRisque() { return niveauRisque; }
    public void setNiveauRisque(NiveauRisque niveauRisque) { this.niveauRisque = niveauRisque; }
}