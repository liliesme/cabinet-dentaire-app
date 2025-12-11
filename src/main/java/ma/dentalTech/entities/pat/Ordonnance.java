package ma.dentalTech.entities.pat;

import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.List;



public class Ordonnance {

    
    private Long id; 
    private Long patientId; 
    private Long utilisateurId; 
    private LocalDateTime dateOrdonnance; 
    private List<String> lignesMedicaments; 


    private List<Prescription> prescriptions; 

    public Ordonnance() {
        this.prescriptions = new ArrayList<>();
        this.lignesMedicaments = new ArrayList<>();
    }

    public Ordonnance(Long id, Long patientId, Long utilisateurId, LocalDateTime dateOrdonnance) {
        this();
        this.id = id;
        this.patientId = patientId;
        this.utilisateurId = utilisateurId;
        this.dateOrdonnance = dateOrdonnance;
    }

    

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    
    public LocalDateTime getDateOrdonnance() { return dateOrdonnance; }
    public void setDateOrdonnance(LocalDateTime dateOrdonnance) { this.dateOrdonnance = dateOrdonnance; }

    
    public List<String> getLignesMedicaments() { return lignesMedicaments; }
    public void setLignesMedicaments(List<String> lignesMedicaments) { this.lignesMedicaments = lignesMedicaments; }

    
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public void setPrescriptions(List<Prescription> prescriptions) { this.prescriptions = prescriptions; }
    public void addPrescription(Prescription prescription) { this.prescriptions.add(prescription); }
}