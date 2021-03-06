package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



import Model.Mensaje;
import Model.Paciente;
import Model.Usuario;
import View.VentanaMensaje;

public class ControladorMensaje implements ActionListener,ListSelectionListener{


	public VentanaMensaje ven;
	private Paciente p;
	private Usuario us;
	public static String ATRAS="ATRAS";
	public static String ENVIAR="ENVIAR";
	public static String MENSAJE ="MENSAJE";
	public static String MENSAJENUEVO ="MENSAJENUEVO";
	public static String ORDENAR="ORDENAR";
	//public static String ENVIADOS = "ENVIADOS";
	//public static String RECIBIDOS = "RECIBIDOS";
	private boolean newtoold=true;
	//private Conexion c1;
	
	public ControladorMensaje(Paciente p,Usuario us) {
		super();
		this.p = p;
		this.us=us;
		this.ven = new VentanaMensaje(this);
	
	}
	public void valueChanged(ListSelectionEvent e) {
		@SuppressWarnings("unchecked")
		Mensaje men = ((JList<Mensaje>) e.getSource()).getSelectedValue();
		if(e.getSource().equals(ven.getEnvlist())) {
			if(!ven.getList().isSelectionEmpty())
				ven.getList().clearSelection();;
		} else {
			/*if(!ven.getEnvlist().isSelectionEmpty())
				ven.getEnvlist().clearSelection();*/
		}
		if(men!=null) {
			ven.actInfo(men);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if(cmd.equals(MENSAJE)) {
			if(ven!=null) {
				ven.dispose();
			}
			ven.VentanaMensajeTodos(p, this,us);
			ven.setVisible(true);
		} else if(cmd.equals(MENSAJENUEVO)) {
			ven.mensajeNuevo(this);
		} 
		else if(cmd.equals(ATRAS)) {
			ven.VentanaMensajeTodos(p, this,us);
		} 
		else if(cmd.equals(ENVIAR)) {
			String mon=Calendar.getInstance().get(Calendar.MONTH)+"";
			if(mon.length()<2) {
				mon="0"+mon;
			}
			System.out.println("entro en enviar");
			String day=Calendar.getInstance().get(Calendar.DATE)+"";
			if(day.length()<2) {
				day="0"+day;
			}
			Conexion c1= new Conexion();
			if(us.getRol().equalsIgnoreCase("tecnico")) {
				System.out.println("El tecnico ha intentado enviar un mensaje");
				/*
				 * EL FALLO ESTA EN LA QUERY QUE SE ENVIA
				 * COMPROBAR QUE LOS TIPOS DE DATOS DEL PROGRAMA JAVA Y LA BBDD COINCIDAN
				 */
			
			String query = "Insert into mensaje(MensajeT,Fecha,DNI_Paciente,Username_medico,Username_tecnico,Asunto) values('"+ven.getTexto().getText()+"','"
					+Integer.parseInt(Calendar.getInstance().get(Calendar.YEAR)+""+mon+""+day+"")+"','"+p.getDni()+"','"+us.getUser()+"','PedroLara','"+ven.asunto.getText()+"');";
			c1.addMensaje(query);
			}
			
			else if(us.getRol().equalsIgnoreCase("medico")) {
				System.out.println("El medico ha intentado enviar un mensaje");
				/*
				 * EL FALLO ESTA EN LA QUERY QUE SE ENVIA
				 * COMPROBAR QUE LOS TIPOS DE DATOS DEL PROGRAMA JAVA Y LA BBDD COINCIDAN
				*/
				 
				String query = "Insert into mensaje(MensajeT,Fecha,DNI_Paciente,Username_medico,Username_tecnico) values('"+ven.getTexto().getText()+"','"
						+Integer.parseInt(Calendar.getInstance().get(Calendar.YEAR)+""+mon+""+day+"")+"','"+p.getDni()+"','"+us.getUser()+"','PedroLara','"+ven.asunto.getText()+"';)";
						
				//String query = "Insert into pi2_bd_cardioparty.mensaje(MensajeT,Fecha,DNI_Paciente,Username_medico,Username_tecnico) values('Prueba','22/06/2015','12345678f','BorjaMonsalve','NULL');";
				c1.addMensaje(query);
				
				}
			
			c1.closeConnection();
			ven.VentanaMensajeTodos(p, this,us);
			
		} else if(cmd.equals(ORDENAR)) {
			if(newtoold) {
				ven.getOrden().setText("Fecha descendente");
			} else {
				ven.getOrden().setText("Fecha Ascendente");
			}
			newtoold=!newtoold;
		
			Mensaje[] mens=new Mensaje[0];
			mens=((Vector<Mensaje>) p.getMensajes()).toArray(mens).clone();
			Vector<Mensaje> recibidos=new Vector<Mensaje>();
			Vector<Mensaje> enviados=new Vector<Mensaje>();
			for(int i=mens.length-1;i>=0;i--) {
				if(us.getRol().equalsIgnoreCase("medico")) {
				if(mens[i].getUsername_med()==us.getUser()) {
					enviados.add(mens[i]);
				} else {
					recibidos.add(mens[i]);
				}
				}
				else if(us.getRol().equalsIgnoreCase("tecnico")) {
					if(mens[i].getUsername_tec()==us.getUser()) {
						enviados.add(mens[i]);
					} else {
						recibidos.add(mens[i]);
					}
				}
			}
			ven.getList().setListData(recibidos);
			ven.getEnvlist().setListData(enviados);
		}
		
	}

	public void setP(Paciente p) {
		this.p = p;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}
	
	


}
