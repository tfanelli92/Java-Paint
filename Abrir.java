import java.io.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.filechooser.*;

public class Abrir
{
	private File arquivo = null;
	private Vector<String> linhasArquivo = new Vector<String>();

	public Abrir() {

		JFileChooser jfc = new JFileChooser();

		jfc.setDialogTitle("Selecione um arquivo");

		jfc.setAcceptAllFileFilterUsed(false);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNT (*.pnt)", "pnt");
		jfc.addChoosableFileFilter(filter);

		int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			this.arquivo = jfc.getSelectedFile();
		}
	}

	public File getDiretorio() {
		return this.arquivo;
	}

	public boolean arquivoVazio (File arquivo) {

		if(this.arquivo.length() > 0) {
			return false;
		}

		return true;
	}

	public Vector <String> lerArquivo (File arquivo) {
		
		try {
			BufferedReader leitor = new BufferedReader (new FileReader (this.arquivo));

			while (leitor.ready()) {
				linhasArquivo.add(leitor.readLine());
			}

			leitor.close();
		}
		catch (Exception erro)
		{System.out.println(erro);}
	
		return linhasArquivo;
	}
}

