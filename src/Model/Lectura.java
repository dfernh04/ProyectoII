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
	
	private Vector<ECG> ecgs;
	Conexion c = new Conexion();
	Conexion c2 = new Conexion();
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
		try(BufferedReader br = new BufferedReader(new FileReader (archivo))) {
			
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
		System.out.println(us.getUser());
		try {
			c.consulta("SELECT * FROM Medico WHERE Medico.Username_medico = '" +us.getUser()+"';");
			while(c.rs.next()) {
				String nombre = c.rs.getString("Nombre_medico");
				String apellidos = c.rs.getString("Apellidos_medico");
				String username = c.rs.getString("Username_medico");
				String DNI = c.rs.getString("DNI_medico");
				String hospital = c.rs.getString("Hospital_medico");
				int numero = c.rs.getInt("Numero_afiliacion_medico");
				
				//COGER PACIENTES DE CLASE MIGUEL
				
				System.out.println(nombre + " " + apellidos);
				
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
		Paciente p = new Paciente();
		ArrayList<Paciente> pacientes = null;
		try {
			c2.consulta("SELECT * FROM Paciente WHERE Paciente.Username_medico like '" +us.getUser()+"';");
			while(c2.rs.next()) {
				String nombre = c2.rs.getString("Nombre_paciente");
				String apellidos = c2.rs.getString("Apellidos_paciente");
				String DNI = c2.rs.getString("DNI_paciente");
				String Foto = c2.rs.getString("Foto_paciente");
				String Localidad = c2.rs.getString("Localidad_paciente");
				String Direccion = c2.rs.getString("Direccion_paciente");
				String N_seguridad_social = c2.rs.getString("N_seguridad_social_paciente");
				
				
				System.out.println(nombre + " " + apellidos);
				
				p = new Paciente(nombre,apellidos,DNI,N_seguridad_social,Localidad,
						 Direccion,Foto,null,null,null);
				p.setEcgs(getECGS(p));
			}
			c2.rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pacientes;
	}
	
	

	public Vector<ECG> getECGS(Paciente p) {
		Vector<ECG> ecgs = null;
		ecgs = null;

		try {
			c3.consulta("SELECT * FROM ECG WHERE ECG.DNI_paciente like'" + p.getDni()+ "';");
			while(c3.rs.next()) {

				String fecha = c3.rs.getString("Fecha");
				String nombreTecnico = c3.rs.getString("Username_tecnico");
				String diagnostico = c3.rs.getString("Diagnostico");
				String dato = c3.rs.getString("");
				int frecuencia = c3.rs.getInt("Frecuencia");
				String nombre = c3.rs.getString("ID_ECG");
				
				ecgs.add(new ECG(fecha,nombreTecnico,null,diagnostico,frecuencia,nombre,null));
			}
			c3.closeConnection();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ecgs;
	}
	
}
