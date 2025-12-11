package ma.dentalTech.repository.test;


import ma.dentalTech.entities.pat.Certificat;
import ma.dentalTech.repository.modules.Certificat.api.CertificatRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests pour CertificatRepository")
class CertificatRepositoryTest {

    @Mock
    private CertificatRepository certificatRepository;

    private AutoCloseable closeable;
    private Certificat certificat;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        certificat = new Certificat();
        certificat.setId(1L);
        certificat.setPatientId(1L);
        certificat.setUtilisateurId(1L);
        certificat.setDateEmission(LocalDateTime.now());
        certificat.setTypeCertificat("Médical");
        certificat.setDureeEnJours(5);
        certificat.setMotif("Repos médical");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }



    @Test
    @DisplayName("findAll - Retourne tous les certificats")
    void testFindAll() {
        Certificat certificat2 = new Certificat();
        certificat2.setId(2L);

        when(certificatRepository.findAll()).thenReturn(Arrays.asList(certificat, certificat2));

        List<Certificat> result = certificatRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(certificatRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne un certificat existant")
    void testFindById() {
        when(certificatRepository.findById(1L)).thenReturn(certificat);

        Certificat result = certificatRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(certificatRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(certificatRepository.findById(999L)).thenReturn(null);

        Certificat result = certificatRepository.findById(999L);

        assertNull(result);
        verify(certificatRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée un nouveau certificat")
    void testCreate() {
        doNothing().when(certificatRepository).create(certificat);

        certificatRepository.create(certificat);

        verify(certificatRepository, times(1)).create(certificat);
    }

    @Test
    @DisplayName("update - Met à jour un certificat")
    void testUpdate() {
        certificat.setMotif("Nouveau motif");
        doNothing().when(certificatRepository).update(certificat);

        certificatRepository.update(certificat);

        verify(certificatRepository, times(1)).update(certificat);
    }

    @Test
    @DisplayName("delete - Supprime un certificat")
    void testDelete() {
        doNothing().when(certificatRepository).delete(certificat);

        certificatRepository.delete(certificat);

        verify(certificatRepository, times(1)).delete(certificat);
    }

    @Test
    @DisplayName("deleteById - Supprime un certificat par ID")
    void testDeleteById() {
        doNothing().when(certificatRepository).deleteById(1L);

        certificatRepository.deleteById(1L);

        verify(certificatRepository, times(1)).deleteById(1L);
    }



    @Test
    @DisplayName("findByPatientId - Retourne les certificats d'un patient")
    void testFindByPatientId() {
        Certificat certificat2 = new Certificat();
        certificat2.setId(2L);
        certificat2.setPatientId(1L);

        when(certificatRepository.findByPatientId(1L)).thenReturn(Arrays.asList(certificat, certificat2));

        List<Certificat> result = certificatRepository.findByPatientId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> c.getPatientId().equals(1L)));
        verify(certificatRepository, times(1)).findByPatientId(1L);
    }

    @Test
    @DisplayName("findByPatientId - Retourne liste vide si aucun certificat")
    void testFindByPatientIdEmpty() {
        when(certificatRepository.findByPatientId(999L)).thenReturn(Arrays.asList());

        List<Certificat> result = certificatRepository.findByPatientId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(certificatRepository, times(1)).findByPatientId(999L);
    }

    @Test
    @DisplayName("findByUtilisateurId - Retourne les certificats créés par un utilisateur")
    void testFindByUtilisateurId() {
        when(certificatRepository.findByUtilisateurId(1L)).thenReturn(Arrays.asList(certificat));

        List<Certificat> result = certificatRepository.findByUtilisateurId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUtilisateurId());
        verify(certificatRepository, times(1)).findByUtilisateurId(1L);
    }

    @Test
    @DisplayName("findByUtilisateurId - Retourne liste vide si aucun certificat")
    void testFindByUtilisateurIdEmpty() {
        when(certificatRepository.findByUtilisateurId(999L)).thenReturn(Arrays.asList());

        List<Certificat> result = certificatRepository.findByUtilisateurId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(certificatRepository, times(1)).findByUtilisateurId(999L);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne les certificats dans une période")
    void testFindByDateBetween() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 31, 23, 59);

        when(certificatRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList(certificat));

        List<Certificat> result = certificatRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(certificatRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Retourne liste vide si aucun certificat dans la période")
    void testFindByDateBetweenEmpty() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);

        when(certificatRepository.findByDateBetween(start, end)).thenReturn(Arrays.asList());

        List<Certificat> result = certificatRepository.findByDateBetween(start, end);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(certificatRepository, times(1)).findByDateBetween(start, end);
    }

    @Test
    @DisplayName("findByDateBetween - Test avec même date début et fin")
    void testFindByDateBetweenSameDay() {
        LocalDateTime date = LocalDateTime.of(2025, 6, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 6, 15, 23, 59);

        when(certificatRepository.findByDateBetween(date, endDate)).thenReturn(Arrays.asList(certificat));

        List<Certificat> result = certificatRepository.findByDateBetween(date, endDate);

        assertNotNull(result);
        verify(certificatRepository, times(1)).findByDateBetween(date, endDate);
    }
}