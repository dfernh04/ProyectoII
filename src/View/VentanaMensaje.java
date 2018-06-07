package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Control.ControladorMensaje;
import Model.Mensaje;
import Model.Usuario;

import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class VentanaMensaje extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Usuario usu;
	private JScrollPane jsp;
	private JLabel paciente;
	private JTextField buscador;
	private JPanel rey4;
	private JPanel rey5;
	private JPanel rey6;
	private Vector<Mensaje> mensajes;
	private JLabel fecha;
	private JLabel asunto;
	private JLabel contenidomensaje;
	private JLabel emisor;
	private JPanel rey7;

	/**
	 * Create the frame.
	 */
	public void crearVista(Vector<Mensaje> mensajes,Usuario au) {
		this.usu = au;
		this.setVisible(true);
		this.mensajes = mensajes;
	}
	public VentanaMensaje() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 120, 75);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));


		//LABEL DE PACIENTES
		paciente = new JLabel("Buscador de pacientes");
		paciente.setFont(new Font("",Font.BOLD,19));

		//LABEL PARA FECHA
		fecha = new JLabel("Fecha: ");
		fecha.setFont(new Font("",Font.BOLD,19));

		//LABEL PARA ASUNTO
		asunto = new JLabel("Asunto: ");
		asunto.setFont(new Font("",Font.BOLD,19));

		//LABEL PARA CONTENIDO MENSAJE
		emisor = new JLabel("Enviado por: ");
		emisor.setFont(new Font("",Font.BOLD,19));

		//LABEL PARA CONTENIDO MENSAJE
		contenidomensaje = new JLabel();
		contenidomensaje.setFont(new Font("",Font.BOLD,19));

		//PANEL EN EL QUE SE MOSTRARA LA INFORMACION DEL MENSAJE SELECCIONADO

		//CREO EL CUADRO DEL BUSCADOR
		buscador = new JTextField();
		buscador.setSize(80, 23);

		//PANEL QUE CONTIENE LOS PANELES DE CADA PACIENTE
		rey4 = new JPanel();
		rey4.setOpaque(false);
		rey4.setLayout(new BoxLayout(rey4,BoxLayout.Y_AXIS));

		//PANEL QUE CONTIENE EL BUSCADOR
		JPanel rey5 = new JPanel();
		rey5.setLayout(new GridLayout(2,1));
		rey5.setOpaque(false);

		//PANEL QUE CONTIENE AL SCROLLPANE QUE CONTIENE A LOS PANELES DE MENSAJE
		JPanel rey6 = new JPanel();
		rey6.setLayout(new BorderLayout());
		rey6.setOpaque(false);

		//PANEL QUE CONTIENE AL SCROLLPANE QUE CONTIENE A LOS PANELES DE MENSAJE
		JPanel rey7 = new JPanel();
		rey7.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		rey7.setOpaque(false);
		rey7.setVisible(false);
		
		//BUCLE QUE RELLENARA EL PANEL CON LOS MENSAJES
		for(int i= 0;i<mensajes.size();i++){
			PanelMensaje pan = new PanelMensaje(mensajes.get(i));
			pan.setBorder(new LineBorder(Color.gray, 2));
			ControladorMensaje cp = new ControladorMensaje(this, mensajes.get(i));
			pan.addMouseListener(cp);
			JLabel invi = new JLabel("lalalalalal");
			invi.setVisible(false);
			rey4.add(pan);	
			rey4.add(invi);
		}

		//CREO EL SCROLL
		jsp = new JScrollPane();
		jsp.getViewport().setOpaque(false);
		jsp.setBackground(Color.red);
		jsp.setOpaque(false);
		jsp.setViewportView(rey4);

		rey5.add(paciente);
		rey5.add(buscador);
		rey6.add(rey5,BorderLayout.NORTH);
		rey6.add(jsp, BorderLayout.CENTER);

		rey7.add(fecha);
		rey7.add(asunto);
		rey7.add(emisor);
		rey7.add(contenidomensaje);

		contentPane.add(rey6,BorderLayout.WEST);
		contentPane.add(rey7,BorderLayout.CENTER);
	}
	public Vector<Mensaje> getMensajes() {
		return mensajes;
	}
	public JLabel getFecha() {
		return fecha;
	}
	public void setFecha(JLabel fecha) {
		this.fecha = fecha;
	}
	public JLabel getAsunto() {
		return asunto;
	}
	public void setAsunto(JLabel asunto) {
		this.asunto = asunto;
	}
	public JLabel getContenidomensaje() {
		return contenidomensaje;
	}
	public void setContenidomensaje(JLabel contenidomensaje) {
		this.contenidomensaje = contenidomensaje;
	}
	public JLabel getEmisor() {
		return emisor;
	}
	public void setEmisor(JLabel emisor) {
		this.emisor = emisor;
	}
	public JPanel getRey7() {
		return rey7;
	}
	
}
