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

class Agenda {
    private Long idAgenda;
    private Integer annee;
    private Integer mois;
    private List<LocalDate> joursOuvrables;
    private List<LocalDate> joursFeries;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Integer dureeConsultation;
    
    public Agenda() {
        this.joursOuvrables = new ArrayList<>();
        this.joursFeries = new ArrayList<>();
    }
    
    public Agenda(Long idAgenda, Integer annee, Integer mois) {
        this();
        this.idAgenda = idAgenda;
        this.annee = annee;
        this.mois = mois;
    }
    
    public Long getIdAgenda() { return idAgenda; }
    public void setIdAgenda(Long idAgenda) { this.idAgenda = idAgenda; }
    
    public Integer getAnnee() { return annee; }
    public void setAnnee(Integer annee) { this.annee = annee; }
    
    public Integer getMois() { return mois; }
    public void setMois(Integer mois) { this.mois = mois; }
    
    public List<LocalDate> getJoursOuvrables() { return joursOuvrables; }
    public void addJourOuvrable(LocalDate date) { this.joursOuvrables.add(date); }
    
    public List<LocalDate> getJoursFeries() { return joursFeries; }
    public void addJourFerie(LocalDate date) { this.joursFeries.add(date); }
    
    public LocalTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }
    
    public LocalTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalTime heureFin) { this.heureFin = heureFin; }
    
    public Integer getDureeConsultation() { return dureeConsultation; }
    public void setDureeConsultation(Integer dureeConsultation) { this.dureeConsultation = dureeConsultation; }
}