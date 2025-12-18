package ma.dentalTech.repository.modules.facture.impl.mySQL;

import ma.dentalTech.entities.pat.Facture;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.facture.api.FactureRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FactureRepositoryImpl implements FactureRepository {

    private static final String TABLE_NAME = "Factures";



    @Override
    public List<Facture> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY dateFacture DESC";
        List<Facture> out = new ArrayList<>();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapFacture(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Optional<Facture> findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapFacture(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void create(Facture f) {
        
        String sql = """
            INSERT INTO Factures(patient_id, dateFacture, montantTotal, estPayee, numeroFacture)
            VALUES(?,?,?,?,?)
            """;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, f.getPatientId());
            ps.setTimestamp(2, Timestamp.valueOf(f.getDateFacture()));
            ps.setDouble(3, f.getMontantTotal());
            ps.setBoolean(4, f.isEstPayee());
            ps.setString(5, f.getNumeroFacture()); 
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) f.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Facture f) {
        String sql = """
            UPDATE Factures SET patient_id=?, dateFacture=?, montantTotal=?, estPayee=?, numeroFacture=?
            WHERE id=?
            """;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, f.getPatientId());
            ps.setTimestamp(2, Timestamp.valueOf(f.getDateFacture()));
            ps.setDouble(3, f.getMontantTotal());
            ps.setBoolean(4, f.isEstPayee());
            ps.setString(5, f.getNumeroFacture()); 
            ps.setLong(6, f.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Facture f) {
        if (f != null) deleteById(f.getId());
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
    public List<Facture> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE patient_id = ? ORDER BY dateFacture DESC";
        List<Facture> out = new ArrayList<>();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapFacture(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Facture> findByEstPayee(boolean estPayee) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE estPayee = ? ORDER BY dateFacture DESC";
        List<Facture> out = new ArrayList<>();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, estPayee);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapFacture(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Facture> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE dateFacture BETWEEN ? AND ? ORDER BY dateFacture ASC";
        List<Facture> out = new ArrayList<>();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapFacture(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Optional<Facture> findByNumeroFacture(String numeroFacture) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE numeroFacture = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numeroFacture);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapFacture(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}