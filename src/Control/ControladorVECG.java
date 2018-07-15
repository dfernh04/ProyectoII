package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.event.ChangeEvent;

import Model.*;
import View.DetallePaciente;
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
	private ECG ecg;

	private String arch="";
	DetallePaciente d;
	
	public ControladorVECG(Paciente p,Usuario us, VentanaECG ven,DetallePaciente d) {
		super();
		this.p = p;
		this.us = us;
		this.ven = ven;
		this.d = d;
	}
	public ControladorVECG(PacienteTecnico p,Usuario us, VentanaECG ven,DetallePaciente d) {
		super();
		this.pa = p;
		this.us = us;
		this.ven = ven;
		this.d = d;
	}
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand().toString(); 
		if(cmd.equals(ControladorVECG.ENVIAR)) {
			Lectura l = new Lectura();
			int file = (int) Math.floor(Math.random()*2+1);
			File f = new File("Resource/ECG/ECG"+file +".txt");
			ECG aux = Lectura.lecturaEcg(f,pa,us.getUser());
			ecg = aux;
			if(!ecg.getPuntos().isEmpty()) {
				d.getBtnEnivar().setEnabled(true);
			}
			d.getEcg().addGraphic(ecg);
			((GraphController) d.getEcg().getSl().getChangeListeners()[d.getEcg().getSl().getChangeListeners().length-1]).stateChanged(new ChangeEvent(d.getEcg().getSl()));
			ven.dispose();		}
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
	public ECG getEcg() {
		return ecg;
	}

}
