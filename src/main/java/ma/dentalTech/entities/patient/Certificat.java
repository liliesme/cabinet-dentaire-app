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



class Certificat {
    private Long idCertif;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Integer duree;
    private String noteMedecin;
    
    public Certificat() {}
    
    public Certificat(Long idCertif, LocalDate dateDebut, LocalDate dateFin) {
        this.idCertif = idCertif;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
    public Long getIdCertif() { return idCertif; }
    public void setIdCertif(Long idCertif) { this.idCertif = idCertif; }
    
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    
    public Integer getDuree() { return duree; }
    public void setDuree(Integer duree) { this.duree = duree; }
    
    public String getNoteMedecin() { return noteMedecin; }
    public void setNoteMedecin(String noteMedecin) { this.noteMedecin = noteMedecin; }
}