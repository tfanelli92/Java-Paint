//
import java.awt.*;
import javax.swing.*;

public class Paleta extends JFrame {

	private JFrame frame = new JFrame("Paleta de Cores");
	private Color novaCor = null;
	
	public Paleta () {
		
		 this.novaCor = JColorChooser.showDialog(this.frame, "Escolha uma cor", this.frame.getBackground());

		if(this.novaCor != null && !this.novaCor.getClass().getName().equals("javax.swing.plaf.ColorUIResource")){
			this.frame.getContentPane().setBackground(this.novaCor);
		}
		
		else {
			this.novaCor = null;
		}

		Container pane = this.frame.getContentPane();
		pane.setLayout(new FlowLayout());

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(300, 200);
	}
	
	public Color getNovaCor() {
		return this.novaCor;
	}
}