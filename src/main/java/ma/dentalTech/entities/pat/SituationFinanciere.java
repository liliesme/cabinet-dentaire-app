package ma.dentalTech.entities.pat;




import ma.dentalTech.entities.enums.StatutFinancier;

public class SituationFinanciere {
    private Long id;
    private Long patientId; 
    private Double totalDesActes;
    private Double totalePaye;
    private Double credit;
    private StatutFinancier statut; 
    private String observation;

    public SituationFinanciere() {}

    public SituationFinanciere(Long id, Long patientId, Double totalDesActes, Double totalePaye) {
        this.id = id;
        this.patientId = patientId; 
        this.totalDesActes = totalDesActes;
        this.totalePaye = totalePaye;
        this.credit = totalDesActes - totalePaye;
    }

    
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getTotalDesActes() { return totalDesActes; }
    public void setTotalDesActes(Double totalDesActes) {
        this.totalDesActes = totalDesActes;
        calculerCredit();
    }

    public Double getTotalePaye() { return totalePaye; }
    public void setTotalePaye(Double totalePaye) {
        this.totalePaye = totalePaye;
        calculerCredit();
    }

    public Double getCredit() { return credit; }
    
    public void setCredit(Double credit) { this.credit = credit; }

    private void calculerCredit() {
        if (totalDesActes != null && totalePaye != null) {
            this.credit = totalDesActes - totalePaye;
        }
    }

    
    public StatutFinancier getStatut() { return statut; }
    public void setStatut(StatutFinancier statut) { this.statut = statut; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
}