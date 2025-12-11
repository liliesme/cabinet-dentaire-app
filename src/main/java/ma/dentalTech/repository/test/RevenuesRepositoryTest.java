package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Revenues;
import ma.dentalTech.repository.modules.revenues.api.RevenuesRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour RevenuesRepository")
class RevenuesRepositoryTest {

    @Mock
    private RevenuesRepository revenuesRepository;

    private AutoCloseable closeable;
    private Revenues revenues;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        revenues = new Revenues();
        revenues.setId(1L);
        revenues.setTitre("Consultations");
        revenues.setDescription("Revenus consultations - Semaine 1");
        revenues.setMontant(12000.0);
        revenues.setDate(LocalDateTime.of(2024, 11, 7, 0, 0));
        revenues.setUtilisateurId(1L);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne tous les revenus")
    void testFindAll() {
        Revenues revenues2 = new Revenues();
        revenues2.setId(2L);
        revenues2.setTitre("Interventions chirurgicales");
        revenues2.setDescription("Implants et extractions");
        revenues2.setMontant(25000.0);
        revenues2.setDate(LocalDateTime.of(2024, 11, 14, 0, 0));

        when(revenuesRepository.findAll()).thenReturn(Arrays.asList(revenues, revenues2));

        List<Revenues> result = revenuesRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(revenuesRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Retourne liste vide si aucun revenu")
    void testFindAllEmpty() {
        when(revenuesRepository.findAll()).thenReturn(Collections.emptyList());

        List<Revenues> result = revenuesRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(revenuesRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne un revenu existant")
    void testFindById() {
        when(revenuesRepository.findById(1L)).thenReturn(revenues);

        Revenues result = revenuesRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Consultations", result.getTitre());
        assertEquals(12000.0, result.getMontant());
        verify(revenuesRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(revenuesRepository.findById(999L)).thenReturn(null);

        Revenues result = revenuesRepository.findById(999L);

        assertNull(result);
        verify(revenuesRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouveau revenu")
    void testCreate() {
        doNothing().when(revenuesRepository).create(revenues);

        revenuesRepository.create(revenues);

        verify(revenuesRepository, times(1)).create(revenues);
    }

    @Test
    @DisplayName("update - Met à jour un revenu")
    void testUpdate() {
        revenues.setMontant(15000.0);
        revenues.setDescription("Revenus consultations - Mise à jour");
        doNothing().when(revenuesRepository).update(revenues);

        revenuesRepository.update(revenues);

        verify(revenuesRepository, times(1)).update(revenues);
    }

    @Test
    @DisplayName("delete - Supprime un revenu")
    void testDelete() {
        doNothing().when(revenuesRepository).delete(revenues);

        revenuesRepository.delete(revenues);

        verify(revenuesRepository, times(1)).delete(revenues);
    }

    @Test
    @DisplayName("deleteById - Supprime un revenu par ID")
    void testDeleteById() {
        doNothing().when(revenuesRepository).deleteById(1L);

        revenuesRepository.deleteById(1L);

        verify(revenuesRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByTitre - Retourne les revenus par titre")
    void testFindByTitre() {
        when(revenuesRepository.findByTitre("Consultations")).thenReturn(Arrays.asList(revenues));

        List<Revenues> result = revenuesRepository.findByTitre("Consultations");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Consultations", result.get(0).getTitre());
        verify(revenuesRepository, times(1)).findByTitre("Consultations");
    }

    @Test
    @DisplayName("findByTitre - Retourne liste vide si aucun revenu")
    void testFindByTitreEmpty() {
        when(revenuesRepository.findByTitre("Inconnu")).thenReturn(Collections.emptyList());

        List<Revenues> result = revenuesRepository.findByTitre("Inconnu");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(revenuesRepository, times(1)).findByTitre("Inconnu");
    }

    @Test
    @DisplayName("findByDateBetween - Retourne les revenus dans une période")
    void testFindByDateBetween() {
        LocalDate start = LocalDate.of(2024, 11, 1);
        LocalDate end = LocalDate.of(2024, 11, 30);

        when(revenuesRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(revenues));

        List<Revenues> result = revenuesRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(revenuesRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne liste vide hors période")
    void testFindByDateBetweenEmpty() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 12, 31);

        when(revenuesRepository.findByDateBetween(start, end)).thenReturn(Collections.emptyList());

        List<Revenues> result = revenuesRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(revenuesRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne revenus d'une journée")
    void testFindByDateBetweenSameDay() {
        LocalDate date = LocalDate.of(2024, 11, 7);

        when(revenuesRepository.findByDateBetween(date, date)).thenReturn(Arrays.asList(revenues));

        List<Revenues> result = revenuesRepository.findByDateBetween(date, date);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(revenuesRepository, times(1)).findByDateBetween(date, date);
    }
}