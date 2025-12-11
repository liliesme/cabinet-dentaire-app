package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.medecin;
import ma.dentalTech.repository.modules.medecin.api.MedecinRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour MedecinRepository")
class MedecinRepositoryTest {

    @Mock
    private MedecinRepository medecinRepository;

    private AutoCloseable closeable;
    private medecin med;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        med = new medecin();
        med.setId(1L);
        med.setNom("Martin");
        med.setPrenom("Jean");
        med.setEmail("dr.martin@dental.com");
        med.setTelephone("0612345678");
        med.setAdresse("123 Rue de la Santé");
        med.setSpecialite("Orthodontie");
        med.setDateCreation(LocalDateTime.now());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne tous les médecins")
    void testFindAll() {
        medecin med2 = new medecin();
        med2.setId(2L);
        med2.setNom("Dupont");
        med2.setSpecialite("Endodontie");

        when(medecinRepository.findAll()).thenReturn(Arrays.asList(med, med2));

        List<medecin> result = medecinRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(medecinRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Retourne liste vide si aucun médecin")
    void testFindAllEmpty() {
        when(medecinRepository.findAll()).thenReturn(Collections.emptyList());

        List<medecin> result = medecinRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medecinRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne un médecin existant")
    void testFindById() {
        when(medecinRepository.findById(1L)).thenReturn(med);

        medecin result = medecinRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Martin", result.getNom());
        assertEquals("Orthodontie", result.getSpecialite());
        verify(medecinRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(medecinRepository.findById(999L)).thenReturn(null);

        medecin result = medecinRepository.findById(999L);

        assertNull(result);
        verify(medecinRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouveau médecin")
    void testCreate() {
        doNothing().when(medecinRepository).create(med);

        medecinRepository.create(med);

        verify(medecinRepository, times(1)).create(med);
    }

    @Test
    @DisplayName("update - Met à jour un médecin")
    void testUpdate() {
        med.setSpecialite("Parodontie");
        med.setTelephone("0698765432");
        doNothing().when(medecinRepository).update(med);

        medecinRepository.update(med);

        verify(medecinRepository, times(1)).update(med);
    }

    @Test
    @DisplayName("delete - Supprime un médecin")
    void testDelete() {
        doNothing().when(medecinRepository).delete(med);

        medecinRepository.delete(med);

        verify(medecinRepository, times(1)).delete(med);
    }

    @Test
    @DisplayName("deleteById - Supprime un médecin par ID")
    void testDeleteById() {
        doNothing().when(medecinRepository).deleteById(1L);

        medecinRepository.deleteById(1L);

        verify(medecinRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByEmail - Retourne un médecin par email")
    void testFindByEmail() {
        when(medecinRepository.findByEmail("dr.martin@dental.com")).thenReturn(Optional.of(med));

        Optional<medecin> result = medecinRepository.findByEmail("dr.martin@dental.com");

        assertTrue(result.isPresent());
        assertEquals("dr.martin@dental.com", result.get().getEmail());
        verify(medecinRepository, times(1)).findByEmail("dr.martin@dental.com");
    }

    @Test
    @DisplayName("findByEmail - Retourne vide si non trouvé")
    void testFindByEmailNotFound() {
        when(medecinRepository.findByEmail("inconnu@dental.com")).thenReturn(Optional.empty());

        Optional<medecin> result = medecinRepository.findByEmail("inconnu@dental.com");

        assertFalse(result.isPresent());
        verify(medecinRepository, times(1)).findByEmail("inconnu@dental.com");
    }

    @Test
    @DisplayName("findByTelephone - Retourne un médecin par téléphone")
    void testFindByTelephone() {
        when(medecinRepository.findByTelephone("0612345678")).thenReturn(Optional.of(med));

        Optional<medecin> result = medecinRepository.findByTelephone("0612345678");

        assertTrue(result.isPresent());
        assertEquals("0612345678", result.get().getTelephone());
        verify(medecinRepository, times(1)).findByTelephone("0612345678");
    }

    @Test
    @DisplayName("findByTelephone - Retourne vide si non trouvé")
    void testFindByTelephoneNotFound() {
        when(medecinRepository.findByTelephone("0000000000")).thenReturn(Optional.empty());

        Optional<medecin> result = medecinRepository.findByTelephone("0000000000");

        assertFalse(result.isPresent());
        verify(medecinRepository, times(1)).findByTelephone("0000000000");
    }

    @Test
    @DisplayName("searchByNomPrenom - Recherche par nom ou prénom")
    void testSearchByNomPrenom() {
        when(medecinRepository.searchByNomPrenom("Martin")).thenReturn(Arrays.asList(med));

        List<medecin> result = medecinRepository.searchByNomPrenom("Martin");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Martin", result.get(0).getNom());
        verify(medecinRepository, times(1)).searchByNomPrenom("Martin");
    }

    @Test
    @DisplayName("searchByNomPrenom - Retourne liste vide si aucun résultat")
    void testSearchByNomPrenomEmpty() {
        when(medecinRepository.searchByNomPrenom("Inconnu")).thenReturn(Collections.emptyList());

        List<medecin> result = medecinRepository.searchByNomPrenom("Inconnu");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medecinRepository, times(1)).searchByNomPrenom("Inconnu");
    }

    @Test
    @DisplayName("existsById - Retourne true si le médecin existe")
    void testExistsByIdTrue() {
        when(medecinRepository.existsById(1L)).thenReturn(true);

        boolean result = medecinRepository.existsById(1L);

        assertTrue(result);
        verify(medecinRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("existsById - Retourne false si le médecin n'existe pas")
    void testExistsByIdFalse() {
        when(medecinRepository.existsById(999L)).thenReturn(false);

        boolean result = medecinRepository.existsById(999L);

        assertFalse(result);
        verify(medecinRepository, times(1)).existsById(999L);
    }

    @Test
    @DisplayName("count - Compte le nombre de médecins")
    void testCount() {
        when(medecinRepository.count()).thenReturn(10L);

        long count = medecinRepository.count();

        assertEquals(10L, count);
        verify(medecinRepository, times(1)).count();
    }

    @Test
    @DisplayName("findPage - Retourne une page de médecins")
    void testFindPage() {
        medecin med2 = new medecin();
        med2.setId(2L);
        med2.setNom("Dupont");

        when(medecinRepository.findPage(10, 0)).thenReturn(Arrays.asList(med, med2));

        List<medecin> result = medecinRepository.findPage(10, 0);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(medecinRepository, times(1)).findPage(10, 0);
    }

    @Test
    @DisplayName("findPage - Retourne liste vide pour page sans résultats")
    void testFindPageEmpty() {
        when(medecinRepository.findPage(10, 100)).thenReturn(Collections.emptyList());

        List<medecin> result = medecinRepository.findPage(10, 100);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medecinRepository, times(1)).findPage(10, 100);
    }

    @Test
    @DisplayName("findBySpecialite - Retourne les médecins par spécialité")
    void testFindBySpecialite() {
        when(medecinRepository.findBySpecialite("Orthodontie")).thenReturn(Arrays.asList(med));

        List<medecin> result = medecinRepository.findBySpecialite("Orthodontie");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Orthodontie", result.get(0).getSpecialite());
        verify(medecinRepository, times(1)).findBySpecialite("Orthodontie");
    }

    @Test
    @DisplayName("findBySpecialite - Retourne liste vide si aucun médecin")
    void testFindBySpecialiteEmpty() {
        when(medecinRepository.findBySpecialite("SpecialiteInexistante")).thenReturn(Collections.emptyList());

        List<medecin> result = medecinRepository.findBySpecialite("SpecialiteInexistante");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medecinRepository, times(1)).findBySpecialite("SpecialiteInexistante");
    }

    @Test
    @DisplayName("findBySpecialite - Retourne plusieurs médecins de même spécialité")
    void testFindBySpecialiteMultiple() {
        medecin med2 = new medecin();
        med2.setId(2L);
        med2.setNom("Dupont");
        med2.setSpecialite("Orthodontie");

        when(medecinRepository.findBySpecialite("Orthodontie")).thenReturn(Arrays.asList(med, med2));

        List<medecin> result = medecinRepository.findBySpecialite("Orthodontie");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(m -> "Orthodontie".equals(m.getSpecialite())));
        verify(medecinRepository, times(1)).findBySpecialite("Orthodontie");
    }
}