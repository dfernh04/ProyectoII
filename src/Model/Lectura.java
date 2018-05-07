package Model;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;

import Control.ConnBBDD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public static ConnBBDD c = new ConnBBDD();
	
	/**
	 * Metodo que nos permite leer aquellos electrocardiogramas 
	 * que ya hallan sido leidos y guardarlos para poder operar 
	 * con ellos en otras clases
	 * 
	 * @param f File 
	 * @return ECG
	 */
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
	public static  Medico lectura_medico(Usuario us) {
		c.conexion("SELECT * FROM Medicos;");
		System.out.println(c.rs);
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		Medico m = new Medico();
		
		return m;
	}
	
	
}
