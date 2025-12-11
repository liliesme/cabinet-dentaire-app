package ma.dentalTech.conf;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import ma.dentalTech.conf.util.PropertiesExtractor;


public final class SessionFactory {

    
    private static volatile SessionFactory INSTANCE;

    
    private Connection connection;

    
    private static final String PROPS_PATH = "config/db.properties";
    private static final String URL_KEY    = "datasource.url";
    private static final String USER_KEY   = "datasource.user";
    private static final String PASS_KEY   = "datasource.password";
    private static final String DRIVER_KEY = "datasource.driver";

    
    private final String url;
    private final String user;
    private final String password;
    private final String driver;

    
    private SessionFactory() {
        var properties = PropertiesExtractor.loadConfigFile(PROPS_PATH);

        this.url      = PropertiesExtractor.getPropertyValue(URL_KEY, properties);
        this.user     = PropertiesExtractor.getPropertyValue(USER_KEY, properties);
        this.password = PropertiesExtractor.getPropertyValue(PASS_KEY, properties);
        this.driver   = PropertiesExtractor.getPropertyValue(DRIVER_KEY, properties);

        
        if (driver != null && !driver.isBlank()) {
            try {
                Class.forName(driver);
                System.out.println(" Driver JDBC chargé avec succès : " + driver);
            } catch (ClassNotFoundException e) {
                System.err.println(" Driver JDBC introuvable : " + driver);
            }
        }
    }



    
    public static SessionFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (SessionFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SessionFactory();
                }
            }
        }
        return INSTANCE;
    }



    
    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed() || !isValid(connection)) {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println(" Nouvelle connexion JDBC établie avec succès !");
        }
        return connection;
    }
    
    private boolean isValid(Connection conn) {
        try {
            return conn != null && conn.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }



    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion JDBC fermée proprement.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }


}
