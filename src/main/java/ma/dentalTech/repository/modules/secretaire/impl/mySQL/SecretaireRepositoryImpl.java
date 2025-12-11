package ma.dentalTech.repository.modules.secretaire.impl.mySQL;

import ma.dentalTech.entities.enums.Sexe;
import ma.dentalTech.entities.pat.medecin;
import ma.dentalTech.entities.pat.Patient;
import ma.dentalTech.entities.pat.RDV;
import ma.dentalTech.entities.pat.Secretaire;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.secretaire.api.SecretaireRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter; 

public class SecretaireRepositoryImpl implements SecretaireRepository {

    
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
            ps.setString(7, s.getSexe() != null ? s.getSexe().name() : Sexe.AUTRE.name());
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
            ps.setString(7, s.getSexe() != null ? s.getSexe().name() : Sexe.AUTRE.name());
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
    public List<medecin> findAllMedecins() {
        
        String sql = "SELECT * FROM Medecins ORDER BY nom";
        List<medecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapMedecin(rs)); 
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Optional<medecin> findMedecinById(Long id) {
        String sql = "SELECT * FROM Medecins WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapMedecin(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<medecin> searchMedecinsByNomPrenom(String keyword) {
        String sql = "SELECT * FROM Medecins WHERE nom LIKE ? OR prenom LIKE ?";
        List<medecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedecin(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<medecin> findMedecinsBySpecialite(String specialite) {
        String sql = "SELECT * FROM Medecins WHERE specialite = ?";
        List<medecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, specialite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedecin(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    

    
    

    @Override
    public void createRendezVous(RDV rdv) {
        String sql = "INSERT INTO Rdv(patient_id, medecin_id, dateHeure, motif, statut) VALUES(?,?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, rdv.getPatientId());
            ps.setLong(2, rdv.getMedecinId());
            ps.setTimestamp(3, Timestamp.valueOf(rdv.getDateHeure()));
            ps.setString(4, rdv.getMotif());
            ps.setString(5, rdv.getStatut().name());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) rdv.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void updateRendezVous(RDV rdv) {
        String sql = "UPDATE Rdv SET patient_id=?, medecin_id=?, dateHeure=?, motif=?, statut=? WHERE id=?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, rdv.getPatientId());
            ps.setLong(2, rdv.getMedecinId());
            ps.setTimestamp(3, Timestamp.valueOf(rdv.getDateHeure()));
            ps.setString(4, rdv.getMotif());
            ps.setString(5, rdv.getStatut().name());
            ps.setLong(6, rdv.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void deleteRendezVous(Long id) {
        String sql = "DELETE FROM Rdv WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<RDV> findRendezVousById(Long id) {
        String sql = "SELECT * FROM Rdv WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapRDV(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<RDV> findRendezVousByPatient(Long patientId) {
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
    public List<RDV> findRendezVousByMedecin(Long medecinId) {
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
    public List<RDV> findRendezVousByDate(String date) {
        
        LocalDate targetDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime start = targetDate.atStartOfDay();
        LocalDateTime end = targetDate.plusDays(1).atStartOfDay().minusNanos(1);

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
    public Optional<Patient> findPatientByEmail(String email) {
        
        String sql = "SELECT * FROM Patients WHERE email = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapPatient(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<Patient> findPatientByTelephone(String telephone) {
        String sql = "SELECT * FROM Patients WHERE telephone = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, telephone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapPatient(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Patient> searchPatientsByNomPrenom(String keyword) {
        String sql = "SELECT * FROM Patients WHERE nom LIKE ? OR prenom LIKE ? ORDER BY nom, prenom";
        List<Patient> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapPatient(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Patient> findAllPatients() {
        String sql = "SELECT * FROM Patients ORDER BY nom, prenom";
        List<Patient> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapPatient(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }


    
    @Override
    public void assignMedecinToPatient(Long patientId, Long medecinId) {
        
        String sql = "INSERT INTO Patient_Medecin(patient_id, medecin_id) VALUES(?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            ps.setLong(2, medecinId);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void removeMedecinFromPatient(Long patientId, Long medecinId) {
        String sql = "DELETE FROM Patient_Medecin WHERE patient_id=? AND medecin_id=?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            ps.setLong(2, medecinId);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<medecin> getMedecinsOfPatient(Long patientId) {
        
        String sql = "SELECT m.* FROM Medecins m JOIN Patient_Medecin pm ON m.id = pm.medecin_id WHERE pm.patient_id = ?";
        List<medecin> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedecin(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Patient> getPatientsOfMedecin(Long medecinId) {
        
        String sql = "SELECT p.* FROM Patients p JOIN Patient_Medecin pm ON p.id = pm.patient_id WHERE pm.medecin_id = ?";
        List<Patient> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, medecinId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapPatient(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}