package vista;


import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import clases.Logs;
import javax.swing.JButton;

/**
 * La classe Records representa la finestra de registres del joc.
 */

public class Records extends JFrame {

    private static final long serialVersionUID = 1L;
    private DefaultTableModel tableModelFacil;
    private DefaultTableModel tableModelDificil;
    private JTable tablaDatosFacil;
    private JTable tablaDatosDificil;
    private JButton btnEixir;
    private Font font = new Font("Consolas", Font.PLAIN, 20);
    private Font fontMenuda = new Font("Consolas", Font.PLAIN, 10);

    
    /**
     * Constructor de la classe Records.
     * Configura la finestra per a mostrar els registres de les partides.
     */
    
    public Records() {
        setTitle("Records");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1006, 568);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);

        JLabel lblJoc2x4 = new JLabel("Joc 2x4");
        lblJoc2x4.setFont(font);
        lblJoc2x4.setBounds(178, 20, 100, 20);
        panel.add(lblJoc2x4);

        tableModelFacil = new DefaultTableModel();


        tablaDatosFacil = new JTable(tableModelFacil);
        tablaDatosFacil.setFont(fontMenuda);
        JScrollPane scrollPaneTabla1 = new JScrollPane(tablaDatosFacil);
        scrollPaneTabla1.setBounds(50, 50, 364, 448);
        panel.add(scrollPaneTabla1);

        JLabel lblJoc4x4 = new JLabel("Joc 4x4");
        lblJoc4x4.setFont(font);
        lblJoc4x4.setBounds(591, 20, 100, 20);
        panel.add(lblJoc4x4);

        tableModelDificil = new DefaultTableModel();

        tablaDatosDificil = new JTable(tableModelDificil);
        tablaDatosDificil.setFont(fontMenuda);
        JScrollPane scrollPaneTabla2 = new JScrollPane(tablaDatosDificil);
        scrollPaneTabla2.setBounds(460, 50, 363, 448);
        panel.add(scrollPaneTabla2);
        
        btnEixir = new JButton("Eixir");
        btnEixir.setFont(font);
        btnEixir.setBounds(858, 206, 100, 138);
        btnEixir.setBackground(new Color(255, 128, 128));
        panel.add(btnEixir);
    }
    
    

    public JButton getBtnEixir() {
		return btnEixir;
	}


    /**
     * Omple la taula de les partides facils amb les dades proporcionades.
     * @param logsFacils Llista de Logs que conte les dades de les partides facils.
     */
	public void plenaTablaFacil(List<Logs> logsFacils) {
		String[] nomColumnes = new String[4];

		nomColumnes[0] = "Usuari";
		nomColumnes[1] = "Dificultat";
		nomColumnes[2] = "Time Stamp";
		nomColumnes[3] = "Temps";

		tableModelFacil.setColumnIdentifiers(nomColumnes);

		for (int i = 0; i < logsFacils.size(); i++) {
			
			Vector<Object> fila = new Vector<>();

			fila.add(logsFacils.get(i).getNomUsuari()); 
			fila.add(logsFacils.get(i).getDificultat());
			fila.add(logsFacils.get(i).getHoraPartida());
			fila.add(logsFacils.get(i).getDuracioPartida());
			
			tableModelFacil.addRow(fila);
		}
		
    }
	
	
	/**
     * Omple la taula de les partides dificils amb les dades proporcionades.
     * @param logsDificils Llista de Logs que conte les dades de les partides dificils.
     */
    
    public void plenaTablaDificil(List<Logs> logsDificils) {
		String[] nomColumnes = new String[4];

		nomColumnes[0] = "Usuari";
		nomColumnes[1] = "Dificultat";
		nomColumnes[2] = "Time Stamp";
		nomColumnes[3] = "Temps";

		tableModelDificil.setColumnIdentifiers(nomColumnes);

		for (int i = 0; i < logsDificils.size(); i++) {
			
			Vector<Object> fila = new Vector<>();

			fila.add(logsDificils.get(i).getNomUsuari()); 
			fila.add(logsDificils.get(i).getDificultat());
			fila.add(logsDificils.get(i).getHoraPartida());
			fila.add(logsDificils.get(i).getDuracioPartida());
			
			tableModelDificil.addRow(fila);
		}
		
    }

    /**
     * Tanca la finestra de registres i reinicia les taules.
     */
    public void tancaRecords() {
    	this.dispose();
    	tableModelDificil.setRowCount(0);
    	tableModelFacil.setRowCount(0);
    }
}