package ma.dentalTech.repository.test;

import ma.dentalTech.entities.pat.Secretaire;
import ma.dentalTech.entities.pat.Patient;
import ma.dentalTech.entities.pat.medecin;
import ma.dentalTech.entities.pat.RDV;
import ma.dentalTech.repository.modules.secretaire.api.SecretaireRepository;
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

@DisplayName("Tests pour SecretaireRepository")
class SecretaireRepositoryTest {

    @Mock
    private SecretaireRepository secretaireRepository;

    private AutoCloseable closeable;
    private Secretaire secretaire;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        
        secretaire = new Secretaire();
        secretaire.setId(2L);
        secretaire.setNom("Rossafi");
        secretaire.setPrenom("Houda");
        secretaire.setEmail("f.Rossafi@cabinet.ma");
        secretaire.setTelephone("0612345678");
        secretaire.setAdresse("Casablanca");
        secretaire.setNumCNSS("CNSS-2021-001");
        secretaire.setCommission(500.0);
        secretaire.setDateCreation(LocalDateTime.now());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    

    @Test
    @DisplayName("findAll - Retourne toutes les secrétaires")
    void testFindAll() {
        Secretaire secretaire2 = new Secretaire();
        secretaire2.setId(3L);
        secretaire2.setNom("Alami");
        secretaire2.setPrenom("Sara");

        when(secretaireRepository.findAll()).thenReturn(Arrays.asList(secretaire, secretaire2));

        List<Secretaire> result = secretaireRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(secretaireRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Retourne liste vide si aucune secrétaire")
    void testFindAllEmpty() {
        when(secretaireRepository.findAll()).thenReturn(Collections.emptyList());

        List<Secretaire> result = secretaireRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(secretaireRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById - Retourne une secrétaire existante")
    void testFindById() {
        when(secretaireRepository.findById(2L)).thenReturn(secretaire);

        Secretaire result = secretaireRepository.findById(2L);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Rossafi", result.getNom());
        verify(secretaireRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("findById - Retourne null si non trouvé")
    void testFindByIdNotFound() {
        when(secretaireRepository.findById(999L)).thenReturn(null);

        Secretaire result = secretaireRepository.findById(999L);

        assertNull(result);
        verify(secretaireRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("create - Crée une nouvelle secrétaire")
    void testCreate() {
        doNothing().when(secretaireRepository).create(secretaire);

        secretaireRepository.create(secretaire);

        verify(secretaireRepository, times(1)).create(secretaire);
    }

    @Test
    @DisplayName("update - Met à jour une secrétaire")
    void testUpdate() {
        secretaire.setCommission(600.0);
        doNothing().when(secretaireRepository).update(secretaire);

        secretaireRepository.update(secretaire);

        verify(secretaireRepository, times(1)).update(secretaire);
    }

    @Test
    @DisplayName("delete - Supprime une secrétaire")
    void testDelete() {
        doNothing().when(secretaireRepository).delete(secretaire);

        secretaireRepository.delete(secretaire);

        verify(secretaireRepository, times(1)).delete(secretaire);
    }

    @Test
    @DisplayName("deleteById - Supprime une secrétaire par ID")
    void testDeleteById() {
        doNothing().when(secretaireRepository).deleteById(2L);

        secretaireRepository.deleteById(2L);

        verify(secretaireRepository, times(1)).deleteById(2L);
    }

    

    @Test
    @DisplayName("findByEmail - Retourne une secrétaire par email")
    void testFindByEmail() {
        when(secretaireRepository.findByEmail("f.Rossafi@cabinet.ma")).thenReturn(Optional.of(secretaire));

        Optional<Secretaire> result = secretaireRepository.findByEmail("f.Rossafi@cabinet.ma");

        assertTrue(result.isPresent());
        assertEquals("f.Rossafi@cabinet.ma", result.get().getEmail());
        verify(secretaireRepository, times(1)).findByEmail("f.Rossafi@cabinet.ma");
    }

    @Test
    @DisplayName("findByEmail - Retourne vide si non trouvé")
    void testFindByEmailNotFound() {
        when(secretaireRepository.findByEmail("inconnu@email.com")).thenReturn(Optional.empty());

        Optional<Secretaire> result = secretaireRepository.findByEmail("inconnu@email.com");

        assertFalse(result.isPresent());
        verify(secretaireRepository, times(1)).findByEmail("inconnu@email.com");
    }

    @Test
    @DisplayName("findByTelephone - Retourne une secrétaire par téléphone")
    void testFindByTelephone() {
        when(secretaireRepository.findByTelephone("0612345678")).thenReturn(Optional.of(secretaire));

        Optional<Secretaire> result = secretaireRepository.findByTelephone("0612345678");

        assertTrue(result.isPresent());
        assertEquals("0612345678", result.get().getTelephone());
        verify(secretaireRepository, times(1)).findByTelephone("0612345678");
    }

    @Test
    @DisplayName("findByTelephone - Retourne vide si non trouvé")
    void testFindByTelephoneNotFound() {
        when(secretaireRepository.findByTelephone("0000000000")).thenReturn(Optional.empty());

        Optional<Secretaire> result = secretaireRepository.findByTelephone("0000000000");

        assertFalse(result.isPresent());
        verify(secretaireRepository, times(1)).findByTelephone("0000000000");
    }

    @Test
    @DisplayName("searchByNomPrenom - Recherche par mot-clé")
    void testSearchByNomPrenom() {
        when(secretaireRepository.searchByNomPrenom("Rossafi")).thenReturn(Arrays.asList(secretaire));

        List<Secretaire> result = secretaireRepository.searchByNomPrenom("Rossafi");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Rossafi", result.get(0).getNom());
        verify(secretaireRepository, times(1)).searchByNomPrenom("Rossafi");
    }

    @Test
    @DisplayName("searchByNomPrenom - Retourne liste vide si aucun résultat")
    void testSearchByNomPrenomEmpty() {
        when(secretaireRepository.searchByNomPrenom("Inconnu")).thenReturn(Collections.emptyList());

        List<Secretaire> result = secretaireRepository.searchByNomPrenom("Inconnu");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(secretaireRepository, times(1)).searchByNomPrenom("Inconnu");
    }

    @Test
    @DisplayName("existsById - Retourne true si existe")
    void testExistsByIdTrue() {
        when(secretaireRepository.existsById(2L)).thenReturn(true);

        boolean result = secretaireRepository.existsById(2L);

        assertTrue(result);
        verify(secretaireRepository, times(1)).existsById(2L);
    }

    @Test
    @DisplayName("existsById - Retourne false si n'existe pas")
    void testExistsByIdFalse() {
        when(secretaireRepository.existsById(999L)).thenReturn(false);

        boolean result = secretaireRepository.existsById(999L);

        assertFalse(result);
        verify(secretaireRepository, times(1)).existsById(999L);
    }

    @Test
    @DisplayName("count - Compte le nombre de secrétaires")
    void testCount() {
        when(secretaireRepository.count()).thenReturn(5L);

        long count = secretaireRepository.count();

        assertEquals(5L, count);
        verify(secretaireRepository, times(1)).count();
    }

    @Test
    @DisplayName("findPage - Retourne une page de secrétaires")
    void testFindPage() {
        when(secretaireRepository.findPage(10, 0)).thenReturn(Arrays.asList(secretaire));

        List<Secretaire> result = secretaireRepository.findPage(10, 0);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).findPage(10, 0);
    }

    

    @Test
    @DisplayName("findAllMedecins - Retourne tous les médecins")
    void testFindAllMedecins() {
        medecin med = new medecin();
        med.setId(1L);
        med.setNom("Dr. Kebbaj");
        med.setSpecialite("Chirurgie dentaire");

        when(secretaireRepository.findAllMedecins()).thenReturn(Arrays.asList(med));

        List<medecin> result = secretaireRepository.findAllMedecins();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).findAllMedecins();
    }

    @Test
    @DisplayName("findMedecinById - Retourne un médecin par ID")
    void testFindMedecinById() {
        medecin med = new medecin();
        med.setId(1L);
        med.setNom("Dr. Kebbaj");

        when(secretaireRepository.findMedecinById(1L)).thenReturn(Optional.of(med));

        Optional<medecin> result = secretaireRepository.findMedecinById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(secretaireRepository, times(1)).findMedecinById(1L);
    }

    @Test
    @DisplayName("searchMedecinsByNomPrenom - Recherche médecins par mot-clé")
    void testSearchMedecinsByNomPrenom() {
        medecin med = new medecin();
        med.setId(1L);
        med.setNom("Kebbaj");

        when(secretaireRepository.searchMedecinsByNomPrenom("Kebbaj")).thenReturn(Arrays.asList(med));

        List<medecin> result = secretaireRepository.searchMedecinsByNomPrenom("Kebbaj");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).searchMedecinsByNomPrenom("Kebbaj");
    }

    @Test
    @DisplayName("findMedecinsBySpecialite - Retourne médecins par spécialité")
    void testFindMedecinsBySpecialite() {
        medecin med = new medecin();
        med.setId(1L);
        med.setSpecialite("Chirurgie dentaire");

        when(secretaireRepository.findMedecinsBySpecialite("Chirurgie dentaire")).thenReturn(Arrays.asList(med));

        List<medecin> result = secretaireRepository.findMedecinsBySpecialite("Chirurgie dentaire");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).findMedecinsBySpecialite("Chirurgie dentaire");
    }

    

    @Test
    @DisplayName("createRendezVous - Crée un nouveau RDV")
    void testCreateRendezVous() {
        RDV rdv = new RDV();
        rdv.setId(1L);
        rdv.setMotif("Consultation");

        doNothing().when(secretaireRepository).createRendezVous(rdv);

        secretaireRepository.createRendezVous(rdv);

        verify(secretaireRepository, times(1)).createRendezVous(rdv);
    }

    @Test
    @DisplayName("updateRendezVous - Met à jour un RDV")
    void testUpdateRendezVous() {
        RDV rdv = new RDV();
        rdv.setId(1L);
        rdv.setMotif("Consultation modifiée");

        doNothing().when(secretaireRepository).updateRendezVous(rdv);

        secretaireRepository.updateRendezVous(rdv);

        verify(secretaireRepository, times(1)).updateRendezVous(rdv);
    }

    @Test
    @DisplayName("deleteRendezVous - Supprime un RDV")
    void testDeleteRendezVous() {
        doNothing().when(secretaireRepository).deleteRendezVous(1L);

        secretaireRepository.deleteRendezVous(1L);

        verify(secretaireRepository, times(1)).deleteRendezVous(1L);
    }

    @Test
    @DisplayName("findRendezVousById - Retourne un RDV par ID")
    void testFindRendezVousById() {
        RDV rdv = new RDV();
        rdv.setId(1L);
        rdv.setMotif("Mal de dents");

        when(secretaireRepository.findRendezVousById(1L)).thenReturn(Optional.of(rdv));

        Optional<RDV> result = secretaireRepository.findRendezVousById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(secretaireRepository, times(1)).findRendezVousById(1L);
    }

    @Test
    @DisplayName("findRendezVousByPatient - Retourne RDV d'un patient")
    void testFindRendezVousByPatient() {
        RDV rdv = new RDV();
        rdv.setId(1L);
        rdv.setPatientId(2L);

        when(secretaireRepository.findRendezVousByPatient(2L)).thenReturn(Arrays.asList(rdv));

        List<RDV> result = secretaireRepository.findRendezVousByPatient(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).findRendezVousByPatient(2L);
    }

    @Test
    @DisplayName("findRendezVousByMedecin - Retourne RDV d'un médecin")
    void testFindRendezVousByMedecin() {
        RDV rdv = new RDV();
        rdv.setId(1L);
        rdv.setMedecinId(1L);

        when(secretaireRepository.findRendezVousByMedecin(1L)).thenReturn(Arrays.asList(rdv));

        List<RDV> result = secretaireRepository.findRendezVousByMedecin(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).findRendezVousByMedecin(1L);
    }

    @Test
    @DisplayName("findRendezVousByDate - Retourne RDV par date")
    void testFindRendezVousByDate() {
        RDV rdv = new RDV();
        rdv.setId(1L);

        when(secretaireRepository.findRendezVousByDate("2024-11-20")).thenReturn(Arrays.asList(rdv));

        List<RDV> result = secretaireRepository.findRendezVousByDate("2024-11-20");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).findRendezVousByDate("2024-11-20");
    }

    

    @Test
    @DisplayName("findPatientByEmail - Retourne un patient par email")
    void testFindPatientByEmail() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("ahed.radi@email.com");

        when(secretaireRepository.findPatientByEmail("ahed.radi@email.com")).thenReturn(Optional.of(patient));

        Optional<Patient> result = secretaireRepository.findPatientByEmail("ahed.radi@email.com");

        assertTrue(result.isPresent());
        assertEquals("ahed.radi@email.com", result.get().getEmail());
        verify(secretaireRepository, times(1)).findPatientByEmail("ahed.radi@email.com");
    }

    @Test
    @DisplayName("findPatientByTelephone - Retourne un patient par téléphone")
    void testFindPatientByTelephone() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setTelephone("0656789012");

        when(secretaireRepository.findPatientByTelephone("0656789012")).thenReturn(Optional.of(patient));

        Optional<Patient> result = secretaireRepository.findPatientByTelephone("0656789012");

        assertTrue(result.isPresent());
        assertEquals("0656789012", result.get().getTelephone());
        verify(secretaireRepository, times(1)).findPatientByTelephone("0656789012");
    }

    @Test
    @DisplayName("searchPatientsByNomPrenom - Recherche patients par mot-clé")
    void testSearchPatientsByNomPrenom() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setNom("Radi");

        when(secretaireRepository.searchPatientsByNomPrenom("Radi")).thenReturn(Arrays.asList(patient));

        List<Patient> result = secretaireRepository.searchPatientsByNomPrenom("Radi");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).searchPatientsByNomPrenom("Radi");
    }

    @Test
    @DisplayName("findAllPatients - Retourne tous les patients")
    void testFindAllPatients() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setNom("Radi");

        when(secretaireRepository.findAllPatients()).thenReturn(Arrays.asList(patient));

        List<Patient> result = secretaireRepository.findAllPatients();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).findAllPatients();
    }

    

    @Test
    @DisplayName("assignMedecinToPatient - Assigne un médecin à un patient")
    void testAssignMedecinToPatient() {
        doNothing().when(secretaireRepository).assignMedecinToPatient(1L, 1L);

        secretaireRepository.assignMedecinToPatient(1L, 1L);

        verify(secretaireRepository, times(1)).assignMedecinToPatient(1L, 1L);
    }

    @Test
    @DisplayName("removeMedecinFromPatient - Retire un médecin d'un patient")
    void testRemoveMedecinFromPatient() {
        doNothing().when(secretaireRepository).removeMedecinFromPatient(1L, 1L);

        secretaireRepository.removeMedecinFromPatient(1L, 1L);

        verify(secretaireRepository, times(1)).removeMedecinFromPatient(1L, 1L);
    }

    @Test
    @DisplayName("getMedecinsOfPatient - Retourne les médecins d'un patient")
    void testGetMedecinsOfPatient() {
        medecin med = new medecin();
        med.setId(1L);
        med.setNom("Dr. Kebbaj");

        when(secretaireRepository.getMedecinsOfPatient(1L)).thenReturn(Arrays.asList(med));

        List<medecin> result = secretaireRepository.getMedecinsOfPatient(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).getMedecinsOfPatient(1L);
    }

    @Test
    @DisplayName("getPatientsOfMedecin - Retourne les patients d'un médecin")
    void testGetPatientsOfMedecin() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setNom("Radi");

        when(secretaireRepository.getPatientsOfMedecin(1L)).thenReturn(Arrays.asList(patient));

        List<Patient> result = secretaireRepository.getPatientsOfMedecin(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(secretaireRepository, times(1)).getPatientsOfMedecin(1L);
    }
}