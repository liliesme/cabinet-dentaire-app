package ma.dentalTech.repository.modules.rdv.impl.mySQL;

import ma.dentalTech.entities.pat.RDV;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.rdv.api.RdvRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RdvRepositoryImpl implements RdvRepository {



    @Override
    public List<RDV> findAll() {
        String sql = "SELECT * FROM Rdv ORDER BY dateHeure DESC";
        List<RDV> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapRDV(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Optional<RDV> findById(Long id) {
        String sql = "SELECT * FROM Rdv WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapRDV(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(RDV r) {
        String sql = "INSERT INTO Rdv(patient_id, medecin_id, dateHeure, motif, statut) VALUES(?,?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, r.getPatientId());
            ps.setLong(2, r.getMedecinId());
            ps.setTimestamp(3, Timestamp.valueOf(r.getDateHeure()));
            ps.setString(4, r.getMotif());
            ps.setString(5, r.getStatut().name());

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) r.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(RDV r) {
        String sql = """
            UPDATE Rdv SET patient_id=?, medecin_id=?, dateHeure=?, motif=?, statut=?
            WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, r.getPatientId());
            ps.setLong(2, r.getMedecinId());
            ps.setTimestamp(3, Timestamp.valueOf(r.getDateHeure()));
            ps.setString(4, r.getMotif());
            ps.setString(5, r.getStatut().name());
            ps.setLong(6, r.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(RDV r) {
        if (r != null) {
            deleteById(r.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Rdv WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    
    
    

    @Override
    public List<RDV> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM Rdv WHERE patient_id = ? ORDER BY dateHeure DESC";
        List<RDV> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapRDV(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<RDV> findByMedecinId(Long medecinId) {
        String sql = "SELECT * FROM Rdv WHERE medecin_id = ? ORDER BY dateHeure DESC";
        List<RDV> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, medecinId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapRDV(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<RDV> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM Rdv WHERE dateHeure BETWEEN ? AND ? ORDER BY dateHeure";
        List<RDV> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapRDV(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<RDV> findTodayRdv() {
        LocalDate today = LocalDate.now();
        
        LocalDateTime start = today.atStartOfDay();
        
        LocalDateTime end = today.plusDays(1).atStartOfDay().minusNanos(1);

        return findByDateBetween(start, end);
    }
}