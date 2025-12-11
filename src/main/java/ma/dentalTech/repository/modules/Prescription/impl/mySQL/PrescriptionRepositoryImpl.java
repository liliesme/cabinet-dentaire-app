package ma.dentalTech.repository.modules.Prescription.impl.mySQL;


import ma.dentalTech.entities.pat.Prescription;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.Prescription.api.PrescriptionRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    private static final String TABLE_NAME = "Prescriptions";


    private String listToString(List<String> list) {
        if (list == null) return null;
        return String.join("||", list); 
    }



    @Override
    public List<Prescription> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY datePrescription DESC";
        List<Prescription> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapPrescription(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Prescription findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapPrescription(rs);
                return null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(Prescription p) {
        
        String sql = "INSERT INTO " + TABLE_NAME + "(patient_id, utilisateur_id, datePrescription, lignesMedicaments) VALUES(?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, p.getPatientId());
            ps.setLong(2, p.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(p.getDatePrescription()));
            ps.setString(4, listToString(p.getLignesMedicaments()));

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) p.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Prescription p) {
        String sql = """
            UPDATE """ + TABLE_NAME + """
             SET patient_id=?, utilisateur_id=?, datePrescription=?, lignesMedicaments=?
            WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, p.getPatientId());
            ps.setLong(2, p.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(p.getDatePrescription()));
            ps.setString(4, listToString(p.getLignesMedicaments()));
            ps.setLong(5, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Prescription p) {
        if (p != null) deleteById(p.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }



    @Override
    public List<Prescription> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE patient_id = ? ORDER BY datePrescription DESC";
        List<Prescription> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapPrescription(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Prescription> findByUtilisateurId(Long utilisateurId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE utilisateur_id = ? ORDER BY datePrescription DESC";
        List<Prescription> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapPrescription(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Prescription> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE datePrescription BETWEEN ? AND ? ORDER BY datePrescription DESC";
        List<Prescription> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapPrescription(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}