package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;



/**
 * La classe Joc representa la finestra del joc de memoria.
 * Hereta de JFrame per a proporcionar la funcionalitat del joc.
 */

public class Joc extends JFrame {

	private static Font font = new Font("Consolas", Font.PLAIN, 14);
	private static final long serialVersionUID = 1L;
	private String jugador;
	private Timer cronometre;
	private int segonsTranscurrits;
	private int grauDificultat;
	private List<JButton> llistaBotonsImg = new ArrayList<>();
	private List<ImageIcon> icones = new ArrayList<>();
	private List<JButton> llistaBotonsApretats = new ArrayList<>();
	private JPanel contentPane;
	private JButton btnComencarJoc;
	private JButton btnGuardarDades;
	private JButton btnObirRecords;
	private JButton btnTancarSessio;
	private JLabel lblTemps;
	private JLabel imageLabel;
	private JLabel lblJugador;
	private ImageIcon imageIcon;


	
	/**
     * Constructor de la classe Joc.
     * Configura la finestra per a mostrar el joc de memoria.
     */
	
	public Joc() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1005, 699);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		int leftPaneWidth = (int) (getWidth() * 0.2);
		contentPane.setLayout(null);

		btnComencarJoc = new JButton("Començar joc");
		btnComencarJoc.setBounds(30, 39, 171, 30);
		btnComencarJoc.setFont(font);
		contentPane.add(btnComencarJoc);

		btnGuardarDades = new JButton("Guardar dades");
		btnGuardarDades.setBounds(30, 89, 171, 30);
		btnGuardarDades.setFont(font);
		btnGuardarDades.setEnabled(false);
		contentPane.add(btnGuardarDades);

		btnObirRecords = new JButton("Obrir récords");
		btnObirRecords.setBounds(30, 549, 171, 30);
		btnObirRecords.setFont(font);
		contentPane.add(btnObirRecords);

		btnTancarSessio = new JButton("Tancar sessió");
		btnTancarSessio.setBounds(30, 599, 171, 30);
		btnTancarSessio.setFont(font);
		btnTancarSessio.setBackground(new Color(255, 128, 128));
		contentPane.add(btnTancarSessio);

		imageIcon = new ImageIcon("./src/recursos/memory.png");
		imageLabel = new JLabel();
		imageLabel.setIcon(imageIcon);
		imageLabel.setBounds(49, 167, leftPaneWidth - 50, 200);
		contentPane.add(imageLabel);

		lblTemps = new JLabel("00:00:00");
		lblTemps.setBounds(49, 462, 116, 57);
		lblTemps.setFont(new Font("Consolas", Font.PLAIN, 25));
		contentPane.add(lblTemps);

	}
	
	

	public List<ImageIcon> getIcones() {
		return icones;
	}

	public String getJugador() {
		return jugador;
	}

	public void setJugador(String jugador) {
		this.jugador = jugador;
	}

	public int getSegonsTranscurrits() {
		return segonsTranscurrits;
	}

	public int getGrauDificultat() {
		return grauDificultat;
	}

	public JLabel getLblTemps() {
		return lblTemps;
	}

	public void setLblTemps(JLabel lblTemps) {
		this.lblTemps = lblTemps;
	}

	public JButton getBtnComencarJoc() {
		return btnComencarJoc;
	}

	public JButton getBtnGuardarDades() {
		return btnGuardarDades;
	}

	public JButton getBtnObirRecords() {
		return btnObirRecords;
	}

	public JButton getBtnTancarSessio() {
		return btnTancarSessio;
	}

	public List<JButton> getllistaBotonsImg() {
		return llistaBotonsImg;
	}
	
	
	public List<JButton> getLlistaBotonsApretats() {
		return llistaBotonsApretats;
	}

	
	
	/**
	 * Assigna una imatge a un boto especific en el joc i actualitza el contingut del panell.
	 * @param boto JButton que representa el boto a actualitzar amb la imatge corresponent.
	 * @param index index que indica la posicio de la imatge a utilitzar dins de la llista d'icones.
	 */
	
	public void apretarBoto(JButton boto, int index) {
		boto.setIcon(icones.get(index));
		llistaBotonsApretats.add(boto);
		refrescaContentPane();
	}


	
	/**
	 * Genera la graella de botons amb les imatges corresponents proporcionades.
	 * @param imatges Llista de noms de fitxers d'imatges per a cada boto de la graella.
	 */
	
	@SuppressWarnings("unused")
	public void generaGraellaBotons(List<String> imatges) {
		
        int btnX = 272;
        int btnY = 39;
        int btnWidth = 125;
        int btnHeight = 125;
        int rowCount = 4;
        int totalButtons = this.grauDificultat;

        for (int i = 0; i < totalButtons; i++) {
        	final int index = i;
			JButton btnImg = new JButton("");
			btnImg.setIcon(null);

			Image img = null;
			try {
				img = ImageIO.read(new File(imatges.get(i)));
			} catch (IOException exc) {
				exc.printStackTrace();
			}
			ImageIcon icona = new ImageIcon(img);
			icones.add(icona);

			btnImg.setBounds(btnX, btnY, btnWidth, btnHeight);
			contentPane.add(btnImg);
			llistaBotonsImg.add(btnImg);

            btnX += 161;
            if ((i + 1) % rowCount == 0) {
                btnX = 272;
                btnY += 151;
            }
        }
	}
	
	
	
	/**
	 * Genera i mostra el label del jugador si aquest esta definit.
	 */

	public void generaLblJugador() {
		if (this.jugador != null) {
			lblJugador = new JLabel("JUGADOR/A: " + this.jugador.toUpperCase());
			lblJugador.setBounds(40, 432, 250, 20);
			lblJugador.setFont(new Font("Consolas", Font.PLAIN, 14));
			contentPane.add(lblJugador);
		}
	}

	
	/**
	 * Buida la llista de botons apretats durant el joc.
	 */
	
	public void buidaLlistaApretats() {
		llistaBotonsApretats.clear();
	}
	
	
	
	/**
	 * Accio realitzada en cas que la parella de botons seleccionats no sigui correcta.
	 * Esborra les icones dels botons apretats i buida la llista de botons apretats.
	 */
	
	public void parellaIncorrecta() {
	    
	    Timer temporitzador = new Timer(500, (actionEvent) -> {

	        for (int i = 0; i < llistaBotonsApretats.size(); i++) {
	            llistaBotonsApretats.get(i).setIcon(null);
	        }
	        
	        buidaLlistaApretats(); 
	    });
	    
	    temporitzador.setRepeats(false);
	    temporitzador.start();
	    
	}
	
	
	/**
	 * Cancel·la l'estat dels botons apretats durant el joc quan una parella es correcta.
	 */
	
	public void cancelaBotons() {
		for (int i = 0; i < llistaBotonsApretats.size(); i++) {
			llistaBotonsApretats.get(i).setEnabled(false);
		}
	}

	
	/**
	 * Mostra un quadre de dialeg per seleccionar la graella de joc desitjada (2x4 o 4x4).
	 * @return Retorna true si la seleccio es valida, en cas contrari, retorna false.
	 */
	
	public boolean mostrarMissatgeGrauDificultat() {

		boolean ok = false;
		String mensaje = "Quina graella vols jugar?";
		String titulo = "Grau de dificultat";
		Object[] opciones = { "2x4", "4x4" };

		int seleccio = JOptionPane.showOptionDialog(null, mensaje, titulo, JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

		if (seleccio != JOptionPane.CLOSED_OPTION) {

			ok = true;

			if (opciones[seleccio].equals("2x4")) {
				this.grauDificultat = 8;
			} else if (opciones[seleccio].equals("4x4")) {
				this.grauDificultat = 16;
			}
		} else {
			ok = false;
		}

		return ok;
	}

	/**
	 * Refresca el contingut del panell principal de la finestra del joc.
	 */
	
	public void refrescaContentPane() {
		contentPane.revalidate();
		contentPane.repaint();
	}
	
	
	/**
	 * Reinicia l'estat del joc, desactivant el botó de guardar dades i esborrant els botons de la graella.
	 */
	
	public void reiniciarEstat() {
		btnGuardarDades.setEnabled(false);
		if (llistaBotonsImg != null) {
			for (JButton btn : llistaBotonsImg) {
				contentPane.remove(btn);
			}
			llistaBotonsImg.clear();
		}
		contentPane.revalidate();
		contentPane.repaint();
	}

	
	/**
	 * Inicia el cronometre del joc, comptant el temps transcorregut.
	 */
	
	public void iniciarCronometre() {
		segonsTranscurrits = 0;
		cronometre = new Timer(1000, e -> {
			segonsTranscurrits++;
			actualitzaLblCronometre();
		});
		cronometre.start();
	}

	
	/**
	 * Dete el cronometre del joc i retorna els segons transcorreguts.
	 * @return Retorna el temps transcorregut en segons.
	 */

	public int detindreCronometre() {
		if (cronometre != null && cronometre.isRunning()) {
			cronometre.stop();
		}
		return this.segonsTranscurrits;
	}
	
	
	
	/**
	 * Actualitza el label del temps mostrat en el joc amb el temps transcorregut.
	 */
	
	private void actualitzaLblCronometre() {
		int horas = segonsTranscurrits / 3600;
		int minutos = (segonsTranscurrits % 3600) / 60;
		int segonsTranscurritsMostrar = segonsTranscurrits % 60;

		String tiempo = String.format("%02d:%02d:%02d", horas, minutos, segonsTranscurritsMostrar);
		lblTemps.setText(tiempo);
	}
	
	
	/**
	 * Mostra un missatge informatiu a traves d'un quadre de dialeg.
	 * @param missatge El missatge a mostrar.
	 * @param titol El titol del quadre de dialeg.
	 */
	
	public void mostrarMissatge(String missatge, String titol) {
		JOptionPane.showMessageDialog(null, missatge, titol, JOptionPane.INFORMATION_MESSAGE);
	}

	
	/**
	 * Tanca la finestra del joc, reiniciant l'estat i restablent els labels de jugador i temps.
	 */
	
	public void tancaJoc() {
		reiniciarEstat();
		lblJugador.setText("");
		lblTemps.setText("00:00:00");
		this.dispose();
	}

}