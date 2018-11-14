package othello.dbConnection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.*;

public class DbConnection {
    private static Connection conn = null;
    private static String connURL = "";
    private static String user = "";
    private static String pass = "";

    private static void initConfig() {
        try {
            InitialContext initialContext = new InitialContext();
            connURL = "jdbc:sqlserver://DESKTOP-LAGF36K\\SQLEXPRESS:1433;databaseName=SWD_Project;integratedSecurity=true";
            user = "sa";
            pass = "sa";
//            Context environmentContext = (Context) initialContext.lookup("java:/comp/env");
//            connURL = (String) environmentContext.lookup("myConnURL");
//            user = (String) environmentContext.lookup("dbUser");
//            pass = (String) environmentsaContext.lookup("dbPass");
        } catch (NamingException e) {
            System.out.println("Cannot read config");
            System.out.println(e.getMessage());
        }
    }

    public static void closeConn(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
    public static void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
    }
    public static void closePrepareStatement(PreparedStatement ps) throws SQLException {
        if (ps != null && !ps.isClosed()) {
            ps.close();
        }
    }

    public static Connection connToMssql() throws SQLException, ClassNotFoundException {
//        String url = "jdbc:sqlserver://"+serverName+":"+portNumber +";databaseName="+dbName;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        initConfig();
        return DriverManager.getConnection(connURL, user, pass);
    }

}
