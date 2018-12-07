import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Salvar {
	private File arquivo = null;

	public Salvar(Vector<Figura> figuras) {
		JFileChooser jfc = new JFileChooser();

		jfc.setDialogTitle("Salvar como");

		jfc.setAcceptAllFileFilterUsed(false);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNT (*.pnt)", "pnt");
		jfc.addChoosableFileFilter(filter);

		// int returnValue = jfc.showOpenDialog(null);
		int returnValue = jfc.showSaveDialog(null);

		while (returnValue == JFileChooser.APPROVE_OPTION) {

			this.arquivo = jfc.getSelectedFile();

			//juntar a extensão no arquivo
			String diretorio = this.arquivo.getAbsolutePath();
			
			if(!diretorio.endsWith(".pnt")) {
				this.arquivo = new File(diretorio + ".pnt");
			}

			if(this.arquivo.exists()) {

				int resposta = JOptionPane.showConfirmDialog(null,
						"O arquivo ''" + jfc.getSelectedFile().getName() + "'' já existe.\nDeseja substitui-lo?",
						"Confirmar", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					gravarArquivo(this.arquivo, figuras);
					break;
				}
			}

			else {
				if(nomeArquivoValido(this.arquivo)) {
					gravarArquivo(this.arquivo, figuras);
					break;
				}
			}
			
			returnValue = jfc.showSaveDialog(null);
		}
	}

	public boolean nomeArquivoValido (File arquivo) {

		for (int x = 0; x < arquivo.getName().length(); x++) {

			if (
					arquivo.getName().charAt(x) == '\\' ||
					arquivo.getName().charAt(x) == '/'  || 
					arquivo.getName().charAt(x) == '|'  || 
					arquivo.getName().charAt(x) == '*'  || 
					arquivo.getName().charAt(x) == '?'  || 
					arquivo.getName().charAt(x) == '<'  || 
					arquivo.getName().charAt(x) == '>'  || 
					arquivo.getName().charAt(x) == ':'  || 
					arquivo.getName().charAt(x) == '"'
					) {
				JOptionPane.showMessageDialog(null, "Caractere " + arquivo.getName().charAt(x) + " invalido", "Erro", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		return true;
	}

	public void gravarArquivo (File Arquivo, Vector<Figura> figuras) {

		try {
			PrintWriter gravador = new PrintWriter(new FileWriter(this.arquivo));

			for (int x = 0; x < figuras.size(); x++) {
				gravador.println(figuras.get(x).toString());
			}

			gravador.close();

				JOptionPane.showMessageDialog(null, "O arquivo ''" + arquivo.getName()
				+ "'' foi salvo com sucesso em ''" + arquivo.getParent() + "''", "Aviso",
				JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception erro) {
			System.out.println("Erro ao gravar = " + erro);
		}
	}
	
	public int hashCode() {

		int primo = 31, ret = 1;

		ret = ret * primo + this.arquivo.hashCode();

		return ret;
	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Salvar salvar = (Salvar) obj;

		if(salvar.arquivo != this.arquivo) {
			return false;
		}

		return true;
	} 
}