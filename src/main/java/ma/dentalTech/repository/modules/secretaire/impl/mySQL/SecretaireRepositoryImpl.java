package ma.dentalTech.repository.modules.secretaire.impl.mySQL;

import ma.dentalTech.entities.patient.Patient;
import ma.dentalTech.entities.secretaire.Secretaire;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.secretaire.api.SecretaireRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class SecretaireRepositoryImpl implements SecretaireRepository {

    // -------- CRUD --------
    @Override
    public List<Secretaire> findAll() {
        String sql = "SELECT * FROM Secretaires ORDER BY id";
        List<Secretaire> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapSecretaire(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Secretaire findById(Long id) {
        String sql = "SELECT * FROM Secretaires WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapSecretaire(rs);
                return null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(Patient patient) {

    }

    @Override
    public void update(Patient patient) {

    }

    @Override
    public void delete(Patient patient) {

    }

    @Override
    public void create(Secretaire s) {
        String sql = "INSERT INTO Secretaires(nom, prenom, adresse, telephone, email, dateCreation, sexe) VALUES(?,?,?,?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getNom());
            ps.setString(2, s.getPrenom());
            ps.setString(3, s.getAdresse());
            ps.setString(4, s.getTelephone());
            ps.setString(5, s.getEmail());
            ps.setTimestamp(6, s.getDateCreation() != null ? Timestamp.valueOf(s.getDateCreation()) : new Timestamp(System.currentTimeMillis()));
            ps.setString(7, s.getSexe().name());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) s.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Secretaire s) {
        String sql = "UPDATE Secretaires SET nom=?, prenom=?, adresse=?, telephone=?, email=?, dateCreation=?, sexe=? WHERE id=?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getNom());
            ps.setString(2, s.getPrenom());
            ps.setString(3, s.getAdresse());
            ps.setString(4, s.getTelephone());
            ps.setString(5, s.getEmail());
            ps.setTimestamp(6, s.getDateCreation() != null ? Timestamp.valueOf(s.getDateCreation()) : new Timestamp(System.currentTimeMillis()));
            ps.setString(7, s.getSexe().name());
            ps.setLong(8, s.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Secretaire s) { if (s != null) deleteById(s.getId()); }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Secretaires WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // -------- Extras --------
    @Override
    public Optional<Secretaire> findByEmail(String email) {
        String sql = "SELECT * FROM Secretaires WHERE email = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapSecretaire(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<Secretaire> findByTelephone(String telephone) {
        String sql = "SELECT * FROM Secretaires WHERE telephone = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, telephone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapSecretaire(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Secretaire> searchByNomPrenom(String keyword) {
        String sql = "SELECT * FROM Secretaires WHERE nom LIKE ? OR prenom LIKE ? ORDER BY nom, prenom";
        List<Secretaire> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapSecretaire(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT 1 FROM Secretaires WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Secretaires";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Secretaire> findPage(int limit, int offset) {
        String sql = "SELECT * FROM Secretaires ORDER BY id LIMIT ? OFFSET ?";
        List<Secretaire> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapSecretaire(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Medecin> findAllMedecins() {
        return List.of();
    }

    @Override
    public Optional<Medecin> findMedecinById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Medecin> searchMedecinsByNomPrenom(String keyword) {
        return List.of();
    }

    @Override
    public List<Medecin> findMedecinsBySpecialite(String specialite) {
        return List.of();
    }

    @Override
    public void createRendezVous(RendezVous rdv) {

    }

    @Override
    public void updateRendezVous(RendezVous rdv) {

    }

    @Override
    public void deleteRendezVous(Long id) {

    }

    @Override
    public Optional<RendezVous> findRendezVousById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<RendezVous> findRendezVousByPatient(Long patientId) {
        return List.of();
    }

    @Override
    public List<RendezVous> findRendezVousByMedecin(Long medecinId) {
        return List.of();
    }

    @Override
    public List<RendezVous> findRendezVousByDate(String date) {
        return List.of();
    }

    @Override
    public void assignMedecinToPatient(Long patientId, Long medecinId) {

    }

    @Override
    public void removeMedecinFromPatient(Long patientId, Long medecinId) {

    }

    @Override
    public List<Medecin> getMedecinsOfPatient(Long patientId) {
        return List.of();
    }

    @Override
    public List<Patient> getPatientsOfMedecin(Long medecinId) {
        return List.of();
    }
}
