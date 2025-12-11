package ma.dentalTech.repository.modules.InterventionMedecin.impl.mySQL;


import ma.dentalTech.entities.pat.InterventionMedecin;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.InterventionMedecin.api.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InterventionMedecinRepositoryImpl implements InterventionMedecinRepository {

    private static final String TABLE_NAME = "InterventionsMedecin";


    @Override
    public List<InterventionMedecin> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY dateIntervention DESC";
        List<InterventionMedecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapInterventionMedecin(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public InterventionMedecin findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapInterventionMedecin(rs);
                return null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(InterventionMedecin i) {
        
        String sql = "INSERT INTO " + TABLE_NAME + "(patient_id, utilisateur_id, dateIntervention, typeIntervention, description) VALUES(?,?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, i.getPatientId());
            ps.setLong(2, i.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(i.getDateIntervention()));
            ps.setString(4, i.getTypeIntervention());
            ps.setString(5, i.getDescription());

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) i.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(InterventionMedecin i) {
        String sql = """
            UPDATE """ + TABLE_NAME + """
             SET patient_id=?, utilisateur_id=?, dateIntervention=?, typeIntervention=?, description=?
            WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, i.getPatientId());
            ps.setLong(2, i.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(i.getDateIntervention()));
            ps.setString(4, i.getTypeIntervention());
            ps.setString(5, i.getDescription());
            ps.setLong(6, i.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(InterventionMedecin i) {
        if (i != null) deleteById(i.getId());
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
    public List<InterventionMedecin> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE patient_id = ? ORDER BY dateIntervention DESC";
        List<InterventionMedecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapInterventionMedecin(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<InterventionMedecin> findByUtilisateurId(Long utilisateurId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE utilisateur_id = ? ORDER BY dateIntervention DESC";
        List<InterventionMedecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapInterventionMedecin(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<InterventionMedecin> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE dateIntervention BETWEEN ? AND ? ORDER BY dateIntervention ASC";
        List<InterventionMedecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapInterventionMedecin(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}