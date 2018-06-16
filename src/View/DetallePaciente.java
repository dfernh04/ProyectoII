package View;

import javax.swing.JPanel;

import Model.Paciente;
import Model.PacienteTecnico;
import Model.Usuario;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

import Control.ControladorFicha;
import Control.ControladorMensaje;
import Control.GraphController;


/**
 * DetallePaciente es aquella clase que se encarga de mostrar la ficha
 * del paciente propia que obtenemos al acceder desde la VentanaTecnico.
 * DetallePaciente es un panel en el cual se agregan una serie de componentes
 * graficos para dar forma a la ficha de paciente que queremos obtener.
 * 
 * @author Heartlight
 *
 * @version Final
 *
 * @see ControladorFicha
 * @see GraphController
 */
public class DetallePaciente extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel_1;
	private JButton btnTomarDatos;
	private JButton btnEnivar;
	private JLabel lblNewLabel;
	private JLabel lblApellidos ;
	private JLabel lblObservaciones;
	private JLabel lblDni;
	private JLabel lblfrecuencia;
	private JLabel lbltiempo;
	private JTextPane textArea;
	private JButton button;
	private PacienteTecnico p;
	private GraficaECG ecg;
	private JTextArea obser;
	private JTextField frecuencia;
	private JTextField tiempo;
	private JButton btnMensaje;
	
	
	
	
	
	/**
	 * Getter del boton que se encarga de enviar los datos
	 * @return btnEnivar JButton
	 */
	public JButton getBtnEnivar() {
		return btnEnivar;
	}
	/**
	 * Getter del are de texto que posee las observaciones
	 * @return obser JTextArea
	 */
	public JTextArea getObser() {
		return obser;
	}
	
	/**
	 * Setter para agregar informacion del paciente
	 * @param p PacienteTecnico
	 */
	public void setP(PacienteTecnico p) {
		this.p = p;
	}
	/**
	 * Getter para obtener la informacion del paciente
	 * @return p PacienteTecnico
	 */
	public PacienteTecnico getP() {
		return p;
	}
	
	/**
	 * Getter de la etiqueta de apellidos
	 * @return lblApellidos JLabel
	 */
	public JLabel getLblApellidos() {
		return lblApellidos;
	}
	/**
	 * Getter de la etiqueta que contiene la imagen
	 * @return lblNewLabel JLabel
	 */
	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}
	/**
	 * Getter de la etiqueta del DNI
	 * @return lblDni JLabel
	 */
	public JLabel getLblDni() {
		return lblDni;
	}
	
	/**
	 * Constructor de la clase DetallePaciente en el cual
	 * se crea la vista de dicha clase a traves de layouts y 
	 * elementos graficos.
	 * 
	 * @param p PacienteTecnico
	 */
	public DetallePaciente(PacienteTecnico p) {
		this.p=p;
		this.setOpaque(false);
		Font font=new Font("",Font.BOLD,15);
		//Fondo fon = new Fondo(this,fondo2);
		//this.add(fon);
		obser=new JTextArea();
		obser.setLineWrap(true);
		obser.setWrapStyleWord(true);
		button = new JButton("<- Atras");
		button.setActionCommand(ControladorFicha.ATRAS);
		lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setIcon(new ImageIcon("Resource/Imagenes/hombre.png"));
		btnTomarDatos = new JButton("Tomar datos");
		btnTomarDatos.setActionCommand(ControladorFicha.TOMAR);
		btnEnivar = new JButton("Enviar");
		btnEnivar.setActionCommand(ControladorFicha.ENVIAR);
		lblNewLabel = new JLabel(p.getNombre());
		lblApellidos = new JLabel(p.getApellido());
		lblDni = new JLabel(p.getDni() );
		lblNewLabel.setFont(font);
		lblApellidos.setFont(font);
		lblDni.setFont(font);
		lblObservaciones = new JLabel("Observaciones:   ");
		textArea = new JTextPane();
		lblfrecuencia = new JLabel("Frecuencia:   ");
		frecuencia = new JTextField();
		frecuencia.setText("");
		lbltiempo = new JLabel("Tiempo:   ");
		tiempo = new JTextField();
		tiempo.setText("");

	    JLabel l=new JLabel(" ");
	    l.setFont(new Font("",Font.BOLD,50));
		
		JPanel aux=new JPanel();
		aux.setOpaque(false);
		BorderLayout bor=new BorderLayout();
		bor.minimumLayoutSize(l);
		this.setLayout(bor);
		JPanel up=new Fondo(aux,"Resource/Imagenes/FondoPanel.jpeg");
		//up.setOpaque(false);
		//up.setBackground(Color.LIGHT_GRAY);
		up.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		JPanel iz=new JPanel();
		
		
		aux.setLayout(new BorderLayout());
		up.setLayout(new BorderLayout());
		FlowLayout izl=new FlowLayout();
		iz.setOpaque(false);
		izl.setAlignment(FlowLayout.LEFT);
		
		iz.setLayout(izl);
		
		JPanel fo=new JPanel();
		JPanel datos=new JPanel();
		datos.setOpaque(false);
		datos.setLayout(new BoxLayout(datos, BoxLayout.Y_AXIS));
		datos.add(lblNewLabel);
		datos.add(lblApellidos);
		datos.add(lblDni);
		
		 //BOTON PARA ENTRAR EN MENSAJE
	    btnMensaje = new JButton("Revisar Mensajes");
	    btnMensaje.setOpaque(false);
	    btnMensaje.setActionCommand(ControladorMensaje.MENSAJE);
	    btnMensaje.setIcon(new ImageIcon("Resource/Imagenes/mensaje.png"));
		btnMensaje.setContentAreaFilled(false);
		btnMensaje.setBorderPainted(false);
		
		fo.add(lblNewLabel_1);
		iz.add(fo);
		iz.add(datos);
		
		JPanel panboton = new JPanel();
		panboton.setOpaque(false);
		panboton.setLayout(new BoxLayout(up,BoxLayout.X_AXIS));
		panboton.add(btnEnivar);
		panboton.add(btnMensaje);
		
		up.add(iz,BorderLayout.WEST);
		up.add(panboton,BorderLayout.EAST);
		
		JPanel panel=new JPanel();
		BorderLayout borde=new BorderLayout();
		borde.setHgap(5);
		panel.setLayout(borde);
		//panel.setOpaque(false);
	    ecg = new GraficaECG();
	    
	    GraphController control=new GraphController(ecg);
	    ecg.addController(control);
	    ecg.initUITEC();
	    
	    JPanel down=new JPanel();
	    BorderLayout borde2=new BorderLayout();
		borde2.setHgap(1);
	    down.setLayout(new BoxLayout(down,BoxLayout.PAGE_AXIS));
	    JPanel datosva=new JPanel();
	    datosva.setLayout(new FlowLayout());
	    down.add(obser);
	    JPanel auxi=new JPanel();
	    JButton invi2=new JButton();
	    invi2.setOpaque(false);
	    invi2.setBorderPainted(false);
	    invi2.setContentAreaFilled(false);
	    auxi.setLayout(borde2);
	    auxi.add(obser,BorderLayout.CENTER);
	    auxi.add(invi2,BorderLayout.SOUTH);
	    auxi.add(new JPanel(),BorderLayout.EAST);
	    obser.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
	    down.add(auxi);
	    
	    
	    BorderLayout borde3=new BorderLayout();
	    borde3.setHgap(25);
	    JPanel bu=new JPanel();
	    bu.setLayout(borde3);
	     FlowLayout fl2=new FlowLayout();
	     fl2.setAlignment(FlowLayout.LEFT);
	     JPanel pa=new JPanel();
	    pa.setLayout(fl2);
	    JPanel boton=new JPanel();
	    boton.setLayout(new BorderLayout());
	    JPanel boton2=new JPanel();
	    boton2.setLayout(new BorderLayout());
	    boton.add(btnTomarDatos, BorderLayout.SOUTH);
	    boton.add(button, BorderLayout.NORTH);
	    boton2.add(lblfrecuencia, BorderLayout.NORTH);
	    boton2.add(frecuencia, BorderLayout.NORTH);
	    boton2.add(lbltiempo, BorderLayout.SOUTH);
	    boton2.add(tiempo, BorderLayout.SOUTH);
	    pa.add(boton);
	    pa.add(boton2);
	    
	    
	    JButton invi4=new JButton();
	    invi4.setOpaque(false);
	    invi4.setBorderPainted(false);
	    invi4.setContentAreaFilled(false);
	    
	    FlowLayout fl3=new FlowLayout();
	     fl3.setAlignment(FlowLayout.RIGHT);
	     JPanel basu=new JPanel();
	     basu.setLayout(new BorderLayout());
	     JPanel pa2=new JPanel();
	    pa2.setLayout(fl3);
	    basu.add(invi4,BorderLayout.CENTER);
	    basu.add(lblObservaciones,BorderLayout.SOUTH);
	    pa2.add(basu);
	    
	    FlowLayout fl4=new FlowLayout();
	     fl4.setAlignment(FlowLayout.RIGHT);
	    JPanel pul=new JPanel();
	    pul.setLayout(fl4);
	    
	    bu.add(pul,BorderLayout.CENTER);
	    bu.add(pa,BorderLayout.WEST);
	    bu.add(pa2,BorderLayout.EAST);
	    
	    
	    JButton invi=new JButton();
	    invi.setOpaque(false);
	    invi.setBorderPainted(false);
	    invi.setContentAreaFilled(false);
	  //  down.add(datosva);
	    panel.add(bu,BorderLayout.NORTH);
	    panel.add(ecg,BorderLayout.CENTER);
	    panel.add(invi,BorderLayout.SOUTH);
	    aux.add(up,BorderLayout.CENTER);

	    panel.add(down,BorderLayout.EAST);
	    this.add(aux,BorderLayout.NORTH);
	    this.add(panel,BorderLayout.CENTER);
	    
		
	}
	
	/**
	 * Getter de la grafica de ECGs
	 * @return ecg GraficaECG
	 */
	public GraficaECG getEcg() {
		return ecg;
	}
	
	/**
	 * Metodo que anade el controlador ControladorFicha
	 * a la clase DetallePaciente 
	 * @param cf ControladorFicha 
	 */
	public void addController(ControladorFicha cf){
		button.addActionListener(cf);
		btnTomarDatos.addActionListener(cf);
		btnEnivar.addActionListener(cf);
	}
	
	/**
	 * Getter del textArea
	 * @return textArea JTextPane
	 */
	public JTextPane getTextArea() {
		return textArea;
	}
	/**
	 * Setter del textArea
	 * @param textArea JTextPane
	 */
	public void setTextArea(JTextPane textArea) {
		this.textArea = textArea;
	}
	public void MensajeCont(ControladorMensaje me) {
		btnMensaje.addActionListener(me);
	}
	public void actUsuarioPaciente(Usuario us,Paciente p) {
		for(int i=0;i<btnMensaje.getActionListeners().length;i++) {
			if(btnMensaje.getActionListeners()[i] instanceof ControladorMensaje) {
				ControladorMensaje con=(ControladorMensaje)(btnMensaje.getActionListeners()[i]);
				con.setP(p);
				con.setUs(us);
			}
		}
	}


}

