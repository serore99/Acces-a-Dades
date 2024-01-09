package clases;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import static com.mongodb.client.model.Filters.*;
import javax.imageio.ImageIO;
import javax.swing.Icon;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;


/**
 * La classe Model conté la lògica principal de l'aplicació, com la gestió d'usuaris, la connexió amb la base de dades i altres funcionalitats relacionades.
 * Tambe s'encarrega de la manipulació d'imatges, el guardatge de logs i altres funcionalitats essencials per al funcionament de l'aplicacio.
 */

public class Model {

	MongoCollection<Document> colUsers;
	MongoCollection<Document> colRecords;
	MongoCollection<Document> colImatges;
	public static List<Logs> llistaRecordsFacil = new ArrayList<Logs>();
	public static List<Logs> llistaRecordsDificil = new ArrayList<Logs>();

	
	/**
     * Constructor de la classe Model. Inicialitza les col·leccions de MongoDB i carrega les imatges a disc.
     */
	
	public Model() {

		Connexio c = new Connexio();
		colUsers = c.getUsuaris();
		colRecords = c.getRecords();
		colImatges = c.getImatges();

		carregaImatgesADisc();
		carregaLlistesLogs();

	}
	
	
	/**
     * Verifica si dues contrasenyes coincideixen.
     *
     * @param contra1 Primera contrasenya a comparar.
     * @param contra2 Segona contrasenya a comparar.
     * @return {@code true} si les contrasenyes coincideixen; {@code false} si no coincideixen.
     */

	public boolean confirmaContrasenya(String contra1, String contra2) {
		boolean iguals = false;
		if (contra1.equals(contra2)) {
			iguals = true;
		}
		return iguals;
	}

	
	 /**
     * Comprova si existeix un usuari en la base de dades.
     *
     * @param user Nom d'usuari a buscar.
     * @return {@code true} si l'usuari existeix; {@code false} si no existeix.
     */
	
	public boolean existeixUsuari(String user) {
		boolean existeix = false;

		MongoCursor<Document> cursor = colUsers.find().iterator();
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			if (user.equals(obj.getString("user"))) {
				existeix = true;
			}
		}

		return existeix;
	}

	
	/**
     * Comprova si les credencials d'inici de sessió són correctes.
     *
     * @param usuari     Nom d'usuari per iniciar sessió.
     * @param contrasenya Contrasenya de l'usuari per iniciar sessió.
     * @return 1 si les credencials són correctes, 0 si la contrasenya és incorrecta, -1 si l'usuari no existeix.
     */
	
	public int loginCorrecte(String usuari, String contrasenya) {
		int estat = -1; 

		if (existeixUsuari(usuari)) {
			MongoCursor<Document> cursor = colUsers.find().iterator();
			while (cursor.hasNext()) {
				JSONObject obj = new JSONObject(cursor.next().toJson());
				if (usuari.equals(obj.getString("user"))
						&& DigestUtils.sha256Hex(contrasenya).equals(obj.get("pass"))) {
					estat = 1; 
					break; 
				} else {
					estat = 0; 
				}
			}
		}

		return estat;
	}

	
	
	/**
     * Registra un nou usuari a la base de dades.
     *
     * @param user    Nom d'usuari del nou usuari.
     * @param contra1 Primera contrasenya del nou usuari.
     * @param contra2 Segona contrasenya del nou usuari per confirmar.
     * @return 0 si tot és correcte, 1 si l'usuari ja existeix, 2 si les contrasenyes no coincideixen, 3 si hi ha camps buits.
     */
	
	public int registraUsuari(String user, String contra1, String contra2) {

		if (user.isBlank() || contra1.isBlank() || contra2.isBlank()) {
			return 3;
		} else {
			if (!existeixUsuari(user)) {
				if (confirmaContrasenya(contra1, contra2)) {

					Document doc = new Document();
					doc.append("user", user);
					doc.append("pass", DigestUtils.sha256Hex(contra1));
					colUsers.insertOne(doc);
					return 0;

				} else {
					return 2;
				}
			} else {
				return 1;
			}
		}
	}

	
	/**
     * Guarda un log de la partida jugada a la base de dades.
     *
     * @param log Log de la partida per guardar.
     */
	
	public void guardaLog(Logs log) {

		Document document = new Document("usuario", log.getNomUsuari()).append("dificultad", log.getDificultat())
				.append("timestamp", log.getHoraPartida()).append("duracion", log.getDuracioPartida());

		colRecords.insertOne(document);

		if (log.getDificultat() == 8) {
			llistaRecordsFacil.add(log);
		} else if (log.getDificultat() == 16) {
			llistaRecordsDificil.add(log);
		}
	}

	
	/**
     * Carrega els logs de les partides jugades de la base de dades a les llistes corresponents.
     */
	
	public void carregaLlistesLogs() {

		Bson filtreDif8 = eq("dificultad", 8);

		MongoCursor<Document> cursor = colRecords.find(filtreDif8).iterator();
		while (cursor.hasNext()) {

			JSONObject obj = new JSONObject(cursor.next().toJson());
			String usu = obj.getString("usuario");
			int dificultad = obj.getInt("dificultad");
			String timeStamp = obj.getString("timestamp");
			int duracion = obj.getInt("duracion");

			Logs l = new Logs(usu, dificultad, duracion, timeStamp);
			llistaRecordsFacil.add(l);

		}

		Bson filtreDif16 = eq("dificultad", 16);

		MongoCursor<Document> cursor2 = colRecords.find(filtreDif16).iterator();
		while (cursor2.hasNext()) {

			JSONObject obj = new JSONObject(cursor2.next().toJson());
			String usu = obj.getString("usuario");
			int dificultad = obj.getInt("dificultad");
			String timeStamp = obj.getString("timestamp");
			int duracion = obj.getInt("duracion");

			Logs l = new Logs(usu, dificultad, duracion, timeStamp);
			llistaRecordsDificil.add(l);

		}
	}

	
	
	/**
     * Carrega les imatges de la base de dades al disc.
     *
     */
	
	@SuppressWarnings("unused")
	public void carregaImatgesADisc() {

		MongoCursor<Document> cursor = colImatges.find().iterator();
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			byte[] btDataFile = Base64.decodeBase64(obj.getString("base64"));
			BufferedImage imatge;
			try {
				imatge = ImageIO.read(new ByteArrayInputStream(btDataFile));
				Image img = imatge.getScaledInstance(-1, 400, java.awt.Image.SCALE_SMOOTH);
				ImageIO.write(imatge, "jpg", new File("./src/img/" + obj.getString("id")));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	
	/**
     * Carrega les imatges del joc en mode 8 cartes.
     *
     * @return Llista de rutes de les imatges per al mode de joc de 8 cartes.
     */
	
	public List<String> carregaImatges8() {
		LinkedList<String> llistaImatgesJoc = new LinkedList<>();
		List<String> llistaImatgesSensera = carregaArrayImatges();

		int n = 4;
		List<Integer> listaNums = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int num = (int) (Math.random() * llistaImatgesSensera.size());
			if (!listaNums.contains(num)) {
				listaNums.add(num);
			} else {
				i--;
			}
		}

		for (int i = 0; i < listaNums.size(); i++) {
			llistaImatgesJoc.add(llistaImatgesSensera.get(listaNums.get(i)));
			llistaImatgesJoc.add(llistaImatgesSensera.get(listaNums.get(i)));
		}

		Collections.shuffle(llistaImatgesJoc);

		return llistaImatgesJoc;
	}

	
	/**
     * Carrega les imatges del joc en mode 16 cartes.
     *
     * @return Llista de rutes de les imatges per al mode de joc de 16 cartes.
     */
	
	public List<String> carregaImatges16() {
		LinkedList<String> llistaImatgesJoc = new LinkedList<>();
		List<String> llistaImatgesSensera = carregaArrayImatges();

		int n = 8;
		List<Integer> listaNums = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int num = (int) (Math.random() * llistaImatgesSensera.size());
			if (!listaNums.contains(num)) {
				listaNums.add(num);
			} else {
				i--;
			}
		}

		for (int i = 0; i < listaNums.size(); i++) {
			llistaImatgesJoc.add(llistaImatgesSensera.get(listaNums.get(i)));
			llistaImatgesJoc.add(llistaImatgesSensera.get(listaNums.get(i)));
		}

		Collections.shuffle(llistaImatgesJoc);

		return llistaImatgesJoc;
	}
	
	
	/**
     * Carrega una llista de rutes d'imatges predefinides.
     *
     * @return Llista de rutes d'imatges predefinides.
     */
	
	public List<String> carregaArrayImatges() {
		List<String> llistaImatges = new ArrayList<>();
		llistaImatges.add("src/img/key.jpg");
		llistaImatges.add("src/img/labrador-head.jpg");
		llistaImatges.add("src/img/new-born.jpg");
		llistaImatges.add("src/img/robot-golem.jpg");
		llistaImatges.add("src/img/rocket.jpg");
		llistaImatges.add("src/img/rolling-dices.jpg");
		llistaImatges.add("src/img/shambling-zombie.jpg");
		llistaImatges.add("src/img/wolf-head.jpg");

		return llistaImatges;
	}

	
	
	/**
     * Comprova si dos icones representen les mateixes imatges.
     *
     * @param icon1 Primer icona a comparar.
     * @param icon2 Segona icona a comparar.
     * @return Cert si les imatges representades pels icones son les mateixes, Fals si son diferents o algun dels icones es nul.
     */
	
	public boolean sonImatgesIguals(Icon icon1, Icon icon2) {
		if (icon1 != null && icon2 != null) {
			BufferedImage img1 = obtenirImatgeDesdeIcona(icon1);
			BufferedImage img2 = obtenirImatgeDesdeIcona(icon2);

			if (img1 != null && img2 != null) {
				byte[] img1Bytes = obtenirBytesDesdeImatge(img1);
				byte[] img2Bytes = obtenirBytesDesdeImatge(img2);

				return Arrays.equals(img1Bytes, img2Bytes);
			}
		}
		return false;
	}

	
	
	/**
     * Obté una imatge en memoria (BufferedImage) a partir d'una icona.
     *
     * @param icona Icona a partir de la qual es vol obtenir la imatge.
     * @return La imatge en memoria (BufferedImage) corresponent a la icona.
     */
	
	private BufferedImage obtenirImatgeDesdeIcona(Icon icona) {
		BufferedImage imatgeEnMemoria = new BufferedImage(icona.getIconWidth(), icona.getIconHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics g = imatgeEnMemoria.createGraphics();
		icona.paintIcon(null, g, 0, 0);
		g.dispose();
		return imatgeEnMemoria;
	}

	
	/**
     * Obte un conjunt de bytes a partir d'una imatge en memoria (BufferedImage).
     *
     * @param imatge La imatge de la qual s'obtindra el conjunt de bytes.
     * @return El conjunt de bytes de la imatge en memoria.
     */
	
	private byte[] obtenirBytesDesdeImatge(BufferedImage imatge) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(imatge, "png", baos);
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	
	
	/**
     * Genera una marca de temps en format de cadena de caracters.
     *
     * @return Marca de temps en format ddMMyyyy_HH:mm:ss.
     */

	public String generaTimeStamp() {

		Date dataHoraActual = new Date();
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy_HH:mm:ss");
		String marcaTemps = format.format(dataHoraActual);

		return marcaTemps;
	}

}