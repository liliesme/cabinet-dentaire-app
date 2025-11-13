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
class Ordonnance {
    private Long idOrd;
    private LocalDate date;
    
    private List<Prescription> prescriptions;
    
    public Ordonnance() {
        this.prescriptions = new ArrayList<>();
    }
    
    public Ordonnance(Long idOrd, LocalDate date) {
        this();
        this.idOrd = idOrd;
        this.date = date;
    }
    
    public Long getIdOrd() { return idOrd; }
    public void setIdOrd(Long idOrd) { this.idOrd = idOrd; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public void addPrescription(Prescription prescription) { this.prescriptions.add(prescription); }
}
