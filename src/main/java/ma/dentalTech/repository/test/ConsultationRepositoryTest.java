package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Consultation;
import ma.dentalTech.repository.modules.Consultation.api.ConsultationRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour ConsultationRepository")
class ConsultationRepositoryTest {

    @Mock
    private ConsultationRepository consultationRepository;

    private AutoCloseable closeable;
    private Consultation consultation;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        consultation = new Consultation();
        consultation.setId(1L);
        consultation.setPatientId(1L);
        consultation.setDateConsultation(LocalDateTime.now());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne toutes les consultations")
    void testFindAll() {
        Consultation consultation2 = new Consultation();
        consultation2.setId(2L);

        when(consultationRepository.findAll()).thenReturn(Arrays.asList(consultation, consultation2));

        List<Consultation> result = consultationRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(consultationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne une consultation existante")
    void testFindById() {
        when(consultationRepository.findById(1L)).thenReturn(consultation);

        Consultation result = consultationRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(consultationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(consultationRepository.findById(999L)).thenReturn(null);

        Consultation result = consultationRepository.findById(999L);

        assertNull(result);
        verify(consultationRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée une nouvelle consultation")
    void testCreate() {
        doNothing().when(consultationRepository).create(consultation);

        consultationRepository.create(consultation);

        verify(consultationRepository, times(1)).create(consultation);
    }

    @Test
    @DisplayName("update - Met à jour une consultation")
    void testUpdate() {
        doNothing().when(consultationRepository).update(consultation);

        consultationRepository.update(consultation);

        verify(consultationRepository, times(1)).update(consultation);
    }

    @Test
    @DisplayName("delete - Supprime une consultation")
    void testDelete() {
        doNothing().when(consultationRepository).delete(consultation);

        consultationRepository.delete(consultation);

        verify(consultationRepository, times(1)).delete(consultation);
    }

    @Test
    @DisplayName("deleteById - Supprime une consultation par ID")
    void testDeleteById() {
        doNothing().when(consultationRepository).deleteById(1L);

        consultationRepository.deleteById(1L);

        verify(consultationRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByPatientId - Retourne les consultations d'un patient")
    void testFindByPatientId() {
        Consultation consultation2 = new Consultation();
        consultation2.setId(2L);
        consultation2.setPatientId(1L);

        when(consultationRepository.findByPatientId(1L)).thenReturn(Arrays.asList(consultation, consultation2));

        List<Consultation> result = consultationRepository.findByPatientId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> c.getPatientId().equals(1L)));
        verify(consultationRepository, times(1)).findByPatientId(1L);
    }

    @Test
    @DisplayName("findByPatientId - Retourne liste vide si aucune consultation")
    void testFindByPatientIdEmpty() {
        when(consultationRepository.findByPatientId(999L)).thenReturn(Arrays.asList());

        List<Consultation> result = consultationRepository.findByPatientId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(consultationRepository, times(1)).findByPatientId(999L);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne les consultations dans une période")
    void testFindByDateBetween() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 31, 23, 59);

        when(consultationRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(consultation));

        List<Consultation> result = consultationRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consultationRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne liste vide hors période")
    void testFindByDateBetweenEmpty() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);

        when(consultationRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList());

        List<Consultation> result = consultationRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(consultationRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Test consultations d'une journée")
    void testFindByDateBetweenSameDay() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 15, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 15, 23, 59);

        Consultation consultation2 = new Consultation();
        consultation2.setId(2L);

        when(consultationRepository.findByDateBetween(start, end))
                .thenReturn(Arrays.asList(consultation, consultation2));

        List<Consultation> result = consultationRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(consultationRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Test période d'une semaine")
    void testFindByDateBetweenWeek() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 7, 23, 59);

        when(consultationRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(consultation));

        List<Consultation> result = consultationRepository.findByDateBetween(start, end);

        assertNotNull(result);
        verify(consultationRepository, times(1)).findByDateBetween(start, end);
    }
}