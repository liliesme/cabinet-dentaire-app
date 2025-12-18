package ma.dentalTech.repository.modules.Consultation.impl.mySQL;


import ma.dentalTech.entities.pat.Consultation;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.Consultation.api.ConsultationRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    private static final String TABLE_NAME = "Consultations";

    @Override
    public List<Consultation> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY dateConsultation DESC";
        List<Consultation> out = new ArrayList<>();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapConsultation(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Optional<Consultation> findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapConsultation(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void create(Consultation c) {
        
        String sql = "INSERT INTO " + TABLE_NAME + "(patient_id, utilisateur_id, dateConsultation, diagnostic, traitement) VALUES(?,?,?,?,?)";

        
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, c.getPatientId());
            ps.setLong(2, c.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(c.getDateConsultation()));
            ps.setString(4, c.getDiagnostic());
            ps.setString(5, c.getTraitement());

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) c.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Consultation c) {
        String sql = """
            UPDATE """ + TABLE_NAME + """
             SET patient_id=?, utilisateur_id=?, dateConsultation=?, diagnostic=?, traitement=?
            WHERE id=?
            """;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, c.getPatientId());
            ps.setLong(2, c.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(c.getDateConsultation()));
            ps.setString(4, c.getDiagnostic());
            ps.setString(5, c.getTraitement());
            ps.setLong(6, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Consultation c) {
        if (c != null) deleteById(c.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    
    
    

    @Override
    public List<Consultation> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE patient_id = ? ORDER BY dateConsultation DESC";
        List<Consultation> out = new ArrayList<>();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapConsultation(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Consultation> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE dateConsultation BETWEEN ? AND ? ORDER BY dateConsultation ASC";
        List<Consultation> out = new ArrayList<>();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapConsultation(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}