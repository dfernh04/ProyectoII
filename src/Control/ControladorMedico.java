package Control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.Lectura;
import Model.Medico;
import Model.Usuario;
import View.BuscadorMedico;
import View.CompararECG;
import View.Formulario;
import View.VentanaHelp;
import View.VentanaLogin;
import View.VentanaMedico;
import View.VentanaMedicoECG;

/**
 * ControladorMedico es la clase que se encargara de ejercer como controlador de 
 * la clase VentanaMedico. Su funcion es asignar una accion dependiendo del boton 
 * que se pulse, teniendo como opciones los botones "inicio", "pacientes", "revisar ECGs"
 * ,"crear", "logout", "enviar", "cancelar" y finalmente "ayuda". Para mas informacion 
 * de la funcionalidad de cada boton, revisar el manual tecnico o el apartado de ayuda
 * de la VentanaMedico dentro de la aplicacion.
 * 
 * @author Heartlight
 * 
 * @version Final
 * 
 * @see Lectura
 * @see Medico
 * @see Usuario
 * @see BuscadorMedico
 * @see CompararECG
 * @see Formulario
 * @see VentanaHelp
 * @see VentanaLogin
 * @see VentanaMedico
 * @see VentanaMedicoECG
 *
 */
public class ControladorMedico implements ActionListener,MouseListener,KeyListener{
	static public String INICIO= "INICIO";
	static public String ALTA="ALTA";
	static public String ECG="ECG";
	static public String PAC="PAC";
	public static String LOGOUT ="LOGOUT";
	public static String HELP ="HELP";
	public static String ENVIAR="ENVIAR";
	public static String CANCEL="CANCEL";
	private VentanaMedico vm;
	private Formulario formulario=null;
	private VentanaLogin ven;
	private Medico med;
	private VentanaHelp help;
	public Lectura l;
	/**
	 * Primer constructor de la clase ControladorMedico
	 * @param vm VentanaMedico 
	 * @param us Medico 
	 */
	public ControladorMedico(VentanaMedico vm, Medico us) {
		this.vm=vm;
		med= us;
	}
	/**
	 * Segundo constructor de la clase ControladorMedico
	 * @param vm VentanaMedico 
	 * @param us Usuario 
	 */
	public ControladorMedico(VentanaMedico vm, Usuario us) {
		this.vm=vm;
		med= l.lectura_medico(us);
	}

	/**
	 * Metodo actionPerformed propio de un ActionListener
	 * @param e ActionEvent  
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand().toString();
		if(cmd.equals(ControladorMedico.ALTA)) {
			for(int i=0;i<CompararECG.getFrames().length;i++){
				if(CompararECG.getFrames()[i] instanceof CompararECG)
				CompararECG.getFrames()[i].dispose();
			}
			if(help!=null){
				help.dispose();
			}
			if(formulario!=null){
				formulario.dispose();
			}
			formulario= new Formulario();
			formulario.setAlwaysOnTop(true);
			formulario.paciente(this);
			formulario.ver();
		}
		else if(cmd.equals(ControladorMedico.ECG)) {
			if(help!=null){
				help.dispose();
			}
			if(formulario!=null){
				formulario.dispose();
			}
			vm.getBtnRevisarEcg().setBackground(new Color(51,153,255).darker());
			vm.getCentro().setVisible(false);
			vm.getCentro().removeAll();
			for(int i=0;i<med.getPacientes().size();i++) {
				med.getPacientes().get(i).setEcgs(l.getECGS((med.getPacientes().get(i))));
			}
			VentanaMedicoECG ecg = new VentanaMedicoECG(vm,med);
			ecg.ver();
			vm.getCentro().add(ecg);
			vm.getCentro().setVisible(true);
		}
		else if (cmd.equals(ControladorMedico.INICIO)) {
			vm.getCentro().setVisible(false);
			vm.getCentro().removeAll();
			vm.getCentro().setVisible(true);
		}
		else if (cmd.equals(ControladorMedico.PAC)) {
			Lectura l = new Lectura();
			med.setPacientes(l.getPacientesMedico(med));
			vm.getCentro().setVisible(false);
			vm.getCentro().removeAll();
			BuscadorMedico bm = new BuscadorMedico();
			bm.crearVista(med,vm);
			vm.getCentro().add(bm);
			vm.getCentro().setVisible(true);
			
		}
		else if(cmd.equals(ControladorMedico.LOGOUT)){
			for(int i=0;i<CompararECG.getFrames().length;i++){
				if(CompararECG.getFrames()[i] instanceof CompararECG)
				CompararECG.getFrames()[i].dispose();
			}
			
			if(help!=null){
				help.dispose();
			}
			if(formulario!=null){
				formulario.dispose();
			}
			for(int i=0;i<CompararECG.getFrames().length;i++){
				if(CompararECG.getFrames()[i] instanceof CompararECG)
				CompararECG.getFrames()[i].dispose();
			}
			vm.dispose();
			ven=new VentanaLogin();
			ControladorLogin con=new ControladorLogin(ven);
			ven.asignarControlador(con);
			ven.ver();
		} else if(cmd.equals(ControladorMedico.HELP)){
			if(help!=null) {
				help.dispose();
			}
			if(formulario!=null)
				formulario.dispose();
			for(int i=0;i<CompararECG.getFrames().length;i++){
				if(CompararECG.getFrames()[i] instanceof CompararECG)
				CompararECG.getFrames()[i].dispose();
			}
			
			File fileH = new File("Resource/Usuarios/4.txt");
			  help=new VentanaHelp(3);
			  help.setAlwaysOnTop(true);
		        try (BufferedReader read = new BufferedReader(new FileReader(fileH))){
		            
		            String line = read.readLine();
		            
		            while(line !=null){
		                help.getText().append(line+" "+"\n");
		                line =read.readLine();
		            }
		            
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
			
		}
		else if(cmd.equals(ControladorMedico.CANCEL)) {
			if(formulario!=null)
				formulario.dispose();
		}
		else if(cmd.equals(ControladorMedico.ENVIAR)) {
			boolean bien=true;
			if(formulario.getNombre().getText().isEmpty()){
				formulario.getNombre().setBackground(Color.RED);
				bien=false;
			} else {
				formulario.getNombre().setBackground(Color.WHITE);
			}
			if(formulario.getApellido1().getText().isEmpty()){
				formulario.getApellido1().setBackground(Color.RED);
				bien=false;
			} else {
				formulario.getApellido1().setBackground(Color.WHITE);
			}/*
			if(formulario.getApellido2().getText().isEmpty()){
				formulario.getApellido2().setBackground(Color.RED);
				bien=false;
			} else {
				formulario.getApellido2().setBackground(Color.WHITE);
			}*/
			if(formulario.getDni().getText().isEmpty()){
				formulario.getDni().setBackground(Color.RED);
				bien=false;
			} else {
				formulario.getDni().setBackground(Color.WHITE);
			}
			if(formulario.getLugar().getText().isEmpty()){
				formulario.getLugar().setBackground(Color.RED);
				bien=false;
			} else {
				formulario.getLugar().setBackground(Color.WHITE);
			}
			if(formulario.getDireccion().getText().isEmpty()){
				formulario.getDireccion().setBackground(Color.RED);
				bien=false;
			} else {
				formulario.getDireccion().setBackground(Color.WHITE);
			}
			if(formulario.getSs().getText().isEmpty()){
				formulario.getSs().setBackground(Color.RED);
				bien=false;
			} else {
				formulario.getSs().setBackground(Color.WHITE);
			}
			if(formulario.getUrgencia().getSelectedItem().equals("Elija una opcion")){
				formulario.getUrgencia().setBackground(Color.RED);
				bien=false;
			} else {
				formulario.getUrgencia().setBackground(Color.WHITE);
			}
			if(formulario.getDni().getText().length() != 9 | 
					!Character.isLetter(formulario.getDni().getText().toCharArray()[formulario.getDni().getText().toCharArray().length -1])) {
				bien = false;
				formulario.getDni().setBackground(Color.RED);
			} else {
				formulario.getDni().setBackground(Color.green);
				bien = true;
			}
			String sexo;
			if(formulario.rdbtnFemenino.isSelected())
				sexo = "Mujer.jpg";
			else
				sexo = "Hombre.jpg";
			if(bien==true) {
				Conexion c = new Conexion();
				String stm = formulario.getNombre().getText()+" "+formulario.getApellido1().getText();
				String query = "insert into Paciente (Nombre_paciente, Apellidos_paciente, DNI_paciente, Localidad_paciente, Direccion_paciente, N_seguridad_social_paciente, Username_medico,Foto_paciente) values( '"
						+formulario.getNombre().getText()+"','"
						+formulario.getApellido1().getText()+"','"
						+formulario.getDni().getText()+"','"
						+formulario.getLugar().getText()+"','"
						+formulario.getDireccion().getText()+"','"
						+formulario.getSs().getText()+"','"
						+med.getUsername()+"','"
						+sexo+	"');";
				JOptionPane.showMessageDialog(formulario, "Paciente dado de alta con exito: "+stm, "Creado", JOptionPane.INFORMATION_MESSAGE);
				c.addPaciente(query);

				formulario.dispose();
			} else {
				JOptionPane.showMessageDialog(formulario, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Metodo KeyTyped propio de un Keylistener 
	 * @param e Keyevent 
	 */
	public void keyTyped(KeyEvent e) {
		if(e.getComponent().getName()!=null){
		if(e.getComponent().getName().equals("N")) {
			if(!Character.isDigit(e.getKeyChar())){
				e.consume();
			}
		} else if(e.getComponent().getName().equals("T")) {
			if(!Character.isAlphabetic(e.getKeyChar())){
				e.consume();
			}
		} 
	} else {
			if(!Character.isAlphabetic(e.getKeyChar()) && !Character.isDigit(e.getKeyChar())){
				e.consume();
			}
		}
	}

	/**
	 * Metodo KeyPressed propio de un Keylistener
	 * @param e Keyevent 
	 */
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * Metodo KeyReleased propio de un Keylistener
	 * @param e Keyevent 
	 */
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Metodo MouseClicked propio de un Mouselistener que se encarga
	 * de que al hacer click se cambie el color del fondo a blanco.
	 * 
	 * @param e MouseEvent 
	 */
	public void mouseClicked(MouseEvent e) {
		e.getComponent().setBackground(Color.white);
		
		
	}

	/**
	 * Metodo MousePressed propio de un Mouselistener 
	 * @param e MouseEvent
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Metodo MouseReleased propio de un Mouselistener 
	 * @param e MouseEvent 
	 */
	public void mouseReleased(MouseEvent e) {
		
	}

	/**
	 * Metodo MouseEntered propio de un Mouselistener 
	 * @param e MouseEvent 
	 */
	public void mouseEntered(MouseEvent e) {
		
	}

	/**
	 * Metodo MouseExited propio de un Mouselistener 
	 * @param e MouseEvent 
	 */
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Metodo que se encarga de crear un archivo de texto asociado al paciente en cuestion
	 * en el cual introducira la informacion de cada paciente.
	 * 
	 * @param nombre String 
	 * @param apellido1 String 
	 * @param apellido2 String 
	 * @param dni String 
	 * @param ss String 
	 * @param poblacion String 
	 * @param direccion String 
	 * @param urgencia String 
	 */
	public void escribirPaciente(String nombre,String apellido1,String apellido2,String dni,String ss,String poblacion,String direccion,String urgencia){
		try(FileWriter aux=new FileWriter("Resource/Pacientes/"+nombre+".txt",true)){
			aux.write(nombre+";"+apellido1+""+apellido2+";"+dni+";"+ss+";"+poblacion+";"+direccion+"\r\n"+urgencia);
		}catch(Exception exc){
		}
		try(FileWriter aux=new FileWriter("Resource/Pacientes/pacientes.txt",true)){
			aux.write(nombre+";"+apellido1+""+apellido2+";"+dni+"\r\n");
		}catch(Exception exc){
		}
	}

}


