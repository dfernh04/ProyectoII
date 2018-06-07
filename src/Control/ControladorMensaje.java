package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import Model.Mensaje;
import Model.Usuario;
import View.VentanaMensaje;

public class ControladorMensaje implements ActionListener, MouseListener{
	private VentanaMensaje vm;
	private Usuario us;
	private Mensaje mensaje;
	
	public ControladorMensaje(VentanaMensaje ms,Mensaje men) {
		this.vm = ms;
		this.mensaje = men;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		vm.setFecha(new JLabel("Fecha: "+mensaje.getFecha()));
		vm.setAsunto(new JLabel("Asunto: "+mensaje.getAsunto()));
		vm.setEmisor(new JLabel("Enviado: "+mensaje.getUsername_med()+mensaje.getUsername_tec()));
		vm.setContenidomensaje(new JLabel(""+mensaje.getData()));
		vm.getRey7().setVisible(true);
		
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

}
