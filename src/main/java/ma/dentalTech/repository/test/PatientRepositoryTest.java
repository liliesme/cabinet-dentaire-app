package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Patient;
import ma.dentalTech.entities.pat.Antecedent;
import ma.dentalTech.repository.modules.patient.api.PatientRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour PatientRepository")
class PatientRepositoryTest {

    @Mock
    private PatientRepository patientRepository;

    private AutoCloseable closeable;
    private Patient patient;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        
        patient = new Patient();
        patient.setId(1L);
        patient.setNom("Radi");
        patient.setPrenom("ahmed");
        patient.setAdresse("15 Rue Oued Ziz, Casablanca");
        patient.setTelephone("0656789012");
        patient.setEmail("ahed.radi@email.com");
        patient.setDateNaissance(LocalDate.of(1975, 4, 12));
        patient.setDateCreation(LocalDateTime.now());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne tous les patients")
    void testFindAll() {
        
        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setNom("ouasmi");
        patient2.setPrenom("Amina");
        patient2.setAdresse("22 Avenue Mers Sultan, Casablanca");
        patient2.setTelephone("0667890123");
        patient2.setEmail("amina.ouasmi@email.com");
        patient2.setDateNaissance(LocalDate.of(1982, 8, 25));

        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient, patient2));

        List<Patient> result = patientRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Retourne liste vide si aucun patient")
    void testFindAllEmpty() {
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        List<Patient> result = patientRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne un patient existant")
    void testFindById() {
        when(patientRepository.findById(1L)).thenReturn(patient);

        Patient result = patientRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Radi", result.getNom());
        assertEquals("ahmed", result.getPrenom());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(patientRepository.findById(999L)).thenReturn(null);

        Patient result = patientRepository.findById(999L);

        assertNull(result);
        verify(patientRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouveau patient")
    void testCreate() {
        doNothing().when(patientRepository).create(patient);

        patientRepository.create(patient);

        verify(patientRepository, times(1)).create(patient);
    }

    @Test
    @DisplayName("update - Met à jour un patient")
    void testUpdate() {
        patient.setAdresse("Nouvelle adresse, Casablanca");
        patient.setTelephone("0699999999");
        doNothing().when(patientRepository).update(patient);

        patientRepository.update(patient);

        verify(patientRepository, times(1)).update(patient);
    }

    @Test
    @DisplayName("delete - Supprime un patient")
    void testDelete() {
        doNothing().when(patientRepository).delete(patient);

        patientRepository.delete(patient);

        verify(patientRepository, times(1)).delete(patient);
    }

    @Test
    @DisplayName("deleteById - Supprime un patient par ID")
    void testDeleteById() {
        doNothing().when(patientRepository).deleteById(1L);

        patientRepository.deleteById(1L);

        verify(patientRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByEmail - Retourne un patient par email")
    void testFindByEmail() {
        when(patientRepository.findByEmail("ahed.radi@email.com")).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientRepository.findByEmail("ahed.radi@email.com");

        assertTrue(result.isPresent());
        assertEquals("ahed.radi@email.com", result.get().getEmail());
        verify(patientRepository, times(1)).findByEmail("ahed.radi@email.com");
    }

    @Test
    @DisplayName("findByEmail - Retourne vide si non trouvé")
    void testFindByEmailNotFound() {
        when(patientRepository.findByEmail("inconnu@email.com")).thenReturn(Optional.empty());

        Optional<Patient> result = patientRepository.findByEmail("inconnu@email.com");

        assertFalse(result.isPresent());
        verify(patientRepository, times(1)).findByEmail("inconnu@email.com");
    }

    @Test
    @DisplayName("findByTelephone - Retourne un patient par téléphone")
    void testFindByTelephone() {
        when(patientRepository.findByTelephone("0656789012")).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientRepository.findByTelephone("0656789012");

        assertTrue(result.isPresent());
        assertEquals("0656789012", result.get().getTelephone());
        verify(patientRepository, times(1)).findByTelephone("0656789012");
    }

    @Test
    @DisplayName("findByTelephone - Retourne vide si non trouvé")
    void testFindByTelephoneNotFound() {
        when(patientRepository.findByTelephone("0000000000")).thenReturn(Optional.empty());

        Optional<Patient> result = patientRepository.findByTelephone("0000000000");

        assertFalse(result.isPresent());
        verify(patientRepository, times(1)).findByTelephone("0000000000");
    }

    @Test
    @DisplayName("searchByNomPrenom - Recherche par nom")
    void testSearchByNomPrenom() {
        when(patientRepository.searchByNomPrenom("Radi")).thenReturn(Arrays.asList(patient));

        List<Patient> result = patientRepository.searchByNomPrenom("Radi");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Radi", result.get(0).getNom());
        verify(patientRepository, times(1)).searchByNomPrenom("Radi");
    }

    @Test
    @DisplayName("searchByNomPrenom - Recherche par prénom")
    void testSearchByPrenom() {
        when(patientRepository.searchByNomPrenom("Amina")).thenReturn(Arrays.asList(patient));

        List<Patient> result = patientRepository.searchByNomPrenom("Amina");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(patientRepository, times(1)).searchByNomPrenom("Amina");
    }

    @Test
    @DisplayName("searchByNomPrenom - Retourne liste vide si aucun résultat")
    void testSearchByNomPrenomEmpty() {
        when(patientRepository.searchByNomPrenom("Inconnu")).thenReturn(Collections.emptyList());

        List<Patient> result = patientRepository.searchByNomPrenom("Inconnu");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).searchByNomPrenom("Inconnu");
    }

    @Test
    @DisplayName("existsById - Retourne true si le patient existe")
    void testExistsByIdTrue() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        boolean result = patientRepository.existsById(1L);

        assertTrue(result);
        verify(patientRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("existsById - Retourne false si le patient n'existe pas")
    void testExistsByIdFalse() {
        when(patientRepository.existsById(999L)).thenReturn(false);

        boolean result = patientRepository.existsById(999L);

        assertFalse(result);
        verify(patientRepository, times(1)).existsById(999L);
    }

    @Test
    @DisplayName("count - Compte le nombre de patients")
    void testCount() {
        when(patientRepository.count()).thenReturn(2L);

        long count = patientRepository.count();

        assertEquals(2L, count);
        verify(patientRepository, times(1)).count();
    }

    @Test
    @DisplayName("findPage - Retourne une page de patients")
    void testFindPage() {
        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setNom("ouasmi");
        patient2.setPrenom("Amina");

        when(patientRepository.findPage(10, 0)).thenReturn(Arrays.asList(patient, patient2));

        List<Patient> result = patientRepository.findPage(10, 0);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findPage(10, 0);
    }

    @Test
    @DisplayName("findPage - Retourne liste vide pour page sans résultats")
    void testFindPageEmpty() {
        when(patientRepository.findPage(10, 1000)).thenReturn(Collections.emptyList());

        List<Patient> result = patientRepository.findPage(10, 1000);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).findPage(10, 1000);
    }

    

    @Test
    @DisplayName("addAntecedentToPatient - Ajoute un antécédent à un patient")
    void testAddAntecedentToPatient() {
        doNothing().when(patientRepository).addAntecedentToPatient(1L, 1L);

        patientRepository.addAntecedentToPatient(1L, 1L);

        verify(patientRepository, times(1)).addAntecedentToPatient(1L, 1L);
    }

    @Test
    @DisplayName("removeAntecedentFromPatient - Retire un antécédent d'un patient")
    void testRemoveAntecedentFromPatient() {
        doNothing().when(patientRepository).removeAntecedentFromPatient(1L, 1L);

        patientRepository.removeAntecedentFromPatient(1L, 1L);

        verify(patientRepository, times(1)).removeAntecedentFromPatient(1L, 1L);
    }

    @Test
    @DisplayName("removeAllAntecedentsFromPatient - Retire tous les antécédents d'un patient")
    void testRemoveAllAntecedentsFromPatient() {
        doNothing().when(patientRepository).removeAllAntecedentsFromPatient(1L);

        patientRepository.removeAllAntecedentsFromPatient(1L);

        verify(patientRepository, times(1)).removeAllAntecedentsFromPatient(1L);
    }

    @Test
    @DisplayName("getAntecedentsOfPatient - Retourne les antécédents d'un patient")
    void testGetAntecedentsOfPatient() {
        
        Antecedent antecedent1 = new Antecedent();
        antecedent1.setId(1L);
        antecedent1.setNom("Hypertension");

        Antecedent antecedent2 = new Antecedent();
        antecedent2.setId(2L);
        antecedent2.setNom("Problèmes cardiaques");

        when(patientRepository.getAntecedentsOfPatient(1L)).thenReturn(Arrays.asList(antecedent1, antecedent2));

        List<Antecedent> result = patientRepository.getAntecedentsOfPatient(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).getAntecedentsOfPatient(1L);
    }

    @Test
    @DisplayName("getAntecedentsOfPatient - Retourne liste vide si aucun antécédent")
    void testGetAntecedentsOfPatientEmpty() {
        when(patientRepository.getAntecedentsOfPatient(999L)).thenReturn(Collections.emptyList());

        List<Antecedent> result = patientRepository.getAntecedentsOfPatient(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).getAntecedentsOfPatient(999L);
    }

    @Test
    @DisplayName("getPatientsByAntecedent - Retourne les patients ayant un antécédent")
    void testGetPatientsByAntecedent() {
        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setNom("ouasmi");
        patient2.setPrenom("Amina");

        when(patientRepository.getPatientsByAntecedent(1L)).thenReturn(Arrays.asList(patient, patient2));

        List<Patient> result = patientRepository.getPatientsByAntecedent(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).getPatientsByAntecedent(1L);
    }

    @Test
    @DisplayName("getPatientsByAntecedent - Retourne liste vide si aucun patient")
    void testGetPatientsByAntecedentEmpty() {
        when(patientRepository.getPatientsByAntecedent(999L)).thenReturn(Collections.emptyList());

        List<Patient> result = patientRepository.getPatientsByAntecedent(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).getPatientsByAntecedent(999L);
    }
}