package ma.dentalTech.repository.common;

import ma.dentalTech.entities.enums.*;
import ma.dentalTech.entities.pat.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class RowMappers {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private RowMappers(){}

    private static List<String> stringToList(String str) {
        if (str == null || str.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(str.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    
    public static List<LocalDate> stringToListDate(String str) {
        if (str == null || str.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(str.split(","))
                .map(String::trim)

                .map(LocalDate::parse)
                .collect(Collectors.toList());
    }



    public static Patient mapPatient(ResultSet rs) throws SQLException {
        Patient patientRow = new Patient();
        patientRow.setId(Long.valueOf(rs.getLong("id")));
        patientRow.setNom(rs.getString("nom"));
        patientRow.setPrenom(rs.getString("prenom"));

        String sexeStr = rs.getString("sexe");
        if (sexeStr != null) {
            patientRow.setSexe(Sexe.valueOf(sexeStr));
        }

        String assuranceStr = rs.getString("assurance");
        if (assuranceStr != null) {
            patientRow.setAssurance(Assurance.valueOf(assuranceStr));
        }
        return patientRow;
    }

    public static Antecedent mapAntecedent(ResultSet rs) throws SQLException {
        Antecedent antecedentRow = new Antecedent();
        antecedentRow.setId(Long.valueOf(rs.getLong("id")));
        antecedentRow.setNom(rs.getString("nom"));

        String catStr = rs.getString("categorie");
        if (catStr != null) {
            antecedentRow.setCategorie(CategorieAntecedent.valueOf(catStr));
        }

        String risqueStr = rs.getString("niveauRisque");
        if (risqueStr != null) {
            antecedentRow.setNiveauRisque(NiveauRisque.valueOf(risqueStr));
        }
        return antecedentRow;
    }

    public static Acte mapActe(ResultSet rs) throws SQLException {
        Acte acteRow = new Acte();
        acteRow.setId(Long.valueOf(rs.getLong("id")));
        acteRow.setNom(rs.getString("nom"));
        acteRow.setDescription(rs.getString("description"));
        acteRow.setTypeActe(rs.getString("typeActe"));
        acteRow.setTarifBase(Double.valueOf(rs.getDouble("tarifBase")));
        return acteRow;
    }

    public static Agenda mapAgendaConfiguration(ResultSet rs) throws SQLException {
        Agenda agendaRow = new Agenda();
        agendaRow.setIdAgenda(Long.valueOf(rs.getLong("idAgenda")));
        agendaRow.setAnnee(Integer.valueOf(rs.getInt("annee")));
        agendaRow.setMois(Integer.valueOf(rs.getInt("mois")));

        Time debut = rs.getTime("heureDebut");
        if (debut != null) agendaRow.setHeureDebut(debut.toLocalTime());

        Time fin = rs.getTime("heureFin");
        if (fin != null) agendaRow.setHeureFin(fin.toLocalTime());

        agendaRow.setDureeConsultation(Integer.valueOf(rs.getInt("dureeConsultation")));

        String joursOuvrablesStr = rs.getString("joursOuvrables");
        agendaRow.setJoursOuvrables(stringToListDate(joursOuvrablesStr));

        String joursFeriesStr = rs.getString("joursFeries");
        agendaRow.setJoursFeries(stringToListDate(joursFeriesStr));

        return agendaRow;
    }

    public static Certificat mapCertificat(ResultSet rs) throws SQLException {
        Certificat certRow = new Certificat();
        certRow.setId(Long.valueOf(rs.getLong("id")));
        certRow.setPatientId(Long.valueOf(rs.getLong("patient_id")));
        certRow.setUtilisateurId(Long.valueOf(rs.getLong("utilisateur_id")));

        Timestamp ts = rs.getTimestamp("dateEmission");
        if (ts != null) { certRow.setDateEmission(ts.toLocalDateTime()); }

        certRow.setTypeCertificat(rs.getString("typeCertificat"));
        certRow.setDureeEnJours(Integer.valueOf(rs.getInt("dureeEnJours")));
        certRow.setMotif(rs.getString("motif"));

        return certRow;
    }

    public static Charges mapCharges(ResultSet rs) throws SQLException {
        Charges chargesRow = new Charges();
        chargesRow.setId(Long.valueOf(rs.getLong("id")));
        chargesRow.setTitre(rs.getString("titre"));
        chargesRow.setDescription(rs.getString("description"));
        chargesRow.setMontant(Double.valueOf(rs.getDouble("montant")));
        chargesRow.setUtilisateurId(Long.valueOf(rs.getLong("utilisateur_id")));

        Timestamp ts = rs.getTimestamp("date");
        if (ts != null) { chargesRow.setDate(ts.toLocalDateTime()); }

        return chargesRow;
    }

    public static Consultation mapConsultation(ResultSet rs) throws SQLException {
        Consultation consultationRow = new Consultation();

        consultationRow.setId(Long.valueOf(rs.getLong("id")));
        consultationRow.setPatientId(Long.valueOf(rs.getLong("patient_id")));
        consultationRow.setUtilisateurId(Long.valueOf(rs.getLong("utilisateur_id")));

        Timestamp ts = rs.getTimestamp("dateConsultation");
        if (ts != null) { consultationRow.setDateConsultation(ts.toLocalDateTime()); }

        consultationRow.setDiagnostic(rs.getString("diagnostic"));
        consultationRow.setTraitement(rs.getString("traitement"));


        try {
            String statutStr = rs.getString("statut");
            if (statutStr != null) {
                consultationRow.setStatut(StatutRendezVous.valueOf(statutStr));
            }
        } catch (SQLException | IllegalArgumentException ignored) {
            
        }

        return consultationRow;
    }
    public static DossierMedicale mapDossierMedical(ResultSet rs) throws SQLException {
        DossierMedicale dmRow = new DossierMedicale();

        dmRow.setId(Long.valueOf(rs.getLong("id")));
        dmRow.setPatientId(Long.valueOf(rs.getLong("patient_id")));


        Timestamp ts = rs.getTimestamp("dateCreation");
        if (ts != null) {
            dmRow.setDateCreation(ts.toLocalDateTime());
        }

        dmRow.setNotesInitiales(rs.getString("notesInitiales"));
        return dmRow;
    }
    public static Facture mapFacture(ResultSet rs) throws SQLException {
        Facture factureRow = new Facture();

        factureRow.setId(Long.valueOf(rs.getLong("id")));
        factureRow.setPatientId(Long.valueOf(rs.getLong("patient_id")));


        Timestamp ts = rs.getTimestamp("dateFacture");
        if (ts != null) {
            factureRow.setDateFacture(ts.toLocalDateTime());
        }

        factureRow.setMontantTotal(Double.valueOf(rs.getDouble("montantTotal")));
        factureRow.setEstPayee(rs.getBoolean("estPayee"));


        try {
            factureRow.setNumeroFacture(rs.getString("numeroFacture"));
        } catch (SQLException ignored) {  }

        return factureRow;
    }
    public static InterventionMedecin mapInterventionMedecin(ResultSet rs) throws SQLException {
        InterventionMedecin imRow = new InterventionMedecin();

        imRow.setId(Long.valueOf(rs.getLong("id")));
        imRow.setPatientId(Long.valueOf(rs.getLong("patient_id")));
        imRow.setUtilisateurId(Long.valueOf(rs.getLong("utilisateur_id")));

        Timestamp ts = rs.getTimestamp("dateIntervention");
        if (ts != null) {
            imRow.setDateIntervention(ts.toLocalDateTime());
        }

        imRow.setTypeIntervention(rs.getString("typeIntervention"));
        imRow.setDescription(rs.getString("description"));

        try {
            imRow.setPrixDePatient(Double.valueOf(rs.getDouble("prixDePatient")));
            imRow.setNombDelit(Integer.valueOf(rs.getInt("nombDelit")));
        } catch (SQLException ignored) {}

        return imRow;
    }
    public static medecin mapMedecin(ResultSet rs) throws SQLException {
        medecin medecinRow = new medecin();

        medecinRow.setId(Long.valueOf(rs.getLong("id")));
        medecinRow.setNom(rs.getString("nom"));
        medecinRow.setPrenom(rs.getString("prenom"));
        medecinRow.setAdresse(rs.getString("adresse"));
        medecinRow.setTelephone(rs.getString("telephone"));
        medecinRow.setEmail(rs.getString("email"));
        medecinRow.setSpecialite(rs.getString("specialite"));


        Timestamp ts = rs.getTimestamp("dateCreation");
        if (ts != null) {
            medecinRow.setDateCreation(ts.toLocalDateTime());
        }


        try {
            medecinRow.setAgendaMensuel(rs.getString("agendaMensuel"));
        } catch (SQLException ignored) {}

        return medecinRow;
    }
    public static Medicament mapMedicament(ResultSet rs) throws SQLException {
        Medicament medicamentRow = new Medicament();

        medicamentRow.setId(Long.valueOf(rs.getLong("id")));
        medicamentRow.setNom(rs.getString("nom"));

        try {
            medicamentRow.setDosage(rs.getString("dosage"));
            medicamentRow.setPresentation(rs.getString("presentation"));
        } catch (SQLException ignored) {}

        try {
            medicamentRow.setLaboratoire(rs.getString("laboratoire"));
            medicamentRow.setTypeDosage(rs.getString("typeDosage"));
            medicamentRow.setForme(rs.getString("forme"));
            medicamentRow.setRemboursable(rs.getBoolean("remboursable"));
            medicamentRow.setPosologie(rs.getString("posologie"));
            medicamentRow.setDescription(rs.getString("description"));
        } catch (SQLException ignored) {}

        return medicamentRow;
    }
    public static Ordonnance mapOrdonnance(ResultSet rs) throws SQLException {
        Ordonnance ordonnanceRow = new Ordonnance();

        ordonnanceRow.setId(Long.valueOf(rs.getLong("id")));
        ordonnanceRow.setPatientId(Long.valueOf(rs.getLong("patient_id")));
        ordonnanceRow.setUtilisateurId(Long.valueOf(rs.getLong("utilisateur_id")));

        Timestamp ts = rs.getTimestamp("dateOrdonnance");
        if (ts != null) {
            ordonnanceRow.setDateOrdonnance(ts.toLocalDateTime());
        }

        String lignesStr = rs.getString("lignesMedicaments");

        List<String> lignes = lignesStr != null ? Arrays.asList(lignesStr.split("\\|\\|")) : new ArrayList<>();
        ordonnanceRow.setLignesMedicaments(lignes);

        return ordonnanceRow;
    }
    public static Prescription mapPrescription(ResultSet rs) throws SQLException {
        Prescription prescriptionRow = new Prescription();

        prescriptionRow.setId(Long.valueOf(rs.getLong("id")));
        prescriptionRow.setPatientId(Long.valueOf(rs.getLong("patient_id")));
        prescriptionRow.setUtilisateurId(Long.valueOf(rs.getLong("utilisateur_id")));

        Timestamp ts = rs.getTimestamp("datePrescription");
        if (ts != null) {
            prescriptionRow.setDatePrescription(ts.toLocalDateTime());
        }


        String lignesStr = rs.getString("lignesMedicaments");
        List<String> lignes = lignesStr != null ? Arrays.asList(lignesStr.split("\\|\\|")) : new ArrayList<>();
        prescriptionRow.setLignesMedicaments(lignes);


        try {
            prescriptionRow.setDescription(rs.getString("description"));

        } catch (SQLException ignored) {}

        return prescriptionRow;
    }
    public static RDV mapRDV(ResultSet rs) throws SQLException {
        RDV rdvRow = new RDV();

        rdvRow.setId(Long.valueOf(rs.getLong("id")));
        rdvRow.setPatientId(Long.valueOf(rs.getLong("patient_id")));
        rdvRow.setMedecinId(Long.valueOf(rs.getLong("medecin_id")));

        Timestamp ts = rs.getTimestamp("dateHeure");
        if (ts != null) {
            rdvRow.setDateHeure(ts.toLocalDateTime());
        }

        rdvRow.setMotif(rs.getString("motif"));

        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            try {
                rdvRow.setStatut(StatutRendezVous.valueOf(statutStr));
            } catch (IllegalArgumentException e) {
                System.err.println("Statut RDV inconnu: " + statutStr);
            }
        }

        try {
            rdvRow.setNoteRdvMedecin(rs.getString("noteRdvMedecin"));
        } catch (SQLException ignored) {}

        return rdvRow;
    }
    public static Revenues mapRevenues(ResultSet rs) throws SQLException {
        Revenues revenuesRow = new Revenues();

        revenuesRow.setId(Long.valueOf(rs.getLong("id")));
        revenuesRow.setTitre(rs.getString("titre"));
        revenuesRow.setMontant(Double.valueOf(rs.getDouble("montant")));
        revenuesRow.setDescription(rs.getString("description"));

        Timestamp ts = rs.getTimestamp("date");
        if (ts != null) {
            revenuesRow.setDate(ts.toLocalDateTime());
        }

        Long factureId = (Long) rs.getLong("facture_id");
        if (rs.wasNull()) {
            revenuesRow.setFactureId(null);
        } else {
            revenuesRow.setFactureId(factureId);
        }

        revenuesRow.setUtilisateurId(Long.valueOf(rs.getLong("utilisateur_id")));

        return revenuesRow;
    }

    public static Role mapRole(ResultSet rs) throws SQLException {
        Role roleRow = new Role();

        roleRow.setId(Long.valueOf(rs.getLong("id")));
        roleRow.setNom(rs.getString("nom"));

        try {
            roleRow.setDescription(rs.getString("description"));
        } catch (SQLException ignored) {
        }

        return roleRow;
    }
    public static Secretaire mapSecretaire(ResultSet rs) throws SQLException {
        Secretaire secretaireRow = new Secretaire();

        secretaireRow.setId(Long.valueOf(rs.getLong("id")));
        secretaireRow.setNom(rs.getString("nom"));
        secretaireRow.setPrenom(rs.getString("prenom"));
        secretaireRow.setAdresse(rs.getString("adresse"));
        secretaireRow.setTelephone(rs.getString("telephone"));
        secretaireRow.setEmail(rs.getString("email"));

        Timestamp ts = rs.getTimestamp("dateCreation");
        if (ts != null) {
            secretaireRow.setDateCreation(ts.toLocalDateTime());
        }


        String sexeStr = rs.getString("sexe");
        if (sexeStr != null) {
            try {
                secretaireRow.setSexe(Sexe.valueOf(sexeStr));
            } catch (IllegalArgumentException ignored) {}
        }


        try {
            secretaireRow.setNumCNSS(rs.getString("numCNSS"));
            secretaireRow.setCommission(Double.valueOf(rs.getDouble("commission")));
        } catch (SQLException ignored) {}

        return secretaireRow;
    }
    public static SituationFinanciere mapSituationFinanciere(ResultSet rs) throws SQLException {
        SituationFinanciere sfRow = new SituationFinanciere();

        sfRow.setId(Long.valueOf(rs.getLong("id")));
        sfRow.setPatientId(Long.valueOf(rs.getLong("patient_id")));
        sfRow.setTotalDesActes(Double.valueOf(rs.getDouble("totalDesActes")));
        sfRow.setTotalePaye(Double.valueOf(rs.getDouble("totalePaye")));
        sfRow.setCredit(Double.valueOf(rs.getDouble("credit")));
        sfRow.setObservation(rs.getString("observation"));

        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            try {
                sfRow.setStatut(StatutFinancier.valueOf(statutStr));
            } catch (IllegalArgumentException e) {}
        }

        return sfRow;
    }

    public static Utilisateur mapUtilisateur(ResultSet rs) throws SQLException {

        Utilisateur u = new Utilisateur() {};

        u.setId(Long.valueOf(rs.getLong("id")));
        u.setNom(rs.getString("nom"));
        u.setPrenom(rs.getString("prenom"));
        u.setEmail(rs.getString("email"));
        u.setLogin(rs.getString("login"));
        u.setMotDePasse(rs.getString("password"));

        u.setAdresse(rs.getString("adresse"));


        String sexeStr = rs.getString("sexe");
        if (sexeStr != null) {
            try { u.setSexe(Sexe.valueOf(sexeStr)); } catch (IllegalArgumentException ignored) {}
        }

        u.setRoleId(Long.valueOf(rs.getLong("role_id")));

        java.sql.Date dateN = rs.getDate("dateNaissance");
        if (dateN != null) u.setDateNaissance(dateN.toLocalDate());

        java.sql.Date dateL = rs.getDate("dateLoginDate");
        if (dateL != null) u.setDateLoginDate(dateL.toLocalDate());

        return u;
    }

    public static Staff mapStaff(ResultSet rs) throws SQLException {
        Staff staffRow = new Staff();

        staffRow.setId(Long.valueOf(rs.getLong("id")));
        staffRow.setNom(rs.getString("nom"));
        staffRow.setPrenom(rs.getString("prenom"));

        staffRow.setEmail(rs.getString("email"));

        staffRow.setCin(rs.getString("cin"));
        staffRow.setRoleId(rs.getLong("role_id"));

        staffRow.setSalaire(Double.valueOf(rs.getDouble("salaire")));

        Double prime = (Double) rs.getDouble("prime");
        if (!rs.wasNull()) {
            staffRow.setPrime(prime);
        } else {
            staffRow.setPrime(null);
        }


        java.sql.Date dateEmbauche = rs.getDate("dateEmbauche");
        if (dateEmbauche != null) {
            staffRow.setDateEmbauche(dateEmbauche.toLocalDate());
        }

        java.sql.Date dateDepart = rs.getDate("dateDepart");
        if (dateDepart != null) {
            staffRow.setDateDepart(dateDepart.toLocalDate());
        }

        return staffRow;
    }
}