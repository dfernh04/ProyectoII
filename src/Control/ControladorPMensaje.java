package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import Model.Lectura;
import Model.Paciente;
import Model.Usuario;
import View.VentanaMensaje;

public class ControladorPMensaje implements ActionListener, MouseListener  {
	public ControladorPMensaje(Paciente p,Usuario us) {
		super();
		this.paciente = p;
		this.usuario = us;
	}
	private VentanaMensaje vm;
	private Usuario usuario;
	private Paciente paciente;
	private String MENSAJE = "MENSAJE";
	private String ENVIAR = "ENVIAR";
	private Lectura l;
	private Conexion c;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if(cmd.equals(MENSAJE)) {
			if(vm!=null) {
				vm.dispose();
			}
			vm = new VentanaMensaje(l.consultarMensajes(paciente), usuario);
			vm.crearVista();
		}
		else if(cmd.equals(ENVIAR)) {
			String mon=Calendar.getInstance().get(Calendar.MONTH)+"";
			if(mon.length()<2) {
				mon="0"+mon;
			}
			String day=Calendar.getInstance().get(Calendar.DATE)+"";
			if(day.length()<2) {
				day="0"+day;
			}
			if(usuario.getRol().equalsIgnoreCase("tecnico")) {
				c.consulta("Insert into mensaje(MensajeT,Fecha,DNI_Paciente,Username_tecnico) values('"+vm.getTexto().getText()+"',"
						+Integer.parseInt(Calendar.getInstance().get(Calendar.YEAR)+""+mon+""+day)+",'"+paciente.getDni().substring(0, paciente.getDni().length()-1)+","+usuario.getUser()+"');");
			}
			else if(usuario.getRol().toString().equalsIgnoreCase("medico")) {
				c.consulta("Insert into mensaje(MensajeT,Fecha,DNI_Paciente,Username_medico) values('"+vm.getTexto().getText()+"',"
						+Integer.parseInt(Calendar.getInstance().get(Calendar.YEAR)+""+mon+""+day)+",'"+paciente.getDni().substring(0, paciente.getDni().length()-1)+","+usuario.getUser()+"');");
			}

		}

	}
}
