package ma.dentalTech.repository.modules.Acte.impl.mySQL;

import ma.dentalTech.entities.pat.Acte;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.Acte.api.ActeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActeRepositoryImpl implements ActeRepository {

    private static final String TABLE_NAME = "Actes";

    @Override
    public List<Acte> findAll() {
        String sql = "SELECT id, nom, description, typeActe, tarifBase FROM " + TABLE_NAME + " ORDER BY nom ASC";
        List<Acte> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapActe(rs));
        } catch (SQLException e) { throw new RuntimeException("Erreur lors de la récupération de tous les actes", e); }
        return out;
    }

    @Override
    public Optional<Acte> findById(Long id) {
        String sql = "SELECT id, nom, description, typeActe, tarifBase FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapActe(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par ID: " + id, e);
        }
    }

    @Override
    public void create(Acte a) {
        String sql = "INSERT INTO " + TABLE_NAME + "(nom, description, typeActe, tarifBase) VALUES(?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getNom());
            ps.setString(2, a.getDescription());
            ps.setString(3, a.getTypeActe());
            ps.setDouble(4, a.getTarifBase());

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) a.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException("Erreur lors de la création de l'acte: " + a.getNom(), e); }
    }

    @Override
    public void update(Acte a) {
        String sql = "UPDATE " + TABLE_NAME + " SET nom=?, description=?, typeActe=?, tarifBase=? WHERE id=?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getNom());
            ps.setString(2, a.getDescription());
            ps.setString(3, a.getTypeActe());
            ps.setDouble(4, a.getTarifBase());
            ps.setLong(5, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erreur lors de la mise à jour de l'acte: " + a.getId(), e); }
    }

    @Override
    public void delete(Acte a) {
        if (a != null) deleteById(a.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erreur lors de la suppression par ID: " + id, e); }
    }

    @Override
    public Optional<Acte> findByNom(String nom) {
        String sql = "SELECT id, nom, description, typeActe, tarifBase FROM " + TABLE_NAME + " WHERE nom = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nom);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapActe(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("Erreur lors de la recherche par nom: " + nom, e); }
    }

    @Override
    public List<Acte> findByTypeActe(String typeActe) {
        String sql = "SELECT id, nom, description, typeActe, tarifBase FROM " + TABLE_NAME + " WHERE typeActe = ? ORDER BY nom ASC";
        List<Acte> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, typeActe);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapActe(rs));
            }
        } catch (SQLException e) { throw new RuntimeException("Erreur lors de la recherche par type d'acte: " + typeActe, e); }
        return out;
    }
}