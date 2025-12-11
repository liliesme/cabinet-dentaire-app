package ma.dentalTech.repository.modules.patient.api;

import ma.dentalTech.entities.pat.Antecedent;
import ma.dentalTech.entities.pat.Patient;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AntecedentRepository extends CrudRepository<Antecedent, Long> {

    Optional<Antecedent> findByNom(String nom);

    boolean existsById(Long id);
    long count();
    List<Antecedent> findPage(int limit, int offset);


    List<Patient> getPatientsHavingAntecedent(Long antecedentId);
}