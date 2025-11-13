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

class Medecin extends Staff {
    private String specialite;
    private String agendaMensuel;
    private List<Agenda> agendas;
    
    public medecin() {
        super();
        this.agendas = new ArrayList<>();
    }
    
    public medecin(Long idUser, String nom, String email, Double salaire, String specialite) {
        super(idUser, nom, email, salaire);
        this.specialite = specialite;
        this.agendas = new ArrayList<>();
    }
    
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
    
    public String getAgendaMensuel() { return agendaMensuel; }
    public void setAgendaMensuel(String agendaMensuel) { this.agendaMensuel = agendaMensuel; }
    
    public List<Agenda> getAgendas() { return agendas; }
    public void addAgenda(Agenda agenda) { this.agendas.add(agenda); }
}





