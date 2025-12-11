package ma.dentalTech.entities.pat;

import java.time.LocalDateTime;

public class Facture {

    
    private Long id; 
    private Long patientId; 
    private Double montantTotal; 
    private boolean estPayee; 

    
    
    private Double totalPaye;
    private Double reste;
    private String statut;
    private LocalDateTime dateFacture;
    private String numeroFacture; 

    public Facture() {}

    
    public Facture(Long id, Long patientId, LocalDateTime dateFacture, Double montantTotal, boolean estPayee, Double totalPaye) {
        this.id = id;
        this.patientId = patientId;
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
        this.estPayee = estPayee;
        this.totalPaye = totalPaye;
        calculerReste();
    }

    

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Double getMontantTotal() { return montantTotal; } 
    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
        calculerReste();
    }

    public boolean isEstPayee() { return estPayee; }
    public void setEstPayee(boolean estPayee) { this.estPayee = estPayee; }

    public String getNumeroFacture() { return numeroFacture; }
    public void setNumeroFacture(String numeroFacture) { this.numeroFacture = numeroFacture; }


    
    public Double getTotalPaye() { return totalPaye; }
    public void setTotalPaye(Double totalPaye) {
        this.totalPaye = totalPaye;
        calculerReste();
    }

    public Double getReste() { return reste; }

    private void calculerReste() {
        if (montantTotal != null && totalPaye != null) {
            this.reste = montantTotal - totalPaye;
        }
        
        this.statut = this.estPayee ? "Payée" : "En attente";
    }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDateTime getDateFacture() { return dateFacture; }
    public void setDateFacture(LocalDateTime dateFacture) { this.dateFacture = dateFacture; }
}