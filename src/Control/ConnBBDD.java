package Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Paciente;

public class ConnBBDD {
	
	String nameBBDD = "Resource/BBDD.db";
	public Connection c = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	public String query = "";
	
	public ConnBBDD() {
		System.out.println("Conn Init");
	}
	
	public void conexion() {
		try {
			Class.forName("org.sqlite.JBDC");
			c = DriverManager.getConnection("jbdc:sqlite:" + this.nameBBDD);
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			rs = stmt.executeQuery(query);
			System.out.println(rs.getString("DNI_medico"));
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	
}
