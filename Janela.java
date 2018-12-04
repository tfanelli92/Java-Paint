import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

public class Janela extends JFrame 
{
	protected static final long serialVersionUID = 1L;

	protected JButton btnPonto   = new JButton ("Ponto"),
			btnLinha   = new JButton ("Linha"),
			btnCirculo = new JButton ("Circulo"),
			btnElipse  = new JButton ("Elipse"),
			btnCores   = new JButton ("Cores"),
			btnAbrir   = new JButton ("Abrir"),
			btnSalvar  = new JButton ("Salvar"),
			btnApagar  = new JButton ("Apagar"),
			btnSair    = new JButton ("Sair");

	protected MeuJPanel pnlDesenho = new MeuJPanel ();

	protected JLabel statusBar1 = new JLabel ("Mensagem:"),
			statusBar2 = new JLabel ("Coordenada:");

	protected boolean esperaPonto, esperaInicioReta, esperaFimReta;

	protected Color corAtual = Color.BLACK;
	protected Ponto p1;

	protected Vector<Figura> figuras = new Vector<Figura>();
	protected Vector<String> linhasArquivo = new Vector<String>();

	public Janela ()
	{
		super("Editor Gráfico");

		try
		{
			Image btnPontoImg = ImageIO.read(getClass().getResource("ponto.jpg"));
			btnPonto.setIcon(new ImageIcon(btnPontoImg));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog (null,
					"Arquivo ponto.jpg não foi encontrado",
					"Arquivo de imagem ausente",
					JOptionPane.WARNING_MESSAGE);
		}

		try
		{
			Image btnLinhaImg = ImageIO.read(getClass().getResource("linha.jpg"));
			btnLinha.setIcon(new ImageIcon(btnLinhaImg));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog (null,
					"Arquivo linha.jpg não foi encontrado",
					"Arquivo de imagem ausente",
					JOptionPane.WARNING_MESSAGE);
		}

		try
		{
			Image btnCirculoImg = ImageIO.read(getClass().getResource("circulo.jpg"));
			btnCirculo.setIcon(new ImageIcon(btnCirculoImg));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog (null,
					"Arquivo circulo.jpg não foi encontrado",
					"Arquivo de imagem ausente",
					JOptionPane.WARNING_MESSAGE);
		}

		try
		{
			Image btnElipseImg = ImageIO.read(getClass().getResource("elipse.jpg"));
			btnElipse.setIcon(new ImageIcon(btnElipseImg));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog (null,
					"Arquivo elipse.jpg não foi encontrado",
					"Arquivo de imagem ausente",
					JOptionPane.WARNING_MESSAGE);
		}

		try
		{
			Image btnCoresImg = ImageIO.read(getClass().getResource("cores.jpg"));
			btnCores.setIcon(new ImageIcon(btnCoresImg));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog (null,
					"Arquivo cores.jpg não foi encontrado",
					"Arquivo de imagem ausente",
					JOptionPane.WARNING_MESSAGE);
		}

		try
		{
			Image btnAbrirImg = ImageIO.read(getClass().getResource("abrir.jpg"));
			btnAbrir.setIcon(new ImageIcon(btnAbrirImg));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog (null,
					"Arquivo abrir.jpg não foi encontrado",
					"Arquivo de imagem ausente",
					JOptionPane.WARNING_MESSAGE);
		}

		try
		{
			Image btnSalvarImg = ImageIO.read(getClass().getResource("salvar.jpg"));
			btnSalvar.setIcon(new ImageIcon(btnSalvarImg));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog (null,
					"Arquivo salvar.jpg não foi encontrado",
					"Arquivo de imagem ausente",
					JOptionPane.WARNING_MESSAGE);
		}

		try
		{
			Image btnApagarImg = ImageIO.read(getClass().getResource("apagar.jpg"));
			btnApagar.setIcon(new ImageIcon(btnApagarImg));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog (null,
					"Arquivo apagar.jpg não foi encontrado",
					"Arquivo de imagem ausente",
					JOptionPane.WARNING_MESSAGE);
		}

		try
		{
			Image btnSairImg = ImageIO.read(getClass().getResource("sair.jpg"));
			btnSair.setIcon(new ImageIcon(btnSairImg));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog (null,
					"Arquivo sair.jpg não foi encontrado",
					"Arquivo de imagem ausente",
					JOptionPane.WARNING_MESSAGE);
		}

		//adicionar clique

		btnPonto.addActionListener (new DesenhoDePonto());
		btnLinha.addActionListener (new DesenhoDeReta ());
		btnAbrir.addActionListener (new abrirArquivo());

		JPanel     pnlBotoes = new JPanel();
		FlowLayout flwBotoes = new FlowLayout(); 
		pnlBotoes.setLayout (flwBotoes);

		pnlBotoes.add (btnAbrir);
		pnlBotoes.add (btnSalvar);
		pnlBotoes.add (btnPonto);
		pnlBotoes.add (btnLinha);
		pnlBotoes.add (btnCirculo);
		pnlBotoes.add (btnElipse);
		pnlBotoes.add (btnCores);
		pnlBotoes.add (btnApagar);
		pnlBotoes.add (btnSair);

		JPanel     pnlStatus = new JPanel();
		GridLayout grdStatus = new GridLayout(1,2);
		pnlStatus.setLayout(grdStatus);

		pnlStatus.add(statusBar1);
		pnlStatus.add(statusBar2);

		Container cntForm = this.getContentPane();
		cntForm.setLayout (new BorderLayout());
		cntForm.add (pnlBotoes,  BorderLayout.NORTH);
		cntForm.add (pnlDesenho, BorderLayout.CENTER);
		cntForm.add (pnlStatus,  BorderLayout.SOUTH);

		this.addWindowListener (new FechamentoDeJanela());

		this.setSize (700,500);
		this.setVisible (true);
	}

	protected class MeuJPanel extends    JPanel 
	implements MouseListener,
	MouseMotionListener
	{
		public MeuJPanel()
		{
			super();

			this.addMouseListener       (this);
			this.addMouseMotionListener (this);
		}

		public void paint (Graphics g)
		{
			for (int i=0 ; i<figuras.size(); i++)
				figuras.get(i).torneSeVisivel(g);
		}

		public void mousePressed (MouseEvent e)
		{
			if (esperaPonto)
			{
				figuras.add (new Ponto (e.getX(), e.getY(), corAtual));
				figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
				esperaPonto = false;
			}
			else
				if (esperaInicioReta)
				{
					p1 = new Ponto (e.getX(), e.getY(), corAtual);
					esperaInicioReta = false;
					esperaFimReta = true;
					statusBar1.setText("Mensagem: clique o ponto final da reta");    
				}
				else
					if (esperaFimReta)
					{
						esperaInicioReta = false;
						esperaFimReta = false;
						figuras.add (new Linha(p1.getX(), p1.getY(), e.getX(), e.getY(), corAtual));
						figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
						statusBar1.setText("Mensagem:");    
					}
		}

		public void mouseReleased (MouseEvent e)
		{}

		public void mouseClicked (MouseEvent e)
		{}

		public void mouseEntered (MouseEvent e)
		{}

		public void mouseExited (MouseEvent e)
		{}

		public void mouseDragged(MouseEvent e)
		{}

		public void mouseMoved(MouseEvent e)
		{
			statusBar2.setText("Coordenada: "+e.getX()+","+e.getY());
		}
	}

	protected class abrirArquivo implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{
			statusBar1.setText("Mensagem: aguardando arquivo...");

			Abrir diretorio = new Abrir();

			File arquivo = diretorio.getDiretorio();

			if(arquivo != null) {

				if(arquivo.exists() && !diretorio.arquivoVazio(arquivo)) {

					String split[] = arquivo.toString().split("\\\\");

					String nomeArquivo = split[split.length-1];

					statusBar1.setText("Mensagem: " + nomeArquivo); //mostrar nome do diretorio
					linhasArquivo = diretorio.lerArquivo(arquivo);
					carregarArquivo(linhasArquivo);
				}

				else {
					statusBar1.setText("Mensagem: arquivo não encontrado ou vazio");
				}
			}

			else {
				statusBar1.setText("Mensagem: ");
			}
		}
	}

	protected void carregarArquivo (Vector <String> linhasArquivo) {

		int invalido = 0;

		for(int x = 0; x < linhasArquivo.size(); x++) {

			if(linhasArquivo.get(x) != null) {

				String split[] = linhasArquivo.get(x).toString().split(":");

				System.out.println(linhasArquivo.get(x));
				
				if(split[0].equals("p")) {

					try {
						if
						(
								(Integer.parseInt(split[1]) >= 0) && //x
								(Integer.parseInt(split[2]) >= 0) && //y
								(Integer.parseInt(split[3]) >= 0 && Integer.parseInt(split[3]) <= 255) && //RED
								(Integer.parseInt(split[4]) >= 0 && Integer.parseInt(split[4]) <= 255) && //GREEN
								(Integer.parseInt(split[5]) >= 0 && Integer.parseInt(split[5]) <= 255)    //BLUE
								) {		
							figuras.add (new Ponto (linhasArquivo.get(x)));
							figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
						}
						else {
							invalido++;
						}
					}

					catch (Exception e) {
						invalido++;
					}
				}

				else if(split[0].equals("l")) {

					try {
						if
						(
								(Integer.parseInt(split[1]) >= 0) && //x1
								(Integer.parseInt(split[2]) >= 0) && //y1
								(Integer.parseInt(split[3]) >= 0) && //x2
								(Integer.parseInt(split[4]) >= 0) && //y2
								(Integer.parseInt(split[5]) >= 0 && Integer.parseInt(split[5]) <= 255) && //RED
								(Integer.parseInt(split[6]) >= 0 && Integer.parseInt(split[6]) <= 255) && //GREEN
								(Integer.parseInt(split[7]) >= 0 && Integer.parseInt(split[7]) <= 255)    //BLUE
								) {		
							figuras.add (new Linha (linhasArquivo.get(x)));
							figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
						}
						else {
							invalido++;
						}
					}
					catch (Exception e) {
						invalido++;
					}
				}

				else if(split[0].equals("c")) {
					
					try {
						if
						(
								(Integer.parseInt(split[1]) >= 0) && //x
								(Integer.parseInt(split[2]) >= 0) && //y
								(Integer.parseInt(split[3]) >= 0) && //r
								(Integer.parseInt(split[4]) >= 0 && Integer.parseInt(split[4]) <= 255) && //RED
								(Integer.parseInt(split[5]) >= 0 && Integer.parseInt(split[5]) <= 255) && //GREEN
								(Integer.parseInt(split[6]) >= 0 && Integer.parseInt(split[6]) <= 255)    //BLUE
								) {		
							figuras.add (new Circulo (linhasArquivo.get(x)));
							figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
						}
						else {
							invalido++;
						}
					}
					catch (Exception e) {
						invalido++;
					}
				}

				else if(split[0].equals("e")) {
				
					try {
						if
						(
								(Integer.parseInt(split[1]) >= 0) && //x
								(Integer.parseInt(split[2]) >= 0) && //y
								(Integer.parseInt(split[3]) >= 0) && //r1
								(Integer.parseInt(split[4]) >= 0) && //r2
								(Integer.parseInt(split[5]) >= 0 && Integer.parseInt(split[5]) <= 255) && //RED
								(Integer.parseInt(split[6]) >= 0 && Integer.parseInt(split[6]) <= 255) && //GREEN
								(Integer.parseInt(split[7]) >= 0 && Integer.parseInt(split[7]) <= 255)    //BLUE
								) {		
							figuras.add (new Elipse (linhasArquivo.get(x)));
							figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
						}	
					}
					catch(Exception e) {
						invalido++;
					}
				}

				else {
					invalido++;
				}
			}
		}

		if(invalido > 0) {
			statusBar1.setText("Mensagem: falha na leitura do arquivo, conteúdo inválido"); //mostrar erro
		}
	}

	protected class DesenhoDePonto implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{
			esperaPonto      = true;
			esperaInicioReta = false;
			esperaFimReta    = false;

			statusBar1.setText("Mensagem: clique o local do ponto desejado");
		}
	}

	protected class DesenhoDeReta implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{
			esperaPonto      = false;
			esperaInicioReta = true;
			esperaFimReta    = false;

			statusBar1.setText("Mensagem: clique o ponto inicial da reta");
		}
	}

	protected class FechamentoDeJanela extends WindowAdapter
	{
		public void windowClosing (WindowEvent e)
		{
			System.exit(0);
		}
	}
}
