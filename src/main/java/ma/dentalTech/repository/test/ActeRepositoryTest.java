package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Acte;
import ma.dentalTech.repository.modules.Acte.api.ActeRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour ActeRepository")
class ActeRepositoryTest {

    @Mock
    private ActeRepository acteRepository;

    private AutoCloseable closeable;
    private Acte acte;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        acte = new Acte();
        acte.setId(1L);
        acte.setNom("Extraction dentaire");
        acte.setTypeActe("Chirurgie");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }



    @Test
    @DisplayName("findAll - Retourne tous les actes")
    void testFindAll() {
        Acte acte2 = new Acte();
        acte2.setId(2L);
        acte2.setNom("Détartrage");

        when(acteRepository.findAll()).thenReturn(Arrays.asList(acte, acte2));

        List<Acte> result = acteRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(acteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(acteRepository.findById(999L)).thenReturn(null);

        Acte result = acteRepository.findById(999L);

        assertNull(result);
        verify(acteRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouvel acte")
    void testCreate() {
        doNothing().when(acteRepository).create(acte);

        acteRepository.create(acte);

        verify(acteRepository, times(1)).create(acte);
    }

    @Test
    @DisplayName("update - Met à jour un acte existant")
    void testUpdate() {
        acte.setNom("Extraction modifiée");
        doNothing().when(acteRepository).update(acte);

        acteRepository.update(acte);

        verify(acteRepository, times(1)).update(acte);
    }

    @Test
    @DisplayName("delete - Supprime un acte")
    void testDelete() {
        doNothing().when(acteRepository).delete(acte);

        acteRepository.delete(acte);

        verify(acteRepository, times(1)).delete(acte);
    }

    @Test
    @DisplayName("deleteById - Supprime un acte par ID")
    void testDeleteById() {
        doNothing().when(acteRepository).deleteById(1L);

        acteRepository.deleteById(1L);

        verify(acteRepository, times(1)).deleteById(1L);
    }



    @Test
    @DisplayName("findByNom - Retourne un acte par nom")
    void testFindByNom() {
        when(acteRepository.findByNom("Extraction dentaire")).thenReturn(Optional.of(acte));

        Optional<Acte> result = acteRepository.findByNom("Extraction dentaire");

        assertTrue(result.isPresent());
        assertEquals("Extraction dentaire", result.get().getNom());
        verify(acteRepository, times(1)).findByNom("Extraction dentaire");
    }

    @Test
    @DisplayName("findByNom - Retourne vide si non trouvé")
    void testFindByNomNotFound() {
        when(acteRepository.findByNom("Inconnu")).thenReturn(Optional.empty());

        Optional<Acte> result = acteRepository.findByNom("Inconnu");

        assertFalse(result.isPresent());
        verify(acteRepository, times(1)).findByNom("Inconnu");
    }

    @Test
    @DisplayName("findByTypeActe - Retourne les actes par type")
    void testFindByTypeActe() {
        Acte acte2 = new Acte();
        acte2.setId(2L);
        acte2.setNom("Implant");
        acte2.setTypeActe("Chirurgie");

        when(acteRepository.findByTypeActe("Chirurgie")).thenReturn(Arrays.asList(acte, acte2));

        List<Acte> result = acteRepository.findByTypeActe("Chirurgie");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> "Chirurgie".equals(a.getTypeActe())));
        verify(acteRepository, times(1)).findByTypeActe("Chirurgie");
    }

    @Test
    @DisplayName("findByTypeActe - Retourne liste vide si aucun acte")
    void testFindByTypeActeEmpty() {
        when(acteRepository.findByTypeActe("TypeInexistant")).thenReturn(Arrays.asList());

        List<Acte> result = acteRepository.findByTypeActe("TypeInexistant");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(acteRepository, times(1)).findByTypeActe("TypeInexistant");
    }
}