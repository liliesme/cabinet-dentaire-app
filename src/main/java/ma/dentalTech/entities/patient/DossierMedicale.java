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


class DossierMedicale {
    private Long idDM;
    private LocalDate dateDeCreation;
    
    private List<Consultation> consultations;
    
    public DossierMedicale() {
        this.consultations = new ArrayList<>();
    }
    
    public DossierMedicale(Long idDM, LocalDate dateDeCreation) {
        this();
        this.idDM = idDM;
        this.dateDeCreation = dateDeCreation;
    }
    
    public Long getIdDM() { return idDM; }
    public void setIdDM(Long idDM) { this.idDM = idDM; }
    
    public LocalDate getDateDeCreation() { return dateDeCreation; }
    public void setDateDeCreation(LocalDate dateDeCreation) { this.dateDeCreation = dateDeCreation; }
    
    public List<Consultation> getConsultations() { return consultations; }
    public void addConsultation(Consultation consultation) { this.consultations.add(consultation); }
}