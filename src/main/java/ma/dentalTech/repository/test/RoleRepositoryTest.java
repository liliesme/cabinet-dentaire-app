package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Role;
import ma.dentalTech.repository.modules.role.api.RoleRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour RoleRepository")
class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    private AutoCloseable closeable;
    private Role role;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        
        role = new Role();
        role.setId(1L);
        role.setNom("ADMIN");
        role.setDescription("Administrateur du système");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne tous les rôles")
    void testFindAll() {
        Role role2 = new Role();
        role2.setId(2L);
        role2.setNom("MEDECIN");
        role2.setDescription("Médecin dentiste");

        Role role3 = new Role();
        role3.setId(3L);
        role3.setNom("SECRETAIRE");
        role3.setDescription("Secrétaire du cabinet");

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role, role2, role3));

        List<Role> result = roleRepository.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Retourne liste vide si aucun rôle")
    void testFindAllEmpty() {
        when(roleRepository.findAll()).thenReturn(Collections.emptyList());

        List<Role> result = roleRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne un rôle existant")
    void testFindById() {
        when(roleRepository.findById(1L)).thenReturn(role);

        Role result = roleRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ADMIN", result.getNom());
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(roleRepository.findById(999L)).thenReturn(null);

        Role result = roleRepository.findById(999L);

        assertNull(result);
        verify(roleRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouveau rôle")
    void testCreate() {
        doNothing().when(roleRepository).create(role);

        roleRepository.create(role);

        verify(roleRepository, times(1)).create(role);
    }

    @Test
    @DisplayName("update - Met à jour un rôle")
    void testUpdate() {
        role.setDescription("Administrateur principal");
        doNothing().when(roleRepository).update(role);

        roleRepository.update(role);

        verify(roleRepository, times(1)).update(role);
    }

    @Test
    @DisplayName("delete - Supprime un rôle")
    void testDelete() {
        doNothing().when(roleRepository).delete(role);

        roleRepository.delete(role);

        verify(roleRepository, times(1)).delete(role);
    }

    @Test
    @DisplayName("deleteById - Supprime un rôle par ID")
    void testDeleteById() {
        doNothing().when(roleRepository).deleteById(1L);

        roleRepository.deleteById(1L);

        verify(roleRepository, times(1)).deleteById(1L);
    }

    

    @Test
    @DisplayName("findByNom - Retourne un rôle par nom")
    void testFindByNom() {
        when(roleRepository.findByNom("ADMIN")).thenReturn(Optional.of(role));

        Optional<Role> result = roleRepository.findByNom("ADMIN");

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getNom());
        verify(roleRepository, times(1)).findByNom("ADMIN");
    }

    @Test
    @DisplayName("findByNom - Retourne vide si non trouvé")
    void testFindByNomNotFound() {
        when(roleRepository.findByNom("INCONNU")).thenReturn(Optional.empty());

        Optional<Role> result = roleRepository.findByNom("INCONNU");

        assertFalse(result.isPresent());
        verify(roleRepository, times(1)).findByNom("INCONNU");
    }

    @Test
    @DisplayName("findByNom - Test avec rôle MEDECIN")
    void testFindByNomMedecin() {
        Role medecin = new Role();
        medecin.setId(2L);
        medecin.setNom("MEDECIN");

        when(roleRepository.findByNom("MEDECIN")).thenReturn(Optional.of(medecin));

        Optional<Role> result = roleRepository.findByNom("MEDECIN");

        assertTrue(result.isPresent());
        assertEquals("MEDECIN", result.get().getNom());
        verify(roleRepository, times(1)).findByNom("MEDECIN");
    }

    @Test
    @DisplayName("existsByNom - Retourne true si le rôle existe")
    void testExistsByNomTrue() {
        when(roleRepository.existsByNom("ADMIN")).thenReturn(true);

        boolean result = roleRepository.existsByNom("ADMIN");

        assertTrue(result);
        verify(roleRepository, times(1)).existsByNom("ADMIN");
    }

    @Test
    @DisplayName("existsByNom - Retourne false si le rôle n'existe pas")
    void testExistsByNomFalse() {
        when(roleRepository.existsByNom("INCONNU")).thenReturn(false);

        boolean result = roleRepository.existsByNom("INCONNU");

        assertFalse(result);
        verify(roleRepository, times(1)).existsByNom("INCONNU");
    }

    @Test
    @DisplayName("existsByNom - Test avec rôle SECRETAIRE")
    void testExistsByNomSecretaire() {
        when(roleRepository.existsByNom("SECRETAIRE")).thenReturn(true);

        boolean result = roleRepository.existsByNom("SECRETAIRE");

        assertTrue(result);
        verify(roleRepository, times(1)).existsByNom("SECRETAIRE");
    }
}