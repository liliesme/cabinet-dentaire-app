package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Agenda;
import ma.dentalTech.repository.modules.Agenda.api.AgendaRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour AgendaRepository")
class AgendaRepositoryTest {

    @Mock
    private AgendaRepository agendaRepository;

    private AutoCloseable closeable;
    private Agenda agenda;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        agenda = new Agenda();
        agenda.setIdAgenda(1L);
        agenda.setAnnee(2025);
        agenda.setMois(1);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }



    @Test
    @DisplayName("findAll - Retourne tous les agendas")
    void testFindAll() {
        Agenda agenda2 = new Agenda();
        agenda2.setIdAgenda(2L);
        agenda2.setAnnee(2025);
        agenda2.setMois(2);

        when(agendaRepository.findAll()).thenReturn(Arrays.asList(agenda, agenda2));

        List<Agenda> result = agendaRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(agendaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne un agenda existant")
    void testFindById() {
        when(agendaRepository.findById(1L)).thenReturn(agenda);

        Agenda result = agendaRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdAgenda());
        verify(agendaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(agendaRepository.findById(999L)).thenReturn(null);

        Agenda result = agendaRepository.findById(999L);

        assertNull(result);
        verify(agendaRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouvel agenda")
    void testCreate() {
        doNothing().when(agendaRepository).create(agenda);

        agendaRepository.create(agenda);

        verify(agendaRepository, times(1)).create(agenda);
    }

    @Test
    @DisplayName("update - Met à jour un agenda existant")
    void testUpdate() {
        agenda.setMois(12);
        doNothing().when(agendaRepository).update(agenda);

        agendaRepository.update(agenda);

        verify(agendaRepository, times(1)).update(agenda);
    }

    @Test
    @DisplayName("delete - Supprime un agenda")
    void testDelete() {
        doNothing().when(agendaRepository).delete(agenda);

        agendaRepository.delete(agenda);

        verify(agendaRepository, times(1)).delete(agenda);
    }

    @Test
    @DisplayName("deleteById - Supprime un agenda par ID")
    void testDeleteById() {
        doNothing().when(agendaRepository).deleteById(1L);

        agendaRepository.deleteById(1L);

        verify(agendaRepository, times(1)).deleteById(1L);
    }



    @Test
    @DisplayName("findByAnneeAndMois - Retourne un agenda par année et mois")
    void testFindByAnneeAndMois() {
        when(agendaRepository.findByAnneeAndMois(2025, 1)).thenReturn(Optional.of(agenda));

        Optional<Agenda> result = agendaRepository.findByAnneeAndMois(2025, 1);

        assertTrue(result.isPresent());
        assertEquals(2025, result.get().getAnnee());
        assertEquals(1, result.get().getMois());
        verify(agendaRepository, times(1)).findByAnneeAndMois(2025, 1);
    }

    @Test
    @DisplayName("findByAnneeAndMois - Retourne vide si non trouvé")
    void testFindByAnneeAndMoisNotFound() {
        when(agendaRepository.findByAnneeAndMois(1999, 13)).thenReturn(Optional.empty());

        Optional<Agenda> result = agendaRepository.findByAnneeAndMois(1999, 13);

        assertFalse(result.isPresent());
        verify(agendaRepository, times(1)).findByAnneeAndMois(1999, 13);
    }

    @Test
    @DisplayName("findByAnneeAndMois - Gestion des valeurs limites (Décembre)")
    void testFindByAnneeAndMoisDecembre() {
        Agenda decembre = new Agenda();
        decembre.setIdAgenda(12L);
        decembre.setAnnee(2025);
        decembre.setMois(12);

        when(agendaRepository.findByAnneeAndMois(2025, 12)).thenReturn(Optional.of(decembre));

        Optional<Agenda> result = agendaRepository.findByAnneeAndMois(2025, 12);

        assertTrue(result.isPresent());
        assertEquals(12, result.get().getMois());
        verify(agendaRepository, times(1)).findByAnneeAndMois(2025, 12);
    }
}
