import java.sql.*;
public class Conexão {
    public static Connection createConnection() throws SQLException{
        String url="jdbc:mysql://localhost:3306/bdlojacarlos";
        String user="root";
        String pass="1234";
        Connection conexaoloja = DriverManager.getConnection(url,user,pass);
        return conexaoloja;
    }
}
