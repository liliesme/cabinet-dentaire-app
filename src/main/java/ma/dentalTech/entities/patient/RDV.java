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

class RDV {
    private Long idRDV;
    private LocalDate date;
    private LocalTime heure;
    private String motif;
    private Statut statut;
    private String noteRdvMedecin;
    
    public RDV() {}
    
    public RDV(Long idRDV, LocalDate date, LocalTime heure) {
        this.idRDV = idRDV;
        this.date = date;
        this.heure = heure;
    }
    
    public Long getIdRDV() { return idRDV; }
    public void setIdRDV(Long idRDV) { this.idRDV = idRDV; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }
    
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    
    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
    
    public String getNoteRdvMedecin() { return noteRdvMedecin; }
    public void setNoteRdvMedecin(String noteRdvMedecin) { this.noteRdvMedecin = noteRdvMedecin; }
}