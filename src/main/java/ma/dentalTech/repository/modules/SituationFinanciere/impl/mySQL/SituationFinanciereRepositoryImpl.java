package ma.dentalTech.repository.modules.SituationFinanciere.impl.mySQL;


import ma.dentalTech.entities.pat.SituationFinanciere;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.SituationFinanciere.api.SituationFinanciereRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SituationFinanciereRepositoryImpl implements SituationFinanciereRepository {

    private static final String TABLE_NAME = "SituationsFinancieres";



    @Override
    public List<SituationFinanciere> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY id ASC";
        List<SituationFinanciere> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapSituationFinanciere(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public SituationFinanciere findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapSituationFinanciere(rs);
                return null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(SituationFinanciere sf) {
        
        
        String sql = "INSERT INTO " + TABLE_NAME + "(patient_id, totalDesActes, totalePaye, credit, statut, observation) VALUES(?,?,?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, sf.getPatientId());
            ps.setDouble(2, sf.getTotalDesActes());
            ps.setDouble(3, sf.getTotalePaye());
            ps.setDouble(4, sf.getCredit()); 
            ps.setString(5, sf.getStatut() != null ? sf.getStatut().name() : null);
            ps.setString(6, sf.getObservation());

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) sf.setId(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(SituationFinanciere sf) {
        String sql = """
            UPDATE """ + TABLE_NAME + """
             SET patient_id=?, totalDesActes=?, totalePaye=?, credit=?, statut=?, observation=?
            WHERE id=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, sf.getPatientId());
            ps.setDouble(2, sf.getTotalDesActes());
            ps.setDouble(3, sf.getTotalePaye());
            ps.setDouble(4, sf.getCredit());
            ps.setString(5, sf.getStatut() != null ? sf.getStatut().name() : null);
            ps.setString(6, sf.getObservation());
            ps.setLong(7, sf.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(SituationFinanciere sf) {
        if (sf != null) deleteById(sf.getId());
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
    public Optional<SituationFinanciere> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE patient_id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapSituationFinanciere(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}