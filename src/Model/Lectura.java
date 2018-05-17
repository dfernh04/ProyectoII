package Model;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;

import Control.Conexion;

import java.io.File;
import java.io.FileNotFoundException;
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
	
	public static Conexion c = new Conexion();
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
	public static Medico lectura_medico(Usuario us) {
		Medico m = new Medico();
		try {
			c.consulta("SELECT * FROM Medico WHERE Medico.Username_medico = '" +us.getUser()+"';");
			while(c.rs.next()) {
				String nombre = c.rs.getString("Nombre_medico");
				String apellidos = c.rs.getString("Apellidos_medico");
				String username = c.rs.getString("Username_medico");
				String DNI = c.rs.getString("DNI_medico");
				String hospital = c.rs.getString("Hospital_medico");
				String numero = c.rs.getString("Numero_afiliacion_medico");
				
				//COGER PACIENTES DE CLASE MIGUEL
				//ArrayList<Paciente> pacientes = getPacientes();
				
				System.out.println(nombre + " " + apellidos);
				
				m = new Medico(nombre,apellidos,DNI,username,
						null, hospital,numero,null);
			}
			c.closeConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}
	
	
	ArrayList<ECG> getECGS(Paciente p) {
		ArrayList<ECG> ecgs = null;
		try {
			c.consulta("SELECT * FROM ECG WHERE ECG.DNI_paciente ='" + p.getDni()+ "';");
			while(c.rs.next()) {
				String fecha = c.rs.getString("Fecha");
				String nombreTecnico = c.rs.getString("Username_tecnico");
				String diagnostico = c.rs.getString("Diagnostico");
				String dato = c.rs.getString("");
				int frecuencia = c.rs.getInt("Frecuencia");
				String nombre = c.rs.getString("ID_ECG");
				
				ecgs.add(new ECG(fecha,nombreTecnico,null,diagnostico,frecuencia,nombre,null));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ecgs;
	}
	
}
