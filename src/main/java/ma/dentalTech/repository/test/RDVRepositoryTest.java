package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.RDV;
import ma.dentalTech.entities.enums.StatutRendezVous;
import ma.dentalTech.repository.modules.rdv.api.RdvRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour RdvRepository")
class RDVRepositoryTest {

    @Mock
    private RdvRepository rdvRepository;

    private AutoCloseable closeable;
    private RDV rdv;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        rdv = new RDV();
        rdv.setId(1L);
        rdv.setPatientId(2L);
        rdv.setMedecinId(1L);
        rdv.setDateHeure(LocalDateTime.of(2024, 11, 20, 10, 0));
        rdv.setMotif("Mal de dents");
        rdv.setStatut(StatutRendezVous.TERMINE);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne tous les RDV")
    void testFindAll() {
        RDV rdv2 = new RDV();
        rdv2.setId(2L);
        rdv2.setPatientId(4L);
        rdv2.setMedecinId(1L);
        rdv2.setDateHeure(LocalDateTime.of(2024, 11, 22, 9, 30));
        rdv2.setMotif("Détartrage");
        rdv2.setStatut(StatutRendezVous.PLANIFIE);

        when(rdvRepository.findAll()).thenReturn(Arrays.asList(rdv, rdv2));

        List<RDV> result = rdvRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rdvRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Retourne liste vide si aucun RDV")
    void testFindAllEmpty() {
        when(rdvRepository.findAll()).thenReturn(Collections.emptyList());

        List<RDV> result = rdvRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(rdvRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne un RDV existant")
    void testFindById() {
        when(rdvRepository.findById(1L)).thenReturn(rdv);

        RDV result = rdvRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mal de dents", result.getMotif());
        assertEquals(StatutRendezVous.TERMINE, result.getStatut());
        verify(rdvRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(rdvRepository.findById(999L)).thenReturn(null);

        RDV result = rdvRepository.findById(999L);

        assertNull(result);
        verify(rdvRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouveau RDV")
    void testCreate() {
        doNothing().when(rdvRepository).create(rdv);

        rdvRepository.create(rdv);

        verify(rdvRepository, times(1)).create(rdv);
    }

    @Test
    @DisplayName("update - Met à jour un RDV")
    void testUpdate() {
        rdv.setMotif("Contrôle dentaire");
        rdv.setStatut(StatutRendezVous.PLANIFIE);
        doNothing().when(rdvRepository).update(rdv);

        rdvRepository.update(rdv);

        verify(rdvRepository, times(1)).update(rdv);
    }

    @Test
    @DisplayName("delete - Supprime un RDV")
    void testDelete() {
        doNothing().when(rdvRepository).delete(rdv);

        rdvRepository.delete(rdv);

        verify(rdvRepository, times(1)).delete(rdv);
    }

    @Test
    @DisplayName("deleteById - Supprime un RDV par ID")
    void testDeleteById() {
        doNothing().when(rdvRepository).deleteById(1L);

        rdvRepository.deleteById(1L);

        verify(rdvRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByPatientId - Retourne les RDV d'un patient")
    void testFindByPatientId() {
        RDV rdv2 = new RDV();
        rdv2.setId(2L);
        rdv2.setPatientId(2L);

        when(rdvRepository.findByPatientId(2L)).thenReturn(Arrays.asList(rdv, rdv2));

        List<RDV> result = rdvRepository.findByPatientId(2L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r -> r.getPatientId().equals(2L)));
        verify(rdvRepository, times(1)).findByPatientId(2L);
    }

    @Test
    @DisplayName("findByPatientId - Retourne liste vide si aucun RDV")
    void testFindByPatientIdEmpty() {
        when(rdvRepository.findByPatientId(999L)).thenReturn(Collections.emptyList());

        List<RDV> result = rdvRepository.findByPatientId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(rdvRepository, times(1)).findByPatientId(999L);
    }

    @Test
    @DisplayName("findByMedecinId - Retourne les RDV d'un médecin")
    void testFindByMedecinId() {
        RDV rdv2 = new RDV();
        rdv2.setId(2L);
        rdv2.setMedecinId(1L);

        when(rdvRepository.findByMedecinId(1L)).thenReturn(Arrays.asList(rdv, rdv2));

        List<RDV> result = rdvRepository.findByMedecinId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r -> r.getMedecinId().equals(1L)));
        verify(rdvRepository, times(1)).findByMedecinId(1L);
    }

    @Test
    @DisplayName("findByMedecinId - Retourne liste vide si aucun RDV")
    void testFindByMedecinIdEmpty() {
        when(rdvRepository.findByMedecinId(999L)).thenReturn(Collections.emptyList());

        List<RDV> result = rdvRepository.findByMedecinId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(rdvRepository, times(1)).findByMedecinId(999L);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne les RDV dans une période")
    void testFindByDateBetween() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 30, 23, 59);

        when(rdvRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(rdv));

        List<RDV> result = rdvRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(rdvRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne liste vide hors période")
    void testFindByDateBetweenEmpty() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);

        when(rdvRepository.findByDateBetween(start, end)).thenReturn(Collections.emptyList());

        List<RDV> result = rdvRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(rdvRepository, times(1)).findByDateBetween(start, end);
    }

}