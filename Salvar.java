import java.io.*;

public class Salvar
{
    public static void main (String[] args)
    {
        try
        {
            PrintWriter gravador = new PrintWriter (
                                   new FileWriter (
                                   "arquivo.txt"));

            gravador.println ("Edezio");
            gravador.println ("Marcella");
            gravador.println ("Jefferson");
            gravador.println ("Francisco");
            gravador.println ("Renato");
            gravador.println ("Talita");
            gravador.println ("Rafael");
            gravador.println ("Sirley");
            gravador.println ("Kamila");
            gravador.println ("Lucas");
            gravador.println ("Gabriel");
            gravador.println ("Vitor Sa");
            gravador.println ("Giuliano");

            gravador.close();
        }
        catch (Exception erro)
        {}
    }
}