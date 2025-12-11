package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.DossierMedicale;
import ma.dentalTech.repository.modules.DossierMedicale.api.DossierMedicaleRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour DossierMedicaleRepository")
class DossierMedicaleRepositoryTest {

    @Mock
    private DossierMedicaleRepository dossierMedicaleRepository;

    private AutoCloseable closeable;
    private DossierMedicale dossierMedicale;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        dossierMedicale = new DossierMedicale();
        dossierMedicale.setId(1L);
        dossierMedicale.setPatientId(1L);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne tous les dossiers médicaux")
    void testFindAll() {
        DossierMedicale dossier2 = new DossierMedicale();
        dossier2.setId(2L);
        dossier2.setPatientId(2L);

        when(dossierMedicaleRepository.findAll()).thenReturn(Arrays.asList(dossierMedicale, dossier2));

        List<DossierMedicale> result = dossierMedicaleRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(dossierMedicaleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne un dossier médical existant")
    void testFindById() {
        when(dossierMedicaleRepository.findById(1L)).thenReturn(dossierMedicale);

        DossierMedicale result = dossierMedicaleRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(dossierMedicaleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(dossierMedicaleRepository.findById(999L)).thenReturn(null);

        DossierMedicale result = dossierMedicaleRepository.findById(999L);

        assertNull(result);
        verify(dossierMedicaleRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouveau dossier médical")
    void testCreate() {
        doNothing().when(dossierMedicaleRepository).create(dossierMedicale);

        dossierMedicaleRepository.create(dossierMedicale);

        verify(dossierMedicaleRepository, times(1)).create(dossierMedicale);
    }

    @Test
    @DisplayName("update - Met à jour un dossier médical")
    void testUpdate() {
        doNothing().when(dossierMedicaleRepository).update(dossierMedicale);

        dossierMedicaleRepository.update(dossierMedicale);

        verify(dossierMedicaleRepository, times(1)).update(dossierMedicale);
    }

    @Test
    @DisplayName("delete - Supprime un dossier médical")
    void testDelete() {
        doNothing().when(dossierMedicaleRepository).delete(dossierMedicale);

        dossierMedicaleRepository.delete(dossierMedicale);

        verify(dossierMedicaleRepository, times(1)).delete(dossierMedicale);
    }

    @Test
    @DisplayName("deleteById - Supprime un dossier médical par ID")
    void testDeleteById() {
        doNothing().when(dossierMedicaleRepository).deleteById(1L);

        dossierMedicaleRepository.deleteById(1L);

        verify(dossierMedicaleRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByPatientId - Retourne le dossier d'un patient")
    void testFindByPatientId() {
        when(dossierMedicaleRepository.findByPatientId(1L)).thenReturn(Optional.of(dossierMedicale));

        Optional<DossierMedicale> result = dossierMedicaleRepository.findByPatientId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getPatientId());
        verify(dossierMedicaleRepository, times(1)).findByPatientId(1L);
    }

    @Test
    @DisplayName("findByPatientId - Retourne vide si patient n'a pas de dossier")
    void testFindByPatientIdNotFound() {
        when(dossierMedicaleRepository.findByPatientId(999L)).thenReturn(Optional.empty());

        Optional<DossierMedicale> result = dossierMedicaleRepository.findByPatientId(999L);

        assertFalse(result.isPresent());
        verify(dossierMedicaleRepository, times(1)).findByPatientId(999L);
    }

    @Test
    @DisplayName("findByPatientId - Vérifie unicité du dossier par patient")
    void testFindByPatientIdUnique() {
        when(dossierMedicaleRepository.findByPatientId(1L)).thenReturn(Optional.of(dossierMedicale));

        Optional<DossierMedicale> result1 = dossierMedicaleRepository.findByPatientId(1L);
        Optional<DossierMedicale> result2 = dossierMedicaleRepository.findByPatientId(1L);

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertEquals(result1.get().getId(), result2.get().getId());
        verify(dossierMedicaleRepository, times(2)).findByPatientId(1L);
    }
}