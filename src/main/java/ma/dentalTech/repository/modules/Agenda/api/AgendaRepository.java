package ma.dentalTech.repository.modules.Agenda.api;


import ma.dentalTech.entities.pat.Agenda;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.Optional;

public interface AgendaRepository extends CrudRepository<Agenda, Long> {


    Optional<Agenda> findByAnneeAndMois(Integer annee, Integer mois);
}