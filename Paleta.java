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
	
    public String toString()
    {
    	String cor = this.novaCor.toString();
    	
        return cor;
    }
	
	public Color getNovaCor() {
		return this.novaCor;
	}
	
	public int hashCode() {

		int primo = 31, ret = super.hashCode();

		ret = ret * primo + this.novaCor.hashCode();
		
		ret = ret * primo + this.frame.hashCode();

		return ret;
	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		if (!super.equals(obj))
			return false;

		Paleta paleta = (Paleta) obj;

		if(paleta.novaCor != this.novaCor || paleta.frame != this.frame) {
			return false;
		}

		return true;
	} 
}