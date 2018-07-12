package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Paciente;
import Model.Usuario;
import View.DetallePaciente;
import View.VentanaECG;
import View.VentanaTecnico;

public class ControladorVECG implements ActionListener{
	private Paciente p;
	private Usuario us;
	private VentanaECG ven;
	static public String ECG ="ECG";
	static public String ENVIAR = "ENVIAR";
	static public String ATRAS = "ATRAS";
	
	public ControladorVECG(Paciente p,Usuario us) {
		super();
		this.p = p;
		this.us = us;
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
