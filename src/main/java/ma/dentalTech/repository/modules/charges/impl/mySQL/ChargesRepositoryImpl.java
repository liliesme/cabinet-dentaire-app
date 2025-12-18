package ma.dentalTech.repository.modules.charges.impl.mySQL;

import ma.dentalTech.entities.pat.Charges;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.charges.api.ChargesRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChargesRepositoryImpl implements ChargesRepository {

    @Override
    public List<Charges> findAll() {
        String sql = "SELECT * FROM Charges ORDER BY date DESC";
        List<Charges> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapCharges(rs)); 
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Optional<Charges> findById(Long id) {
        String sql = "SELECT * FROM Charges WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapCharges(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void create(Charges ch) {
        String sql = "INSERT INTO Charges(titre, montant, date, description, utilisateur_id) VALUES(?,?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ch.getTitre());
            ps.setDouble(2, ch.getMontant());
            
            ps.setTimestamp(3, Timestamp.valueOf(ch.getDate()));
            ps.setString(4, ch.getDescription());
            ps.setLong(5, ch.getUtilisateurId());

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) ch.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Charges ch) {
        String sql = """
            UPDATE Charges SET titre=?, montant=?, date=?, description=?, utilisateur_id=?
            WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, ch.getTitre());
            ps.setDouble(2, ch.getMontant());
            ps.setTimestamp(3, Timestamp.valueOf(ch.getDate()));
            ps.setString(4, ch.getDescription());
            ps.setLong(5, ch.getUtilisateurId());
            ps.setLong(6, ch.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Charges ch) {
        if (ch != null) deleteById(ch.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Charges WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    

    @Override
    public List<Charges> findByTitre(String titre) {
        String sql = "SELECT * FROM Charges WHERE titre = ? ORDER BY date DESC";
        List<Charges> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, titre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapCharges(rs)); 
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    
    @Override
    public List<Charges> findByDateBetween(LocalDate start, LocalDate end) {
        
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59, 999999999);

        String sql = "SELECT * FROM Charges WHERE date BETWEEN ? AND ? ORDER BY date DESC";
        List<Charges> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(2, Timestamp.valueOf(endDateTime));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapCharges(rs)); 
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}