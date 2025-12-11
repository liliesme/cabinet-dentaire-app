package ma.dentalTech.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import ma.dentalTech.repository.modules.patient.api.PatientRepository;
import ma.dentalTech.repository.modules.medecin.api.MedecinRepository;
import ma.dentalTech.repository.modules.rdv.api.RdvRepository;
import ma.dentalTech.repository.modules.Consultation.api.ConsultationRepository;
import ma.dentalTech.repository.modules.facture.api.FactureRepository;
import ma.dentalTech.repository.modules.Ordonnance.api.OrdonnanceRepository;
import ma.dentalTech.repository.modules.Certificat.api.CertificatRepository;
import ma.dentalTech.repository.modules.DossierMedicale.api.DossierMedicaleRepository;
import ma.dentalTech.repository.modules.SituationFinanciere.api.SituationFinanciereRepository;
import ma.dentalTech.repository.modules.charges.api.ChargesRepository;
import ma.dentalTech.repository.modules.revenues.api.RevenuesRepository;
import ma.dentalTech.repository.modules.utilisateur.api.UtilisateurRepository;
import ma.dentalTech.repository.modules.role.api.RoleRepository;


import ma.dentalTech.service.modules.patient.api.PatientService;
import ma.dentalTech.service.modules.medecin.api.MedecinService;
import ma.dentalTech.service.modules.rdv.api.RdvService;
import ma.dentalTech.service.modules.consultation.api.ConsultationService;
import ma.dentalTech.service.modules.facture.api.FactureService;
import ma.dentalTech.service.modules.ordonnance.api.OrdonnanceService;
import ma.dentalTech.service.modules.certificat.api.CertificatService;
import ma.dentalTech.service.modules.dossierMedical.api.DossierMedicalService;
import ma.dentalTech.service.modules.situationFinanciere.api.SituationFinanciereService;
import ma.dentalTech.service.modules.charges.api.ChargesService;
import ma.dentalTech.service.modules.revenues.api.RevenuesService;
import ma.dentalTech.service.modules.utilisateur.api.UtilisateurService;
import ma.dentalTech.service.modules.auth.api.AuthService;
import ma.dentalTech.service.modules.dashboard.api.DashboardService;


import ma.dentalTech.mvc.controllers.modules.patient.api.PatientController;


public class ApplicationContext {

    private static final Map<Class<?>, Object> context = new HashMap<>();
    private static final Map<String, Object> contextByName = new HashMap<>();

    static {
        Properties properties = loadProperties();

        if (properties != null) {
            try {
                
                PatientRepository patientRepo = createBean(properties, "patientRepo");
                MedecinRepository medecinRepo = createBean(properties, "medecinRepo");
                RdvRepository rdvRepo = createBean(properties, "rdvRepo");
                ConsultationRepository consultationRepo = createBean(properties, "consultationRepo");
                FactureRepository factureRepo = createBean(properties, "factureRepo");
                OrdonnanceRepository ordonnanceRepo = createBean(properties, "ordonnanceRepo");
                CertificatRepository certificatRepo = createBean(properties, "certificatRepo");
                DossierMedicaleRepository dossierMedicalRepo = createBean(properties, "dossierMedicalRepo");
                SituationFinanciereRepository situationFinanciereRepo = createBean(properties, "situationFinanciereRepo");
                ChargesRepository chargesRepo = createBean(properties, "chargesRepo");
                RevenuesRepository revenuesRepo = createBean(properties, "revenuesRepo");
                UtilisateurRepository utilisateurRepo = createBean(properties, "utilisateurRepo");
                RoleRepository roleRepo = createBean(properties, "roleRepo");

                
                PatientService patientService = createBean(properties, "patientService", patientRepo);
                MedecinService medecinService = createBean(properties, "medecinService", medecinRepo);
                RdvService rdvService = createBean(properties, "rdvService", rdvRepo);
                ConsultationService consultationService = createBean(properties, "consultationService", consultationRepo);
                FactureService factureService = createBean(properties, "factureService", factureRepo);
                OrdonnanceService ordonnanceService = createBean(properties, "ordonnanceService", ordonnanceRepo);
                CertificatService certificatService = createBean(properties, "certificatService", certificatRepo);
                DossierMedicalService dossierMedicalService = createBean(properties, "dossierMedicalService", dossierMedicalRepo);
                SituationFinanciereService situationFinanciereService = createBean(properties, "situationFinanciereService", situationFinanciereRepo);
                ChargesService chargesService = createBean(properties, "chargesService", chargesRepo);
                RevenuesService revenuesService = createBean(properties, "revenuesService", revenuesRepo);
                UtilisateurService utilisateurService = createBean(properties, "utilisateurService", utilisateurRepo);

                
                AuthService authService = createBean(properties, "authService", utilisateurRepo, null);

                
                DashboardService dashboardService = createBean(properties, "dashboardService",
                        patientService, rdvService, revenuesService, chargesService);

                
                PatientController patientController = createBean(properties, "patientController", patientService);
                

                
                
                registerBean(PatientRepository.class, "patientRepo", patientRepo);
                registerBean(MedecinRepository.class, "medecinRepo", medecinRepo);
                registerBean(RdvRepository.class, "rdvRepo", rdvRepo);
                registerBean(ConsultationRepository.class, "consultationRepo", consultationRepo);
                registerBean(FactureRepository.class, "factureRepo", factureRepo);
                registerBean(OrdonnanceRepository.class, "ordonnanceRepo", ordonnanceRepo);
                registerBean(CertificatRepository.class, "certificatRepo", certificatRepo);
                registerBean(DossierMedicaleRepository.class, "dossierMedicalRepo", dossierMedicalRepo);
                registerBean(SituationFinanciereRepository.class, "situationFinanciereRepo", situationFinanciereRepo);
                registerBean(ChargesRepository.class, "chargesRepo", chargesRepo);
                registerBean(RevenuesRepository.class, "revenuesRepo", revenuesRepo);
                registerBean(UtilisateurRepository.class, "utilisateurRepo", utilisateurRepo);
                registerBean(RoleRepository.class, "roleRepo", roleRepo);

                
                registerBean(PatientService.class, "patientService", patientService);
                registerBean(MedecinService.class, "medecinService", medecinService);
                registerBean(RdvService.class, "rdvService", rdvService);
                registerBean(ConsultationService.class, "consultationService", consultationService);
                registerBean(FactureService.class, "factureService", factureService);
                registerBean(OrdonnanceService.class, "ordonnanceService", ordonnanceService);
                registerBean(CertificatService.class, "certificatService", certificatService);
                registerBean(DossierMedicalService.class, "dossierMedicalService", dossierMedicalService);
                registerBean(SituationFinanciereService.class, "situationFinanciereService", situationFinanciereService);
                registerBean(ChargesService.class, "chargesService", chargesService);
                registerBean(RevenuesService.class, "revenuesService", revenuesService);
                registerBean(UtilisateurService.class, "utilisateurService", utilisateurService);
                registerBean(AuthService.class, "authService", authService);
                registerBean(DashboardService.class, "dashboardService", dashboardService);

                
                registerBean(PatientController.class, "patientController", patientController);

                System.out.println("✅ ApplicationContext initialisé avec succès !");

            } catch (Exception e) {
                System.err.println("❌ Erreur lors de l'initialisation du contexte :");
                e.printStackTrace();
            }
        } else {
            System.err.println("❌ Erreur : Le fichier beans.properties est introuvable !");
        }
    }

    
    private static Properties loadProperties() {
        var configFile = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("config/beans.properties");

        if (configFile != null) {
            Properties properties = new Properties();
            try {
                properties.load(configFile);
                return properties;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    
    @SuppressWarnings("unchecked")
    private static <T> T createBean(Properties properties, String key) throws Exception {
        String className = properties.getProperty(key);
        Class<?> clazz = Class.forName(className);
        return (T) clazz.getDeclaredConstructor().newInstance();
    }

    
    @SuppressWarnings("unchecked")
    private static <T> T createBean(Properties properties, String key, Object dependency) throws Exception {
        String className = properties.getProperty(key);
        Class<?> clazz = Class.forName(className);
        return (T) clazz.getDeclaredConstructor(dependency.getClass().getInterfaces()[0])
                .newInstance(dependency);
    }

    
    @SuppressWarnings("unchecked")
    private static <T> T createBean(Properties properties, String key, Object dep1, Object dep2) throws Exception {
        String className = properties.getProperty(key);
        Class<?> clazz = Class.forName(className);

        
        if (dep2 == null) {
            return (T) clazz.getDeclaredConstructor(
                    dep1.getClass().getInterfaces()[0],
                    ma.dentalTech.entities.pat.Utilisateur.class
            ).newInstance(dep1, null);
        }

        throw new UnsupportedOperationException("Constructeur à 2 paramètres non supporté pour ce type");
    }

    
    @SuppressWarnings("unchecked")
    private static <T> T createBean(Properties properties, String key,
                                    Object dep1, Object dep2, Object dep3, Object dep4) throws Exception {
        String className = properties.getProperty(key);
        Class<?> clazz = Class.forName(className);

        return (T) clazz.getDeclaredConstructor(
                dep1.getClass().getInterfaces()[0],
                dep2.getClass().getInterfaces()[0],
                dep3.getClass().getInterfaces()[0],
                dep4.getClass().getInterfaces()[0]
        ).newInstance(dep1, dep2, dep3, dep4);
    }

    
    private static <T> void registerBean(Class<T> type, String name, T instance) {
        context.put(type, instance);
        contextByName.put(name, instance);
    }

    
    public static Object getBean(String beanName) {
        return contextByName.get(beanName);
    }

    
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> beanClass) {
        return (T) context.get(beanClass);
    }

    
    public static void printBeans() {
        System.out.println("\n========== BEANS ENREGISTRÉS ==========");
        contextByName.forEach((name, bean) ->
                System.out.println("  • " + name + " → " + bean.getClass().getSimpleName())
        );
        System.out.println("=======================================\n");
    }
}