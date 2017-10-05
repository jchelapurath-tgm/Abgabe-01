package Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class Test2 {

	// Arrays die mit Datenbanken gefüllt sind
    static ArrayList<String> länder;
    static ArrayList<String> städte;
    static ArrayList<String> mehrlist;
    static ArrayList<String> mehrlist2;
    static HashSet<String> land = new HashSet<String>();
    
	static Hashtable<String, ArrayList> flughafen1 = new Hashtable<String, ArrayList>();
	
	static boolean var = false;
	
	// Frame Variablen
	private JFrame frame;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton book = new JButton();
	private JPanel panel = new JPanel();
	private JTextField txtVorname;
	private JTextField txtNachname;
	private JComboBox comboBox_3;
	private JComboBox comboBox = new JComboBox();
	private JComboBox comboBox_1 = new JComboBox();
	
	//DB Variablen
	static Connection myConn;
	static Statement myStat;
	static Statement myStat2;
	static Statement myStat3;
	int maxrow = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		try {
			String server = JOptionPane.showInputDialog("ServerLink z.B. locaclhost..?");
			String user = JOptionPane.showInputDialog("Benutzer");
			String pass = JOptionPane.showInputDialog("Passwort");
		myConn = DriverManager.getConnection("jdbc:mysql://"+server, user, pass);
		myStat = myConn.createStatement();
		// Es werden gleich zu beginn alle Länder die Flüge haben von der DBMS abgefragt und in ein Länder Array sortiert
		ResultSet myRs = myStat.executeQuery("Select name from countries;");		
		while(myRs.next()){
			land.add(myRs.getString("name"));
			
			// Es wird zum jeweiligen Land ein Array von seinen Flughäfen erstellt
			myStat = myConn.createStatement();
			if(myRs.getString("name").equals("Cote D'Ivoire (Ivory Coast)")) {
			}else {
				ResultSet myRs1 = myStat.executeQuery("Select airports.name FROM airports INNER JOIN countries ON airports.country= countries.code where countries.name = '"+ myRs.getString("name")+"';");
				städte = new ArrayList<String>();
				while(myRs1.next()) {
					städte.add(myRs1.getString("name"));
					
				}
			}
	
			// Das Land wird als Key und seine Flughäfen als sein Value Array in eine Hashtabelle eingetragen
			flughafen1.put(myRs.getString("name"), städte);
		}

		länder = new ArrayList<String>(land);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		
		// Frame wird erstellt
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Test2 window = new Test2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application frame.
	 */
	public Test2() {
		txtVorname = new JTextField();
		txtVorname.setColumns(10);
		
		txtNachname = new JTextField();
		txtNachname.setColumns(10);

		
		JLabel lblNewLabel_4 = new JLabel("Vorname");
		
		JLabel lblNewLabel_5 = new JLabel("Nachname");
		
		JLabel lblReihe = new JLabel("Reihe");
		
		JLabel lblSitzplatz = new JLabel("Sitzplatz");
		
		JButton btnBuchen = new JButton("Buchen");
		btnBuchen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Es werden alle relevanten Daten von dem Feldern geholt und es wird ein neuer Insert Befehl ausgeführt
				String vorname = txtVorname.getText();
				String nachname = txtNachname.getText();
				String flightnr = (String)table.getModel().getValueAt(0, 1);
				String airline = (String) table.getModel().getValueAt(0, 0);
				int row = Integer.parseInt((String)comboBox.getSelectedItem());
				String seat = (String) comboBox_1.getSelectedItem();
				
				try {
					myStat3 = myConn.createStatement();
					myStat3.executeUpdate("INSERT INTO passengers VALUES(NULL,'"+vorname+"','"+nachname+"','"+airline+"','"+flightnr+"','"+row+"','"+seat+"');");
	    			JOptionPane.showMessageDialog(frame,
	    				    "Erfolgreich gebucht!");
				} catch (SQLException en) {
					en.printStackTrace();
				}
			}
		});
		
		// Frame
	
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addGap(72)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtVorname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(62)
							.addComponent(txtNachname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(91)
							.addComponent(lblNewLabel_5)))
					.addGap(46)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(1)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblReihe))
					.addGap(45)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSitzplatz)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(110, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(273, Short.MAX_VALUE)
					.addComponent(btnBuchen, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addGap(267))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(48)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSitzplatz)
								.addComponent(lblReihe))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_5))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtVorname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtNachname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
					.addComponent(btnBuchen, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(24))
		);
		panel.setBackground(new Color(0, 128, 128));
		panel.setLayout(gl_panel);
		panel.setVisible(false);
		initialize();

		}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(32, 178, 170));
		frame.getContentPane().setForeground(new Color(0, 128, 128));
		frame.setBounds(100, 100, 1013, 873);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblAbflug = new JLabel("Abflug");
		lblAbflug.setBackground(new Color(47, 79, 79));
		lblAbflug.setForeground(new Color(255, 140, 0));
		lblAbflug.setFont(new Font("Myriad Pro", Font.BOLD, 33));
		
		// Die Länder Werden in die Combobox eingefüllt
		JComboBox comboBox_2 = new JComboBox(länder.toArray());
		comboBox_2.setBackground(new Color(255, 228, 196));
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBackground(new Color(255, 228, 196));
		
		comboBox_2.addActionListener(new ActionListener() {
			
			// Je nachdem welches Land ausgewählt wird ändert sich der Der Flughafen Combo
			@Override
			public void actionPerformed(ActionEvent e) {
				 ArrayList<String> s = flughafen1.get(comboBox_2.getSelectedItem().toString());
				 DefaultComboBoxModel model = new DefaultComboBoxModel(s.toArray());
				 comboBox_3.setModel(model);

				
			}
		});
		
		JComboBox comboBox_4 = new JComboBox(länder.toArray());
		comboBox_4.setBackground(new Color(255, 228, 196));
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setBackground(new Color(255, 228, 196));
		
		comboBox_4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 ArrayList<String> s = flughafen1.get(comboBox_4.getSelectedItem().toString());
				 DefaultComboBoxModel model = new DefaultComboBoxModel(s.toArray());
				 comboBox_5.setModel(model);

				
			}
		});
		
		
		//Es wird eine Tabelle erstellt um Flüge anzuzeigen
        table = new JTable();
        
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		   
			//Beim Buchen klick wird das Flugzeug gesucht und es erscheint eine Neue Box wo man Flüge auswählen kann
			@Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 5) {
		        	panel.setVisible(true);
		        	
					try {
						String flightnr = (String) table.getModel().getValueAt(0, 1);
						String query = "select planes.maxseats,planes.seatsperrow from planes,flights WHERE flights.planetype = planes.id AND flights.flightnr ='"+flightnr +"' AND flights.airline ='"+table.getModel().getValueAt(0, 0)+"';";
						myStat3 = myConn.createStatement();
						ResultSet rs = myStat3.executeQuery(query);
						
						while (rs.next()) {
							maxrow = rs.getInt("maxseats") / rs.getInt("seatsperrow");
						}
						
						String[] seat = {"A","B","C","D","E","F","G"};
						ArrayList<Integer> rows = new ArrayList<Integer>();
						for(int i=0;i<seat.length;i++) {
							comboBox_1.addItem(seat[i]);
						}
						
						for(int i=1;i<=maxrow;i++) {
							rows.add(i);
						}
						
						Integer[] rowArr = rows.toArray(new Integer[rows.size()]);
						
						for (Integer nr:rowArr) {
							comboBox.addItem(nr.toString());
						}
						
						
					} catch (SQLException e) {
					}
		        }
		    }
		});
		
		
		JLabel lblAnkunft = new JLabel("Ankunft");
		lblAnkunft.setForeground(new Color(255, 140, 0));
		lblAnkunft.setFont(new Font("Myriad Pro", Font.BOLD, 33));
		
		JButton Suchenbutton = new JButton("Suchen");
		Suchenbutton.setForeground(new Color(255, 165, 0));
		Suchenbutton.setBackground(new Color(128, 0, 0));
		Suchenbutton.addActionListener(new ActionListener() {
			
			// Hier wird nach zuständigen flügen gesucht
			// Es kann sein das gleichname Flughäfen im gleichen Land gibt -- komischer Weise
			public void actionPerformed(ActionEvent arg0) {
			      String abflugs = comboBox_3.getSelectedItem().toString();//.replaceAll("\\s","");
			      String ankunfts= comboBox_5.getSelectedItem().toString();//.replaceAll("\\s","");
			    
					
					table.setModel(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"airline", "flightnr", "deparute", "destination","planetype","Buchen"
						}
					));
					table.getColumnModel().getColumn(0).setPreferredWidth(103);
					table.getColumnModel().getColumn(1).setPreferredWidth(210);
					table.getColumnModel().getColumn(2).setPreferredWidth(102);
					table.getColumnModel().getColumn(3).setPreferredWidth(243);
					table.getColumnModel().getColumn(4).setPreferredWidth(243);
					scrollPane.setViewportView(table);
			        DefaultTableModel model =  (DefaultTableModel)table.getModel();
			        Object[] row = new Object[6];
			        
			        try {
			        	String one = "select airportcode from airports where name ='" +abflugs + "';";
			        	ResultSet abflug1 = myStat.executeQuery(one);
			        	int i =0;
			        	boolean mehr = false;
			        	while(abflug1.next()) {
			        		one = abflug1.getString("airportcode");
			        		i++;
			        		if(i > 1) {
			        			mehr =true;
			        			break;
			        		}
			        	}
			        	if(mehr == true) {
			        		ResultSet abflugmehr = myStat.executeQuery("select airportcode from airports where name ='" +abflugs + "';");
			        		mehrlist = new ArrayList<String>();
				        	while(abflugmehr.next()) {
				        		mehrlist.add(abflugmehr.getString("airportcode"));
				        		
				        	}
				        	   one = (String) JOptionPane.showInputDialog(frame, 
				        		        "Mehrere Flughäfen",
				        		        "Flughafen",
				        		        JOptionPane.QUESTION_MESSAGE, 
				        		        null, 
				        		        mehrlist.toArray(),mehrlist.toArray()[0]);
			        	}
			        	
			        	myStat2 = myConn.createStatement();
			        	String two = "select airportcode from airports where name ='" +ankunfts + "';";
			        	ResultSet abflug2 = myStat2.executeQuery(two);
			        	int ii =0;
			        	boolean mehr2 = false;
			        	while(abflug2.next()) {
			        		two = abflug2.getString("airportcode");
			        		ii++;
			        		if(ii > 1) {
			        			mehr2 =true;
			        			break;
			        		}
			        	}
			        	
			        	if(mehr2 == true) {
			        		ResultSet abflugmehr = myStat.executeQuery("select airportcode from airports where name ='" +abflugs + "';");
			        		mehrlist2 = new ArrayList<String>();
				        	while(abflugmehr.next()) {
				        		mehrlist.add(abflugmehr.getString("airportcode"));
				        		
				        	}
				     	   two = (String) JOptionPane.showInputDialog(frame, 
			        		        "Mehrere Flughäfen",
			        		        "Flughafen",
			        		        JOptionPane.QUESTION_MESSAGE, 
			        		        null, 
			        		        mehrlist.toArray(),mehrlist.toArray()[0]);
			        	}
			        	String s = "select * from flights where departure_airport like '"+ one +"'AND  destination_airport like '" + two + "';" ;
			    		ResultSet myRs = myStat.executeQuery(s);
			    		
			    		while(myRs.next()){
			    	           row[0] = myRs.getString("airline");
			    	           row[1] = myRs.getString("flightnr");
			    	           row[2] = myRs.getDate("departure_time");
			    	           row[3] = myRs.getDate("destination_time");
			    	           row[4] = myRs.getInt("planetype");
			    	           row[5] = "Buchen";
			    	           model.addRow(row);
			    		}
		
			    		}catch(Exception e) {
			    			JOptionPane.showMessageDialog(frame,
			    				    "Keine Flüge gefunden");
			    			frame.repaint();
			    		}
			      
			}
		});
		

		
		scrollPane = new JScrollPane();
		

		
		JLabel lblNewLabel = new JLabel("Land");
		lblNewLabel.setBackground(new Color(47, 79, 79));
		lblNewLabel.setFont(new Font("Myriad Pro Light", Font.BOLD, 20));
		lblNewLabel.setForeground(new Color(255, 140, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Flughafen");
		lblNewLabel_1.setBackground(new Color(47, 79, 79));
		lblNewLabel_1.setFont(new Font("Myriad Pro", Font.BOLD, 20));
		lblNewLabel_1.setForeground(new Color(255, 140, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Land");
		lblNewLabel_2.setFont(new Font("Myriad Pro", Font.BOLD, 20));
		lblNewLabel_2.setForeground(new Color(255, 140, 0));
		
		JLabel lblNewLabel_3 = new JLabel("Flughafen");
		lblNewLabel_3.setFont(new Font("Myriad Pro", Font.BOLD, 20));
		lblNewLabel_3.setForeground(new Color(255, 140, 0));
		

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(209)
					.addComponent(lblAbflug, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
					.addComponent(lblAnkunft, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(199))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(101)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addGap(152)
					.addComponent(lblNewLabel_1)
					.addGap(175)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
					.addComponent(lblNewLabel_3)
					.addGap(123))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(175)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 647, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(173, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(56)
					.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addGap(56)
					.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
					.addGap(96)
					.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
					.addComponent(comboBox_5, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addGap(86))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(402)
					.addComponent(Suchenbutton, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(420, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(70)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 813, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(112, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAbflug, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addComponent(lblAnkunft))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_1)
							.addComponent(lblNewLabel_2))
						.addComponent(lblNewLabel_3))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(46)
					.addComponent(Suchenbutton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
					.addGap(246))
		);
		


		frame.getContentPane().setLayout(groupLayout);
	}
}



