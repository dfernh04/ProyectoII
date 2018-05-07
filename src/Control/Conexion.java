package Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

	String BBDDName = "Resource/BBDD.db";
	Connection c = null;
	Statement stmt = null;
	public ResultSet rs = null;
	
	public void consultaMedico(String query) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+BBDDName);
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			rs = stmt.executeQuery(query);
			
			//TREATMENT
				
		}catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		System.out.println("Consulta terminada");
	}
	
	public void closeConnection() {

		try {
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
