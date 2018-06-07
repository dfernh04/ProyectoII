package Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import Model.Medico;
import Model.Paciente;
import Model.PacienteTecnico;
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
		
	}
	/**
	 * Metodo que nos permite obtener un objeto del tipo medico con su nombre de usuario
	 * n de colegiado y dni, a traves de una consulta sql.
	 * 
	 * @param us Usuario 
	 * 
	 * @return m Medico 
	 */
	public static  Medico queryMedico(Usuario us){
		
		Medico m= new Medico(us,0,"");
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+BBDDName);
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select Numero_afiliacion_medico,DNI_medico "
					+ "from Medico" + 
					" where Username_medico = " + us.getUser() + ";");
			if (rs.next()) {
				int nC = rs.getInt("Numero_afiliacion_medico");
				String nT = rs.getString("DNI_medico");
				
				m = new Medico(us,nC,nT);
			}
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return m;
		
	}
	/**
	 * Metodo que nos permite obtener un arraylis tcon objetos del tipo paciente
	 * a traves de una consulta sql
	 * 
	 * @param us Medico 
	 * 
	 * @return m {@link ArrayList<Paciente>} 
	 */
	static public ArrayList<Paciente> queryPacMedico(Medico m) {
		ArrayList<Paciente> pac=new ArrayList<Paciente>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+BBDDName);
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select Nombre_paciente, Apellidos_paciente, DNI_paciente, Localidad_paciente,Direccion_paciente,N_seguridad_social_paciente,Username_medico" + 
					"from Paciente\r\n" + 
					"join Medico on Username_medico = Paciente.Username_medico"
					+ " where DNI_medico = "+m.getDni()+" ;");
			while (rs.next()) {
				String dni = rs.getString("DNI_paciente");
				System.out.println(dni);
				int nss = rs.getInt("N_seguridad_social_paciente");
				String ape=rs.getString("Apellido_paciente");
				String nombre = rs.getString("Nombre_paciente");
				String ubicacion=rs.getString("Direccion_paciente");
				String ss = Integer.toString(nss);
				pac.add(new Paciente(nombre,ape,dni,ubicacion,ss));//añadir nss, cambiar en el constructor!
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return pac;
	}
	/**
	 * Metodo que nos permite obtener un arraylist con todos los pacientes del sistema
	 * pensando que el tecnico tiene disponibkes todos los pacientes.
	 * 
	 * @param
	 * 
	 * @return arraylis<PacienteTecnico>
	 */
	//public Paciente(String id,String nombre,String apellido,String dni)
	static public ArrayList<PacienteTecnico> queryPacTecnico() {
		ArrayList<PacienteTecnico> pac=new ArrayList<PacienteTecnico>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+BBDDName);
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DNI_paciente,Nombre_paciente,Apellidos_paciente,Direccion_paciente FROM Paciente;");
			while (rs.next()) {
				String dni = rs.getString("DNI_paciente");
				String nombre = rs.getString("Nombre_paciente");
				String ape = rs.getString("Apellidos_paciente");
				String ubicacion=rs.getString("Direccion_paciente");
				pac.add(new PacienteTecnico(dni,ape,ubicacion,nombre));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		for(int i=0; i < pac.size();++i)
			System.out.println(pac.get(i).getId());
		return pac;
	}
	
	static public Vector<Usuario> queryUsuarios(){
		Vector<Usuario> users=new Vector<Usuario>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+BBDDName);
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario;");
			while (rs.next()) {
				String nick = rs.getString("Usuario");
				String con=rs.getString("Password");
				String rol=rs.getString("Role");
				
				users.add(new Usuario(nick,rol,con));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return users;
	}
	static public Usuario consultaLogin(String nom, String pass) {
		Usuario a=new Usuario(null,null,null);
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+BBDDName);
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario where Usuario like '"+nom+"';");
			if (rs.next()) {
				String nombre = rs.getString("Usuario");
				String con = rs.getString("Password");
				String rol=rs.getString("Role");
				a=new Usuario(nombre,rol,con);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		System.out.println("Consulta terminada Usuario: " + a.getUser());
		return a;
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
	public void addPaciente(String query) {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+BBDDName);
			//c.setAutoCommit(false);
			
			stmt = c.createStatement();
			stmt.executeQuery(query);
			stmt.close();
			
			//TREATMENT
				
		}catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		
	}
	
	public void poblarBBDD() {
		//Creamos las tablas
		
		String tablaAdministrador = "CREATE TABLE Administrador" +
									"(username_admin TEXT PRIMARY KEY NOT NULL,"+
									"Apellidos_admin TEXT NOT NULL"+
									"Contrase�a_admin TEXT NOT NULL"+
									"Email_admin TEXT NOT NULL"+
									"DNI_admin TEXT NOT NULL"+
									"Nombre_admin TEXT NOT NULL)";

		
		String tablaTecnico= "CREATE TABLE Tecnico" +
				"(Username_tecnico TEXT PRIMARY KEY NOT NULL,"+
				"Apellidos_tecnico TEXT NOT NULL"+
				"Contrase�a_tecnico TEXT NOT NULL"+
				"Email_tecnico TEXT NOT NULL"+
				"DNI_tecnico TEXT NOT NULL"+
				"Nombre_tecnico TEXT NOT NULL)";
		
		String tablaMedico = "CREATE TABLE Medico" +
				"(Username_medico TEXT PRIMARY KEY NOT NULL,"+
				"Apellidos_medico TEXT NOT NULL"+
				"Contrasena_medico TEXT NOT NULL"+
				"Email_medico TEXT NOT NULL"+
				"DNI_medico TEXT NOT NULL"+
				"Nombre_medico TEXT NOT NULL"+
				"Numero_afiliacion_medico TEXT NOT NULL" + 
				"Hospital_medico TEXT NOT NULL)";
		
		String tablaPaciente = "CREATE TABLE Paciente" +
				"(DNI_paciente TEXT PRIMARY KEY NOT NULL,"+
				"Apellidos_paciente TEXT NOT NULL"+
				"Localidad TEXT"+
				"Direccion TEXT"+
				"DNI_paciente TEXT NOT NULL"+
				"Nombre_paciente TEXT NOT NULL"+
				"Foto_paciente TEXT "+
				"Username_medico TEXT NOT NULL"+
				"N_seguridad_social_paciente INTEGER NOT NULL";
		
		String tablaECG = "CREATE TABLE ECG" +
				"(ID_ECG TEXT PRIMARY KEY AUTOINCREMENT,"+
				"Duracion INTEGER NOT NULL"+
				"Diagnostico TEXT NOT NULL"+
				"Frecuencia INTEGER NOT NULL"+
				"DNI_paciente TEXT NOT NULL"+
				"Username_tecnico TEXT NOT NULL"+
				"Dato BLOB NOT NULL" +
				"Pulsaciones INTEGER NOT NULL" +
				"Fecha TEXT NOT NULL)";

	}
	
}
