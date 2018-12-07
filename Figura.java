import java.awt.*;

public abstract class Figura
{
    protected Color cor;
	  
    protected Figura ()
    {
        this (Color.BLACK);
    }
	  
    protected Figura (Color cor)
    {
        this.cor = cor;
    }
	  
    public void setCor (Color cor)
    {
        this.cor = cor;
    }
	  
    public Color getCor()
    {
    	return this.cor;
    }

  //public abstract boolean equals         (Object obj);
  //public abstract int     hashCode       ();
  //public abstract Object  clone          ();
    public abstract String  toString       ();
    public abstract void    torneSeVisivel (Graphics g);
	
    public int hashCode() {

		int primo = 31, ret = 1;
		
		ret = ret * primo + this.cor.hashCode();
		
		return ret;
	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Figura figura = (Figura) obj;
		
		if(figura.cor != this.cor) {
			return false;
		}
		
		return true;
	}
}