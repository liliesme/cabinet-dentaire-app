package ma.dentalTech.repository.modules.role.impl.mySQL;

import ma.dentalTech.entities.pat.Role;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.role.api.RoleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleRepositoryImpl implements RoleRepository {



    @Override
    public List<Role> findAll() {
        String sql = "SELECT * FROM Roles ORDER BY nom";
        List<Role> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapRole(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Role findById(Long id) {
        String sql = "SELECT * FROM Roles WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapRole(rs);
                return null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(Role r) {
        String sql = "INSERT INTO Roles(nom, description) VALUES(?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getNom());
            ps.setString(2, r.getDescription());

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) r.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Role r) {
        String sql = "UPDATE Roles SET nom=?, description=? WHERE id=?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, r.getNom());
            ps.setString(2, r.getDescription());
            ps.setLong(3, r.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Role r) {
        if (r != null) deleteById(r.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Roles WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }



    @Override
    public Optional<Role> findByNom(String nom) {
        String sql = "SELECT * FROM Roles WHERE nom = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nom);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapRole(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean existsByNom(String nom) {
        String sql = "SELECT COUNT(id) FROM Roles WHERE nom = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nom);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
                return false;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}