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

public class CabinetMedicale {
    
    private Long id;
    private String nom;
    private String email;
    private String logo;
    private String adresse;
    private String tel1;
    private String tel2;
    private String siteWeb;
    private String instagram;
    private String facebook;
    private String description;
    
    private List<Utilisateur> utilisateurs;
    private List<Charges> charges;
    private List<Revenues> revenues;
    
    public CabinetMedicale() {
        this.utilisateurs = new ArrayList<>();
        this.charges = new ArrayList<>();
        this.revenues = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public String getTel1() {
        return tel1;
    }
    
    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }
    
    public String getTel2() {
        return tel2;
    }
    
    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }
    
    public String getSiteWeb() {
        return siteWeb;
    }
    
    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }
    
    public String getInstagram() {
        return instagram;
    }
    
    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
    
    public String getFacebook() {
        return facebook;
    }
    
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }
    
    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
    
    public void addUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.add(utilisateur);
    }
    
    public List<Charges> getCharges() {
        return charges;
    }
    
    public void setCharges(List<Charges> charges) {
        this.charges = charges;
    }
    
    public void addCharge(Charges charge) {
        this.charges.add(charge);
    }
    
    public List<Revenues> getRevenues() {
        return revenues;
    }
    
    public void setRevenues(List<Revenues> revenues) {
        this.revenues = revenues;
    }
    
    public void addRevenue(Revenues revenue) {
        this.revenues.add(revenue);
    }
}