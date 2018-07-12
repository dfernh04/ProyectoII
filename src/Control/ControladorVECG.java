package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Paciente;
import Model.PacienteTecnico;
import Model.Usuario;
import View.VentanaECG;

public class ControladorVECG implements ActionListener{
	@SuppressWarnings("unused")
	private Paciente p;
	@SuppressWarnings("unused")
	private Usuario us;
	@SuppressWarnings("unused")
	private PacienteTecnico pa;
	private VentanaECG ven;
	static public String ECG ="ECG";
	static public String ENVIAR = "ENVIAR";
	static public String ATRAS = "ATRAS";
	
	public ControladorVECG(Paciente p,Usuario us, VentanaECG ven) {
		super();
		this.p = p;
		this.us = us;
		this.ven = ven;
	}
	public ControladorVECG(PacienteTecnico p,Usuario us, VentanaECG ven) {
		super();
		this.pa = p;
		this.us = us;
		this.ven = ven;
	}
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand().toString(); 
		if(cmd.equals(ControladorVECG.ENVIAR)) {
			
		}
		else if(cmd.equals(ControladorVECG.ATRAS)){
		ven.dispose();
		}
		else if(cmd.equals(ControladorVECG.ECG)) {
		if(ven!=null) {
			ven.dispose();
		}
		ven = new VentanaECG();
		ven.addController(this);
		}
	}

}
