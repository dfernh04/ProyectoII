package Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexionMYSQL {
	
	String BBDDName = "pi2_bd_heartlight2";
	
	public void test() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String bd = "jdbc:mariadb://esp.uem.es:3306/"+ BBDDName;
			String user = "pib2";
			String pw = "pib218";
			
			conn = DriverManager.getConnection(bd,user,pw);
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM Paciente");
			
			while(rs.next()) {
				String nombre = rs.getString("Nombre_paciente");
				String apellidos = rs.getString("Apellidos_paciente");
				String DNI = rs.getString("DNI_paciente");
				String Foto = rs.getString("Foto_paciente");
				String Localidad = rs.getString("Localidad_paciente");
				String Direccion = rs.getString("Direccion_paciente");
				String N_seguridad_social = rs.getString("N_seguridad_social_paciente");
				
				System.out.println(nombre + " " + apellidos);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) {
		ConexionMYSQL c = new ConexionMYSQL();
		c.test();
	}
	
	
	

}
