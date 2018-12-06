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

	protected boolean esperaPonto, esperaInicioReta, esperaFimReta, esperaInicioRaioCirculo, esperaFimRaioCirculo, esperaInicioElipse, esperaFimElipse, esperaIniciarApagar;

	protected Color corAtual = Color.BLACK;
	protected Ponto p1;
	protected Ponto p2;

	protected Vector<Figura> figuras = new Vector<Figura>();
	protected Vector<Figura> pontoApagado = new Vector<Figura>();
	protected Vector<String> linhasArquivo = new Vector<String>();

	public Janela ()
	{
		super("Editor Grafico");

		try
		{
			Image btnPontoImg = ImageIO.read(getClass().getResource("ponto.jpg"));
			btnPonto.setIcon(new ImageIcon(btnPontoImg));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog (null,
					"Arquivo ponto.jpg n�o foi encontrado",
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
					"Arquivo linha.jpg n�o foi encontrado",
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
					"Arquivo circulo.jpg n�o foi encontrado",
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
					"Arquivo elipse.jpg n�o foi encontrado",
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
					"Arquivo cores.jpg n�o foi encontrado",
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
					"Arquivo abrir.jpg n�o foi encontrado",
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
					"Arquivo salvar.jpg n�o foi encontrado",
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
					"Arquivo apagar.jpg n�o foi encontrado",
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
					"Arquivo sair.jpg n�o foi encontrado",
					"Arquivo de imagem ausente",
					JOptionPane.WARNING_MESSAGE);
		}

		//adicionar clique
		btnAbrir.addActionListener   (new AbrirArquivo());
		btnSalvar.addActionListener  (new SalvarArquivo());
		btnPonto.addActionListener   (new DesenhoDePonto());
		btnLinha.addActionListener   (new DesenhoDeReta ());
		btnCirculo.addActionListener (new DesenhoDeCirculo());
		btnElipse.addActionListener  (new DesenhoDeElipse());
		btnCores.addActionListener   (new SelecionarCor());
		btnApagar.addActionListener  (new Apagar());
		btnSair.addActionListener    (new Sair());

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

		this.setSize (900,500);
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
				esperaPonto = true;
				statusBar1.setText("Mensagem: ");
			}
			else
				if (esperaInicioReta)
				{
					p1 = new Ponto (e.getX(), e.getY(), corAtual);
					esperaInicioReta = false;
					esperaFimReta = true;
					statusBar1.setText("Mensagem: arraste e clique para terminar a reta");    
				}
				else
					if (esperaFimReta)
					{
						esperaInicioReta = true;
						esperaFimReta = false;
						figuras.add (new Linha(p1.getX(), p1.getY(), e.getX(), e.getY(), corAtual));
						figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
						statusBar1.setText("Mensagem:");    
					}
					else
						if (esperaInicioRaioCirculo)
						{
							p1 = new Ponto (e.getX(), e.getY(), corAtual);
							esperaInicioRaioCirculo = false;
							esperaFimRaioCirculo = true;
							statusBar1.setText("Mensagem: arraste e clique para terminar o circulo");
						}
						else
							if (esperaFimRaioCirculo)
							{
								esperaInicioRaioCirculo = true;
								esperaFimRaioCirculo = false;

								p2 = new Ponto (e.getX(), e.getY(), corAtual);

								int raio = (p1.getX() - p2.getX());

								figuras.add (new Circulo(p1.getX(), p1.getY(), Math.abs(raio), corAtual));
								figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
							}
							else
								if(esperaInicioElipse)
								{
									p1 = new Ponto (e.getX(), e.getY(), corAtual);
									esperaInicioElipse      = false;
									esperaFimElipse         = true;
									statusBar1.setText("Mensagem: arraste e clique para terminar a elipse");
								}
								else
									if(esperaFimElipse)
									{
										esperaInicioElipse      = true;
										esperaFimElipse         = false;
										
										int r1     = Math.abs(p1.getX() - e.getX())/2;
										int r2     = Math.abs(p1.getY() - e.getY())/2;

										Ponto centro = new Ponto ((e.getX() + p1.getX())/2, (e.getY() + p1.getY())/2, corAtual);

										figuras.add (new Elipse(centro.getX(), centro.getY(), r1, r2, corAtual));
										figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
									}
		}

		public void mouseReleased (MouseEvent e)
		{}

		public void mouseClicked (MouseEvent e)
		{
			if(esperaPonto) {
				
				statusBar1.setText("Mensagem: clique para marcar o ponto");
				esperaPonto      = true;
				esperaInicioReta = false;
				esperaFimReta    = false;
				esperaInicioRaioCirculo = false;
				esperaFimRaioCirculo = false;
				esperaInicioElipse = false;
				esperaFimElipse = false;
				esperaIniciarApagar = false;
			}
			
			else if(esperaInicioReta) {
				
				statusBar1.setText("Mensagem: clique para iniciar a reta");  
				esperaPonto      = false;
				esperaInicioReta = true;
				esperaFimReta    = false;
				esperaInicioRaioCirculo = false;
				esperaFimRaioCirculo = false;
				esperaInicioElipse = false;
				esperaFimElipse = false;
				esperaIniciarApagar = false;
			}
			
			else if(esperaInicioRaioCirculo) {
				
				statusBar1.setText("Mensagem: clique o ponto (x) do centro do circulo"); 
				esperaPonto      = false;
				esperaInicioReta = false;
				esperaFimReta    = false;
				esperaInicioRaioCirculo = true;
				esperaFimRaioCirculo = false;
				esperaInicioElipse = false;
				esperaFimElipse = false;
				esperaIniciarApagar = false;
			}
			
			else if (esperaInicioElipse) {
				
				statusBar1.setText("Mensagem: clique o ponto (x) do centro da elipse");
				
				esperaPonto      = false;
				esperaInicioReta = false;
				esperaFimReta    = false;
				esperaInicioRaioCirculo = false;
				esperaFimRaioCirculo = false;
				esperaInicioElipse = true;
				esperaFimElipse = false;
				esperaIniciarApagar = false;
			}
		}

		public void mouseEntered (MouseEvent e)
		{
			if (esperaPonto) {
				statusBar1.setText("Mensagem: clique para marcar o ponto");
			}

			else if (esperaInicioReta) 	{
				statusBar1.setText("Mensagem: clique para iniciar a reta");   
			}

			else if (esperaInicioRaioCirculo) {
				statusBar1.setText("Mensagem: clique o ponto (x) do centro do circulo");
			}

			else if(esperaInicioElipse) {
				statusBar1.setText("Mensagem: clique o ponto (x) do centro da elipse");
			}

			else if (esperaIniciarApagar) {
				statusBar1.setText("Mensagem: clique e arraste o mouse na figura");
			}

			else {
				statusBar1.setText("Mensagem: ");
			}
		}

		public void mouseExited (MouseEvent e)
		{
			statusBar1.setText("Mensagem: Nao pode desenhar aqui...");
		}

		public void mouseDragged(MouseEvent e)
		{		
			statusBar2.setText("Coordenada: "+e.getX()+","+e.getY());

			if(esperaIniciarApagar) {

				figuras.add(new Ponto (e.getX(), e.getY(), pnlDesenho.getBackground()));
				figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
			}			
		}

		public void mouseMoved(MouseEvent e)
		{
			statusBar2.setText("Coordenada: "+e.getX()+","+e.getY());
		}
	}

	protected class AbrirArquivo implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{
			statusBar1.setText("Mensagem: Abrindo arquivo...");

			Abrir diretorio = new Abrir();

			File arquivo = diretorio.getDiretorio();

			if(arquivo != null) {

				if(arquivo.exists() && !diretorio.arquivoVazio(arquivo)) {

					statusBar1.setText("Mensagem: " + arquivo.getName()); //mostrar nome do diretorio
					linhasArquivo = diretorio.lerArquivo(arquivo);
					CarregarArquivo(linhasArquivo);
				}

				else {
					statusBar1.setText("Mensagem: arquivo nao encontrado ou vazio");
				}
			}

			else {
				statusBar1.setText("Mensagem: ");
			}
		}
	}

	protected void CarregarArquivo (Vector <String> linhasArquivo) {

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
			statusBar1.setText("Mensagem: " + invalido + " linha(s) com erro na leitura"); //mostrar erro
		}	
	}

	protected class SalvarArquivo implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{
			statusBar1.setText("Mensagem: Salvando arquivo...");

			new Salvar(figuras);

			statusBar1.setText("Mensagem: ");
		}
	}

	protected class SelecionarCor implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{
			statusBar1.setText("Mensagem: aguardando cor...");

			Color novaCor = new Paleta().getNovaCor();

			if(novaCor != null) {
				corAtual = novaCor;
			}

			statusBar1.setText("Mensagem: ");
		}
	}

	protected class DesenhoDePonto implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{
			esperaPonto      = true;
			esperaInicioReta = false;
			esperaFimReta    = false;
			esperaInicioRaioCirculo = false;
			esperaFimRaioCirculo = false;
			esperaInicioElipse = false;
			esperaFimElipse = false;
			esperaIniciarApagar = false;	
		}
	}

	protected class DesenhoDeReta implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{		
			esperaPonto      = false;
			esperaInicioReta = true;
			esperaFimReta    = false;
			esperaInicioRaioCirculo = false;
			esperaFimRaioCirculo = false;
			esperaInicioElipse = false;
			esperaFimElipse = false;
			esperaIniciarApagar = false;
		}
	}

	protected class DesenhoDeCirculo implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{			
			esperaPonto      = false;
			esperaInicioReta = false;
			esperaFimReta    = false;
			esperaInicioRaioCirculo = true;
			esperaFimRaioCirculo = false;
			esperaInicioElipse = false;
			esperaFimElipse = false;
			esperaIniciarApagar = false;
		}
	}

	protected class DesenhoDeElipse implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{
			esperaPonto      = false;
			esperaInicioReta = false;
			esperaFimReta    = false;
			esperaInicioRaioCirculo = false;
			esperaFimRaioCirculo = false;
			esperaInicioElipse = true;
			esperaFimElipse = false;
			esperaIniciarApagar = false;
		}
	}

	protected class Sair implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{
			System.exit(0);
		}
	}

	protected class Apagar implements ActionListener
	{
		public void actionPerformed (ActionEvent e)    
		{		
			esperaPonto      = false;
			esperaInicioReta = false;
			esperaFimReta    = false;
			esperaInicioRaioCirculo = false;
			esperaFimRaioCirculo = false;
			esperaInicioElipse = false;
			esperaFimElipse = false;
			esperaIniciarApagar = true;
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
