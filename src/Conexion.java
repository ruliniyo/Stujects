import java.sql.*;


public class Conexion {

	private static Connection cnx = null;
	   public static Connection obtener() throws SQLException, ClassNotFoundException {
	      if (cnx == null) {
	         try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            cnx = DriverManager.getConnection("jdbc:mysql://localhost/agenda", "root", "manolo");
	         } catch (SQLException ex) {
	            throw new SQLException(ex);
	         } catch (ClassNotFoundException ex) {
	            throw new ClassCastException(ex.getMessage());
	         }
	      }
	      return cnx;
	   }
	   public static void cerrar() throws SQLException {
	      if (cnx != null) {
	         cnx.close();
	      }
	   }
	
	
}
