package ma.dentalTech.repository.modules.Agenda.impl.mySQL;


import ma.dentalTech.entities.pat.Agenda;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.repository.common.RowMappers;
import ma.dentalTech.repository.modules.Agenda.api.AgendaRepository;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime; 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AgendaRepositoryImpl implements AgendaRepository {

    private static final String TABLE_NAME = "AgendaConfiguration";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;


    
    private String listDateToString(List<LocalDate> dates) {
        if (dates == null) return null;
        return dates.stream().map(DATE_FORMATTER::format).collect(Collectors.joining(","));
    }


    @Override
    public List<Agenda> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY annee DESC, mois DESC";
        List<Agenda> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapAgendaConfiguration(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public Agenda findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE idAgenda = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapAgendaConfiguration(rs);
                return null;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(Agenda a) {
        String sql = "INSERT INTO " + TABLE_NAME + "(annee, mois, joursOuvrables, joursFeries, heureDebut, heureFin, dureeConsultation) VALUES(?,?,?,?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getAnnee());
            ps.setInt(2, a.getMois());
            ps.setString(3, listDateToString(a.getJoursOuvrables()));
            ps.setString(4, listDateToString(a.getJoursFeries()));
            ps.setTime(5, Time.valueOf(a.getHeureDebut()));
            ps.setTime(6, Time.valueOf(a.getHeureFin()));
            ps.setInt(7, a.getDureeConsultation());

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) a.setIdAgenda(keys.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(Agenda a) {
        String sql = """
            UPDATE """ + TABLE_NAME + """
             SET annee=?, mois=?, joursOuvrables=?, joursFeries=?, heureDebut=?, heureFin=?, dureeConsultation=?
            WHERE idAgenda=?
            """;
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, a.getAnnee());
            ps.setInt(2, a.getMois());
            ps.setString(3, listDateToString(a.getJoursOuvrables()));
            ps.setString(4, listDateToString(a.getJoursFeries()));
            ps.setTime(5, Time.valueOf(a.getHeureDebut()));
            ps.setTime(6, Time.valueOf(a.getHeureFin()));
            ps.setInt(7, a.getDureeConsultation());
            ps.setLong(8, a.getIdAgenda());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(Agenda a) {
        if (a != null) deleteById(a.getIdAgenda());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE idAgenda = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<Agenda> findByAnneeAndMois(Integer annee, Integer mois) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE annee = ? AND mois = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, annee);
            ps.setInt(2, mois);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapAgendaConfiguration(rs));
                return Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}