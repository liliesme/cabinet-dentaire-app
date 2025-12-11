package ma.dentalTech.repository.modules.Ordonnance.impl.mySQL;


import ma.dentalTech.entities.pat.Ordonnance;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.Ordonnance.api.OrdonnanceRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrdonnanceRepositoryImpl implements OrdonnanceRepository {

    private static final String TABLE_NAME = "Ordonnances";


    private String listToString(List<String> list) {
        if (list == null) return null;
        return String.join("||", list); 
    }



    @Override
    public List<Ordonnance> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY dateOrdonnance DESC";
        List<Ordonnance> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapOrdonnance(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Ordonnance findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapOrdonnance(rs);
                return null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(Ordonnance o) {
        
        String sql = "INSERT INTO " + TABLE_NAME + "(patient_id, utilisateur_id, dateOrdonnance, lignesMedicaments) VALUES(?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, o.getPatientId());
            ps.setLong(2, o.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(o.getDateOrdonnance()));
            ps.setString(4, listToString(o.getLignesMedicaments()));

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) o.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Ordonnance o) {
        String sql = """
            UPDATE """ + TABLE_NAME + """
             SET patient_id=?, utilisateur_id=?, dateOrdonnance=?, lignesMedicaments=?
            WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, o.getPatientId());
            ps.setLong(2, o.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(o.getDateOrdonnance()));
            ps.setString(4, listToString(o.getLignesMedicaments()));
            ps.setLong(5, o.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Ordonnance o) {
        if (o != null) deleteById(o.getId());
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
    public List<Ordonnance> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE patient_id = ? ORDER BY dateOrdonnance DESC";
        List<Ordonnance> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapOrdonnance(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Ordonnance> findByUtilisateurId(Long utilisateurId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE utilisateur_id = ? ORDER BY dateOrdonnance DESC";
        List<Ordonnance> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapOrdonnance(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Ordonnance> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE dateOrdonnance BETWEEN ? AND ? ORDER BY dateOrdonnance DESC";
        List<Ordonnance> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapOrdonnance(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}