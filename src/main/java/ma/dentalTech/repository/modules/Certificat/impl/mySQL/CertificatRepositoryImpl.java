package ma.dentalTech.repository.modules.Certificat.impl.mySQL;


import ma.dentalTech.entities.pat.Certificat;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.Certificat.api.CertificatRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CertificatRepositoryImpl implements CertificatRepository {

    private static final String TABLE_NAME = "Certificats";



    @Override
    public List<Certificat> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY dateEmission DESC";
        List<Certificat> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapCertificat(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Optional<Certificat> findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(RowMappers.mapCertificat(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void create(Certificat cert) {
        String sql = "INSERT INTO " + TABLE_NAME + "(patient_id, utilisateur_id, dateEmission, typeCertificat, dureeEnJours, motif) VALUES(?,?,?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, cert.getPatientId());
            ps.setLong(2, cert.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(cert.getDateEmission()));
            ps.setString(4, cert.getTypeCertificat());
            ps.setInt(5, cert.getDureeEnJours());
            ps.setString(6, cert.getMotif());

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) cert.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Certificat cert) {
        String sql = """
            UPDATE """ + TABLE_NAME + """
             SET patient_id=?, utilisateur_id=?, dateEmission=?, typeCertificat=?, dureeEnJours=?, motif=?
            WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, cert.getPatientId());
            ps.setLong(2, cert.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(cert.getDateEmission()));
            ps.setString(4, cert.getTypeCertificat());
            ps.setInt(5, cert.getDureeEnJours());
            ps.setString(6, cert.getMotif());
            ps.setLong(7, cert.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Certificat cert) {
        if (cert != null) deleteById(cert.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }



    @Override
    public List<Certificat> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE patient_id = ? ORDER BY dateEmission DESC";
        List<Certificat> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapCertificat(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Certificat> findByUtilisateurId(Long utilisateurId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE utilisateur_id = ? ORDER BY dateEmission DESC";
        List<Certificat> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapCertificat(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public List<Certificat> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE dateEmission BETWEEN ? AND ? ORDER BY dateEmission DESC";
        List<Certificat> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapCertificat(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}