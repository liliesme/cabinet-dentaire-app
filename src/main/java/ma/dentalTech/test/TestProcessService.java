package ma.dentalTech.test;

import ma.dentalTech.entities.pat.*;
import ma.dentalTech.repository.modules.patient.impl.mySQL.PatientRepositoryImpl;
import ma.dentalTech.repository.modules.DossierMedicale.impl.mySQL.DossierMedicaleRepositoryImpl;
import ma.dentalTech.repository.modules.rdv.impl.mySQL.RdvRepositoryImpl;
import ma.dentalTech.repository.modules.Consultation.impl.mySQL.ConsultationRepositoryImpl;
import ma.dentalTech.repository.modules.Ordonnance.impl.mySQL.OrdonnanceRepositoryImpl;
import ma.dentalTech.repository.modules.Prescription.impl.mySQL.PrescriptionRepositoryImpl;
import ma.dentalTech.repository.modules.Certificat.impl.mySQL.CertificatRepositoryImpl;
import ma.dentalTech.repository.modules.facture.impl.mySQL.FactureRepositoryImpl;
import ma.dentalTech.repository.modules.SituationFinanciere.impl.mySQL.SituationFinanciereRepositoryImpl;
import ma.dentalTech.repository.modules.patient.impl.mySQL.AntecedentRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestProcessService {

    private final PatientRepositoryImpl patientRepo = new PatientRepositoryImpl();
    private final DossierMedicaleRepositoryImpl dossierRepo = new DossierMedicaleRepositoryImpl();
    private final RdvRepositoryImpl rdvRepo = new RdvRepositoryImpl();
    private final ConsultationRepositoryImpl consultationRepo = new ConsultationRepositoryImpl();
    private final OrdonnanceRepositoryImpl ordonnanceRepo = new OrdonnanceRepositoryImpl();
    private final PrescriptionRepositoryImpl prescriptionRepo = new PrescriptionRepositoryImpl();
    private final CertificatRepositoryImpl certificatRepo = new CertificatRepositoryImpl();
    private final FactureRepositoryImpl factureRepo = new FactureRepositoryImpl();
    private final SituationFinanciereRepositoryImpl situationRepo = new SituationFinanciereRepositoryImpl();
    private final AntecedentRepositoryImpl antecedentRepo = new AntecedentRepositoryImpl();

    public void run() {
        System.out.println(" DÉBUT DES TESTS \n");

        System.out.println(" 1. Récupération des patients existants");
        List<Patient> patients = patientRepo.findAll();
        System.out.println("   Patients dans la base de données : ");
        patients.forEach(p -> System.out.println("   → ID: " + p.getId() + " | " + p.getNom() + " " + p.getPrenom()));
        System.out.println();

        System.out.println(" 2.  Ajout d'un nouveau patient");
        Patient p = new Patient();
        p.setNom("Ouasmi");
        p.setPrenom("Ali");
        p.setAdresse("Casablanca");
        p.setEmail("ouasmiali@cabinet.ma");
        p.setTelephone("0677889900");
        p.setDateNaissance(LocalDate.of(1990, 2, 2));
        p.setDateCreation(LocalDateTime.now());
        patientRepo.create(p);
        System.out.println(" Patient créé avec succès | ID = " + p.getId());
        System.out.println();

        // 3. Modifier le patient
        System.out.println(" 3. MODIFICATION - Mise à jour du téléphone...");
        String ancienTel = p.getTelephone();
        p.setTelephone("0707070707");
        patientRepo.update(p);
        System.out.println("  Téléphone modifié : " + ancienTel + " → " + p.getTelephone());
        System.out.println();

        System.out.println("4. CRÉATION d un Dossier médical");
        DossierMedicale dossier = new DossierMedicale();
        dossier.setPatientId(p.getId());
        dossier.setDateCreation(LocalDateTime.now());
        dossierRepo.create(dossier);
        System.out.println("Dossier médical créé | ID = " + dossier.getId());
        System.out.println();

        System.out.println(" 5. CRÉATION - Rendez-vous");
        RDV rdv = new RDV();
        rdv.setPatientId(p.getId());
        rdv.setMedecinId(1L);
        rdv.setMotif("Consultation de test");
        rdv.setDateHeure(LocalDateTime.now().plusDays(1));
        rdvRepo.create(rdv);
        System.out.println(" Rendez-vous créé | ID = " + rdv.getId());
        System.out.println();

        System.out.println("6. CRÉATION de Consultation ");
        Consultation cons = new Consultation();
        cons.setPatientId(p.getId());
        cons.setUtilisateurId(1L);
        cons.setDateConsultation(LocalDateTime.now());
        cons.setDiagnostic("Diagnostic de test");
        consultationRepo.create(cons);
        System.out.println(" Consultation créée | ID = " + cons.getId());
        System.out.println();

        System.out.println(" 7. CRÉATION d Ordonnance");
        Ordonnance ord = new Ordonnance();
        ord.setPatientId(p.getId());
        ord.setUtilisateurId(1L);
        ord.setDateOrdonnance(LocalDateTime.now());
        ordonnanceRepo.create(ord);
        System.out.println("  Ordonnance créée | ID = " + ord.getId());
        System.out.println();

        System.out.println("8. CRÉATION de Prescription ");
        Prescription pr = new Prescription();
        pr.setPatientId(p.getId());
        pr.setUtilisateurId(1L);
        pr.setDatePrescription(LocalDateTime.now());
        prescriptionRepo.create(pr);
        System.out.println(" Prescription créée | ID = " + pr.getId());
        System.out.println();

        System.out.println(" 9. CRÉATION de Facture ");
        Facture f = new Facture();
        f.setPatientId(p.getId());
        f.setDateFacture(LocalDateTime.now());
        f.setMontantTotal(200.0);
        f.setTotalPaye(0.0);
        f.setEstPayee(false);
        factureRepo.create(f);
        System.out.println(" Facture créée | ID = " + f.getId() + " | Montant = " + f.getMontantTotal() + " DH");
        System.out.println();

        System.out.println("10. CRÉATION - Situation financière...");
        SituationFinanciere sf = new SituationFinanciere();
        sf.setPatientId(p.getId());
        sf.setTotalDesActes(200.0);
        sf.setTotalePaye(0.0);
        situationRepo.create(sf);
        System.out.println(" Situation financière créée | ID = " + sf.getId());
        System.out.println();

        System.out.println("11. SUPPRESSION  du patient ");
        patientRepo.deleteById(p.getId());
        System.out.println("Patient supprimé avec succès");
        System.out.println();

        System.out.println(" FIN DES TESTS ");
        System.out.println(" Tous les tests ont été exécutés avec succès !");
    }
}
