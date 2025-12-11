package ma.dentalTech.repository.modules.medecin.impl.mySQL;

import ma.dentalTech.entities.pat.medecin;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.medecin.api.MedecinRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedecinRepositoryImpl implements MedecinRepository {


    @Override
    public List<medecin> findAll() {
        String sql = "SELECT * FROM Medecins ORDER BY id";
        List<medecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapMedecin(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    @Override
    public medecin findById(Long id) {
        String sql = "SELECT * FROM Medecins WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapMedecin(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(medecin m) {
        String sql = """
            INSERT INTO Medecins(nom, prenom, adresse, telephone, email, specialite, dateCreation)
            VALUES(?,?,?,?,?,?,?)
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getAdresse());
            ps.setString(4, m.getTelephone());
            ps.setString(5, m.getEmail());
            ps.setString(6, m.getSpecialite());
            if (m.getDateCreation() != null) ps.setTimestamp(7, Timestamp.valueOf(m.getDateCreation()));
            else ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) m.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(medecin m) {
        String sql = """
            UPDATE Medecins SET nom=?, prenom=?, adresse=?, telephone=?, email=?, specialite=?, dateCreation=? WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getAdresse());
            ps.setString(4, m.getTelephone());
            ps.setString(5, m.getEmail());
            ps.setString(6, m.getSpecialite());
            if (m.getDateCreation() != null) ps.setTimestamp(7, Timestamp.valueOf(m.getDateCreation()));
            else ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            ps.setLong(8, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(medecin m) {
        if (m != null) deleteById(m.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Medecins WHERE id=?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    
    @Override
    public Optional<medecin> findByEmail(String email) {
        String sql = "SELECT * FROM Medecins WHERE email=?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapMedecin(rs));
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<medecin> findByTelephone(String telephone) {
        String sql = "SELECT * FROM Medecins WHERE telephone=?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, telephone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapMedecin(rs));
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<medecin> searchByNomPrenom(String keyword) {
        String sql = "SELECT * FROM Medecins WHERE nom LIKE ? OR prenom LIKE ? ORDER BY nom, prenom";
        List<medecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedecin(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT 1 FROM Medecins WHERE id=?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Medecins";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<medecin> findPage(int limit, int offset) {
        String sql = "SELECT * FROM Medecins ORDER BY id LIMIT ? OFFSET ?";
        List<medecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedecin(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    
    @Override
    public List<medecin> findBySpecialite(String specialite) {
        String sql = "SELECT * FROM Medecins WHERE specialite=? ORDER BY nom, prenom";
        List<medecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, specialite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedecin(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
}
