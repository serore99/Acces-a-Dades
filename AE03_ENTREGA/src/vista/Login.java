package vista;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.Font;


/**
 * La classe Login representa la finestra d'inici de sessió.
 */

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblUser;
    private JLabel lblPass;
    private JLabel lblConfirmPass;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JPasswordField txtConfirmPass;
    private JCheckBox checkBox;
    private JButton btnAction;

    /**
     * Constructor de la classe Login.
     * Configura la finestra per a l'inici de sessió.
     */
    
    public Login() {
    	setTitle("Iniciar sessió");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 457, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        Font font = new Font("Consolas", Font.PLAIN, 14);

        lblUser = new JLabel("Usuari:");
        lblUser.setFont(font);
        lblUser.setBounds(30, 30, 80, 20);
        contentPane.add(lblUser);

        txtUser = new JTextField();
        txtUser.setFont(font);
        txtUser.setBounds(206, 30, 206, 20);
        contentPane.add(txtUser);
        txtUser.setColumns(10);

        lblPass = new JLabel("Contrasenya:");
        lblPass.setFont(font);
        lblPass.setBounds(30, 60, 133, 20);
        contentPane.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setFont(font);
        txtPass.setBounds(206, 60, 206, 20);
        contentPane.add(txtPass);
        txtPass.setColumns(10);

        checkBox = new JCheckBox("No tinc compte, vull registrar-me");
        checkBox.setFont(font);
        checkBox.setBounds(30, 100, 300, 20);
        contentPane.add(checkBox);

        lblConfirmPass = new JLabel("Confirma contrasenya:");
        lblConfirmPass.setFont(font);
        lblConfirmPass.setBounds(30, 130, 191, 20);
        lblConfirmPass.setVisible(false);
        contentPane.add(lblConfirmPass);

        txtConfirmPass = new JPasswordField();
        txtConfirmPass.setFont(font);
        txtConfirmPass.setBounds(221, 130, 191, 20);
        txtConfirmPass.setVisible(false);
        contentPane.add(txtConfirmPass);

        btnAction = new JButton("Iniciar sessió");
        btnAction.setFont(font);
        btnAction.setBounds(120, 200, 150, 40);
        contentPane.add(btnAction);
     
        
    }
    
    
    public JLabel getLblConfirmPass() {
		return lblConfirmPass;
	}

	public JCheckBox getCheckBox() {
		return checkBox;
	}


	public JTextField getTxtUser() {
		return txtUser;
	}



	public JPasswordField getTxtPass() {
		return txtPass;
	}



	public JPasswordField getTxtConfirmPass() {
		return txtConfirmPass;
	}



	public JButton getBtnAction() {
		return btnAction;
	}


	/**
     * Tanca la finestra d'inici de sessió i reinicia els camps de text.
     */
	
	public void tancaLogin() {
		this.dispose();
		txtConfirmPass.setText("");
		txtPass.setText("");
		txtUser.setText("");
	}
	
	/**
     * Buida els camps de text (Usuari, Contrasenya, Confirmació de contrasenya).
     */
	
	public void buidaCamps() {
		txtConfirmPass.setText("");
		txtPass.setText("");
		txtUser.setText("");
	}
	
	/**
     * Mostra un missatge informatiu a través d'un quadre de diàleg.
     * @param missatge El missatge a mostrar.
     * @param titol El títol del quadre de diàleg.
     */
	
	public void mostrarMissatge(String missatge, String titol) {
		JOptionPane.showMessageDialog(null, missatge, titol, JOptionPane.INFORMATION_MESSAGE);
	}
	
	
}
