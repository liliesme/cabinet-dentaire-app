package ma.dentalTech.entities.pat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import ma.dentalTech.entities.enums.StatutRendezVous; 

public class RDV {

    
    private Long id; 
    private Long patientId; 
    private Long medecinId; 
    private LocalDateTime dateHeure; 
    private StatutRendezVous statut; 

    
    
    
    
    private String motif;
    private String noteRdvMedecin;

    public RDV() {}

    public RDV(Long id, LocalDateTime dateHeure) {
        this.id = id;
        this.dateHeure = dateHeure;
    }

    

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getMedecinId() { return medecinId; }
    public void setMedecinId(Long medecinId) { this.medecinId = medecinId; }

    
    public LocalDateTime getDateHeure() {
        
        
        return dateHeure;
    }
    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    
    public StatutRendezVous getStatut() { return statut; }
    public void setStatut(StatutRendezVous statut) { this.statut = statut; }

    
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public String getNoteRdvMedecin() { return noteRdvMedecin; }
    public void setNoteRdvMedecin(String noteRdvMedecin) { this.noteRdvMedecin = noteRdvMedecin; }

    
    public LocalDate getDate() { return dateHeure != null ? dateHeure.toLocalDate() : null; }
    public void setDate(LocalDate date) {  }

    public LocalTime getHeure() { return dateHeure != null ? dateHeure.toLocalTime() : null; }
    public void setHeure(LocalTime heure) {  }
}