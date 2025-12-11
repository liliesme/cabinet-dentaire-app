package ma.dentalTech.repository.modules.utilisateur.impl.mySQL;

import ma.dentalTech.entities.pat.Utilisateur;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.utilisateur.api.UtilisateurRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilisateurRepositoryImpl implements UtilisateurRepository {



    @Override
    public List<Utilisateur> findAll() {
        String sql = "SELECT * FROM Utilisateurs ORDER BY nom, prenom";
        List<Utilisateur> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapUtilisateur(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Utilisateur findById(Long id) {
        String sql = "SELECT * FROM Utilisateurs WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapUtilisateur(rs);
                return null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(Utilisateur u) {
        String sql = """
            INSERT INTO Utilisateurs(nom, prenom, login, password, email, role_id)
            VALUES(?,?,?,?,?,?)
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getLogin());
            ps.setString(4, u.getPassword()); 
            ps.setString(5, u.getEmail());
            ps.setObject(6, u.getRoleId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) u.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Utilisateur u) {
        String sql = """
            UPDATE Utilisateurs SET nom=?, prenom=?, login=?, password=?, email=?, role_id=?
            WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getLogin());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getEmail());
            ps.setLong(6, u.getRoleId());
            ps.setLong(7, u.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Utilisateur u) {
        if (u != null) {
            deleteById(u.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Utilisateurs WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }



    @Override
    public Optional<Utilisateur> findByLogin(String login) {
        String sql = "SELECT * FROM Utilisateurs WHERE login = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapUtilisateur(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        String sql = "SELECT * FROM Utilisateurs WHERE email = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapUtilisateur(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean existsByLogin(String login) {
        String sql = "SELECT 1 FROM Utilisateurs WHERE login = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}