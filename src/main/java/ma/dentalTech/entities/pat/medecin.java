package ma.dentalTech.entities.pat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;







public class medecin extends Staff {

    
    private Long id; 
    private String nom; 
    private String prenom; 
    private String adresse; 
    private String telephone; 
    private String email; 
    private LocalDateTime dateCreation; 

    
    private String specialite;
    private String agendaMensuel;
    private List<Agenda> agendas;

    public medecin() {
        super();
        this.agendas = new ArrayList<>();
    }

    

    

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getAgendaMensuel() { return agendaMensuel; }
    public void setAgendaMensuel(String agendaMensuel) { this.agendaMensuel = agendaMensuel; }

    public List<Agenda> getAgendas() { return agendas; }
    public void setAgendas(List<Agenda> agendas) { this.agendas = agendas; }
    public void addAgenda(Agenda agenda) { this.agendas.add(agenda); }
}