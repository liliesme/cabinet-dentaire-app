package ma.dentalTech.service.modules.patient.api;


import java.util.List;
import ma.dentalTech.mvc.dto.PatientDTO;

public interface PatientService {

    
    List<PatientDTO> getTodayPatientsAsDTO();
}
