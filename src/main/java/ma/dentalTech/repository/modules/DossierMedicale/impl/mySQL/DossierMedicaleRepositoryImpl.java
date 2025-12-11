package ma.dentalTech.repository.modules.DossierMedicale.impl.mySQL;


import ma.dentalTech.entities.pat.DossierMedicale;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.DossierMedicale.api.DossierMedicaleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DossierMedicaleRepositoryImpl implements DossierMedicaleRepository {


    @Override
    public List<DossierMedicale> findAll() {
        String sql = "SELECT * FROM DossiersMedicaux ORDER BY dateCreation DESC";
        List<DossierMedicale> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapDossierMedical(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public DossierMedicale findById(Long id) {
        String sql = "SELECT * FROM DossiersMedicaux WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapDossierMedical(rs);
                return null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(DossierMedicale dm) {
        
        String sql = "INSERT INTO DossiersMedicaux(patient_id, dateCreation, notesInitiales) VALUES(?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, dm.getPatientId());
            ps.setTimestamp(2, Timestamp.valueOf(dm.getDateCreation()));
            ps.setString(3, dm.getNotesInitiales());

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) dm.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(DossierMedicale dm) {
        String sql = """
            UPDATE DossiersMedicaux SET patient_id=?, dateCreation=?, notesInitiales=?
            WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, dm.getPatientId());
            ps.setTimestamp(2, Timestamp.valueOf(dm.getDateCreation()));
            ps.setString(3, dm.getNotesInitiales());
            ps.setLong(4, dm.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(DossierMedicale dm) {
        if (dm != null) deleteById(dm.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM DossiersMedicaux WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    
    
    

    @Override
    public Optional<DossierMedicale> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM DossiersMedicaux WHERE patient_id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapDossierMedical(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}