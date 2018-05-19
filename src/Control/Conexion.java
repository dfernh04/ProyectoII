package Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Medico;
import Model.Usuario;

public class Conexion {

	static String BBDDName = "Resource/BBDD2.db";
	static Connection c = null;
	static Statement stmt = null;
	public ResultSet rs = null;
	
	public void consulta(String query) {
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
	public static  Medico consultaMed(Usuario us){
		Medico m= new Medico(us,0,0);
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+BBDDName);
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select Medico.nColegiado,Medico.nTelefono "
					+ "from Medico" + 
					" where Username_medico = " + us.getUser() + ";");
			if (rs.next()) {
				int nC = rs.getInt("nColegiado");
				int nT = rs.getInt("nTelefono");
				
				m = new Medico(us,nC,nT);
			}
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return m;
		
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
	public ResultSet getRs() {
		return rs;
	}
	
}
