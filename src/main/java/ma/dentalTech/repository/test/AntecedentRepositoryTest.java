package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Antecedent;
import ma.dentalTech.entities.pat.Patient;
import ma.dentalTech.entities.enums.CategorieAntecedent;
import ma.dentalTech.entities.enums.NiveauRisque;
import ma.dentalTech.repository.modules.patient.api.AntecedentRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour AntecedentRepository")
class AntecedentRepositoryTest {

    @Mock
    private AntecedentRepository antecedentRepository;

    private AutoCloseable closeable;
    private Antecedent antecedent;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        antecedent = new Antecedent();
        antecedent.setId(1L);
        antecedent.setPatientId(1L);
        antecedent.setNom("Diabète");
        antecedent.setCategorie(CategorieAntecedent.MEDICAL);
        antecedent.setNiveauRisque(NiveauRisque.MODERE);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne tous les antécédents")
    void testFindAll() {
        Antecedent antecedent2 = new Antecedent();
        antecedent2.setId(2L);
        antecedent2.setNom("Hypertension");

        when(antecedentRepository.findAll()).thenReturn(Arrays.asList(antecedent, antecedent2));

        List<Antecedent> result = antecedentRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(antecedentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne un antécédent existant")
    void testFindById() {
        when(antecedentRepository.findById(1L)).thenReturn(antecedent);

        Antecedent result = antecedentRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Diabète", result.getNom());
        verify(antecedentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(antecedentRepository.findById(999L)).thenReturn(null);

        Antecedent result = antecedentRepository.findById(999L);

        assertNull(result);
        verify(antecedentRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouvel antécédent")
    void testCreate() {
        doNothing().when(antecedentRepository).create(antecedent);

        antecedentRepository.create(antecedent);

        verify(antecedentRepository, times(1)).create(antecedent);
    }

    @Test
    @DisplayName("update - Met à jour un antécédent")
    void testUpdate() {
        antecedent.setNom("Diabète Type 2");
        doNothing().when(antecedentRepository).update(antecedent);

        antecedentRepository.update(antecedent);

        verify(antecedentRepository, times(1)).update(antecedent);
    }

    @Test
    @DisplayName("delete - Supprime un antécédent")
    void testDelete() {
        doNothing().when(antecedentRepository).delete(antecedent);

        antecedentRepository.delete(antecedent);

        verify(antecedentRepository, times(1)).delete(antecedent);
    }

    @Test
    @DisplayName("deleteById - Supprime un antécédent par ID")
    void testDeleteById() {
        doNothing().when(antecedentRepository).deleteById(1L);

        antecedentRepository.deleteById(1L);

        verify(antecedentRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByNom - Retourne un antécédent par nom")
    void testFindByNom() {
        when(antecedentRepository.findByNom("Diabète")).thenReturn(Optional.of(antecedent));

        Optional<Antecedent> result = antecedentRepository.findByNom("Diabète");

        assertTrue(result.isPresent());
        assertEquals("Diabète", result.get().getNom());
        verify(antecedentRepository, times(1)).findByNom("Diabète");
    }

    @Test
    @DisplayName("findByNom - Retourne vide si non trouvé")
    void testFindByNomNotFound() {
        when(antecedentRepository.findByNom("Inconnu")).thenReturn(Optional.empty());

        Optional<Antecedent> result = antecedentRepository.findByNom("Inconnu");

        assertFalse(result.isPresent());
        verify(antecedentRepository, times(1)).findByNom("Inconnu");
    }

    @Test
    @DisplayName("existsById - Retourne true si l'antécédent existe")
    void testExistsByIdTrue() {
        when(antecedentRepository.existsById(1L)).thenReturn(true);

        boolean result = antecedentRepository.existsById(1L);

        assertTrue(result);
        verify(antecedentRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("existsById - Retourne false si l'antécédent n'existe pas")
    void testExistsByIdFalse() {
        when(antecedentRepository.existsById(999L)).thenReturn(false);

        boolean result = antecedentRepository.existsById(999L);

        assertFalse(result);
        verify(antecedentRepository, times(1)).existsById(999L);
    }

    @Test
    @DisplayName("count - Compte le nombre d'antécédents")
    void testCount() {
        when(antecedentRepository.count()).thenReturn(5L);

        long count = antecedentRepository.count();

        assertEquals(5L, count);
        verify(antecedentRepository, times(1)).count();
    }

    @Test
    @DisplayName("count - Retourne 0 si aucun antécédent")
    void testCountZero() {
        when(antecedentRepository.count()).thenReturn(0L);

        long count = antecedentRepository.count();

        assertEquals(0L, count);
        verify(antecedentRepository, times(1)).count();
    }

    @Test
    @DisplayName("findPage - Retourne une page d'antécédents")
    void testFindPage() {
        Antecedent antecedent2 = new Antecedent();
        antecedent2.setId(2L);
        antecedent2.setNom("Hypertension");

        when(antecedentRepository.findPage(10, 0)).thenReturn(Arrays.asList(antecedent, antecedent2));

        List<Antecedent> result = antecedentRepository.findPage(10, 0);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(antecedentRepository, times(1)).findPage(10, 0);
    }

    @Test
    @DisplayName("findPage - Retourne liste vide pour page sans résultats")
    void testFindPageEmpty() {
        when(antecedentRepository.findPage(10, 100)).thenReturn(Collections.emptyList());

        List<Antecedent> result = antecedentRepository.findPage(10, 100);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(antecedentRepository, times(1)).findPage(10, 100);
    }

    @Test
    @DisplayName("getPatientsHavingAntecedent - Retourne les patients avec cet antécédent")
    void testGetPatientsHavingAntecedent() {
        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setNom("Dupont");

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setNom("Martin");

        when(antecedentRepository.getPatientsHavingAntecedent(1L))
                .thenReturn(Arrays.asList(patient1, patient2));

        List<Patient> result = antecedentRepository.getPatientsHavingAntecedent(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(antecedentRepository, times(1)).getPatientsHavingAntecedent(1L);
    }

    @Test
    @DisplayName("getPatientsHavingAntecedent - Retourne liste vide si aucun patient")
    void testGetPatientsHavingAntecedentEmpty() {
        when(antecedentRepository.getPatientsHavingAntecedent(999L)).thenReturn(Collections.emptyList());

        List<Patient> result = antecedentRepository.getPatientsHavingAntecedent(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(antecedentRepository, times(1)).getPatientsHavingAntecedent(999L);
    }
}