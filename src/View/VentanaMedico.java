package View;

import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

import Control.ControladorMedico;
import Model.Medico;

public class VentanaMedico extends JFrame{
	/**
	 * @author Pablo Ferrer Luis Ferrer Diego Fernandez
	 * 
	 * La clase VentanaMedico es la ventana principal del usuario Medico
	 * desde ella puede crear un nuevo paciente, puede observar los ultimos ecg
	 * y ademas puede ver todos sus pacientes a parte de generar diagnosticos para cada
	 * uno
	 * 
	 * @version Final
	 * 
	 * @see VentanaMedicoECG
	 * @see BuscadorMedico
	 * @see Formulario
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Medico med;
	private JButton log;
	private JButton help;
	private JButton btnDardeAlta;
	private JButton btnRevisarEcg;
	private JButton btnBuscarPacientes;
	private JButton btnInicio;
	private JPanel centro;
	
	
	public JButton getBtnRevisarEcg() {
		return btnRevisarEcg;
	}

	public void setBtnRevisarEcg(JButton btnRevisarEcg) {
		this.btnRevisarEcg = btnRevisarEcg;
	}

	public JButton getBtnBuscarPacientes() {
		return btnBuscarPacientes;
	}

	public VentanaMedico(Medico au){
		this.med=au;
	}

	public void crearVista(){
		//BASICO
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		ImageIcon img = new ImageIcon("Resource/Imagenes/Logos/logo-cardio-finito100x100.png");
		this.setIconImage(img.getImage());
		int x=(int) (Toolkit.getDefaultToolkit().getScreenSize().width);
		int y=(int) (Toolkit.getDefaultToolkit().getScreenSize().height);
		this.setExtendedState( this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		
		this.setMinimumSize(new Dimension((int)(x*0.5),(int)(y*0.4)));
		this.setLayout(new BorderLayout());
		JPanel usado=new JPanel();
		usado.setSize(x, y);
		usado.setOpaque(false);
		Fondo fondo=new Fondo(this,"Resource/Imagenes/fondo.jpeg");
		fondo.setLayout(new BorderLayout());
		fondo.setSize(x, y);
		fondo.setOpaque(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		Logo logo=new Logo(usado);
		logo.setLayout(new BorderLayout());
		logo.setSize(x,y);
		logo.setOpaque(false);
		logo.centrado(true);
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//PANEL EN EL QUE HAREMOS TODO=USADO
		Font f=new Font("",Font.BOLD,25);
		usado.setLayout(new BorderLayout());
		btnInicio=new JButton("Inicio");
		btnInicio.setForeground(Color.white);
		btnInicio.setActionCommand(ControladorMedico.INICIO);
		btnInicio.setFont(f);
		btnBuscarPacientes=new JButton("Pacientes");
		btnBuscarPacientes.setForeground(Color.white);
		btnBuscarPacientes.setFont(f);
		btnBuscarPacientes.setActionCommand(ControladorMedico.PAC);
		btnDardeAlta=new JButton("Crear");
		btnDardeAlta.setForeground(Color.white);
		btnDardeAlta.setActionCommand(ControladorMedico.ALTA);
		btnDardeAlta.setFont(f);
		btnRevisarEcg=new JButton("Revisar ECG");
		btnRevisarEcg.setForeground(Color.white);
		btnRevisarEcg.setActionCommand(ControladorMedico.ECG);
		btnRevisarEcg.setFont(f);
		
		btnInicio.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, new Color(0,133,255)));
		btnRevisarEcg.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, new Color(0,133,255)));
		btnBuscarPacientes.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, new Color(0,133,255)));
		
		btnRevisarEcg.setContentAreaFilled(false);
		//btnRevisarEcg.setBorderPainted(false);
		btnRevisarEcg.setOpaque(false);
		
		btnDardeAlta.setContentAreaFilled(false);
		btnDardeAlta.setBorderPainted(false);
		btnDardeAlta.setOpaque(false);
		
		btnBuscarPacientes.setContentAreaFilled(false);
		//btnBuscarPacientes.setBorderPainted(false);
		btnBuscarPacientes.setOpaque(false);
		
		btnInicio.setContentAreaFilled(false);
		//btnInicio.setBorderPainted(false);
		btnInicio.setOpaque(false);
		
		JPanel n=new JPanel();
		n.setOpaque(false);
		
		
		
		centro=new JPanel();
		centro.setOpaque(false);
		centro.setLayout(new BorderLayout());
		//centro.setMinimumSize(new Dimension(100,100));
		
		
		JPanel p=new JPanel();
		log=new JButton();
		log.setActionCommand(ControladorMedico.LOGOUT);
		log.setHorizontalAlignment(SwingConstants.RIGHT);
		log.setIcon(new ImageIcon("Resource/Imagenes/off.png"));
		log.setContentAreaFilled(false);
		log.setBorderPainted(false);
		log.setOpaque(false);
		p.add(log);
		p.setOpaque(false);
		
		
		JPanel pp=new JPanel();
		help=new JButton();
		help.setActionCommand(ControladorMedico.HELP);
		help.setIcon(new ImageIcon("Resource/Imagenes/help.png"));
		help.setContentAreaFilled(false);
		help.setBorderPainted(false);
		help.setOpaque(false);
		pp.add(help);
		pp.setOpaque(false);
		n.setLayout(new BorderLayout(0,10));
		n.add(p,BorderLayout.EAST);
		n.add(pp, BorderLayout.WEST);
		JLabel bien=new JLabel("Bienvenido Dr./Dra. "+med.getNombre()+" "+med.getApellidos());
		bien.setFont(new Font("", Font.BOLD,15));
		Menu menu=new Menu();
		menu.setLayout(new GridLayout(1,4,5,0));
		menu.add(btnInicio);
		menu.add(btnBuscarPacientes);
		menu.add(btnRevisarEcg);
		menu.add(btnDardeAlta);
		menu.setOpaque(false);
		
		n.add(menu,BorderLayout.CENTER);
		bien.setHorizontalAlignment(SwingConstants.CENTER);
		n.add(bien, BorderLayout.NORTH);
		usado.add(n,BorderLayout.NORTH);
		usado.add(centro, BorderLayout.CENTER);
		//FINAL BASICO
		////////////////////////////////////////////////////////////////////////////////////////////////////
		logo.add(usado,BorderLayout.CENTER);
		fondo.add(logo,BorderLayout.CENTER);
	    
		this.add(fondo,BorderLayout.CENTER);
	}
	public JPanel getCentro() {
		/**
		 * Nos devuelve el Panel que se encuentrra en el centro con el que 
		 * jugams entre poner visible y no y reemplazar por el resto de 
		 * paneles para ofrecer las distintas opciones.
		 * 
		 * @return centro
		 */
		return centro;
	}

	public void ver(){
		this.setVisible(true);
	}
	public void addController(ControladorMedico cm){
		btnInicio.addActionListener(cm);
		btnDardeAlta.addActionListener(cm);
		btnRevisarEcg.addActionListener(cm);
		btnBuscarPacientes.addActionListener(cm);
		log.addActionListener(cm);
		help.addActionListener(cm);
		
		
	}
}
