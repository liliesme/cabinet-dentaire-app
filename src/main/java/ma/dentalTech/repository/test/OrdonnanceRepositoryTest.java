package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Ordonnance;
import ma.dentalTech.repository.modules.Ordonnance.api.OrdonnanceRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour OrdonnanceRepository")
class OrdonnanceRepositoryTest {

    @Mock
    private OrdonnanceRepository ordonnanceRepository;

    private AutoCloseable closeable;
    private Ordonnance ordonnance;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        ordonnance = new Ordonnance();
        ordonnance.setId(1L);
        ordonnance.setPatientId(1L);
        ordonnance.setUtilisateurId(1L);
        ordonnance.setDateOrdonnance(LocalDateTime.now());
        ordonnance.setLignesMedicaments(Arrays.asList("Doliprane 1000mg", "Amoxicilline 500mg"));
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne toutes les ordonnances")
    void testFindAll() {
        Ordonnance ordonnance2 = new Ordonnance();
        ordonnance2.setId(2L);
        ordonnance2.setPatientId(2L);

        when(ordonnanceRepository.findAll()).thenReturn(Arrays.asList(ordonnance, ordonnance2));

        List<Ordonnance> result = ordonnanceRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(ordonnanceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Retourne liste vide si aucune ordonnance")
    void testFindAllEmpty() {
        when(ordonnanceRepository.findAll()).thenReturn(Collections.emptyList());

        List<Ordonnance> result = ordonnanceRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ordonnanceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne une ordonnance existante")
    void testFindById() {
        when(ordonnanceRepository.findById(1L)).thenReturn(ordonnance);

        Ordonnance result = ordonnanceRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getPatientId());
        verify(ordonnanceRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(ordonnanceRepository.findById(999L)).thenReturn(null);

        Ordonnance result = ordonnanceRepository.findById(999L);

        assertNull(result);
        verify(ordonnanceRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée une nouvelle ordonnance")
    void testCreate() {
        doNothing().when(ordonnanceRepository).create(ordonnance);

        ordonnanceRepository.create(ordonnance);

        verify(ordonnanceRepository, times(1)).create(ordonnance);
    }

    @Test
    @DisplayName("update - Met à jour une ordonnance")
    void testUpdate() {
        ordonnance.setLignesMedicaments(Arrays.asList("Doliprane 500mg"));
        doNothing().when(ordonnanceRepository).update(ordonnance);

        ordonnanceRepository.update(ordonnance);

        verify(ordonnanceRepository, times(1)).update(ordonnance);
    }

    @Test
    @DisplayName("delete - Supprime une ordonnance")
    void testDelete() {
        doNothing().when(ordonnanceRepository).delete(ordonnance);

        ordonnanceRepository.delete(ordonnance);

        verify(ordonnanceRepository, times(1)).delete(ordonnance);
    }

    @Test
    @DisplayName("deleteById - Supprime une ordonnance par ID")
    void testDeleteById() {
        doNothing().when(ordonnanceRepository).deleteById(1L);

        ordonnanceRepository.deleteById(1L);

        verify(ordonnanceRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByPatientId - Retourne les ordonnances d'un patient")
    void testFindByPatientId() {
        Ordonnance ordonnance2 = new Ordonnance();
        ordonnance2.setId(2L);
        ordonnance2.setPatientId(1L);

        when(ordonnanceRepository.findByPatientId(1L)).thenReturn(Arrays.asList(ordonnance, ordonnance2));

        List<Ordonnance> result = ordonnanceRepository.findByPatientId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(o -> o.getPatientId().equals(1L)));
        verify(ordonnanceRepository, times(1)).findByPatientId(1L);
    }

    @Test
    @DisplayName("findByPatientId - Retourne liste vide si aucune ordonnance")
    void testFindByPatientIdEmpty() {
        when(ordonnanceRepository.findByPatientId(999L)).thenReturn(Collections.emptyList());

        List<Ordonnance> result = ordonnanceRepository.findByPatientId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ordonnanceRepository, times(1)).findByPatientId(999L);
    }

    @Test
    @DisplayName("findByUtilisateurId - Retourne les ordonnances créées par un utilisateur")
    void testFindByUtilisateurId() {
        when(ordonnanceRepository.findByUtilisateurId(1L)).thenReturn(Arrays.asList(ordonnance));

        List<Ordonnance> result = ordonnanceRepository.findByUtilisateurId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUtilisateurId());
        verify(ordonnanceRepository, times(1)).findByUtilisateurId(1L);
    }

    @Test
    @DisplayName("findByUtilisateurId - Retourne liste vide si aucune ordonnance")
    void testFindByUtilisateurIdEmpty() {
        when(ordonnanceRepository.findByUtilisateurId(999L)).thenReturn(Collections.emptyList());

        List<Ordonnance> result = ordonnanceRepository.findByUtilisateurId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ordonnanceRepository, times(1)).findByUtilisateurId(999L);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne les ordonnances dans une période")
    void testFindByDateBetween() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 31, 23, 59);

        when(ordonnanceRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(ordonnance));

        List<Ordonnance> result = ordonnanceRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ordonnanceRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne liste vide hors période")
    void testFindByDateBetweenEmpty() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);

        when(ordonnanceRepository.findByDateBetween(start, end)).thenReturn(Collections.emptyList());

        List<Ordonnance> result = ordonnanceRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ordonnanceRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Test ordonnances d'une journée")
    void testFindByDateBetweenSameDay() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 15, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 15, 23, 59);

        when(ordonnanceRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(ordonnance));

        List<Ordonnance> result = ordonnanceRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ordonnanceRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Test période d'un mois")
    void testFindByDateBetweenMonth() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 30, 23, 59);

        Ordonnance ordonnance2 = new Ordonnance();
        ordonnance2.setId(2L);
        ordonnance2.setPatientId(2L);

        when(ordonnanceRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(ordonnance, ordonnance2));

        List<Ordonnance> result = ordonnanceRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(ordonnanceRepository, times(1)).findByDateBetween(start, end);
    }
}