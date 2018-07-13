package Control;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import Model.Usuario;
import View.Formulario;
import View.VentanaAdminPrincipal;
import View.VentanaHelp;
import View.VentanaLogin;

/**
 * @author HeartLight
 * 
 * Controlador que se encarga de las opciones que estan a la disposicion del tecnico
 * abriendo o añadiendo las ventanas/Jpanel necesarias para que el administrador navegue
 * por la aplicacion, tambien se encarga del control de los formularios para la creacion
 * de nuevos usuarios
 * 
 * @version Final
 * 
 * @see Component
 * @see VentanaAdminPrincipal
 * @see Formulario 
 * @see Usuario
 * @see VentanaHelp
 *
 */
public class ControladorAdmin  implements ActionListener,KeyListener,MouseListener{
	static public String CREARME="MEDICO";
	static public String CREARTEC="TECNICO";
	static public String OFF="OFF";
	static public String HELP="HELP";
	static public String ENVIARME="ENVIARME";
	static public String ENVIARTEC="ENVIARTEC";
	static public String CANCEL="CANCEL";
	static public String USUARIOS="USUARIOS";
	static public String BACK="BACK";
	
	private Component aux[];
	private VentanaAdminPrincipal a;
	private Formulario aux1=null;
	private Vector<Usuario> usuario;
	private VentanaHelp help;
	private Conexion c = new Conexion();
	private Conexion c1;
	private Conexion c2;
	private Conexion c3;
	private Conexion c4;
	private String stm;
	private String query;
	private Vector<Usuario> eliminados;
	private Vector<String> querys = new Vector<String>();	
	
	/**
	 * Getter de un vector que contiene todos los usuarios
	 * @return Vector de Usuario
	 */
	public Vector<Usuario> getUsuario() {
		return usuario;
	}
	
	/**
	 * Constructor para el control y asignacion de otros controladores a VentanaAdminPrincipal
	 * @param ax VentanaAdminPrincipal a la que se le ejercerá el control
	 */
	public ControladorAdmin(VentanaAdminPrincipal ax){
		this.a=ax;
		this.usuario = Conexion.queryUsuarios();
		a.getBuscador().addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e) {
				String aux=a.getBuscador().getText();
				if(Character.isAlphabetic(e.getKeyChar()) || Character.isDigit(e.getKeyChar())){
					aux=aux+e.getKeyChar();
				}
					actPanel(aux);
				
				
			}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			
		});
	}
	/**
	 * Constructor para el controlador de un Formulario
	 * @param a Formulario al que se ejercerá un control
	 */
	public ControladorAdmin(Formulario a){
		this.aux1=a;
	}
	
	/**
	 * Metodo encargado de realizar una accion dependiendo del 
	 * elemento con el que interactue el usuario, en este metodo se incluyen
	 * el logout, ayuda, control de paciente ademas de los
	 *  formularios y su control.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand().toString();
		if(cmd.equals(ControladorAdmin.OFF)){
			if(aux1!=null)
				aux1.dispose();
			if(help!=null)
				help.dispose();
			a.dispose();
			VentanaLogin ven=new VentanaLogin();
			ControladorLogin con=new ControladorLogin(ven);
			ven.asignarControlador(con);
			ven.ver();
		} else if(cmd.equals(ControladorAdmin.HELP)){
			if(help!=null)
				help.dispose();
			if(aux1!=null)
				aux1.dispose();
			
			  File fileH = new File("Resource/Usuarios/3.txt");
			  help=new VentanaHelp(3);
			  help.setAlwaysOnTop(true);
		        try(BufferedReader read = new BufferedReader(new FileReader(fileH))) {
		            
		            String line = read.readLine();
		            
		            while(line !=null){
		                help.getText().append(line+" "+"\n");
		                line =read.readLine();
		            }
		            
		        } catch (Exception ex) {
		            
		        }
		} else if(cmd.equals(CREARME)){
			if(aux1!=null)
				aux1.dispose();
			if(help!=null)
				help.dispose();
			aux1=new Formulario();
			aux1.medico(this);
			aux1.setAlwaysOnTop(true);
			aux1.ver();
		} else if(cmd.equals(CREARTEC)){
			if(aux1!=null)
				aux1.dispose();
			if(help!=null)
				help.dispose();
			 aux1=new Formulario();
			 aux1.tecnico(this);
			 aux1.setAlwaysOnTop(true);
			 aux1.ver();
		} else if(cmd.equals(CANCEL)){
			if(aux1!=null)
				aux1.dispose();
		} else if(cmd.equals(ENVIARME)){
			boolean bien=true;
			if(aux1.getNombre().getText().isEmpty()){
				aux1.getNombre().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getNombre().setBackground(Color.WHITE);
			}
			if(aux1.getApellido1().getText().isEmpty()){
				aux1.getApellido1().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getApellido1().setBackground(Color.WHITE);
			}
			if(aux1.getDni().getText().length() != 9 | 
					!Character.isLetter(aux1.getDni().getText().toCharArray()[aux1.getDni().getText().toCharArray().length -1])){
				aux1.getDni().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getDni().setBackground(Color.WHITE);
			}
			if(aux1.getLugar().getText().isEmpty()){
				aux1.getLugar().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getLugar().setBackground(Color.WHITE);
			}
			if(aux1.getTelefono().getText().length() != 9){
				aux1.getTelefono().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getTelefono().setBackground(Color.WHITE);
			}
			if(aux1.getNumero().getText().isEmpty()){
				aux1.getNumero().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getNumero().setBackground(Color.WHITE);
			}
			String pss="";
			for(int i=0;i<aux1.getContrasena1().getPassword().length;i++) {
				pss=pss+aux1.getContrasena1().getPassword()[i];
			}
			String pss2="";
			for(int i=0;i<aux1.getContrasena2().getPassword().length;i++) {
				pss2=pss2+aux1.getContrasena2().getPassword()[i];
			}
			if(pss.equals("")){
				aux1.getContrasena1().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getContrasena1().setBackground(Color.WHITE);
			}
			if(pss.equals("")){
				aux1.getContrasena2().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getContrasena2().setBackground(Color.WHITE);
			}
			
			if(bien==true){
				String con1="";
				for(int j=0;j<aux1.getContrasena1().getPassword().length;j++) {
					con1=con1+aux1.getContrasena1().getPassword()[j];
				}
				String con2="";
				for(int j=0;j<aux1.getContrasena2().getPassword().length;j++) {
					con2=con2+aux1.getContrasena2().getPassword()[j];
				}
				if(!(con1.equals(con2))){
					aux1.getContrasena2().setBackground(Color.RED);
					aux1.getContrasena1().setBackground(Color.RED);
					JOptionPane.showMessageDialog(aux1, "Las passwords no coinciden", "error", JOptionPane.ERROR_MESSAGE);
					
				}else{
					int i=0;
					String st=aux1.getNombre().getText()+aux1.getApellido1().getText();
					Vector<String> aux=new Vector<String>();
					for(int j=0;j<usuario.size();j++) {
						aux.add(usuario.get(i).getUser().toLowerCase());
					}
					if(aux.contains(st.toLowerCase())){
						while(aux.contains(st.toLowerCase()+i)){
							i++;
						}
						st=st+i;
					}
					con1="";
					for(int j=0;j<aux1.getContrasena1().getPassword().length;j++) {
						con1=con1+aux1.getContrasena1().getPassword()[j];
					}
					con2="";
					for(int j=0;j<aux1.getContrasena2().getPassword().length;j++) {
						con2=con2+aux1.getContrasena2().getPassword()[j];
					}
					String query = "INSERT INTO Medico (Nombre_medico, Apellidos_medico, Username_medico, DNI_medico, Contrasena_medico, Email_medico, Numero_afiliacion_medico, Hospital_medico) values ( "+aux1.getNombre().getText()+", "+aux1.getApellido1().getText()+", "+st+", "+aux1.getDni().getText()+", "+aux1.getContrasena1().toString()+", "+null+", "+aux1.getTelefono().getText()+(", ")+null+(")");
					c.consulta(query);
					query = "Insert into Usuario (Usuario, Role, Password) values ("+st+","+"medico ,"+aux1.getContrasena1()+(")");
					c.consulta(query);
					//escribirTecnico(st,con1, aux1.getNombre().getText(), aux1.getApellido1().getText(), aux1.getDni().getText(), aux1.getLugar().getText());
					usuario.add(new Usuario(st,"tecnico",con1));
					JOptionPane.showMessageDialog(aux1, "Medico creado con usuario: "+st, "Creado", JOptionPane.INFORMATION_MESSAGE);
					aux1.dispose();
					c.closeConnection();
				}
			} else {
				JOptionPane.showMessageDialog(aux1, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(cmd.equals(ENVIARTEC)){
			boolean bien=true;
			if(aux1.getNombre().getText().isEmpty()){
				aux1.getNombre().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getNombre().setBackground(Color.WHITE);
			}
			if(aux1.getApellido1().getText().isEmpty()){
				aux1.getApellido1().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getApellido1().setBackground(Color.WHITE);
			}

			if(aux1.getDni().getText().length() != 9 | 
					!Character.isLetter(aux1.getDni().getText().toCharArray()[aux1.getDni().getText().toCharArray().length -1])){
				aux1.getDni().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getDni().setBackground(Color.WHITE);
			}
			if(aux1.getLugar().getText().isEmpty()){
				aux1.getLugar().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getLugar().setBackground(Color.WHITE);
			}
			String pss="";
			for(int i=0;i<aux1.getContrasena1().getPassword().length;i++) {
				pss=pss+aux1.getContrasena1().getPassword()[i];
			}
			String pss2="";
			for(int i=0;i<aux1.getContrasena2().getPassword().length;i++) {
				pss2=pss2+aux1.getContrasena2().getPassword()[i];
			}
			if(pss.equals("")){
				aux1.getContrasena1().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getContrasena1().setBackground(Color.WHITE);
			}
			if(pss.equals("")){
				aux1.getContrasena2().setBackground(Color.RED);
				bien=false;
			} else {
				aux1.getContrasena2().setBackground(Color.WHITE);
			}
			if(bien==true){
				String con1="";
				for(int j=0;j<aux1.getContrasena1().getPassword().length;j++) {
					con1=con1+aux1.getContrasena1().getPassword()[j];
				}
				String con2="";
				for(int j=0;j<aux1.getContrasena2().getPassword().length;j++) {
					con2=con2+aux1.getContrasena2().getPassword()[j];
				}
				if(!(con1.equals(con2))){
				aux1.getContrasena2().setBackground(Color.RED);
				aux1.getContrasena1().setBackground(Color.RED);
				JOptionPane.showMessageDialog(aux1, "Las passwords no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
			} else{
				int i=0;
				String st=aux1.getNombre().getText()+aux1.getApellido1().getText();
				Vector<String> aux=new Vector<String>();
				for(int j=0;j<usuario.size();j++) {
					aux.add(usuario.get(i).getUser().toLowerCase());
				}
				if(aux.contains(st.toLowerCase())){
					while(aux.contains(st.toLowerCase()+i)){
						i++;
					}
					st=st+i;
				}
				con1="";
				for(int j=0;j<aux1.getContrasena1().getPassword().length;j++) {
					con1=con1+aux1.getContrasena1().getPassword()[j];
				}
				con2="";
				for(int j=0;j<aux1.getContrasena2().getPassword().length;j++) {
					con2=con2+aux1.getContrasena2().getPassword()[j];
				}
				
				String query = "Insert into Tecnico (Username_tecnico, Nombre_tecnico, Apellidos_tecnico, DNI_tecnico, Contrase�a_tecnico, Email_tecnico) values ('"+st+"', '"+aux1.getNombre().getText()+"','"+aux1.getApellido1().getText()+"','"+aux1.getDni().getText()+"','"+aux1.getContrasena1().getPassword().toString()+"','"+null+("')");
				c.consulta(query);
				query = "Insert into Usuario (Usuario, Role, Password) values ('"+st+"' , 'tecnico' , '"+aux1.getContrasena1().getPassword().toString()+("')");
				c.consulta(query);
				//escribirTecnico(st,con1, aux1.getNombre().getText(), aux1.getApellido1().getText(), aux1.getDni().getText(), aux1.getLugar().getText());
				usuario.add(new Usuario(st,"tecnico",con1));
				JOptionPane.showMessageDialog(aux1, "Tecnico creado con usuario: "+st, "Creado", JOptionPane.INFORMATION_MESSAGE);
				aux1.dispose();
				c.closeConnection();
					
			}
			} else {
				JOptionPane.showMessageDialog(aux1, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(cmd.equals(USUARIOS)){
			eliminados=new Vector<Usuario>();
			usuario=Conexion.queryUsuarios();
			aux=a.getCentro().getComponents();
			a.getCentro().setVisible(false);
			a.getCentro().removeAll();
			//usuario=this.obtenerUsuarios();
			Vector<Usuario>medi=new Vector<Usuario>();
			Vector<Usuario>tec=new Vector<Usuario>();
			
			for(int i=0;i<usuario.size();i++){
				if(usuario.get(i).getRol().equals("medico")){
					medi.add(usuario.get(i));
				} else {
					tec.add(usuario.get(i));
				}
			}
			
			a.getCentro().add(a.paneldeusuarios(this,medi,tec),BorderLayout.CENTER);
			
			a.getCentro().setVisible(true);

		}  else if(cmd.equals(BACK)){
			if(!eliminados.isEmpty()) {
				int resp = JOptionPane.showConfirmDialog(a, "Seguro que desea mantener los cambios? Serán permanentes", "Guardar cambios",JOptionPane.YES_NO_OPTION);
				if(resp==JOptionPane.NO_OPTION) {
					eliminados.removeAllElements();
				}
			}
			for(int i = 0; i <  querys.size();i++){
				c4.consulta(querys.get(i));
				System.out.println(querys.get(i));
			}
			querys.clear();
			while(!eliminados.isEmpty()) {
				if(eliminados.get(0).getRol().equals("medico")) {
				c4.consulta("delete from Medico where dni="+eliminados.get(0).getDni());
					
				} else if(eliminados.get(0).getRol().equals("tecnico")){
				c4.consulta("delete from tecnico where dni="+eliminados.get(0).getDni());
				} else {
				c4.consulta("delete from administrador where dni="+eliminados.get(0).getDni());
				}
			c4.consulta("delete from Usuario where dni="+eliminados.get(0).getDni());
				usuario.remove(eliminados.get(0));
				eliminados.remove(0);
			}
			a.getCentro().setVisible(false);
			a.getCentro().removeAll();
			for(int i=0;i<aux.length;i++){
				a.getCentro().add(aux[i]);	
			}
			
			a.getCentro().setVisible(true);
			c4.closeConnection();
		}
	}

	/**
	 * Listener que recae sobre los formularios para realizar un filtro de 
	 * caracteres, impidiendo que algunos campos de texto puedan tener numeros o letras
	 * y con algunos poder obtener un nombre de usuario
	 */
	public void keyTyped(KeyEvent e) {
		char key = (char) e.getKeyChar();
		if(e.getComponent().getName()!=null)
        if (e.getComponent().getName().equals("C") && !Character.isDigit(key)){ 
        		e.consume();
        	} else if(e.getComponent().getName().equals("N") && (Character.isDigit(key) || key==' ')){
        		e.consume();
        	} else if(key==' ' && e.getComponent().getName().equals("S")){
        		e.consume();
        	}
		
			aux1.getUser().setText(aux1.getNombre().getText().toString()+aux1.getApellido1().getText().toString());
			if(aux1.getUser().getText().isEmpty()){
				aux1.getUser().setText(" ");
			}
	}
	
/**
 * listener que simplemente se encarga de actualizar el usuario provisional
 * con un par de campos de texto.
 */
	public void keyPressed(KeyEvent e) {
		aux1.getUser().setText(aux1.getNombre().getText().toString()+aux1.getApellido1().getText().toString());
		
	}
	/**
	 * listener que simplemente se encarga de actualizar el usuario provisional
	 * con un par de campos de texto.
	 */
	public void keyReleased(KeyEvent e) {
		aux1.getUser().setText(aux1.getNombre().getText().toString()+aux1.getApellido1().getText().toString());
		e.getComponent().setBackground(Color.white);
	}
	
	
	/**
	 * listener que se encarga de la usabilidad para cuando se interactue
	 * con un campo de texto este alcance un color normal, ya que si un datos es incorrecto
	 * se tornara de color rojo
	 */
	public void mouseClicked(MouseEvent e) {
		e.getComponent().setBackground(Color.white);
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Se encarga de la lectura de todos los usuarios desde su fichero correspondiente
	 * @return Un vector con todos los usuarios que pudo leer de fichero
	 */
	public Vector<Usuario> obtenerUsuarios(){
		System.out.println("obtenerUsuarios()");
		Vector<Usuario> us=new Vector<Usuario>();
		Vector<Usuario> med =new Vector<Usuario>();
		Vector<Usuario> tec =new Vector<Usuario>();
		//FORMA DE RECUPERAR LOS USUARIOS SIN BBDD
		/*
			File file = null;
			file = new File("Resource/Usuarios/Users.txt");
				try (Scanner sc = new Scanner(new FileReader(file))) {
					while (sc.hasNextLine()) {
						String auxi=sc.nextLine();
						String[] usuario=auxi.split(";");
						us.add(new Usuario(usuario[0],usuario[1],usuario[2]));
						
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			*/
			med=obtenermedicos();
			tec=obtenertecnicos();
			for(int i=0;i<med.size();i++) {
			us.add(med.get(i));
			}
			for(int i=0;i<tec.size();i++) {
			us.add(tec.get(i));
			}
		return us;
	}
	/**
	 * Escritura y creacion de un nuevo medico en la base de datos
	 * @param User nombre de usuario	
	 * @param password contrasena del usuario
	 * @param nom nombre
	 * @param ape1 primero apellido
	 * @param ape2 segundo apellido
	 * @param dn DNI
	 * @param tele numero de telefono
	 * @param lugar Lugar/direccion de residencia/Trabajo
	 * @param cole Numero de colegiado
	 */
	public void escribirMedico(String User,String password,String nom,String ape1,String dn,String tele,String lugar,String cole){
		 stm = nom+" "+ape1;
		 query = "insert into Medico (Nombre_medico, Apellidos_medico, Username_medico, DNI_medico, Contrasena_medico, Email_medico, Numero_afiliacion_medico, Hospital_medico) values ("
					+nom.toString()+","
					+ape1.toString()+","
					+User.toString()+","
					+dn+","
					+password.toString()+","
					+tele.toString()+","
					+cole.toString()+","
					+lugar.toString()+")";
			JOptionPane.showMessageDialog(null, "Medico dado de alta con exito: "+stm, "Creado", JOptionPane.INFORMATION_MESSAGE);
			c.consulta(query);
			c.closeConnection();
	}
	/**
	 * Escritura y creacion de un nuevo tecnico en la base de datos
	 * @param User nombre de usuario
	 * @param password contrasena de dicho usuario
	 * @param nom nombre
	 * @param ape1 primer apellido
	 * @param ape2 segundo apellido
	 * @param dn DNI
	 * @param lugar Lugar/direccion de residencia/Trabajo
	 */
	public void escribirTecnico(String User,String password,String nom,String ape1,String dn,String lugar){
	 stm = nom+" "+ape1;
	 query = "insert into Paciente (Username_tecnico, Nombre_tecnico, Apellidos_tecnico, DNI_tecnico, Contrase�a_tecnico, Email_tecnico) values ("
				+User.toString()+","
				+nom.toString()+","
				+ape1.toString()+","
				+dn+","
				+password.toString()+","
				+lugar.toString()+")";
		JOptionPane.showMessageDialog(null, "Tecnico dado de alta con exito: "+stm, "Creado", JOptionPane.INFORMATION_MESSAGE);
		c1.consulta(query);
		c1.closeConnection();
	}
	/**
	 * Se encarga de la actualizacion de los usuarios mostrados en 
	 * el control de usuarios de la ventanaAdminPrincipal aplicandolo un filtro
	 * en el caso de que se haya hecho una busqueda y separandolo entre medicos,
	 * tecnicos y en conjunto con estos, el administrador
	 * @param aux El dato por el que se filtraran los usuarios, viendo si contienen dicho String
	 */
	public void actPanel(String aux){
		int index=this.a.getTabbedPane().getSelectedIndex();
		a.getTabbedPane().setVisible(false);
		
		//usuario=this.obtenerUsuarios();
		Vector<Usuario>medi=new Vector<Usuario>();
		Vector<Usuario>tec=new Vector<Usuario>();
		for(int i=0;i<usuario.size();i++){
			if(usuario.get(i).getRol().equals("medico")){
				if(usuario.get(i).getUser().toLowerCase().contains(aux.toLowerCase()) || aux.equals("")){
					medi.add(usuario.get(i));
				}
			} else {
				if(usuario.get(i).getUser().toLowerCase().contains(aux.toLowerCase()) || aux.equals("")){
					tec.add(usuario.get(i));
				}
			}
		}
		
		a.actPanel(medi, tec,this);
		this.a.getTabbedPane().setSelectedIndex(index);

		a.getTabbedPane().setVisible(true);
	}
	/**
	 * Metodo auxiliar encargado de obtener el Nombre de todos los medicos junto con su contrase�a
	 * y lo asigna a un vector que luego le pasaremos al metodo principal para el control de 
	 * usuarios
	 * @return Vector de user Medico
	 */
	public Vector<Usuario> obtenermedicos(){
		System.out.println("obtenermedicos()");
		Vector<Usuario> us = new Vector<>();
		c2.consulta("SELECT * FROM Medico");
		int i=0;
		try {
			while(c2.rs.next()) {
				us.add(new Usuario(c2.rs.getString("Nombre_medico"), "Medico", c2.rs.getString("Contrasena_medico")));
				System.out.println("med: "+us.get(i).getUser());
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c2.closeConnection();
		return us;
	}
	/**
	 * Metodo auxiliar encargado de obtener el Nombre de todos los tecnicos junto con su contraseña
	 * y lo asigna a un vector que luego le asaremos al metodo principal para el control de 
	 * usuarios
	 * @return Vector de user Tecnico
	 */
	public Vector<Usuario> obtenertecnicos(){
		System.out.println("obtenertecnicos()");
		Vector<Usuario> us = new Vector<>();
		c3.consulta("SELECT * FROM Medico");
		int i=0;
		try {
			while(c3.rs.next()) {
				us.add(new Usuario(c3.rs.getString("Nombre_tecnico"), "Tecnico", c3.rs.getString("Contrase�a_tecnico")));
				System.out.println("tec: "+us.get(i).getUser());
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c3.closeConnection();
		return us;
	}
}
