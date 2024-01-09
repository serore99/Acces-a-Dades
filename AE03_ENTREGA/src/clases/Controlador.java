package clases;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import vista.Joc;
import vista.Login;
import vista.Records;


/**
 * Classe que actua com a controlador entre les vistes i el model.
 */

public class Controlador {

	@SuppressWarnings("unused")
	private Model model;
	@SuppressWarnings("unused")
	private Login login;
	@SuppressWarnings("unused")
	private Joc joc;
	@SuppressWarnings("unused")
	private Records records;
	
	
	
	private ActionListener actionListenerConectaLogin;
	private ActionListener actionListenerObriRecords;
	private ActionListener actionListenerTancaRecords;
	private ActionListener actionListenerTancaSessio;
	private ActionListener actionListenerComencaJoc;
	private ActionListener actionListenerGuardaDades;
	private ActionListener actionListenerCheckBoxLogin;

	
	
	/**
     * Constructor de la classe Controlador.
     *
     * @param model    El model de dades de l'aplicacio.
     * @param login    La vista de login.
     * @param joc      La vista del joc.
     * @param records  La vista dels records.
     */
	
	public Controlador(Model model, Login login, Joc joc, Records records) {
		super();
		this.model = model;
		this.login = login;
		this.joc = joc;
		this.records = records;

		login.setVisible(true);
		
		/**
	     * ActionListener per al checkbox a la vista d'inici de sessió.
	     */
		
		this.actionListenerCheckBoxLogin = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (login.getCheckBox().isSelected()) {
                    login.getLblConfirmPass().setVisible(true);
                    login.getTxtConfirmPass().setVisible(true);
                    login.getBtnAction().setText("Registrarse");
                } else {
                	login.getLblConfirmPass().setVisible(false);
                    login.getTxtConfirmPass().setVisible(false);
                    login.getBtnAction().setText("Iniciar sessió");
                }
			}
		};
		
		login.getCheckBox().addActionListener(actionListenerCheckBoxLogin);

		/**
	     * ActionListener per iniciar sessió i connectar-se al joc.
	     */

		this.actionListenerConectaLogin = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (login.getBtnAction().getText().equals("Iniciar sessió")) {

					String usu = login.getTxtUser().getText();
					char[] contra = login.getTxtPass().getPassword();
					String pass = new String(contra);

					int estat = model.loginCorrecte(usu, pass);

					if (estat == 1) {
						login.tancaLogin();
						joc.setJugador(usu);
						joc.generaLblJugador();
						joc.setVisible(true);
					} else if (estat == -1) {
						login.mostrarMissatge("L'usuari no existeix", "Error");
						login.buidaCamps();
					} else if (estat == 0) {
						login.mostrarMissatge("La contrasenya es incorrecta", "Error");
						login.buidaCamps();
					}

				} else if (login.getBtnAction().getText().equals("Registrarse")) {

					String us = login.getTxtUser().getText();
					String pas1 = new String(login.getTxtPass().getPassword());
					String pas2 = new String(login.getTxtConfirmPass().getPassword());

					
					int registreOk = model.registraUsuari(us, pas1, pas2);

					if (registreOk == 0) {
						login.mostrarMissatge("Registre realitzat correctament", "Operació realitzada");
						login.tancaLogin();
						joc.setJugador(us);
						joc.generaLblJugador();
						joc.setVisible(true);

					} else if (registreOk == 1) {
						login.mostrarMissatge(("L'usuari " + us + " ja existeix."), "Error");
						login.buidaCamps();
					} else if (registreOk == 2) {
						login.mostrarMissatge("Les contrasenyes no coincideixen", "Error");
						login.buidaCamps();
					}else if (registreOk == 3) {
						login.mostrarMissatge("Falten camps per plenar", "Error");
					}

				}

			}
		};

		login.getBtnAction().addActionListener(actionListenerConectaLogin);

		
		
		/**
	     * ActionListener per obrir la finestra dels registres.
	     */

		this.actionListenerObriRecords = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				records.plenaTablaFacil(Model.llistaRecordsFacil);
				records.plenaTablaDificil(Model.llistaRecordsDificil);
				records.setVisible(true);
			}

		};

		joc.getBtnObirRecords().addActionListener(actionListenerObriRecords);

		
		/**
	     * ActionListener per tancar la finestra dels registres.
	     */

		this.actionListenerTancaRecords = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				records.tancaRecords();
			}
		};

		records.getBtnEixir().addActionListener(actionListenerTancaRecords);
		
		

		/**
	     * ActionListener per tancar la sessió i tornar a obrir el login
	     */

		this.actionListenerTancaSessio = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				joc.tancaJoc();
				login.buidaCamps();
				login.setVisible(true);

			}
		};

		joc.getBtnTancarSessio().addActionListener(actionListenerTancaSessio);

		/**
	     * ActionListener per començar el joc
	     */

		this.actionListenerComencaJoc = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				joc.reiniciarEstat();
				if (joc.mostrarMissatgeGrauDificultat()) {

					joc.setVisible(false);

					List<String> imatges = new ArrayList<>();
					if (joc.getGrauDificultat() == 8) {
						imatges = model.carregaImatges8();
					} else if (joc.getGrauDificultat() == 16) {
						imatges = model.carregaImatges16();
					}

					joc.generaGraellaBotons(imatges);
					joc.setVisible(true);
					joc.iniciarCronometre();

					List<JButton> llistaBotonsImg = joc.getllistaBotonsImg();
					

					/**
				     * ActionListener per cada botó d'imatges.
				     * Iterem sobre la llista de botons per a generar els actionListeners
				     * 
				     */
					
					for (int i = 0; i < llistaBotonsImg.size(); i++) {
						final int index = i;

						llistaBotonsImg.get(i).addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {

								joc.apretarBoto(llistaBotonsImg.get(index), index);
								List<JButton> apretats = joc.getLlistaBotonsApretats();

								if (apretats.size() == 2) {

									if (model.sonImatgesIguals(apretats.get(0).getIcon(), apretats.get(1).getIcon())) {
										
										joc.cancelaBotons();
										joc.buidaLlistaApretats();

										boolean allPaired = true;
										for (JButton btn : llistaBotonsImg) {
											if (btn.isEnabled()) {
												allPaired = false;
												break;
											}
										}
										if (allPaired) {
											joc.detindreCronometre();
											joc.getBtnGuardarDades().setEnabled(true);
											joc.mostrarMissatge("Has guanyat el joc", "VICTORIA");
										}
									} else {
										joc.parellaIncorrecta();

									}
								}

							}

						});
					}
				}
			}
		};

		joc.getBtnComencarJoc().addActionListener(actionListenerComencaJoc);

		
		/**
	     * ActionListener per guardar les dades als records.
	     */
		
		this.actionListenerGuardaDades = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int segons = joc.getSegonsTranscurrits();
				String jugador = joc.getJugador();
				int dificultat = joc.getGrauDificultat();
				String timeStamp = model.generaTimeStamp();

				Logs l = new Logs(jugador, dificultat, segons, timeStamp);

				model.guardaLog(l);
				joc.mostrarMissatge("Dades de la partida guardades als récords", "Dades guardades");
			}
		};

		joc.getBtnGuardarDades().addActionListener(actionListenerGuardaDades);


	}

}
