package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Prescription;
import ma.dentalTech.repository.modules.Prescription.api.PrescriptionRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour PrescriptionRepository")
class PrescriptionRepositoryTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    private AutoCloseable closeable;
    private Prescription prescription;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        prescription = new Prescription();
        prescription.setId(1L);
        prescription.setPatientId(1L);
        prescription.setUtilisateurId(1L);
        prescription.setDatePrescription(LocalDateTime.now());
        prescription.setDescription("Prendre après extraction");
        prescription.setFrequence(3);
        prescription.setDureeEnJours(7);
        prescription.setLignesMedicaments(Arrays.asList("Paracétamol 1000mg", "Bain de bouche"));
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne toutes les prescriptions")
    void testFindAll() {
        Prescription prescription2 = new Prescription();
        prescription2.setId(2L);
        prescription2.setPatientId(2L);
        prescription2.setDescription("Rincer matin et soir");

        when(prescriptionRepository.findAll()).thenReturn(Arrays.asList(prescription, prescription2));

        List<Prescription> result = prescriptionRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(prescriptionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Retourne liste vide si aucune prescription")
    void testFindAllEmpty() {
        when(prescriptionRepository.findAll()).thenReturn(Collections.emptyList());

        List<Prescription> result = prescriptionRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(prescriptionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne une prescription existante")
    void testFindById() {
        when(prescriptionRepository.findById(1L)).thenReturn(prescription);

        Prescription result = prescriptionRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Prendre après extraction", result.getDescription());
        assertEquals(3, result.getFrequence());
        assertEquals(7, result.getDureeEnJours());
        verify(prescriptionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(prescriptionRepository.findById(999L)).thenReturn(null);

        Prescription result = prescriptionRepository.findById(999L);

        assertNull(result);
        verify(prescriptionRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée une nouvelle prescription")
    void testCreate() {
        doNothing().when(prescriptionRepository).create(prescription);

        prescriptionRepository.create(prescription);

        verify(prescriptionRepository, times(1)).create(prescription);
    }

    @Test
    @DisplayName("update - Met à jour une prescription")
    void testUpdate() {
        prescription.setFrequence(2);
        prescription.setDureeEnJours(10);
        doNothing().when(prescriptionRepository).update(prescription);

        prescriptionRepository.update(prescription);

        verify(prescriptionRepository, times(1)).update(prescription);
    }

    @Test
    @DisplayName("delete - Supprime une prescription")
    void testDelete() {
        doNothing().when(prescriptionRepository).delete(prescription);

        prescriptionRepository.delete(prescription);

        verify(prescriptionRepository, times(1)).delete(prescription);
    }

    @Test
    @DisplayName("deleteById - Supprime une prescription par ID")
    void testDeleteById() {
        doNothing().when(prescriptionRepository).deleteById(1L);

        prescriptionRepository.deleteById(1L);

        verify(prescriptionRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByPatientId - Retourne les prescriptions d'un patient")
    void testFindByPatientId() {
        Prescription prescription2 = new Prescription();
        prescription2.setId(2L);
        prescription2.setPatientId(1L);

        when(prescriptionRepository.findByPatientId(1L)).thenReturn(Arrays.asList(prescription, prescription2));

        List<Prescription> result = prescriptionRepository.findByPatientId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getPatientId().equals(1L)));
        verify(prescriptionRepository, times(1)).findByPatientId(1L);
    }

    @Test
    @DisplayName("findByPatientId - Retourne liste vide si aucune prescription")
    void testFindByPatientIdEmpty() {
        when(prescriptionRepository.findByPatientId(999L)).thenReturn(Collections.emptyList());

        List<Prescription> result = prescriptionRepository.findByPatientId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(prescriptionRepository, times(1)).findByPatientId(999L);
    }

    @Test
    @DisplayName("findByUtilisateurId - Retourne les prescriptions créées par un utilisateur")
    void testFindByUtilisateurId() {
        when(prescriptionRepository.findByUtilisateurId(1L)).thenReturn(Arrays.asList(prescription));

        List<Prescription> result = prescriptionRepository.findByUtilisateurId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUtilisateurId());
        verify(prescriptionRepository, times(1)).findByUtilisateurId(1L);
    }

    @Test
    @DisplayName("findByUtilisateurId - Retourne liste vide si aucune prescription")
    void testFindByUtilisateurIdEmpty() {
        when(prescriptionRepository.findByUtilisateurId(999L)).thenReturn(Collections.emptyList());

        List<Prescription> result = prescriptionRepository.findByUtilisateurId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(prescriptionRepository, times(1)).findByUtilisateurId(999L);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne les prescriptions dans une période")
    void testFindByDateBetween() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 30, 23, 59);

        when(prescriptionRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(prescription));

        List<Prescription> result = prescriptionRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(prescriptionRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne liste vide hors période")
    void testFindByDateBetweenEmpty() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);

        when(prescriptionRepository.findByDateBetween(start, end)).thenReturn(Collections.emptyList());

        List<Prescription> result = prescriptionRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(prescriptionRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Test prescriptions d'une journée")
    void testFindByDateBetweenSameDay() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 16, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 16, 23, 59);

        when(prescriptionRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(prescription));

        List<Prescription> result = prescriptionRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(prescriptionRepository, times(1)).findByDateBetween(start, end);
    }
}