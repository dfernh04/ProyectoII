package Control;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class JavaRXTX extends javax.swing.JFrame {
	public Vector <Integer> lol = new Vector <Integer>();
	
	int contador;
	int tiempo;
	int frecuencia;
	String frecuencia1;
	String tiempo1;
	JTextField textfieldfrecuencia;
	JTextField textfieldtiempo;
	private PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
	
	private SerialPortEventListener listener = new SerialPortEventListener() {
		@Override
		public void serialEvent(SerialPortEvent spe) {
			
				try {
					if (ino.isMessageAvailable()) {
						//jLabelOutput.setText("Resultado: " + ino.printMessage());
						// contador++;
						//   System.out.println(ino.printMessage());
						String s = ino.printMessage();
						Integer a = Integer.parseInt(s);
						lol.add(a);
						System.out.println(lol);
					}
				} catch (SerialPortException | ArduinoException ex) {
					Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
				}
				
				try {
					PrintWriter myFile = new PrintWriter("Thiriri.txt");
					myFile.println(lol);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}};
		public JavaRXTX() {
			initComponents();
			try {
				ino.arduinoRXTX("COM4", 9600, listener);
			} catch (ArduinoException ex) {
				Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			}
		}


		@SuppressWarnings("unchecked")
		// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
		private void initComponents() {

			jButton1 = new javax.swing.JButton();
			jButton2 = new javax.swing.JButton();
			textfieldfrecuencia = new javax.swing.JTextField();
			textfieldtiempo = new javax.swing.JTextField();
			jLabelOutput = new javax.swing.JLabel();

			setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

			jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
			jButton1.setText("Encender");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					jButton1ActionPerformed(evt);
				}
			});

			jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
			jButton2.setText("Apagar");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					jButton2ActionPerformed(evt);
				}
			});

			jLabelOutput.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
			jLabelOutput.setText("Resultado: ");

			textfieldfrecuencia.setFont(new java.awt.Font("Tahoma", 0, 14));
			textfieldtiempo.setFont(new java.awt.Font("Tahoma", 0 , 14));

			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									.addGroup(layout.createSequentialGroup()
											.addGap(36, 36, 36)
											.addComponent(jButton1)
											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
									.addGroup(layout.createSequentialGroup()
											.addContainerGap()
											.addComponent(jLabelOutput)))
							.addGroup(layout.createSequentialGroup())
							.addContainerGap()
							.addComponent(textfieldfrecuencia)
							.addGroup(layout.createSequentialGroup())
							.addContainerGap()
							.addComponent(textfieldtiempo)
							.addContainerGap(43, Short.MAX_VALUE))
					);
			layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
									.addComponent(jButton1)
									.addComponent(jButton2))
							.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(jLabelOutput)
							.addContainerGap(21, Short.MAX_VALUE))
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE))
					.addComponent(textfieldfrecuencia)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE))
					.addComponent(textfieldtiempo)

					);

			pack();
		}// </editor-fold>//GEN-END:initComponents

		private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)  {//GEN-FIRST:event_jButton1ActionPerformed
			frecuencia1=textfieldfrecuencia.getText().toString();
			frecuencia=Integer.parseInt(frecuencia1);
			tiempo1=textfieldtiempo.getText().toString();
			tiempo=Integer.parseInt(tiempo1);
			for(int i=0;i<frecuencia*tiempo;i++) {
				try {
					ino.sendData("1");
				} catch (ArduinoException | SerialPortException ex) {
					Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
				}

			}
	
		}//GEN-LAST:event_jButton1ActionPerformed

		private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
			//Se hace el envío del String "0"
			try {
				ino.sendData("0");
			} catch (ArduinoException | SerialPortException ex) {
				Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			}
		}//GEN-LAST:event_jButton2ActionPerformed

		/**
		 * @param args the command line arguments
		 */
		public static void main(String args[]) {
			/* Set the Nimbus look and feel */
			//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
			/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
			 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
			 */
			try {
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						javax.swing.UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (ClassNotFoundException ex) {
				java.util.logging.Logger.getLogger(JavaRXTX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			} catch (InstantiationException ex) {
				java.util.logging.Logger.getLogger(JavaRXTX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				java.util.logging.Logger.getLogger(JavaRXTX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			} catch (javax.swing.UnsupportedLookAndFeelException ex) {
				java.util.logging.Logger.getLogger(JavaRXTX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			}
			//</editor-fold>

			/* Create and display the form */
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					new JavaRXTX().setVisible(true);
				}
			});}


		// Variables declaration - do not modify//GEN-BEGIN:variables
		private javax.swing.JButton jButton1;
		private javax.swing.JButton jButton2;
		private javax.swing.JLabel jLabelOutput;}    // End of variables declaration//GEN-END:variables}