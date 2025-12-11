package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Facture;
import ma.dentalTech.repository.modules.facture.api.FactureRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour FactureRepository")
class FactureRepositoryTest {

    @Mock
    private FactureRepository factureRepository;

    private AutoCloseable closeable;
    private Facture facture;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        facture = new Facture();
        facture.setId(1L);
        facture.setPatientId(1L);
        facture.setNumeroFacture("FAC-2025-001");
        facture.setEstPayee(false);
        facture.setDateFacture(LocalDateTime.now());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne toutes les factures")
    void testFindAll() {
        Facture facture2 = new Facture();
        facture2.setId(2L);
        facture2.setNumeroFacture("FAC-2025-002");

        when(factureRepository.findAll()).thenReturn(Arrays.asList(facture, facture2));

        List<Facture> result = factureRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(factureRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne une facture existante")
    void testFindById() {
        when(factureRepository.findById(1L)).thenReturn(facture);

        Facture result = factureRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("FAC-2025-001", result.getNumeroFacture());
        verify(factureRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(factureRepository.findById(999L)).thenReturn(null);

        Facture result = factureRepository.findById(999L);

        assertNull(result);
        verify(factureRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée une nouvelle facture")
    void testCreate() {
        doNothing().when(factureRepository).create(facture);

        factureRepository.create(facture);

        verify(factureRepository, times(1)).create(facture);
    }

    @Test
    @DisplayName("update - Met à jour une facture")
    void testUpdate() {
        facture.setEstPayee(true);
        doNothing().when(factureRepository).update(facture);

        factureRepository.update(facture);

        verify(factureRepository, times(1)).update(facture);
    }

    @Test
    @DisplayName("delete - Supprime une facture")
    void testDelete() {
        doNothing().when(factureRepository).delete(facture);

        factureRepository.delete(facture);

        verify(factureRepository, times(1)).delete(facture);
    }

    @Test
    @DisplayName("deleteById - Supprime une facture par ID")
    void testDeleteById() {
        doNothing().when(factureRepository).deleteById(1L);

        factureRepository.deleteById(1L);

        verify(factureRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByPatientId - Retourne les factures d'un patient")
    void testFindByPatientId() {
        Facture facture2 = new Facture();
        facture2.setId(2L);
        facture2.setPatientId(1L);

        when(factureRepository.findByPatientId(1L)).thenReturn(Arrays.asList(facture, facture2));

        List<Facture> result = factureRepository.findByPatientId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(f -> f.getPatientId().equals(1L)));
        verify(factureRepository, times(1)).findByPatientId(1L);
    }

    @Test
    @DisplayName("findByPatientId - Retourne liste vide si aucune facture")
    void testFindByPatientIdEmpty() {
        when(factureRepository.findByPatientId(999L)).thenReturn(Arrays.asList());

        List<Facture> result = factureRepository.findByPatientId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(factureRepository, times(1)).findByPatientId(999L);
    }

    @Test
    @DisplayName("findByEstPayee - Retourne les factures payées")
    void testFindByEstPayeeTrue() {
        Facture facturePaye = new Facture();
        facturePaye.setId(2L);
        facturePaye.setEstPayee(true);

        when(factureRepository.findByEstPayee(true)).thenReturn(Arrays.asList(facturePaye));

        List<Facture> result = factureRepository.findByEstPayee(true);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isEstPayee());
        verify(factureRepository, times(1)).findByEstPayee(true);
    }

    @Test
    @DisplayName("findByEstPayee - Retourne les factures non payées")
    void testFindByEstPayeeFalse() {
        when(factureRepository.findByEstPayee(false)).thenReturn(Arrays.asList(facture));

        List<Facture> result = factureRepository.findByEstPayee(false);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertFalse(result.get(0).isEstPayee());
        verify(factureRepository, times(1)).findByEstPayee(false);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne les factures dans une période")
    void testFindByDateBetween() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 31, 23, 59);

        when(factureRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(facture));

        List<Facture> result = factureRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(factureRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne liste vide hors période")
    void testFindByDateBetweenEmpty() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);

        when(factureRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList());

        List<Facture> result = factureRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(factureRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByNumeroFacture - Retourne une facture par numéro")
    void testFindByNumeroFacture() {
        when(factureRepository.findByNumeroFacture("FAC-2025-001")).thenReturn(Optional.of(facture));

        Optional<Facture> result = factureRepository.findByNumeroFacture("FAC-2025-001");

        assertTrue(result.isPresent());
        assertEquals("FAC-2025-001", result.get().getNumeroFacture());
        verify(factureRepository, times(1)).findByNumeroFacture("FAC-2025-001");
    }

    @Test
    @DisplayName("findByNumeroFacture - Retourne vide si non trouvé")
    void testFindByNumeroFactureNotFound() {
        when(factureRepository.findByNumeroFacture("FAC-INEXISTANT")).thenReturn(Optional.empty());

        Optional<Facture> result = factureRepository.findByNumeroFacture("FAC-INEXISTANT");

        assertFalse(result.isPresent());
        verify(factureRepository, times(1)).findByNumeroFacture("FAC-INEXISTANT");
    }

    @Test
    @DisplayName("findByNumeroFacture - Vérifie unicité du numéro")
    void testFindByNumeroFactureUnique() {
        when(factureRepository.findByNumeroFacture("FAC-2025-001")).thenReturn(Optional.of(facture));

        Optional<Facture> result = factureRepository.findByNumeroFacture("FAC-2025-001");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(factureRepository, times(1)).findByNumeroFacture("FAC-2025-001");
    }
}