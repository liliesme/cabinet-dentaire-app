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
abstract class Utilisateur {
    protected Long idUser;
    protected String nom;
    protected String email;
    protected Adresse adresse;
    protected String tel;
    protected Sexe sexe;
    protected String login;
    protected String motDePasse;
    protected LocalDate dateLoginDate;
    protected LocalDate dateNaissance;
    protected Role role;
    
    public Utilisateur() {}
    
    public Utilisateur(Long idUser, String nom, String email) {
        this.idUser = idUser;
        this.nom = nom;
        this.email = email;
    }
    
    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Adresse getAdresse() { return adresse; }
    public void setAdresse(Adresse adresse) { this.adresse = adresse; }
    
    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }
    
    public Sexe getSexe() { return sexe; }
    public void setSexe(Sexe sexe) { this.sexe = sexe; }
    
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    
    public LocalDate getDateLoginDate() { return dateLoginDate; }
    public void setDateLoginDate(LocalDate dateLoginDate) { this.dateLoginDate = dateLoginDate; }
    
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}