package View;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Panel pintado con un fondo rectangular redondeado
 * 
 * @author HeartLight
 * 
 * @version Final
 * 
 * @see JPanel
 *
 */
public class Menu extends JPanel{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Pintar el rectangulo redondeado en todo el jpanel
	 */
	protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.setColor(new Color(51,153,255));
	        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30,30);
	 }

}
