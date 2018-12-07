import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class Abrir
{
	private File arquivo = null;
	private Vector<String> linhasArquivo = new Vector<String>();

	public Abrir() {

		JFileChooser jfc = new JFileChooser();

		jfc.setDialogTitle("Salvar como");

		jfc.setAcceptAllFileFilterUsed(false);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNT (*.pnt)", "pnt");
		jfc.addChoosableFileFilter(filter);

		int returnValue = jfc.showOpenDialog(null);
		//int returnValue = jfc.showSaveDialog(null);

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
				this.linhasArquivo.add(leitor.readLine());
			}

			leitor.close();
		}
		catch (Exception erro)
		{System.out.println(erro);}

		return this.linhasArquivo;
	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Abrir abrir = (Abrir) obj;
		
		if(abrir.arquivo != this.arquivo || abrir.linhasArquivo != this.linhasArquivo) {
			return false;
		}

		if(abrir.linhasArquivo.size() != this.linhasArquivo.size()) {
			return false;
		}

		for(int x = 0; x < this.linhasArquivo.size(); x++) {
			if(abrir.linhasArquivo.get(x) != this.linhasArquivo.get(x))
				return false;
		}
		
		return true;
	}
	
	public String toString() {
		String split[] = this.arquivo.getName().split("\\\\");

		return split[split.length-1];
	}
	
	public int hashCode() {
		
		int primo = 31, ret = 1;
		
		ret = ret * primo + this.arquivo.hashCode();
		
		for(int x = 0; x < this.linhasArquivo.size(); x++) {
			ret = ret * primo + this.linhasArquivo.hashCode();
		}
		
		return ret;
	}
}
