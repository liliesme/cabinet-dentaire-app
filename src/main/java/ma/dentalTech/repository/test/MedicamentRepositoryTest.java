package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Medicament;
import ma.dentalTech.repository.modules.medicament.api.MedicamentRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour MedicamentRepository")
class MedicamentRepositoryTest {

    @Mock
    private MedicamentRepository medicamentRepository;

    private AutoCloseable closeable;
    private Medicament medicament;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        medicament = new Medicament();
        medicament.setId(1L);
        medicament.setNom("Doliprane");
        medicament.setLaboratoire("Sanofi");
        medicament.setDosage("1000mg");
        medicament.setPresentation("Comprimé");
        medicament.setForme("Orale");
        medicament.setTypeDosage("Adulte");
        medicament.setRemboursable(true);
        medicament.setPosologie("1 comprimé 3 fois par jour");
        medicament.setDescription("Antalgique et antipyrétique");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne tous les médicaments")
    void testFindAll() {
        Medicament medicament2 = new Medicament();
        medicament2.setId(2L);
        medicament2.setNom("Ibuprofène");
        medicament2.setLaboratoire("Mylan");

        when(medicamentRepository.findAll()).thenReturn(Arrays.asList(medicament, medicament2));

        List<Medicament> result = medicamentRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(medicamentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Retourne liste vide si aucun médicament")
    void testFindAllEmpty() {
        when(medicamentRepository.findAll()).thenReturn(Collections.emptyList());

        List<Medicament> result = medicamentRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medicamentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne un médicament existant")
    void testFindById() {
        when(medicamentRepository.findById(1L)).thenReturn(medicament);

        Medicament result = medicamentRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Doliprane", result.getNom());
        assertEquals("Sanofi", result.getLaboratoire());
        verify(medicamentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(medicamentRepository.findById(999L)).thenReturn(null);

        Medicament result = medicamentRepository.findById(999L);

        assertNull(result);
        verify(medicamentRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouveau médicament")
    void testCreate() {
        doNothing().when(medicamentRepository).create(medicament);

        medicamentRepository.create(medicament);

        verify(medicamentRepository, times(1)).create(medicament);
    }

    @Test
    @DisplayName("update - Met à jour un médicament")
    void testUpdate() {
        medicament.setDosage("500mg");
        medicament.setPosologie("2 comprimés 2 fois par jour");
        doNothing().when(medicamentRepository).update(medicament);

        medicamentRepository.update(medicament);

        verify(medicamentRepository, times(1)).update(medicament);
    }

    @Test
    @DisplayName("delete - Supprime un médicament")
    void testDelete() {
        doNothing().when(medicamentRepository).delete(medicament);

        medicamentRepository.delete(medicament);

        verify(medicamentRepository, times(1)).delete(medicament);
    }

    @Test
    @DisplayName("deleteById - Supprime un médicament par ID")
    void testDeleteById() {
        doNothing().when(medicamentRepository).deleteById(1L);

        medicamentRepository.deleteById(1L);

        verify(medicamentRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByNom - Retourne un médicament par nom")
    void testFindByNom() {
        when(medicamentRepository.findByNom("Doliprane")).thenReturn(Optional.of(medicament));

        Optional<Medicament> result = medicamentRepository.findByNom("Doliprane");

        assertTrue(result.isPresent());
        assertEquals("Doliprane", result.get().getNom());
        verify(medicamentRepository, times(1)).findByNom("Doliprane");
    }

    @Test
    @DisplayName("findByNom - Retourne vide si non trouvé")
    void testFindByNomNotFound() {
        when(medicamentRepository.findByNom("MedicamentInconnu")).thenReturn(Optional.empty());

        Optional<Medicament> result = medicamentRepository.findByNom("MedicamentInconnu");

        assertFalse(result.isPresent());
        verify(medicamentRepository, times(1)).findByNom("MedicamentInconnu");
    }

    @Test
    @DisplayName("searchByNom - Recherche par mot-clé dans le nom")
    void testSearchByNom() {
        Medicament medicament2 = new Medicament();
        medicament2.setId(2L);
        medicament2.setNom("Doliprane Enfant");

        when(medicamentRepository.searchByNom("Doliprane")).thenReturn(Arrays.asList(medicament, medicament2));

        List<Medicament> result = medicamentRepository.searchByNom("Doliprane");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(m -> m.getNom().contains("Doliprane")));
        verify(medicamentRepository, times(1)).searchByNom("Doliprane");
    }

    @Test
    @DisplayName("searchByNom - Retourne liste vide si aucun résultat")
    void testSearchByNomEmpty() {
        when(medicamentRepository.searchByNom("Inexistant")).thenReturn(Collections.emptyList());

        List<Medicament> result = medicamentRepository.searchByNom("Inexistant");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medicamentRepository, times(1)).searchByNom("Inexistant");
    }

    @Test
    @DisplayName("searchByNom - Recherche insensible à la casse")
    void testSearchByNomCaseInsensitive() {
        when(medicamentRepository.searchByNom("doliprane")).thenReturn(Arrays.asList(medicament));

        List<Medicament> result = medicamentRepository.searchByNom("doliprane");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(medicamentRepository, times(1)).searchByNom("doliprane");
    }
}