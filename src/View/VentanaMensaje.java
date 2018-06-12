package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Control.ControladorMensaje;
import Control.ControladorPanel;
import Model.Lectura;
import Model.Mensaje;
import Model.Paciente;
import Model.Usuario;

public class VentanaMensaje extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fondo fondo;
	private JPanel info;
	private JTextField asunto;
	private JLabel fecha;
	private JTextField emisor;
	private JTextArea texto;
	private JList<Mensaje> list;
	private JList<Mensaje> envlist;
	private JPanel central;
	private JButton atras;
	private JButton orden;
	private JButton nuevo;
	private ImageIcon logo = new ImageIcon("Resource/Imagenes/Logos/logo-cardio-finito100x100.png");
	private Lectura lec;
	
	public VentanaMensaje(ControladorMensaje control) {
		
		int x=(int) (Toolkit.getDefaultToolkit().getScreenSize().width);
		this.setAlwaysOnTop(true);
		int y=(int) (Toolkit.getDefaultToolkit().getScreenSize().height);
		this.setSize((int)(x*0.50), (int)(y*0.4));
		
		this.setMinimumSize(new Dimension((int)(x*0.50), (int)(y*0.40)));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocation((x-this.getWidth())/2, (y-this.getHeight())/2);
		this.setLayout(new BorderLayout());
		this.setIconImage(logo.getImage());
		this.setLayout(new BorderLayout());
		
		fondo =new Fondo(this,"Resource/Imagenes/fondo.jpeg");
		fondo.setLayout(new BorderLayout(3,1));
		
		//Panel sobre el que incluiremos los botones
		JPanel aux=new JPanel();
		aux.setLayout(new BoxLayout(fondo,BoxLayout.X_AXIS));
		aux.setOpaque(false);
		
		atras=new JButton("Atras");
		atras.setActionCommand(ControladorMensaje.ATRAS);
		atras.addActionListener(control);
		
		nuevo=new JButton("Mensaje nuevo");
		nuevo.addActionListener(control);
		nuevo.setActionCommand(ControladorMensaje.MENSAJE);
		
		orden=new JButton("Ordenar por fecha");
		orden.setActionCommand(ControladorMensaje.ORDENAR);
		orden.addActionListener(control);
		
		JPanel p=new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		p.setOpaque(false);
		p.add(orden);
		
		aux.add(atras,BorderLayout.WEST);
		aux.add(nuevo,BorderLayout.EAST);
		aux.add(p,BorderLayout.CENTER);
		central=new JPanel();
		
		fondo.add(aux,BorderLayout.NORTH);
	}
	
	public VentanaMensaje(ControladorPanel cp) {
		int x=(int) (Toolkit.getDefaultToolkit().getScreenSize().width);
		int y=(int) (Toolkit.getDefaultToolkit().getScreenSize().height);
		this.setSize((int)(x*0.40), (int)(y*0.5));
		this.setMinimumSize(new Dimension((int)(x*0.40), (int)(y*0.5)));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setIconImage(logo.getImage());
		this.setLayout(new BorderLayout());
		
		fondo =new Fondo(this,"Resource/Imagenes/fondo.jpeg");
		fondo.setLayout(new BorderLayout(3,1));
		
		JPanel aux=new JPanel();
		aux.setLayout(new BorderLayout());
		aux.setOpaque(false);
		
		atras=new JButton("Back");
		atras.setActionCommand(ControladorMensaje.ATRAS);
		atras.addActionListener(cp);
		
		nuevo=new JButton("New Message");
		nuevo.addActionListener(cp);
		nuevo.setActionCommand(ControladorMensaje.MENSAJE);
		
		orden=new JButton("Mas recientes");
		orden.setActionCommand(ControladorMensaje.ORDENAR);
		orden.addActionListener(cp);
		
		JPanel p=new JPanel();
		p.setOpaque(false);
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		p.add(orden);
		
		aux.add(atras,BorderLayout.WEST);
		aux.add(nuevo,BorderLayout.EAST);
		aux.add(p,BorderLayout.CENTER);
		central=new JPanel();
		
		fondo.add(aux,BorderLayout.NORTH);
	}
	
		public void VentanaMensajeTodos(Paciente p,ControladorMensaje control,Usuario us){
			lec = new Lectura();
			System.out.println(p.getApellido());
			p.setMensajes(lec.consultarMensajes(p));
			atras.setEnabled(false);
			orden.setEnabled(true);
			Vector<Mensaje> recibidos=(Vector<Mensaje>) p.getMensajes().clone();
			Vector<Mensaje> enviados=new Vector<Mensaje>();
			for(int i=recibidos.size()-1;i>=0;i--) {
				if(us.getRol().equalsIgnoreCase("medico")) {
				if(recibidos.get(i).getUsername_med()==us.getUser()) {
					enviados.add(recibidos.get(i));
					recibidos.remove(i);
				}
				}
				else if(us.getRol().equalsIgnoreCase("tecnico")) {
					if(recibidos.get(i).getUsername_tec()==us.getUser()) {
						enviados.add(recibidos.get(i));
						recibidos.remove(i);
					}
					}
			}
			 list=new JList<Mensaje>(recibidos);
			 list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			 envlist=new JList<Mensaje>(enviados);
			 envlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			 
			JScrollPane scr=new JScrollPane();
			JScrollPane scr2=new JScrollPane();
			scr.setViewportView(list);
			scr2.setViewportView(envlist);
			
			 list.setCellRenderer(render);
			envlist.setCellRenderer(render);
			list.addListSelectionListener(control);
			envlist.addListSelectionListener(control);
			
			JPanel datos=new JPanel();
			datos.setLayout(new BorderLayout());
			JPanel emisor=new JPanel();
			emisor.setLayout(new BoxLayout(emisor,BoxLayout.Y_AXIS));
			
			//textfield que contendra el asunto para nuevos mensajes
			JPanel asunt=new JPanel();
			asunto=new JTextField(15);
			asunto.setEditable(false);
			asunt.setLayout(new FlowLayout());
			asunt.add(new JLabel("Asunto: "));
			asunt.add(asunto);
			
			info =new JPanel();
			this.emisor=new JTextField(15);
			this.emisor.setEditable(false);
			fecha=new JLabel("fecha   ");
			datos.add(fecha,BorderLayout.EAST);
			JPanel aux=new JPanel();
			aux.setLayout(new FlowLayout());
			aux.add(new JLabel("   De:    "));
			aux.add(this.emisor);
			
			texto=new JTextArea();
			texto.setEditable(false);
			texto.setLineWrap(true);
			
			
			emisor.add(aux);
			emisor.add(asunt);
			datos.add(emisor,BorderLayout.CENTER);
			
			info.setLayout(new BorderLayout());
			
			info.add(datos,BorderLayout.NORTH);
			info.add(texto,BorderLayout.CENTER);
			//fon.add(info,BorderLayout.EAST);
			
			central.removeAll();
			central.setVisible(false);
			central.setOpaque(false);
			central.setLayout(new BorderLayout());
			
			JPanel mens=new JPanel();
			JPanel envi=new JPanel();
			envi.setLayout(new BorderLayout());
			JPanel reci=new JPanel();
			reci.setLayout(new BorderLayout());
			envi.add(scr2,BorderLayout.CENTER);
			envi.add(new JLabel("Mensajes Enviados"),BorderLayout.NORTH);
			reci.add(scr,BorderLayout.CENTER);
			reci.add(new JLabel("Mensajes Recibidos"),BorderLayout.NORTH);
			mens.setLayout(new BorderLayout());
			mens.add(reci,BorderLayout.CENTER);
			mens.add(envi,BorderLayout.SOUTH);
			central.add(mens,BorderLayout.WEST);
			central.add(info,BorderLayout.CENTER);
			
			fondo.add(central,BorderLayout.CENTER);
			central.setVisible(true);
			this.add(fondo,BorderLayout.CENTER);
			
		}
		public JList<Mensaje> getList() {
			return list;
		}
		public void actInfo(Mensaje men) {
			asunto.setText(men.getAsunto());
			emisor.setText(men.getUsername_med()+" "+men.getUsername_tec()+" ");
			texto.setText(men.getData());
			fecha.setText("    "+String.valueOf(men.getFecha()).substring(6, 8)+"-"+String.valueOf(men.getFecha()).substring(4, 6)+"-"+String.valueOf(men.getFecha()).substring(0, 4)+"     ");
			
		}

		public JPanel getCentral() {
			return central;
		}
		
		public void mensajeNuevo(ControladorMensaje control) {
			System.out.println("HOLA");
			atras.setEnabled(true);
			central.setVisible(false);
			central.removeAll();
			JPanel datos=new JPanel();
			datos.setLayout(new BorderLayout());
			JPanel emisor=new JPanel();
			emisor.setLayout(new BoxLayout(emisor,BoxLayout.Y_AXIS));
			JPanel asunt=new JPanel();
			asunto=new JTextField(15);
			asunto.setEditable(true);
			asunt.setLayout(new FlowLayout());
			asunt.add(new JLabel("Asunto: "));
			asunt.add(asunto);
			info =new JPanel();
			this.emisor=new JTextField(15);
			this.emisor.setEditable(true);
			JButton enviar=new JButton("SEND");
			enviar.addActionListener(control);
			enviar.setActionCommand(ControladorMensaje.SENDMENS);
			datos.add(enviar,BorderLayout.EAST);
			JPanel aux=new JPanel();
			aux.setLayout(new FlowLayout());
			texto=new JTextArea();
			texto.setEditable(true);
			texto.setLineWrap(true);
			
			
			emisor.add(aux);
			emisor.add(asunt);
			datos.add(emisor,BorderLayout.CENTER);
			
			info.setLayout(new BorderLayout());
			
			info.add(datos,BorderLayout.NORTH);
			info.add(texto,BorderLayout.CENTER);
			central.add(info,BorderLayout.CENTER);
			central.setVisible(true);
		}

		public JTextField getAsunto() {
			return asunto;
		}

		public JTextArea getTexto() {
			return texto;
		}
		public JList<Mensaje> getEnvlist() {
			return envlist;
		}
		public void setEnvlist(JList<Mensaje> reclist) {
			this.envlist = reclist;
		}
		/**
		 * @return the orden
		 */
		public JButton getOrden() {
			return orden;
		}
		
		DefaultListCellRenderer render=(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index,
                      boolean isSelected, boolean cellHasFocus) {
                 Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                 if (value instanceof Mensaje) {
                      if (isSelected) {
                           setBackground(getBackground().darker());
                      }
                 } 
                 return c;
            }

       });
		
}
