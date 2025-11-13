package ma.dentalTech.repository.modules.finance.impl.mySQL;

import ma.dentalTech.entities.finance.Facture;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.finance.api.FactureRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FactureRepositoryImpl implements FactureRepository {
    // -------- CRUD --------
    @Override
    public List<Facture> findAll() {
        String sql = "SELECT * FROM Factures ORDER BY dateFacture DESC";
        List<Facture> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapFacture(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Facture findById(Long id) {
        String sql = "SELECT * FROM Factures WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapFacture(rs);
                return null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(Facture f) {
        String sql = """
            INSERT INTO Factures(patient_id, dateFacture, montantTotal, estPayee)
            VALUES(?,?,?,?)
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, f.getPatientId());
            ps.setTimestamp(2, Timestamp.valueOf(f.getDateFacture()));
            ps.setDouble(3, f.getMontantTotal());
            ps.setBoolean(4, f.isEstPayee());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) f.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Facture f) {
        String sql = """
            UPDATE Factures SET patient_id=?, dateFacture=?, montantTotal=?, estPayee=?
            WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, f.getPatientId());
            ps.setTimestamp(2, Timestamp.valueOf(f.getDateFacture()));
            ps.setDouble(3, f.getMontantTotal());
            ps.setBoolean(4, f.isEstPayee());
            ps.setLong(5, f.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Facture f) { if (f != null) deleteById(f.getId()); }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Factures WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // -------- Extras (Assuming API methods exist) --------
    public List<Facture> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM Factures WHERE patient_id = ? ORDER BY dateFacture DESC";
        List<Facture> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapFacture(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}