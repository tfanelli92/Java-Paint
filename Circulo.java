import java.awt.*;
import java.util.*;

public class Circulo extends Figura
{
    protected Ponto centro;
    protected int   raio;
	
    public Circulo (int x, int y, int r)
    {
        this (x, y, r, Color.BLACK);
    }
	
    public Circulo (int x, int y, int r, Color cor)
    {
        super (cor);

        this.centro = new Ponto (x,y);
        this.raio   = r;
    }

    public Circulo (String s)
    {
        StringTokenizer quebrador = new StringTokenizer(s,":");

        quebrador.nextToken();

        int   x   = Integer.parseInt(quebrador.nextToken());
        int   y   = Integer.parseInt(quebrador.nextToken());

        int   r   = Integer.parseInt(quebrador.nextToken());

        Color cor = new Color (Integer.parseInt(quebrador.nextToken()),  // R
                               Integer.parseInt(quebrador.nextToken()),  // G
                               Integer.parseInt(quebrador.nextToken())); // B

        this.centro = new Ponto (x,y,cor);
        this.raio   = r;
        this.cor    = cor;
    }

    public void setCentro (int x, int y)
    {
        this.centro = new Ponto (x,y,this.getCor());
    }

    public void setRaio (int r)
    {
        this.raio = r;
    }

    public Ponto getCentro ()
    {
        return this.centro;
    }

    public int setRaio ()
    {
        return this.raio;
    }

    public void torneSeVisivel (Graphics g)
    {
        g.setColor (this.cor);
        g.drawOval (this.centro.getX()-raio, this.centro.getY()-raio, 2*raio, 2*raio);	
    }

    public String toString()
    {
        return "c:" +
               this.centro.getX() +
               ":" +
               this.centro.getY() +
               ":" +
               this.raio +
               ":" +
               this.getCor().getRed() +
               ":" +
               this.getCor().getGreen() +
               ":" +
               this.getCor().getBlue();
    }

	public int hashCode() {
		
		int primo = 31, ret = super.hashCode();
		
		ret = ret * primo + this.centro.hashCode();
		
		ret = ret * primo + new Integer(this.raio);
		
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
		
		Circulo circulo = (Circulo) obj;
		
		if(circulo.centro != this.centro || circulo.raio != this.raio) {
			return false;
		}

		return true;
	}      
	
    public Circulo (Circulo modelo) throws Exception {
    	
    	if(modelo == null) {
    		throw new Exception ("Modelo vazio");
    	}
    	
    	this.centro = modelo.centro;
    	this.raio = modelo.raio;
    }
    
    public Object clone() {
    	
    	Circulo ret = null;
    	
    	try {
    		ret = new Circulo(this);
    	}
    	
    	catch (Exception e){}
    	
    	return ret;
    }
}