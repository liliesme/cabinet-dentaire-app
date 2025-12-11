package ma.dentalTech.entities.pat;

import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.List;









public class DossierMedicale {
    
    private Long id; 
    private Long patientId; 
    private LocalDateTime dateCreation; 
    private String notesInitiales; 

    
    
    

    private List<Consultation> consultations;

    public DossierMedicale() {
        this.consultations = new ArrayList<>();
    }

    
    public DossierMedicale(Long id, Long patientId, LocalDateTime dateCreation, String notesInitiales) {
        this();
        this.id = id;
        this.patientId = patientId;
        this.dateCreation = dateCreation;
        this.notesInitiales = notesInitiales;
    }

    

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public LocalDateTime getDateCreation() { return dateCreation; } 
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; } 

    public String getNotesInitiales() { return notesInitiales; }
    public void setNotesInitiales(String notesInitiales) { this.notesInitiales = notesInitiales; }

    
    public List<Consultation> getConsultations() { return consultations; }
    public void addConsultation(Consultation consultation) { this.consultations.add(consultation); }
    public void setConsultations(List<Consultation> consultations) { this.consultations = consultations; } 
}