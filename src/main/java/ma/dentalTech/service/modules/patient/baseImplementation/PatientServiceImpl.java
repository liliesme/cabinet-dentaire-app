package ma.dentalTech.service.modules.patient.baseImplementation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dentalTech.entities.pat.Patient;
import ma.dentalTech.mvc.dto.PatientDTO;
import ma.dentalTech.repository.modules.patient.api.PatientRepository;
import ma.dentalTech.service.modules.patient.api.PatientService;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientRepository repository;


    private static String formatDate(java.time.LocalDateTime dt) {
        return dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }


    private static int computeAge(java.time.LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public List<PatientDTO> getTodayPatientsAsDTO() {
        LocalDate today = LocalDate.now();
        return repository.findAll().stream()

                .filter(p -> p.getDateCreation() != null && p.getDateCreation().toLocalDate().equals(today))
                .sorted(Comparator.comparing(Patient::getDateCreation).reversed())
                .map(p -> PatientDTO.builder()
                        .nomComplet((p.getNom() == null ? "" : p.getNom().trim()) + " " + (p.getPrenom() == null ? "" : p.getPrenom().trim()))
                        .age(computeAge(p.getDateNaissance()))
                        .dateCreationFormatee(formatDate(p.getDateCreation()))
                        .build())
                .collect(Collectors.toList());
    }
}
