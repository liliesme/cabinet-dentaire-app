package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.InterventionMedecin;
import ma.dentalTech.repository.modules.InterventionMedecin.api.InterventionMedecinRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour InterventionMedecinRepository")
class InterventionMedecinRepositoryTest {

    @Mock
    private InterventionMedecinRepository interventionMedecinRepository;

    private AutoCloseable closeable;
    private InterventionMedecin intervention;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        intervention = new InterventionMedecin();
        intervention.setId(1L);
        intervention.setPatientId(1L);
        intervention.setUtilisateurId(1L);
        intervention.setDateIntervention(LocalDateTime.now());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne toutes les interventions")
    void testFindAll() {
        InterventionMedecin intervention2 = new InterventionMedecin();
        intervention2.setId(2L);

        when(interventionMedecinRepository.findAll()).thenReturn(Arrays.asList(intervention, intervention2));

        List<InterventionMedecin> result = interventionMedecinRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(interventionMedecinRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne une intervention existante")
    void testFindById() {
        when(interventionMedecinRepository.findById(1L)).thenReturn(intervention);

        InterventionMedecin result = interventionMedecinRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(interventionMedecinRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(interventionMedecinRepository.findById(999L)).thenReturn(null);

        InterventionMedecin result = interventionMedecinRepository.findById(999L);

        assertNull(result);
        verify(interventionMedecinRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée une nouvelle intervention")
    void testCreate() {
        doNothing().when(interventionMedecinRepository).create(intervention);

        interventionMedecinRepository.create(intervention);

        verify(interventionMedecinRepository, times(1)).create(intervention);
    }

    @Test
    @DisplayName("update - Met à jour une intervention")
    void testUpdate() {
        doNothing().when(interventionMedecinRepository).update(intervention);

        interventionMedecinRepository.update(intervention);

        verify(interventionMedecinRepository, times(1)).update(intervention);
    }

    @Test
    @DisplayName("delete - Supprime une intervention")
    void testDelete() {
        doNothing().when(interventionMedecinRepository).delete(intervention);

        interventionMedecinRepository.delete(intervention);

        verify(interventionMedecinRepository, times(1)).delete(intervention);
    }

    @Test
    @DisplayName("deleteById - Supprime une intervention par ID")
    void testDeleteById() {
        doNothing().when(interventionMedecinRepository).deleteById(1L);

        interventionMedecinRepository.deleteById(1L);

        verify(interventionMedecinRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByPatientId - Retourne les interventions d'un patient")
    void testFindByPatientId() {
        InterventionMedecin intervention2 = new InterventionMedecin();
        intervention2.setId(2L);
        intervention2.setPatientId(1L);

        when(interventionMedecinRepository.findByPatientId(1L))
                .thenReturn(Arrays.asList(intervention, intervention2));

        List<InterventionMedecin> result = interventionMedecinRepository.findByPatientId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(i -> i.getPatientId().equals(1L)));
        verify(interventionMedecinRepository, times(1)).findByPatientId(1L);
    }

    @Test
    @DisplayName("findByPatientId - Retourne liste vide si aucune intervention")
    void testFindByPatientIdEmpty() {
        when(interventionMedecinRepository.findByPatientId(999L)).thenReturn(Arrays.asList());

        List<InterventionMedecin> result = interventionMedecinRepository.findByPatientId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(interventionMedecinRepository, times(1)).findByPatientId(999L);
    }

    @Test
    @DisplayName("findByUtilisateurId - Retourne les interventions d'un médecin")
    void testFindByUtilisateurId() {
        when(interventionMedecinRepository.findByUtilisateurId(1L)).thenReturn(Arrays.asList(intervention));

        List<InterventionMedecin> result = interventionMedecinRepository.findByUtilisateurId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUtilisateurId());
        verify(interventionMedecinRepository, times(1)).findByUtilisateurId(1L);
    }

    @Test
    @DisplayName("findByUtilisateurId - Retourne liste vide si aucune intervention")
    void testFindByUtilisateurIdEmpty() {
        when(interventionMedecinRepository.findByUtilisateurId(999L)).thenReturn(Arrays.asList());

        List<InterventionMedecin> result = interventionMedecinRepository.findByUtilisateurId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(interventionMedecinRepository, times(1)).findByUtilisateurId(999L);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne les interventions dans une période")
    void testFindByDateBetween() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 31, 23, 59);

        when(interventionMedecinRepository.findByDateBetween(start, end))
                .thenReturn(Arrays.asList(intervention));

        List<InterventionMedecin> result = interventionMedecinRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(interventionMedecinRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne liste vide hors période")
    void testFindByDateBetweenEmpty() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);

        when(interventionMedecinRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList());

        List<InterventionMedecin> result = interventionMedecinRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(interventionMedecinRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Test interventions d'une journée")
    void testFindByDateBetweenSameDay() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 15, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 15, 23, 59);

        when(interventionMedecinRepository.findByDateBetween(start, end))
                .thenReturn(Arrays.asList(intervention));

        List<InterventionMedecin> result = interventionMedecinRepository.findByDateBetween(start, end);

        assertNotNull(result);
        verify(interventionMedecinRepository, times(1)).findByDateBetween(start, end);
    }
}