package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Control.ControladorMensaje;
import Model.Mensaje;

public class PanelMensaje extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ControladorMensaje cm;
	private JLabel nombretecnico;
	private JLabel fecha;
	private JLabel asunto;
	
	public PanelMensaje(Mensaje mensaje) {
    	setLayout(new BorderLayout(10, 0));
    	JPanel aux=new JPanel();
    			aux.setLayout(new BorderLayout(3,0));
    	JLabel btnNewButton = new JLabel();
    	aux.add(new JPanel(), BorderLayout.WEST);
    	aux.add(btnNewButton,BorderLayout.CENTER);
		
		btnNewButton.setIcon(new ImageIcon("Resource/Imagenes/hombre.png"));
		btnNewButton.setOpaque(false);
		
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 0, 0, 0));
		
		nombretecnico = new JLabel(mensaje.getUsername_tec());
		panel.add(nombretecnico);
		
		fecha = new JLabel();
		fecha.setText(String.valueOf(mensaje.getFecha()).substring(6, 8)+"-"+String.valueOf(mensaje.getFecha()).substring(4, 6)+"-"+String.valueOf(mensaje.getFecha()).substring(0, 4)+"     ");
		panel.add(fecha);
		
		asunto  = new JLabel(mensaje.getAsunto());
		panel.add(asunto);
		
		add(aux, BorderLayout.WEST);
	}
	public void addController(ControladorMensaje cm){
		this.cm = cm;
	}

}
