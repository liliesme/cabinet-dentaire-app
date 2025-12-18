package ma.dentalTech.repository.test;

import ma.dentalTech.conf.ApplicationContext;

import ma.dentalTech.entities.enums.StatutRendezVous;
import ma.dentalTech.entities.pat.Patient;
import ma.dentalTech.entities.pat.medecin;
import ma.dentalTech.entities.pat.RDV;
import ma.dentalTech.entities.pat.Facture;

import ma.dentalTech.repository.modules.patient.api.PatientRepository;
import ma.dentalTech.repository.modules.medecin.api.MedecinRepository;
import ma.dentalTech.repository.modules.patient.impl.mySQL.PatientRepositoryImpl;
import ma.dentalTech.repository.modules.rdv.api.RdvRepository;
import ma.dentalTech.repository.modules.facture.api.FactureRepository;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class TestRepo {

    private ApplicationContext context;

    private PatientRepository patientRepository;
    private MedecinRepository medecinRepository;
    private RdvRepository rdvRepository;
    private FactureRepository factureRepository;

    public TestRepo() {
        ApplicationContext context = new ApplicationContext();

        patientRepository = context.getBean(PatientRepository.class);
        medecinRepository = context.getBean(MedecinRepository.class);
        rdvRepository = context.getBean(RdvRepository.class);
        factureRepository = context.getBean(FactureRepository.class);

    }


    void insertProcess() {

        // 1. Patient
        Patient patient = new Patient();
        patient.setNom("Test");
        patient.setPrenom("Patient");
        patientRepository.create(patient);

        // 2. Medecin
        medecin medecin = new medecin();
        medecin.setNom("Test");
        medecin.setPrenom("Medecin");
        medecinRepository.create(medecin);


        RDV rdv = new RDV();
        rdv.setDateHeure(LocalDateTime.now());
        rdv.setPatientId(patient.getId());
        rdv.setMedecinId(medecin.getId());
        rdv.setMotif("Consultation");
        rdv.setStatut(StatutRendezVous.PLANIFIE);

        rdvRepository.create(rdv);


        Facture facture = new Facture();

        facture.setMontantTotal(200.00);
        facture.setPatientId(rdv.getPatientId());
        factureRepository.create(facture);

        System.out.println("INSERT PROCESS OK");
    }


    void updateProcess() {


        PatientRepository patientRepository =
                new PatientRepositoryImpl();

        Optional<Patient> opt = patientRepository.findById(1L);
        if (opt.isPresent()) {
            Patient p = opt.get();
            p.setPrenom("Patient_Modified");
            patientRepository.update(p);
        }

        System.out.println("UPDATE PROCESS OK");
    }


    void deleteProcess() {

        Optional<Patient> opt = patientRepository.findById(1L);
        opt.ifPresent(patientRepository::delete);

        System.out.println("DELETE PROCESS OK");
    }

    void selectProcess() {

        patientRepository.findAll().forEach(p ->
                System.out.println(p.getId() + " - " + p.getNom())
        );

        System.out.println("SELECT PROCESS OK");
    }

    public static void main(String[] args) {

        TestRepo testRepo = new TestRepo();

        testRepo.insertProcess();
        testRepo.selectProcess();
        testRepo.updateProcess();
        testRepo.selectProcess();
        testRepo.deleteProcess();
        testRepo.selectProcess();
    }
}