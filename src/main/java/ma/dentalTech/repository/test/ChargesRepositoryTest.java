package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Charges;
import ma.dentalTech.repository.modules.charges.api.ChargesRepository;
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

@DisplayName("Tests pour ChargesRepository")
class ChargesRepositoryTest {

    @Mock
    private ChargesRepository chargesRepository;

    private AutoCloseable closeable;
    private Charges charges;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        charges = new Charges();
        charges.setId(1L);
        charges.setTitre("Loyer");
        charges.setDescription("Loyer mensuel du cabinet");
        charges.setMontant(5000.0);
        charges.setDate(LocalDateTime.now());
        charges.setUtilisateurId(1L);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne toutes les charges")
    void testFindAll() {
        Charges charges2 = new Charges();
        charges2.setId(2L);
        charges2.setTitre("Électricité");
        charges2.setMontant(800.0);

        when(chargesRepository.findAll()).thenReturn(Arrays.asList(charges, charges2));

        List<Charges> result = chargesRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chargesRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Retourne liste vide si aucune charge")
    void testFindAllEmpty() {
        when(chargesRepository.findAll()).thenReturn(Collections.emptyList());

        List<Charges> result = chargesRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(chargesRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne une charge existante")
    void testFindById() {
        when(chargesRepository.findById(1L)).thenReturn(charges);

        Charges result = chargesRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Loyer", result.getTitre());
        assertEquals(5000.0, result.getMontant());
        verify(chargesRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(chargesRepository.findById(999L)).thenReturn(null);

        Charges result = chargesRepository.findById(999L);

        assertNull(result);
        verify(chargesRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée une nouvelle charge")
    void testCreate() {
        doNothing().when(chargesRepository).create(charges);

        chargesRepository.create(charges);

        verify(chargesRepository, times(1)).create(charges);
    }

    @Test
    @DisplayName("update - Met à jour une charge")
    void testUpdate() {
        charges.setMontant(5500.0);
        charges.setDescription("Loyer mis à jour");
        doNothing().when(chargesRepository).update(charges);

        chargesRepository.update(charges);

        verify(chargesRepository, times(1)).update(charges);
    }

    @Test
    @DisplayName("delete - Supprime une charge")
    void testDelete() {
        doNothing().when(chargesRepository).delete(charges);

        chargesRepository.delete(charges);

        verify(chargesRepository, times(1)).delete(charges);
    }

    @Test
    @DisplayName("deleteById - Supprime une charge par ID")
    void testDeleteById() {
        doNothing().when(chargesRepository).deleteById(1L);

        chargesRepository.deleteById(1L);

        verify(chargesRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByTitre - Retourne les charges par titre")
    void testFindByTitre() {
        when(chargesRepository.findByTitre("Loyer")).thenReturn(Arrays.asList(charges));

        List<Charges> result = chargesRepository.findByTitre("Loyer");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Loyer", result.get(0).getTitre());
        verify(chargesRepository, times(1)).findByTitre("Loyer");
    }

    @Test
    @DisplayName("findByTitre - Retourne liste vide si aucune charge")
    void testFindByTitreEmpty() {
        when(chargesRepository.findByTitre("Inconnu")).thenReturn(Collections.emptyList());

        List<Charges> result = chargesRepository.findByTitre("Inconnu");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(chargesRepository, times(1)).findByTitre("Inconnu");
    }

    @Test
    @DisplayName("findByTitre - Retourne plusieurs charges avec même titre")
    void testFindByTitreMultiple() {
        Charges charges2 = new Charges();
        charges2.setId(2L);
        charges2.setTitre("Loyer");
        charges2.setMontant(5000.0);

        when(chargesRepository.findByTitre("Loyer")).thenReturn(Arrays.asList(charges, charges2));

        List<Charges> result = chargesRepository.findByTitre("Loyer");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> "Loyer".equals(c.getTitre())));
        verify(chargesRepository, times(1)).findByTitre("Loyer");
    }

    @Test
    @DisplayName("findByDateBetween - Retourne les charges dans une période")
    void testFindByDateBetween() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);

        when(chargesRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(charges));

        List<Charges> result = chargesRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(chargesRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne liste vide hors période")
    void testFindByDateBetweenEmpty() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 12, 31);

        when(chargesRepository.findByDateBetween(start, end)).thenReturn(Collections.emptyList());

        List<Charges> result = chargesRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(chargesRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Test avec même date début et fin")
    void testFindByDateBetweenSameDay() {
        LocalDate date = LocalDate.of(2025, 6, 15);

        when(chargesRepository.findByDateBetween(date, date)).thenReturn(Arrays.asList(charges));

        List<Charges> result = chargesRepository.findByDateBetween(date, date);

        assertNotNull(result);
        verify(chargesRepository, times(1)).findByDateBetween(date, date);
    }

    @Test
    @DisplayName("findByDateBetween - Test mois complet")
    void testFindByDateBetweenMonth() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 31);

        Charges charges2 = new Charges();
        charges2.setId(2L);
        charges2.setTitre("Eau");

        when(chargesRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(charges, charges2));

        List<Charges> result = chargesRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chargesRepository, times(1)).findByDateBetween(start, end);
    }
}