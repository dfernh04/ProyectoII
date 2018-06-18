package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Model.PacienteTecnico;
import Model.Usuario;
import View.VentanaAdminPrincipal;
import View.VentanaTecnico;

/**
 * Controlador que permite que cuando el usuario haga click en un paciente
 * en la VentanaTecnico le muestre en la ficha de dicho paciente la informacion
 * correspondiente. A su vez se encarga de hacer un doble check cuando se haga click
 * en uno de los pacientes de forma que el tecnico no se equivoque de paciente
 *  
 * @author Heartlight 
 * 
 * @version Final
 * 
 * @see PacienteTecnico
 * @see Usuario
 * @see VentanaTecnico
 * 
 */
public class ControladorPanel implements ActionListener, MouseListener {

	private String query;
	private VentanaTecnico vt;
	private PacienteTecnico p;
	private Usuario us;
	private ControladorAdmin c;
	private JTextField fl;
	private VentanaAdminPrincipal va;
	private Vector<Usuario> elimi;
	/**
	 * Constructor de la clase ControladorPanel
	 * @param vt VentanaTecnico 
	 * @param p PacienteTecnico 
	 */
	public ControladorPanel(VentanaTecnico vt,PacienteTecnico p){
		this.vt = vt;
		this.p=p;
		
	}
	
	/**
	 * Segundo constructor de la clase ControladorPanel
	 * @param vt VentanaTecnico 
	 * @param c ControladorAdmin 
	 * @param f JTextField 
	 */
	public ControladorPanel(Usuario vt,ControladorAdmin c,JTextField f){
		this.us = vt;
		this.c=c;
		fl=f;
		
	}
	
	public ControladorPanel(Usuario vt,ControladorAdmin c,JTextField f,Vector<Usuario> elimi,VentanaAdminPrincipal va){
		this.us = vt;
		this.c=c;
		fl=f;
		this.elimi=elimi;
		this.va=va;
	}

	/**
	 * Metodo actionPerformed propio de un ActionListener que elimina
	 * el usuario y coge el texto que hay en el JTextField
	 * 
	 * @param arg0 ActionEvent 
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(elimi.contains(us)) {
			if(us.getRol().equals("medico")) {
				boolean encontrado=false;
				int i=0;
				while(encontrado==false && i<c.getQuerys().size()){
					String[] aux=c.getQuerys().get(i).split(" ");
					if(us.getDni()==Integer.parseInt(aux[aux.length-2])){
						c.getQuerys().remove(i);
						encontrado=true;
					}
				}
			}
			elimi.remove(us);
			c.actPanel(fl.getText());
		} else {
			int resp = JOptionPane.showConfirmDialog(c.getA(), "Seguro que desea eliminar al usuario propietario del DNI: "+us.getDni(), "Eliminacion de Usuario",JOptionPane.YES_NO_OPTION);
			if(resp==JOptionPane.YES_OPTION) {
				if(us.getRol().equals("medico")) {
				//	query = va.seleccionMedico(elimi,us);
				}
				System.out.println(us.getRol());
				if(query!=null && us.getRol().equals("medico")){
					elimi.add(us);
					String[] datos = query.split(" ");
					c.getQuerys().add("UPDATE medicopaciente SET medicopaciente.dniMedico = " + datos[0] + " WHERE medicopaciente.dniMedico = " + us.getDni() + " ;");
				} else if(us.getRol().equals("tecnico") || us.getRol().equals("admin")) {
					elimi.add(us);
				}
				c.actPanel(fl.getText());
			} 
		}
	}
	
	/**
	 * Metodo mouseClicked propio de un MouseListener que permite realizar 
	 * el doble check y obtener los datos del paciente que se halla clickado
	 * y posteriormente dar dichos datos a la ficha de paciente.
	 * 
	 * @param arg0 MouseEvent 
	 */
	public void mouseClicked(MouseEvent arg0) {
		
		String respuesta = JOptionPane.showInputDialog("Escriba el DNI del paciente");
		if(respuesta!=null){
			if(respuesta.toLowerCase().equals(p.getDni().toLowerCase())){
				vt.getFicha().getLblNewLabel().setText(p.getNombre());
				vt.getFicha().getLblApellidos().setText(p.getApellido());
				vt.getFicha().getLblDni().setText(p.getDni());
				vt.getFicha().setP(p);
				vt.getFicha().getBtnEnivar().setEnabled(false);
				vt.getFicha().getEcg().cleanGraph();
				vt.getFicha().setVisible(true);
			} else {
				JOptionPane.showMessageDialog(vt, "El DNI introcido no concuerda con el del paciente seleccionado.", "Doble Confirmacion fallida", JOptionPane.WARNING_MESSAGE);
			}
		}	
	}
	
	/** 
	 * Metodo mouseEntered propio de un mouseListener
	 * 
	 */
	public void mouseEntered(MouseEvent arg0) {
	}

	/** 
	 * Metodo mouseExited propio de un mouseListener
	 * 
	 */
	public void mouseExited(MouseEvent arg0) {
	}

	/** 
	 * Metodo mousePressed propio de un mouseListener
	 * 
	 */
	public void mousePressed(MouseEvent arg0) {
	}

	/** 
	 * Metodo mouseReleased propio de un mouseListener
	 * 
	 */
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	

}
