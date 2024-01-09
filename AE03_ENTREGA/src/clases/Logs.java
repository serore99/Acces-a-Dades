package clases;


/**
 * Classe que representa les dades dels logs d'una partida.
 */

public class Logs {
	
	private String nomUsuari;
	private int dificultat;
	private int duracioPartida;
	private String horaPartida;
	

	public Logs() {
		super();
	}


	public Logs(String nomUsuari, int dificultat, int duracioPartida, String horaPartida) {
		super();
		this.nomUsuari = nomUsuari;
		this.dificultat = dificultat;
		this.duracioPartida = duracioPartida;
		this.horaPartida = horaPartida;
	}


	public String getNomUsuari() {
		return nomUsuari;
	}


	public void setNomUsuari(String nomUsuari) {
		this.nomUsuari = nomUsuari;
	}


	public int getDificultat() {
		return dificultat;
	}


	public void setDificultat(int dificultat) {
		this.dificultat = dificultat;
	}


	public int getDuracioPartida() {
		return duracioPartida;
	}


	public void setDuracioPartida(int duracioPartida) {
		this.duracioPartida = duracioPartida;
	}


	public String getHoraPartida() {
		return horaPartida;
	}


	public void setHoraPartida(String horaPartida) {
		this.horaPartida = horaPartida;
	}
	
	
	
	
	

}
