package ma.dentalTech.repository.common;
import ma.dentalTech.entities.patient.Patient;
import ma.dentalTech.entities.enums.*;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public final class RowMappers {

    private RowMappers(){}

    public static Patient mapPatient(ResultSet rs) throws SQLException {

        Patient patientRow = new Patient();

        patientRow.setId(rs.getLong("id"));
        patientRow.setNom(rs.getString("nom"));
        patientRow.setPrenom(rs.getString("prenom"));
        patientRow.setAdresse(rs.getString("adresse"));
        patientRow.setTelephone(rs.getString("telephone"));
        patientRow.setEmail(rs.getString("email"));
        var dn = rs.getDate("dateNaissance");
        if (dn != null) patientRow.setDateNaissance(dn.toLocalDate());
        var dc = rs.getTimestamp("dateCreation");
        if (dc != null) patientRow.setDateCreation(dc.toLocalDateTime());
        patientRow.setSexe(Sexe.valueOf(rs.getString("sexe")));
        patientRow.setAssurance(Assurance.valueOf(rs.getString("assurance")));

        return patientRow;
    }


    public static Antecedent mapAntecedent(ResultSet rs) throws SQLException {
        Antecedent antecedentRow = new Antecedent();

        antecedentRow.setId             (rs.getLong("id"));
        antecedentRow.setNom            (rs.getString("nom"));
        antecedentRow.setCategorie      (CategorieAntecedent.valueOf(rs.getString("categorie")));
        antecedentRow.setNiveauRisque   (NiveauRisque.valueOf(rs.getString("niveauRisque")));

        return antecedentRow;
    }

    public static Medicament mapMedicament(ResultSet rs) throws SQLException {

        return Medicament.builder()
                .id(rs.getLong("id"))
                .nom(rs.getString("nom"))
                .dosage(rs.getString("dosage"))
                .presentation(rs.getString("presentation"))
                .build();
    }
    public static Charges mapCharges(ResultSet rs) throws SQLException {

        Timestamp dateTimestamp = rs.getTimestamp("date");


        return Charges.builder()
                .id(rs.getLong("id"))
                .titre(rs.getString("titre"))
                .description(rs.getString("description"))
                .montant(rs.getDouble("montant"))
                .date(dateTimestamp != null ? dateTimestamp.toLocalDateTime() : null)
                .utilisateurId(rs.getLong("utilisateur_id"))
                .build();
    }
    public static Revenues mapRevenues(ResultSet rs) throws SQLException {


        Timestamp dateTimestamp = rs.getTimestamp("date");


        Revenues revenu = new Revenues();


        revenu.setId(rs.getLong("id"));
        revenu.setTitre(rs.getString("titre"));
        revenu.setDescription(rs.getString("description"));
        revenu.setMontant(rs.getDouble("montant"));
        revenu.setDate(dateTimestamp != null ? dateTimestamp.toLocalDateTime() : null);


        return revenu;
    }
    public static Role mapRole(ResultSet rs) throws SQLException {

        return Role.builder()
                .id(rs.getLong("id"))
                .nom(rs.getString("nom"))
                .description(rs.getString("description"))
                .build();
    }
    public static DossierMedical mapDossierMedical(ResultSet rs) throws SQLException {


        Timestamp dateCreationTimestamp = rs.getTimestamp("dateCreation");

        return DossierMedical.builder()
                .id(rs.getLong("id"))
                .patientId(rs.getLong("patient_id"))
                .dateCreation(dateCreationTimestamp != null ? dateCreationTimestamp.toLocalDateTime() : null)
                .notesInitiales(rs.getString("notesInitiales"))

                .build();
    }
    public static Consultation mapConsultation(ResultSet rs) throws SQLException {


        Timestamp dateTimestamp = rs.getTimestamp("dateConsultation");

        return Consultation.builder()
                .id(rs.getLong("id"))
                .patientId(rs.getLong("patient_id"))
                .utilisateurId(rs.getLong("utilisateur_id"))
                .dateConsultation(dateTimestamp != null ? dateTimestamp.toLocalDateTime() : null)
                .diagnostic(rs.getString("diagnostic"))
                .traitement(rs.getString("traitement"))
                .build();
    }
    public static Agenda mapAgendaConfiguration(ResultSet rs) throws SQLException {


        Time debutTime = rs.getTime("heureDebut");
        Time finTime = rs.getTime("heureFin");


        String joursOuvrablesStr = rs.getString("joursOuvrables");
        String joursFeriesStr = rs.getString("joursFeries");


        Agenda agenda = new Agenda();
        agenda.setIdAgenda(rs.getLong("idAgenda"));
        agenda.setAnnee(rs.getInt("annee"));
        agenda.setMois(rs.getInt("mois"));
        agenda.setJoursOuvrables(stringToListDate(joursOuvrablesStr));
        agenda.setJoursFeries(stringToListDate(joursFeriesStr));
        agenda.setHeureDebut(debutTime != null ? debutTime.toLocalTime() : null);
        agenda.setHeureFin(finTime != null ? finTime.toLocalTime() : null);
        agenda.setDureeConsultation(rs.getInt("dureeConsultation"));

        return agenda;
    }
    public static InterventionMedecin mapInterventionMedecin(ResultSet rs) throws SQLException {


        Timestamp dateTimestamp = rs.getTimestamp("dateIntervention");


        return InterventionMedecin.builder()
                .id(rs.getLong("id"))
                .patientId(rs.getLong("patient_id"))
                .utilisateurId(rs.getLong("utilisateur_id"))
                .dateIntervention(dateTimestamp != null ? dateTimestamp.toLocalDateTime() : null)
                .typeIntervention(rs.getString("typeIntervention"))
                .description(rs.getString("description"))
                .build();
    }
    public static Ordonnance mapOrdonnance(ResultSet rs) throws SQLException {

        Timestamp dateTimestamp = rs.getTimestamp("dateOrdonnance");
        String lignesMedicamentsStr = rs.getString("lignesMedicaments");

        return Ordonnance.builder()
                .id(rs.getLong("id"))
                .patientId(rs.getLong("patient_id"))
                .utilisateurId(rs.getLong("utilisateur_id"))
                .dateOrdonnance(dateTimestamp != null ? dateTimestamp.toLocalDateTime() : null)
                .lignesMedicaments(stringToList(lignesMedicamentsStr))
                .build();
    }
    public static Certificat mapCertificat(ResultSet rs) throws SQLException {

        Timestamp dateTimestamp = rs.getTimestamp("dateEmission");

        return Certificat.builder()
                .id(rs.getLong("id"))
                .patientId(rs.getLong("patient_id"))
                .utilisateurId(rs.getLong("utilisateur_id"))
                .dateEmission(dateTimestamp != null ? dateTimestamp.toLocalDateTime() : null)
                .typeCertificat(rs.getString("typeCertificat"))
                .dureeEnJours(rs.getInt("dureeEnJours"))
                .motif(rs.getString("motif"))
                .build();
    }
    public static Acte mapActe(ResultSet rs) throws SQLException {

        return Acte.builder()
                .id(rs.getLong("id"))
                .nom(rs.getString("nom"))
                .description(rs.getString("description"))
                .typeActe(rs.getString("typeActe"))
                .tarifBase(rs.getDouble("tarifBase"))
                .build();
    }
    public static Prescription mapPrescription(ResultSet rs) throws SQLException {

        Timestamp dateTimestamp = rs.getTimestamp("datePrescription");
        String lignesMedicamentsStr = rs.getString("lignesMedicaments");

        return Prescription.builder()
                .id(rs.getLong("id"))
                .patientId(rs.getLong("patient_id"))
                .utilisateurId(rs.getLong("utilisateur_id"))
                .datePrescription(dateTimestamp != null ? dateTimestamp.toLocalDateTime() : null)
                .lignesMedicaments(stringToList(lignesMedicamentsStr))
                .build();
    }
    public static SituationFinanciere mapSituationFinanciere(ResultSet rs) throws SQLException {


        String statutStr = rs.getString("statut");

        return SituationFinanciere.builder()
                .id(rs.getLong("id"))
                .patientId(rs.getLong("patient_id"))
                .totalDesActes(rs.getDouble("totalDesActes"))
                .totalePaye(rs.getDouble("totalePaye"))
                .credit(rs.getDouble("credit")) // Le crédit est mappé tel quel depuis la DB
                .observation(rs.getString("observation"))
                // .statut(statutStr != null ? Statut.valueOf(statutStr) : null)
                .build();
    }
}
