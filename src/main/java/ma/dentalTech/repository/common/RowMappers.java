package ma.dentalTech.repository.common;
import ma.dentalTech.entities.patient.Patient;
import ma.dentalTech.entities.enums.*;
import ma.dentalTech.entities.patient.Antecedent;
import java.sql.ResultSet;
import java.sql.SQLException;

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


}
package ma.dentalTech.repository.common;