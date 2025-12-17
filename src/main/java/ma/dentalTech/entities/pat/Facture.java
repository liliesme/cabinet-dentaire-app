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

    public Facture() {
        // valeurs par défaut sûres
        this.dateFacture = LocalDateTime.now();
        this.estPayee = false;
        this.totalPaye = 0.0;
        this.statut = "En attente";
    }

    public Facture(Long id, Long patientId, LocalDateTime dateFacture,
                   Double montantTotal, boolean estPayee, Double totalPaye) {
        this.id = id;
        this.patientId = patientId;
        this.dateFacture = dateFacture != null ? dateFacture : LocalDateTime.now();
        this.montantTotal = montantTotal;
        this.estPayee = estPayee;
        this.totalPaye = totalPaye != null ? totalPaye : 0.0;
        calculerEtat();
    }

    /* ================= GETTERS / SETTERS ================= */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
        calculerEtat();
    }

    public boolean isEstPayee() { return estPayee; }
    public void setEstPayee(boolean estPayee) {
        this.estPayee = estPayee;
        calculerEtat();
    }

    public Double getTotalPaye() { return totalPaye; }
    public void setTotalPaye(Double totalPaye) {
        this.totalPaye = totalPaye != null ? totalPaye : 0.0;
        calculerEtat();
    }

    public Double getReste() { return reste; }

    public String getStatut() { return statut; }

    public LocalDateTime getDateFacture() { return dateFacture; }
    public void setDateFacture(LocalDateTime dateFacture) {
        this.dateFacture = dateFacture != null ? dateFacture : LocalDateTime.now();
    }

    public String getNumeroFacture() { return numeroFacture; }
    public void setNumeroFacture(String numeroFacture) { this.numeroFacture = numeroFacture; }

    /* ================= LOGIQUE MÉTIER ================= */

    private void calculerEtat() {
        if (montantTotal != null) {
            this.reste = montantTotal - (totalPaye != null ? totalPaye : 0.0);
        } else {
            this.reste = null;
        }

        if (this.reste != null && this.reste <= 0) {
            this.estPayee = true;
            this.statut = "Payée";
        } else {
            this.estPayee = false;
            this.statut = "En attente";
        }
    }
}
