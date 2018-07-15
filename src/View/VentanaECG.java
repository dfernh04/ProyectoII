package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Control.ControladorVECG;

public class VentanaECG extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon logo = new ImageIcon("Resource/Imagenes/Logos/logo-cardio-finito100x100.png");
	private JPanel fondo;
	private JPanel aux;
	private JPanel datos;
	private JButton atras;
	private JButton enviar;
	public JTextField diagnostico;
	private JTextField id;
	private JLabel lbldiagnostico;
	private JLabel lblid;
	
	public VentanaECG() {
		int x=(int) (Toolkit.getDefaultToolkit().getScreenSize().width);
		this.setAlwaysOnTop(true);
		int y=(int) (Toolkit.getDefaultToolkit().getScreenSize().height);
		this.setSize((int)(x*0.15), (int)(y*0.25));
		
		this.setMinimumSize(new Dimension((int)(x*0.15), (int)(y*0.25)));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocation((x-this.getWidth())/2, (y-this.getHeight())/2);
		this.setLayout(new BorderLayout());
		this.setIconImage(logo.getImage());
		this.setLayout(new BorderLayout());
		
		this.setVisible(true);
		
		fondo = new JPanel();
		fondo.setLayout(new BorderLayout(3, 1));
		fondo.setBackground(Color.DARK_GRAY);
		
		//panel sobre el que colocaremos los botones
		aux = new JPanel();
		aux.setLayout(new FlowLayout());
		aux.setOpaque(false);
		
		//COMPONENTES DE AUX
		//atras
		atras = new JButton("<-- ATRAS");
		atras.setActionCommand(ControladorVECG.ATRAS);
		//enviar
		enviar = new JButton("GENERAR ECG");
		enviar.setActionCommand(ControladorVECG.ENVIAR);
		
		aux.add(atras,BorderLayout.WEST);
		aux.add(enviar,BorderLayout.EAST);
		
		fondo.add(aux,BorderLayout.NORTH);
		
		//PANEL SOBRE EL QUE COLOCAMOS LOS DATOS
		datos = new JPanel();
		datos.setLayout(new BoxLayout(datos, BoxLayout.Y_AXIS));
		datos.setBackground(Color.GRAY);
		//diagnostico
		diagnostico = new JTextField(10);
		diagnostico.setEditable(true);
		
		lbldiagnostico = new JLabel("Escriba el comentario del ecg: ");
		
		JPanel iv = new JPanel();
		iv.setOpaque(true);
		iv.add(lbldiagnostico);
		iv.add(diagnostico);

		//id
		id = new JTextField(10);
		id.setEditable(true);
		
		lblid = new JLabel("Introduzca el id del mensaje(Ej: ECG...)");
		
		JPanel idv = new JPanel();
		idv.setOpaque(true);
		idv.add(lblid);
		idv.add(id);
	
		
		datos.add(iv);
		datos.add(idv);
		
		
		//invisible pa rellenar
		JPanel invi = new JPanel();
		invi.setVisible(false);
		
		fondo.add(datos,BorderLayout.CENTER);
		fondo.add(invi,BorderLayout.SOUTH);
		
		this.add(fondo,BorderLayout.CENTER);
	}
	public void addController(ControladorVECG c) {
		atras.addActionListener(c);
		enviar.addActionListener(c);
	}
}
