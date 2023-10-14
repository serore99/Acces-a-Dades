package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javax.swing.JOptionPane.showMessageDialog;

public class App extends JFrame {
	private static final long serialVersionUID = 3186682671508907164L;
	private JPanel contentPane;
	private JTextField txtCoincidencies;
	private JTextField txtNouFitxer;
	private JList<String> llistaArxius;
	private DefaultListModel<String> listModel;
	private JTextField txtDirectori;
	private String ordenSeleccionado = "Nom";
	private boolean ordenAscendente = true;

	public App() {
		setTitle("Gestió d'arxius");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 982, 553);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 140, 943, 230);
        contentPane.add(scrollPane);
        llistaArxius = new JList<>();
        scrollPane.setViewportView(llistaArxius);

        JButton btnCarregar = new JButton("Carregar");
        btnCarregar.setBounds(20, 100, 117, 26);
        btnCarregar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        contentPane.add(btnCarregar);
        listModel = new DefaultListModel<>(); 

        JComboBox<String> cmbOrdenar = new JComboBox<>();
        cmbOrdenar.setBounds(220, 103, 163, 21);
        cmbOrdenar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cmbOrdenar.setModel(new DefaultComboBoxModel<>(new String[] { "Nom", "Mida", "Data Modificació" }));
        contentPane.add(cmbOrdenar);

        JLabel lblOrdenarPor = new JLabel("Ordenar per...");
        lblOrdenarPor.setBounds(150, 107, 86, 13);
        lblOrdenarPor.setFont(new Font("Tahoma", Font.PLAIN, 12));
        contentPane.add(lblOrdenarPor);

        ButtonGroup buttonGroup = new ButtonGroup();

        JRadioButton rdbtnAscendent = new JRadioButton("Ascendent");
        rdbtnAscendent.setBounds(420, 103, 103, 21);
        rdbtnAscendent.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rdbtnAscendent.setSelected(true);
        contentPane.add(rdbtnAscendent);
        buttonGroup.add(rdbtnAscendent);

        JRadioButton rdbtnDescendent = new JRadioButton("Descendent");
        rdbtnDescendent.setBounds(530, 103, 103, 21);
        rdbtnDescendent.setFont(new Font("Tahoma", Font.PLAIN, 12));
        contentPane.add(rdbtnDescendent);
        buttonGroup.add(rdbtnDescendent);

        txtCoincidencies = new JTextField();
        txtCoincidencies.setBounds(20, 389, 660, 26);
        contentPane.add(txtCoincidencies);
        txtCoincidencies.setColumns(10);

        JButton btnBuscarCoincidencies = new JButton("Buscar Coincidencies");
        btnBuscarCoincidencies.setBounds(700, 389, 251, 26);
        btnBuscarCoincidencies.setFont(new Font("Tahoma", Font.PLAIN, 12));
        contentPane.add(btnBuscarCoincidencies);

        JLabel lblNomNouFitxer = new JLabel("Nom del nou fitxer:");
        lblNomNouFitxer.setBounds(20, 431, 185, 26);
        lblNomNouFitxer.setFont(new Font("Tahoma", Font.PLAIN, 12));
        contentPane.add(lblNomNouFitxer);

        txtNouFitxer = new JTextField();
        txtNouFitxer.setBounds(215, 433, 145, 26);
        txtNouFitxer.setColumns(10);
        contentPane.add(txtNouFitxer);

        JButton btnFusionarFitxers = new JButton("Fusionar Fitxers");
        btnFusionarFitxers.setBounds(370, 433, 128, 26);
        btnFusionarFitxers.setFont(new Font("Tahoma", Font.PLAIN, 12));
        contentPane.add(btnFusionarFitxers);

        txtDirectori = new JTextField();
        txtDirectori.setBounds(20, 66, 660, 26);
        txtDirectori.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtDirectori.setColumns(10);
        contentPane.add(txtDirectori);

        JButton btnSeleccionarDirectori = new JButton("Seleccionar Directori");
        btnSeleccionarDirectori.setBounds(700, 66, 251, 26);
		btnSeleccionarDirectori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedDirectory = fileChooser.getSelectedFile();
					txtDirectori.setText(selectedDirectory.getAbsolutePath());
				}
			}
		});

		btnSeleccionarDirectori.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(btnSeleccionarDirectori);
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarArchivos();
			}
		});

		rdbtnAscendent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ordenAscendente = true;
			}
		});

		rdbtnDescendent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ordenAscendente = false;
			}
		});

		cmbOrdenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ordenSeleccionado = (String) cmbOrdenar.getSelectedItem();
			}

		});

		btnBuscarCoincidencies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File dir = new File(txtDirectori.getText());
				if (dir.exists()) {
					File[] fitxers = getFitxers();
					mostrarCoincidencies(fitxers);
				}
			}
		});

		btnFusionarFitxers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = llistaArxius.getSelectedIndices();
                fusionar(selectedIndices);
            }
        });
    }

	protected void fusionar(int[] selectedIndices) {
		// TODO Auto-generated method stub
		if (selectedIndices.length < 2) {
            showMessageDialog(null, "Selecciona almenys 2 fitxers per a fusionar.");
            return;
        }

        String nuevoNombre = txtNouFitxer.getText();

        File directorio = new File(txtDirectori.getText());
        File nuevoArchivo = new File(directorio, nuevoNombre+".txt");

        try {
            FileWriter fileWriter = new FileWriter(nuevoArchivo, false);

            for (int selectedIndex : selectedIndices) {
                String nombreFichero = listModel.get(selectedIndex);
                File fichero = new File(directorio, nombreFichero.split(" \\| ")[0]);

                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(fichero));
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        fileWriter.write(line + "\n");
                    }

                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            fileWriter.close();
            showMessageDialog(null, "Fitxers fusionats i guardats amb èxit en " + nuevoNombre);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

	protected void mostrarCoincidencies(File[] fitxers) {
	    String res = "";
	    for (int i = 0; i < fitxers.length; i++) {
	        int cont = 0;
	        try {
	            FileReader fr = new FileReader(fitxers[i].getAbsoluteFile());
	            BufferedReader br = new BufferedReader(fr);
	            String line;
	            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(txtCoincidencies.getText()) + "\\b", Pattern.CASE_INSENSITIVE);
	            while ((line = br.readLine()) != null) {
	                Matcher matcher = pattern.matcher(line);
	                while (matcher.find()) {
	                    cont++;
	                }
	            }
	            res += "Fitxer " + fitxers[i].getName() + " te " + cont + " coincidencies de la paraula " + txtCoincidencies.getText() + "\n";
	            br.close();
	            fr.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    // Muestra el mensaje en un cuadro de diálogo tipo "alert"
	    JOptionPane.showMessageDialog(null, res, "Resultats de la cerca", JOptionPane.INFORMATION_MESSAGE);
	}


	private File[] getFitxers() {
// TODO Auto-generated method stub 
		File dir = new File(txtDirectori.getText());
		if (dir.exists()) {
			return dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".txt");
				}
			});
		} else {
			return null;
		}
	}

	private void cargarArchivos() {
		File dir = new File(txtDirectori.getText());
		if (dir.exists()) {
			File[] fitxers = getFitxers();
			ordenarArchivos(fitxers);
			actualizarLista(fitxers);
		}
	}

	private void ordenarArchivos(File[] fitxers) {
	    if (ordenSeleccionado.equals("Nom")) {
	        Arrays.sort(fitxers, (f1, f2) -> {
	            int result = f1.getName().compareTo(f2.getName());
	            return ordenAscendente ? result : -result;
	        });
	    } else if (ordenSeleccionado.equals("Mida")) {
	        Arrays.sort(fitxers, (f1, f2) -> {
	            int result = Long.compare(f1.length(), f2.length());
	            return ordenAscendente ? result : -result;
	        });
	    } else if (ordenSeleccionado.equals("Data Modificació")) {
	        Arrays.sort(fitxers, (f1, f2) -> {
	            int result = Long.compare(f1.lastModified(), f2.lastModified());
	            return ordenAscendente ? result : -result;
	        });
	    }
	}

	private void actualizarLista(File[] fitxers) {
		listModel.clear();
		llistaArxius.setModel(listModel);
		if (fitxers != null && fitxers.length > 0) {
			for (File fitxer : fitxers) {
				String nomFitx = fitxer.getName();
				long tamany = fitxer.length();
				Date ultimaModif = new Date(fitxer.lastModified());
				String info = String.format("%s | Modificat: %s | Tamany: %d bytes", nomFitx, ultimaModif, tamany);
				listModel.addElement(info);
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}