package clases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


/**
 * Classe que estableix la connexio amb la base de dades MongoDB.
 */

public class Connexio {
	
	private MongoCollection<Document> records;
	private MongoCollection<Document> usuaris;
	private MongoCollection<Document> imatges;
	MongoClient mongoClient;
	
	/**
	 * Constructor de la classe Connexio que estableix la connexio amb la base de dades.
	 */
	public Connexio() {

		JSONObject json = llegirJson();
		
		if(json != null) {
			
			String ip =json.getString("ip");
			int port = json.getInt("port");
			String database = json.getString("database");
			String colRecords = json.getString("collection_records");
			String colUsuaris = json.getString("collection_usuaris");
			String colImatges = json.getString("collection_imatges");
			
			mongoClient = new MongoClient(ip, port);
			MongoDatabase db = mongoClient.getDatabase(database);
			this.records = db.getCollection(colRecords);
			this.usuaris = db.getCollection(colUsuaris);
			this.imatges = db.getCollection(colImatges);
			
			
		}else {
			System.out.println("No s'ha pogut llegir el json");
		}
		
	}
	

	public MongoCollection<Document> getRecords() {
		return records;
	}


	public MongoCollection<Document> getUsuaris() {
		return usuaris;
	}


	public MongoCollection<Document> getImatges() {
		return imatges;
	}


	/**
	 * Llegeix les dades de connexió des d'un fitxer JSON.
	 *
	 * @return Un objecte JSON amb les dades de connexió.
	 */
	
	public JSONObject llegirJson() {
		String filePath = "./src/recursos/connexio.json";
		JSONObject jsonObject = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			StringBuilder ficheroJSON = new StringBuilder();
			String linea = br.readLine();

			while (linea != null) {
				ficheroJSON.append(linea);
				linea = br.readLine();
			}

			br.close();

			jsonObject = new JSONObject(ficheroJSON.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	
	
	/**
	 * Tanca la connexió amb la base de dades.
	 */
	public void desconnectarBD() {
		this.mongoClient.close();
	}
	


}
