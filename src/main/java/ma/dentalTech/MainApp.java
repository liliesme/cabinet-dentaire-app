package ma.dentalTech;

import ma.dentalTech.conf.ApplicationContext;
import ma.dentalTech.service.modules.patient.api.PatientService;
import ma.dentalTech.service.modules.medecin.api.MedecinService;
import ma.dentalTech.service.modules.rdv.api.RdvService;
import ma.dentalTech.service.modules.facture.api.FactureService;
import ma.dentalTech.service.modules.auth.api.AuthService;
import ma.dentalTech.service.modules.dashboard.api.DashboardService;
import ma.dentalTech.test.TestProcessService;


public class MainApp {

    public static void main(String[] args) {
        System.out.println(" Démarrage de DentalTech...\n");

        
        ApplicationContext.printBeans();

        
        System.out.println(" Test PatientService...");
        PatientService patientService = ApplicationContext.getBean(PatientService.class);
        System.out.println("  PatientService récupéré : " + patientService.getClass().getSimpleName());

        
        System.out.println("\n Test MedecinService...");
        MedecinService medecinService = ApplicationContext.getBean(MedecinService.class);
        System.out.println("  ✅ MedecinService récupéré : " + medecinService.getClass().getSimpleName());

        
        System.out.println("\n  Test RdvService...");
        RdvService rdvService = ApplicationContext.getBean(RdvService.class);
        System.out.println("  ✅ RdvService récupéré : " + rdvService.getClass().getSimpleName());

        
        System.out.println("\n  Test FactureService...");
        FactureService factureService = ApplicationContext.getBean(FactureService.class);
        System.out.println("  ✅ FactureService récupéré : " + factureService.getClass().getSimpleName());

        
        System.out.println("\n Test AuthService...");
        AuthService authService = ApplicationContext.getBean(AuthService.class);
        System.out.println("  ✅ AuthService récupéré : " + authService.getClass().getSimpleName());

        
        System.out.println("\n Test DashboardService...");
        DashboardService dashboardService = ApplicationContext.getBean(DashboardService.class);
        System.out.println("  ✅ DashboardService récupéré : " + dashboardService.getClass().getSimpleName());

        System.out.println("\n✅ Tous les tests d'injection ont réussi !");
        System.out.println(" DentalTech est prêt à être utilisé !\n");
        TestProcessService testProcess = new TestProcessService();
        testProcess.run();
    }
}