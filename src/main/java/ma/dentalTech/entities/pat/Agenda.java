package ma.dentalTech.entities.pat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;




public class Agenda {


    private Long idAgenda;
    private Integer annee;
    private Integer mois;


    private List<LocalDate> joursOuvrables = new ArrayList<>();
    private List<LocalDate> joursFeries = new ArrayList<>();

    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Integer dureeConsultation;


    public Agenda() {
    }


    public Agenda(Long idAgenda, Integer annee, Integer mois, List<LocalDate> joursOuvrables, List<LocalDate> joursFeries, LocalTime heureDebut, LocalTime heureFin, Integer dureeConsultation) {
        this.idAgenda = idAgenda;
        this.annee = annee;
        this.mois = mois;
        
        this.joursOuvrables = joursOuvrables != null ? joursOuvrables : new ArrayList<>();
        this.joursFeries = joursFeries != null ? joursFeries : new ArrayList<>();
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.dureeConsultation = dureeConsultation;
    }



    public Long getIdAgenda() { return idAgenda; }
    public void setIdAgenda(Long idAgenda) { this.idAgenda = idAgenda; }

    public Integer getAnnee() { return annee; }
    public void setAnnee(Integer annee) { this.annee = annee; }

    public Integer getMois() { return mois; }
    public void setMois(Integer mois) { this.mois = mois; }

    public List<LocalDate> getJoursOuvrables() { return joursOuvrables; }
    public void setJoursOuvrables(List<LocalDate> joursOuvrables) {
        this.joursOuvrables = joursOuvrables != null ? joursOuvrables : new ArrayList<>();
    }

    public List<LocalDate> getJoursFeries() { return joursFeries; }
    public void setJoursFeries(List<LocalDate> joursFeries) {
        this.joursFeries = joursFeries != null ? joursFeries : new ArrayList<>();
    }

    public LocalTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }

    public LocalTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalTime heureFin) { this.heureFin = heureFin; }

    public Integer getDureeConsultation() { return dureeConsultation; }
    public void setDureeConsultation(Integer dureeConsultation) { this.dureeConsultation = dureeConsultation; }

    
    public void addJourOuvrable(LocalDate date) {
        if (this.joursOuvrables == null) this.joursOuvrables = new ArrayList<>();
        this.joursOuvrables.add(date);
    }

    public void addJourFerie(LocalDate date) {
        if (this.joursFeries == null) this.joursFeries = new ArrayList<>();
        this.joursFeries.add(date);
    }
}