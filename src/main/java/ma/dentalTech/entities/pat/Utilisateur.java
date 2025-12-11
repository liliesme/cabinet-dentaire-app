package ma.dentalTech.entities.pat;

import java.time.LocalDate;
import ma.dentalTech.entities.enums.Sexe;


public abstract class Utilisateur {
    protected Long idUser;
    protected String nom;
    protected String prenom; 
    protected String email;
    protected String adresse;
    protected String tel;
    protected Sexe sexe;
    protected String login;
    protected String motDePasse;
    protected LocalDate dateLoginDate;
    protected LocalDate dateNaissance;
    protected Role role;
    protected Long roleId; 

    public Utilisateur() {}

    public Utilisateur(Long idUser, String nom, String email) {
        this.idUser = idUser;
        this.nom = nom;
        this.email = email;
    }

    
    public Long getId() { return idUser; }
    public void setId(Long idUser) { this.idUser = idUser; }

    public String getNom() { return nom; } 
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; } 
    public void setPrenom(String prenom) { this.prenom = prenom; } 

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTel() { return tel; } 
    public void setTel(String tel) { this.tel = tel; }

    public Sexe getSexe() { return sexe; }
    public void setSexe(Sexe sexe) { this.sexe = sexe; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    
    public String getPassword() { return motDePasse; }
    public void setPassword(String motDePasse) { this.motDePasse = motDePasse; }

    public LocalDate getDateLoginDate() { return dateLoginDate; }
    public void setDateLoginDate(LocalDate dateLoginDate) { this.dateLoginDate = dateLoginDate; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public Role getRole() { return role; }

    public Long getRoleId() {
        return roleId;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
}