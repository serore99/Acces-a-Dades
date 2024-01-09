package clases;
import vista.Joc;
import vista.Login;
import vista.Records;


/**
 * La classe Principal es responsable de l'inicialitzacio de les instancies de les classes i la creacio del controlador per gestionar-les.
 * Aquesta classe conte el metode principal main que inicia l'aplicacio.
 */

public class Principal {


	
	/**
     * Metode principal que inicialitza les instancies de les classes i crea un controlador per gestionar-les.
     * @param args Arguments passats a l'aplicacio en el moment de la seva execucio (no s'utilitzen en aquest cas).
     */
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Model m = new Model();
		Joc j = new Joc();
		Login l = new Login();
		Records r = new Records();
		Connexio con = new Connexio();

		Controlador c = new Controlador(m, l, j, r);

	}

}
