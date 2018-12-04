import java.awt.*;
import javax.swing.*;

public class Paleta extends JFrame {

	public Paleta () {

		final JFrame frame = new JFrame("Paleta de Cores");

		Color novaCor = JColorChooser.showDialog(frame, "Escolha uma cor", frame.getBackground());

		if(novaCor != null){
			frame.getContentPane().setBackground(novaCor);
			System.out.println("COR = " + novaCor);
		}
		
		else {
			System.out.println("COR = " + novaCor);
		}

		Container pane = frame.getContentPane();
		pane.setLayout(new FlowLayout());

		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setSize(300, 200);
	}
}