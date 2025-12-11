package ma.dentalTech.repository.modules.staff.api;

import ma.dentalTech.entities.pat.Staff;
import ma.dentalTech.repository.common.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface StaffRepository extends CrudRepository<Staff, Long> {

    
    Optional<Staff> findByCin(String cin);
    List<Staff> findByRole(String role); 
    Optional<Staff> findByTelephone(String telephone);
}