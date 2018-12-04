import java.awt.*;
import javax.swing.*;

public class Paleta extends JFrame {

	private JFrame frame = new JFrame("Paleta de Cores");
	private Color novaCor = null;
	
	public Paleta () {

		 this.novaCor = JColorChooser.showDialog(this.frame, "Escolha uma cor", this.frame.getBackground());

		if(this.novaCor != null){
			this.frame.getContentPane().setBackground(this.novaCor);
		}
		
			System.out.println("COR = " + this.novaCor);
			
		Container pane = this.frame.getContentPane();
		pane.setLayout(new FlowLayout());

		this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.frame.setSize(300, 200);
	}
	
	public Color getNovaCor() {
		return this.novaCor;
	}
}