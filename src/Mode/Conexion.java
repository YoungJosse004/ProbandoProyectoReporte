package Mode;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexion {
    private static String base = "POSV";
    private static String user = "sa";
    private static String password = "123";
    private static String url = "jdbc:sqlserver://localhost:1433;databaseName=" + base+ ";encrypt=true"+ ";trustServerCertificate=true";

    public static Connection getConexion() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion Exitosa");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error en la conexión: " + e);
            e.printStackTrace();
        }
        return conn;
    }
}
