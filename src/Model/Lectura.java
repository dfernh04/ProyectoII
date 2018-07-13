				package Model;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JOptionPane;

import Control.Conexion;

import java.io.File;
import java.io.FileReader;

/**
 * Lectura es una clase la cual contiene una serie de metodos que nos 
 * permitiran obtener informacion de los archivos de texto plano
 * como los medicos y de estos sus pacientes y de cada paciente 
 * sus electrocardiogramas
 * 
 * @author Heartlight
 * 
 * @version Final 
 */
public class Lectura{
	
	@SuppressWarnings("unused")
	private Vector<ECG> ecgs;
	Conexion c = new Conexion();
	Conexion c3 = new Conexion();
	/**
	 * Metodo que nos permite leer aquellos electrocardiogramas 
	 * que ya hallan sido leidos y guardarlos para poder operar 
	 * con ellos en otras clases
	 * 
	 * @param f File 
	 * @return ECG
	 *
	public static ECG lecturaEcgYaGuardado(File f) {
		int pun=0;
		int fecha=0;
		String comen="";
		String diag="";
		String tec="";
		boolean lei=false;
		String[] puntos=new String[0];
		try(Scanner br = new Scanner(new FileReader (f))) {
			fecha=Integer.parseInt(br.nextLine());
			if(br.nextLine().equals("leido")) {
				lei=true;
			}
			tec=br.nextLine();
			pun=Integer.parseInt(br.nextLine());
			puntos=br.nextLine().split(";");
			if(br.hasNextLine()) {
				comen=br.nextLine();
			}
			if(br.hasNextLine()) {
				diag=br.nextLine();
			}
			
			
		}
			catch(Exception e){
				//e.printStackTrace();
			}
		Vector<Double> a=new Vector<Double>();
		for(int i=0;i<puntos.length;i++){
			//System.out.println(puntos[i]);
			a.add(Double.parseDouble(puntos[i]));
		}
		
		return new ECG(fecha,tec,comen,diag,pun,f.getName().replaceAll(".txt", ""),a,lei);
		}
	
	/**
	 * Metodo que nos permite obtener de un arhivo de texto plano que contiene un paciente
	 * los electrocardiogramas de dicho paciente para poder operar con ellos posteriormente
	 * en otros metodos o en otras clases.
	 * 
	 * @param f File 
	 * @param pacien PacienteTecnico 
	 * @param b String 
	 * 
	 * @return aux Object[] 
	 */
	public static Object[] lecturaEcg(File f, PacienteTecnico pacien, String b) {
		Object[] aux=new Object[2];
		Calendar c= Calendar.getInstance();
		String no="ECG_"+Integer.toString(c.get(Calendar.YEAR))+""+Integer.toString(c.get(Calendar.MONTH))+""+Integer.toString(c.get(Calendar.DATE))+"_"+pacien.getId()+"_"+b;
		int pun=-1;
		String[] puntos=new String[0];
		File archivo = f;
		try {
			BufferedReader br = new BufferedReader(new FileReader (archivo));
			pun=Integer.parseInt(br.readLine());
			puntos=br.readLine().split(";");

			Vector<Double> a=new Vector<Double>();
			for(int i=0;i<puntos.length;i++){
				System.out.println(puntos[i]);
				a.add(Double.parseDouble(puntos[i]));
			}
			aux[1]=new ECG(pun,no,a);
			aux[0]=no;
			return aux;
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "El archivo no tiene el formato correcto");
		}
		aux[1]=new ECG(0,"",new Vector<Double>());
		aux[0]="";
		return aux;
	}
	
	/**
	 * Metodo que nos permite leer de un archivo plano el contenido de un medico, 
	 * a partir de este obtenemos los pacientes de dicho medico y finalmente
	 * obtenemos los electrocardiogramas de cada paciente.
	 * 
	 * @param us Usuario 
	 * 
	 * @return m Medico 
	 */
	public  Medico lectura_medico(Usuario us) {
		Medico m = new Medico();
		try {
			c.consulta("SELECT * FROM Medico WHERE Medico.Username_medico = '" +us.getUser()+"';");
			while(c.rs.next()) {
				String nombre = c.rs.getString("Nombre_medico");
				String apellidos = c.rs.getString("Apellidos_medico");
				String username = c.rs.getString("Username_medico");
				String DNI = c.rs.getString("DNI_medico");
				String hospital = c.rs.getString("Hospital_medico");
				int numero = c.rs.getInt("Numero_afiliacion_medico");
				
				
				m = new Medico(nombre,apellidos,username,DNI,
						0, hospital,numero,null);
				m.setPacientes(getPacientes(us));
			}
			c.rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public ArrayList<Paciente> getPacientes(Usuario us) {
		Paciente p;
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		Conexion c2 = new Conexion();
		
		try {
			c2.consulta("SELECT * FROM Paciente WHERE Paciente.Username_medico like '" +us.getUser()+"';");
			while(c2.rs.next()) {
				String nombre = c2.rs.getString("Nombre_paciente");
				String apellidos = c2.rs.getString("Apellidos_paciente");
				String DNI = c2.rs.getString("DNI_paciente");
				@SuppressWarnings("unused")
				String Foto = c2.rs.getString("Foto_paciente");
				String Localidad = c2.rs.getString("Localidad_paciente");
				String Direccion = c2.rs.getString("Direccion_paciente");
				String N_seguridad_social = c2.rs.getString("N_seguridad_social_paciente");
				
				p = new Paciente(nombre,apellidos,DNI,N_seguridad_social,Localidad,
						 Direccion,"Sin foto",null,null,null);
				p.setEcgs(getECGS(p));
				pacientes.add(p);
			}
			c2.rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pacientes;
	}
	
	

	public Vector<ECG> getECGS(Paciente p) {
		Vector<ECG> ecgs = new Vector<ECG>();
		try {
			c3.consulta("SELECT * FROM ECG WHERE ECG.DNI_paciente = '" + p.getDni()+ "';");
			while(c3.rs.next()) {
				if (c3.rs.getString("ID_ECG") != null) {
					String[] puntos=new String[0];
					String fecha = c3.rs.getString("Fecha");
					String nombreTecnico = c3.rs.getString("Username_tecnico");
					String diagnostico = c3.rs.getString("Diagnostico");
					int frecuencia = c3.rs.getInt("Frecuencia");
					String nombre = c3.rs.getString("ID_ECG");
					String datos = c3.rs.getString("Dato");
					//Creamos los puntos en forma de Vector de Double
					puntos = datos.split(";");
					Vector<Double> a=new Vector<Double>();
					for(int i=0;i<puntos.length;i++) {
						a.add(Double.parseDouble(puntos[i]));
					}	
					ecgs.add(new ECG(fecha,nombreTecnico,null,diagnostico,frecuencia,nombre,a));
				}

			}
			c3.closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ecgs;
	}
	
	public ArrayList<Paciente> getPacientesMedico(Medico m) {
		Paciente p;
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		Conexion c2 = new Conexion();
		
		try {
			c2.consulta("SELECT * FROM Paciente WHERE Paciente.Username_medico like '" +m.getUsername()+"';");
			while(c2.rs.next()) {
				String nombre = c2.rs.getString("Nombre_paciente");
				String apellidos = c2.rs.getString("Apellidos_paciente");
				String DNI = c2.rs.getString("DNI_paciente");
				@SuppressWarnings("unused")
				String Foto = c2.rs.getString("Foto_paciente");
				String Localidad = c2.rs.getString("Localidad_paciente");
				String Direccion = c2.rs.getString("Direccion_paciente");
				String N_seguridad_social = c2.rs.getString("N_seguridad_social_paciente");
				
				p = new Paciente(nombre,apellidos,DNI,N_seguridad_social,Localidad,
						 Direccion,"Sin foto",null,null,null);
				p.setEcgs(getECGS(p));
				pacientes.add(p);
			}
			c2.rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pacientes;
	}
	 public Vector<Mensaje> consultarMensajes(Paciente pac){
		Vector<Mensaje> men=new Vector<Mensaje>();
		Conexion c6 = new Conexion();
		int i=0;
		try {
			c6.consulta("SELECT * FROM Mensaje where DNI_paciente='"+pac.getDni()+"' order by fecha desc;");
			while (c6.rs.next()) {
				men.add(new Mensaje(c6.rs.getInt("ID_Mensaje"),c6.rs.getString("Username_medico"),c6.rs.getString("Username_tecnico"),c6.rs.getString("DNI_Paciente"),c6.rs.getString("MensajeT"),c6.rs.getString("Fecha"),null));
				i++;
			}
			System.out.println("se han leido "+i+" mensajes");
			c6.closeConnection();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return men;
	}
	 public Vector<Mensaje> consultarMensajes(PacienteTecnico pac){
			Vector<Mensaje> men=new Vector<Mensaje>();
			Conexion c4 = new Conexion();
			int i =0;
			try {
				c4.consulta("SELECT ID_Mensaje,MensajeT,Fecha,DNI_Paciente,Username_medico,Username_tecnico FROM Mensaje where DNI_paciente='"+pac.getDni()+"' order by fecha desc;");
				while (c4.rs.next()) {
					men.add(new Mensaje(c4.rs.getInt("ID_Mensaje"),c4.rs.getString("Username_medico"),c4.rs.getString("Username_tecnico"),c4.rs.getString("DNI_Paciente"),c4.rs.getString("MensajeT"),c4.rs.getString("Fecha"),null));
					i++;
				}
				System.out.println("se han leido "+i+" mensajes");
				c4.closeConnection();
			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ":1 " + e.getMessage() );
			}
			return men;
		}
	 
	 public Usuario medausuario(Medico med) {
		 Usuario usu = null;
		 Conexion c5 = new Conexion();
		 try {
			 c5.consulta("SELECT * FROM usuario where usuario.usuario = '"+ med.getUsername()+"';");
			 while(c5.rs.next()) {
				 usu = new Usuario(c5.rs.getString("Usuario"), c5.rs.getString("Role"), c5.rs.getString("Password"));
				 System.out.println("Usuario: "+usu.getUser()+"role: "+usu.getRol()+"con: "+usu.getCon());
			 }
		 }
		 catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
		 c5.closeConnection();
		 return usu;
	 }
	 public Paciente pactecapac(PacienteTecnico p) {
		 Paciente pac= null;
		 Conexion c6 = new Conexion();
		 try {
			 c6.consulta("Select Nombre_paciente, Apellidos_paciente, DNI_paciente, Direccion_paciente, N_seguridad_social_paciente FROM paciente where DNI_paciente like '"+p.getDni()+"';");
			while(c6.rs.next()) {
			 pac = new Paciente(c6.rs.getString("Nombre_paciente"), c6.rs.getString("Apellidos_paciente"), c6.rs.getString("DNI_paciente"), c6.rs.getString("Direccion_paciente"), c6.rs.getString("N_seguridad_social_paciente"));
			}	
		 }
		 catch (Exception e) {
			 System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
		 c6.closeConnection();
		 return pac;
	 }
}
