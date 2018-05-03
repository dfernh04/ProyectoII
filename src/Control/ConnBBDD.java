package Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Paciente;


/*
 * 		1: Consulta Pacientes
 * 		2: Consulta Medicos
 * 		3: Consulta Tecnicos
 * 		4: Consulta ECG
 * 		5: Consulta username Medico
 */
public class ConnBBDD {
	
	String nameBBDD = "Resource/BBDD.db";
	public Connection c = null;
	public Statement stmt = null;
	
	public ConnBBDD() {
		System.out.println("Conn Init");
		this.conexion();
	}
	
	public void conexion() {
		try {
			Class.forName("org.sqlite.JBDC");
			c = DriverManager.getConnection("jbdc:sqlite:" + this.nameBBDD);
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			
		}catch(Exception e) {
			System.out.println("Error");
		}

		
	}
	
	
}
