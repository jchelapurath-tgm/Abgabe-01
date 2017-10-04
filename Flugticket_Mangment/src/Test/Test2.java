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
import javax.swing.JComboBox;

public class Test2 {

	//static ArrayList<Flughafen> flughafen= new ArrayList<Flughafen>();
    static ArrayList<String> länder;
    static ArrayList<String> städte;
    static ArrayList<String> mehrlist;
    static ArrayList<String> mehrlist2;
    static HashSet<String> land = new HashSet<String>();
    
	static Hashtable<String, ArrayList> flughafen1 = new Hashtable<String, ArrayList>();
	
	static boolean var = false;
	
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton book = new JButton();
	private JPanel panel = new JPanel();
	int maxrow = 0;
	
	JComboBox comboBox = new JComboBox();
	
	JComboBox comboBox_1 = new JComboBox();
	
	static Connection myConn;
	static Statement myStat;
	static Statement myStat2;
	static Statement myStat3;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField txtVorname;
	private JTextField txtNachname;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String test = "SELECT countries.name as 'land', airports.name, airports.country as 'kurz', airports.airportcode FROM airports INNER JOIN countries ON airports.country= countries.code;";
		try {
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/list_3ahit", "root", "Chelj20101999");
		myStat = myConn.createStatement();
		ResultSet myRs = myStat.executeQuery("Select name from countries;");
		
		while(myRs.next()){
			land.add(myRs.getString("name"));
			System.out.println(land.toString());
			myStat = myConn.createStatement();
			if(myRs.getString("name").equals("Cote D'Ivoire (Ivory Coast)")) {
				/*ResultSet myRs1 = myStat.executeQuery("Select airports.name FROM airports INNER JOIN countries ON airports.country= countries.code where countries.name = Cote D'Ivoire (Ivory Coast);");
				städte = new ArrayList<String>();
				while(myRs1.next()) {
					städte.add(myRs1.getString("name"));
				}*/
			}else {
				ResultSet myRs1 = myStat.executeQuery("Select airports.name FROM airports INNER JOIN countries ON airports.country= countries.code where countries.name = '"+ myRs.getString("name")+"';");
				städte = new ArrayList<String>();
				while(myRs1.next()) {
					System.out.println(myRs1.getString("name"));
					städte.add(myRs1.getString("name"));
					
				}
			}
	
			flughafen1.put(myRs.getString("name"), städte);
			System.out.println(flughafen1.get("Andorra").toString());
			
			
			//airportcode.put(myRs.getString("name"),myRs.getString("airportcode"));
			//flughafen1.put(myRs.getString("land"),airportcode.getKey());
			
			//flughafen.add(new Flughafen(myRs.getString("kurz"), myRs.getString("city"), myRs.getString("airportcode"), myRs.getString("name"),myRs.getString("land")));
		}

		länder = new ArrayList<String>(land);
		System.out.println(flughafen1.get("Andorra").toString());
		}catch(Exception e) {
			System.out.println(e.toString());
		}
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
	 * Create the application.
	 */
	public Test2() {
		txtVorname = new JTextField();
		txtVorname.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setEditable(false);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		txtNachname = new JTextField();
		txtNachname.setColumns(10);
		textField_3.setEditable(false);

		
		JLabel lblNewLabel_4 = new JLabel("Vorname");
		
		JLabel lblNewLabel_5 = new JLabel("Nachname");
		
		JLabel lblReihe = new JLabel("Reihe");
		
		JLabel lblSitzplatz = new JLabel("Sitzplatz");
		
		JButton btnBuchen = new JButton("Buchen");
		btnBuchen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String vorname = txtVorname.getText();
				String nachname = txtNachname.getText();
				int flightnr =  (int) table.getModel().getValueAt(0, 1);
				String airline = (String) table.getModel().getValueAt(0, 0);
				String row = (String) comboBox.getSelectedItem();
				String seat = (String) comboBox_1.getSelectedItem();
				
				try {
					myStat3 = myConn.createStatement();
					myStat3.executeUpdate("INSERT INTO passengers VALUES(NULL,'"+vorname+"','"+nachname+"','"+airline+"','"+flightnr+"','"+seat+"','"+row+"');");
				} catch (SQLException en) {
					en.printStackTrace();
				}
			}
		});
	
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
		panel.setLayout(gl_panel);
		panel.setVisible(false);
		initialize();

		}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1013, 910);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String list = textField.getText();
					if(list.substring(list.length()).equals(" ")) {
						list.substring(0, list.length()-1);
					}
					if(flughafen1.containsKey(list)) {
					textField_2.setEditable(true);
					ArrayList s = flughafen1.get(list);
					AutoSuggestor autoSuggestor3 = new AutoSuggestor(textField_2, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
			            @Override
			            boolean wordTyped(String typedWord) {

			            	
			                setDictionary(s);
			                //addToDictionary("bye");//adds a single word

			                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
			            }
			        };
					}else {
						JOptionPane.showMessageDialog(frame,
							"Land exsistiert nicht",
							    "Land nicht gefunden",
							    JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		textField.setColumns(10);
		
		JLabel lblAbflug = new JLabel("Abflug");
		lblAbflug.setFont(new Font("Tahoma", Font.PLAIN, 32));
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String list = textField.getText();
					if(list.substring(list.length()).equals(" ")) {
						list.substring(0, list.length()-1);
					}
					if(flughafen1.containsKey(list)) {
						
					textField_3.setEditable(true);
					ArrayList si = flughafen1.get(list);   
			        AutoSuggestor autoSuggestor4 = new AutoSuggestor(textField_3, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
			            @Override
			            boolean wordTyped(String typedWord) {


			                setDictionary(si);
			                //addToDictionary("bye");//adds a single word

			                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
			            }
			        };
					}else {
						JOptionPane.showMessageDialog(frame,
								"Land exsistiert nicht",
								    "Land nicht gefunden",
								    JOptionPane.ERROR_MESSAGE);
					
					}
				}
			}
		});
		textField_1.setColumns(10);
		
		
        AutoSuggestor autoSuggestor = new AutoSuggestor(textField, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
            @Override
            boolean wordTyped(String typedWord) {


                setDictionary(länder);
                //addToDictionary("bye");//adds a single word

                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
            }
        };
        
        
     
        AutoSuggestor autoSuggestor2 = new AutoSuggestor(textField_1, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
            @Override
            boolean wordTyped(String typedWord) {


                setDictionary(länder);
                //addToDictionary("bye");//adds a single word

                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
            }
        };
        table = new JTable();
        
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 5) {
		        	System.out.println("test");
		        	panel.setVisible(true);
		        	
					try {
						myStat3 = myConn.createStatement();
						ResultSet rs = myStat3.executeQuery("select planes.maxseats,planes.seatsperrow from planes,flights WHERE flights.planetype = planes.id AND flights.flightnr ='"+table.getModel().getValueAt(0, 1) +"' AND flights.airline ='"+table.getModel().getValueAt(0, 0)+"';");
						
						while (rs.next()) {
							maxrow = rs.getInt("maxseats") / rs.getInt("seatsperrow");
						}
						
						String[] seat = {"A","B","C","D","E","F","G"};
						ArrayList<Integer> rows = new ArrayList<Integer>();
						for(int i=0;i<seat.length;i++) {
							comboBox.addItem(seat[i]);
						}
						
						for(int i=1;i<=maxrow;i++) {
							rows.add(i);
						}
						
						Integer[] rowArr = rows.toArray(new Integer[rows.size()]);
						
						for (Integer nr:rowArr) {
							comboBox_1.addItem(nr.toString());
						}
						
					} catch (SQLException e) {
					}
		        }
		    }
		});
		
		
		JLabel lblAnkunft = new JLabel("Ankunft");
		lblAnkunft.setFont(new Font("Tahoma", Font.PLAIN, 32));
		
		JButton Suchenbutton = new JButton("Suchen");
		Suchenbutton.addActionListener(new ActionListener() {
			
			// Hier wird nach zuständigen flügen gesucht
			public void actionPerformed(ActionEvent arg0) {
			      String abflugs = textField_2.getText();//.replaceAll("\\s","");
			      String ankunfts= textField_3.getText();//.replaceAll("\\s","");
			    
			      if(abflugs.substring(abflugs.length()).equals(" ")){
							abflugs.substring(0, abflugs.length()-1);
		}
			      
			      if(ankunfts.substring(ankunfts.length()).equals(" ")){
							ankunfts.substring(0, ankunfts.length()-1);
		}
					
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
				        	System.out.println(mehrlist.toString());
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
				        	System.out.println(mehrlist.toString());
			        	}
			        	
			    		ResultSet myRs = myStat.executeQuery("select * from flights where departure_airport like '"+ one +"'AND  destination_airport like '" + two + "';" );
			    		
			    		while(myRs.next()){
			    	           row[0] = myRs.getString("airline");
			    	           row[1] = myRs.getInt("flightnr");
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
			    			System.out.println(e.toString());
			    		}
			      
			}
		});
		

		
		scrollPane = new JScrollPane();
		

		
		JLabel lblNewLabel = new JLabel("Land");
		
		JLabel lblNewLabel_1 = new JLabel("Stadt");
		
		JLabel lblNewLabel_2 = new JLabel("Land");
		
		JLabel lblNewLabel_3 = new JLabel("Stadt");
		

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(209)
					.addComponent(lblAbflug, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addGap(335)
					.addComponent(lblAnkunft, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(190, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(410)
					.addComponent(Suchenbutton, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(412, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(111)
					.addComponent(lblNewLabel)
					.addGap(204)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
					.addComponent(lblNewLabel_2)
					.addGap(193)
					.addComponent(lblNewLabel_3)
					.addGap(132))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(52)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addGap(68))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(86)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 813, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(96, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(175)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 647, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(173, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(53)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblAnkunft, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblAbflug, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
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


/**
 * Auto Complete
 **/

class AutoSuggestor {

    private final JTextField textField;
    private final Window container;
    private JPanel suggestionsPanel;
    private JWindow autoSuggestionPopUpWindow;
    private String typedWord;
    private final ArrayList<String> dictionary = new ArrayList<>();
    private int currentIndexOfSpace, tW, tH;
    private DocumentListener documentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }
    };
    private final Color suggestionsTextColor;
    private final Color suggestionFocusedColor;

    public AutoSuggestor(JTextField textField, Window mainWindow, ArrayList<String> words, Color popUpBackground, Color textColor, Color suggestionFocusedColor, float opacity) {
        this.textField = textField;
        this.suggestionsTextColor = textColor;
        this.container = mainWindow;
        this.suggestionFocusedColor = suggestionFocusedColor;
        this.textField.getDocument().addDocumentListener(documentListener);

        setDictionary(words);

        typedWord = "";
        currentIndexOfSpace = 0;
        tW = 0;
        tH = 0;

        autoSuggestionPopUpWindow = new JWindow(mainWindow);
        autoSuggestionPopUpWindow.setOpacity(opacity);

        suggestionsPanel = new JPanel();
        suggestionsPanel.setLayout(new GridLayout(0, 1));
        suggestionsPanel.setBackground(popUpBackground);

        addKeyBindingToRequestFocusInPopUpWindow();
    }

    private void addKeyBindingToRequestFocusInPopUpWindow() {
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        textField.getActionMap().put("Down released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {//focuses the first label on popwindow
                for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
                    if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                        ((SuggestionLabel) suggestionsPanel.getComponent(i)).setFocused(true);
                        autoSuggestionPopUpWindow.toFront();
                        autoSuggestionPopUpWindow.requestFocusInWindow();
                        suggestionsPanel.requestFocusInWindow();
                        suggestionsPanel.getComponent(i).requestFocusInWindow();
                        break;
                    }
                }
            }
        });
        suggestionsPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        suggestionsPanel.getActionMap().put("Down released", new AbstractAction() {
            int lastFocusableIndex = 0;

            @Override
            public void actionPerformed(ActionEvent ae) {//allows scrolling of labels in pop window (I know very hacky for now :))

                ArrayList<SuggestionLabel> sls = getAddedSuggestionLabels();
                int max = sls.size();

                if (max > 1) {//more than 1 suggestion
                    for (int i = 0; i < max; i++) {
                        SuggestionLabel sl = sls.get(i);
                        if (sl.isFocused()) {
                            if (lastFocusableIndex == max - 1) {
                                lastFocusableIndex = 0;
                                sl.setFocused(false);
                                autoSuggestionPopUpWindow.setVisible(false);
                                setFocusToTextField();
                                checkForAndShowSuggestions();//fire method as if document listener change occured and fired it

                            } else {
                                sl.setFocused(false);
                                lastFocusableIndex = i;
                            }
                        } else if (lastFocusableIndex <= i) {
                            if (i < max) {
                                sl.setFocused(true);
                                autoSuggestionPopUpWindow.toFront();
                                autoSuggestionPopUpWindow.requestFocusInWindow();
                                suggestionsPanel.requestFocusInWindow();
                                suggestionsPanel.getComponent(i).requestFocusInWindow();
                                lastFocusableIndex = i;
                                break;
                            }
                        }
                    }
                } else {//only a single suggestion was given
                    autoSuggestionPopUpWindow.setVisible(false);
                    setFocusToTextField();
                    checkForAndShowSuggestions();//fire method as if document listener change occured and fired it
                }
            }
        });
    }

    private void setFocusToTextField() {
        container.toFront();
        container.requestFocusInWindow();
        textField.requestFocusInWindow();
    }

    public ArrayList<SuggestionLabel> getAddedSuggestionLabels() {
        ArrayList<SuggestionLabel> sls = new ArrayList<>();
        for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
            if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                SuggestionLabel sl = (SuggestionLabel) suggestionsPanel.getComponent(i);
                sls.add(sl);
            }
        }
        return sls;
    }

    private void checkForAndShowSuggestions() {
        typedWord = getCurrentlyTypedWord();

        suggestionsPanel.removeAll();//remove previos words/jlabels that were added

        //used to calcualte size of JWindow as new Jlabels are added
        tW = 0;
        tH = 0;

        boolean added = wordTyped(typedWord);

        if (!added) {
            if (autoSuggestionPopUpWindow.isVisible()) {
                autoSuggestionPopUpWindow.setVisible(false);
            }
        } else {
            showPopUpWindow();
            setFocusToTextField();
        }
    }

    protected void addWordToSuggestions(String word) {
        SuggestionLabel suggestionLabel = new SuggestionLabel(word, suggestionFocusedColor, suggestionsTextColor, this);

        calculatePopUpWindowSize(suggestionLabel);

        suggestionsPanel.add(suggestionLabel);
    }

    public String getCurrentlyTypedWord() {//get newest word after last white spaceif any or the first word if no white spaces
        String text = textField.getText();
        String wordBeingTyped = "";
        if (text.contains(" ")) {
            int tmp = text.lastIndexOf(" ");
            if (tmp >= currentIndexOfSpace) {
                currentIndexOfSpace = tmp;
                wordBeingTyped = text.substring(text.lastIndexOf(" "));
            }
        } else {
            wordBeingTyped = text;
        }
        return wordBeingTyped.trim();
    }

    private void calculatePopUpWindowSize(JLabel label) {
        //so we can size the JWindow correctly
        if (tW < label.getPreferredSize().width) {
            tW = label.getPreferredSize().width;
        }
        tH += label.getPreferredSize().height;
    }

    private void showPopUpWindow() {
        autoSuggestionPopUpWindow.getContentPane().add(suggestionsPanel);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.setSize(tW, tH);
        autoSuggestionPopUpWindow.setVisible(true);

        int windowX = 0;
        int windowY = 0;

        windowX = container.getX() + textField.getX() + 5;
        if (suggestionsPanel.getHeight() > autoSuggestionPopUpWindow.getMinimumSize().height) {
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getMinimumSize().height;
        } else {
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getHeight();
        }

        autoSuggestionPopUpWindow.setLocation(windowX, windowY);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.revalidate();
        autoSuggestionPopUpWindow.repaint();

    }

    public void setDictionary(ArrayList<String> words) {
        dictionary.clear();
        if (words == null) {
            return;//so we can call constructor with null value for dictionary without exception thrown
        }
        for (String word : words) {
            dictionary.add(word);
        }
    }

    public JWindow getAutoSuggestionPopUpWindow() {
        return autoSuggestionPopUpWindow;
    }

    public Window getContainer() {
        return container;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void addToDictionary(String word) {
        dictionary.add(word);
    }

    boolean wordTyped(String typedWord) {

        if (typedWord.isEmpty()) {
            return false;
        }
        //System.out.println("Typed word: " + typedWord);

        boolean suggestionAdded = false;

        for (String word : dictionary) {//get words in the dictionary which we added
            boolean fullymatches = true;
            for (int i = 0; i < typedWord.length(); i++) {//each string in the word
                if (!typedWord.toLowerCase().startsWith(String.valueOf(word.toLowerCase().charAt(i)), i)) {//check for match
                    fullymatches = false;
                    break;
                }
            }
            if (fullymatches) {
                addWordToSuggestions(word);
                suggestionAdded = true;
            }
        }
        return suggestionAdded;
    }
}

class SuggestionLabel extends JLabel {

    private boolean focused = false;
    private final JWindow autoSuggestionsPopUpWindow;
    private final JTextField textField;
    private final AutoSuggestor autoSuggestor;
    private Color suggestionsTextColor, suggestionBorderColor;

    public SuggestionLabel(String string, final Color borderColor, Color suggestionsTextColor, AutoSuggestor autoSuggestor) {
        super(string);

        this.suggestionsTextColor = suggestionsTextColor;
        this.autoSuggestor = autoSuggestor;
        this.textField = autoSuggestor.getTextField();
        this.suggestionBorderColor = borderColor;
        this.autoSuggestionsPopUpWindow = autoSuggestor.getAutoSuggestionPopUpWindow();

        initComponent();
    }

    private void initComponent() {
        setFocusable(true);
        setForeground(suggestionsTextColor);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                replaceWithSuggestedText();

                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "Enter released");
        getActionMap().put("Enter released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                replaceWithSuggestedText();
                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });
    }

    public void setFocused(boolean focused) {
        if (focused) {
            setBorder(new LineBorder(suggestionBorderColor));
        } else {
            setBorder(null);
        }
        repaint();
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    private void replaceWithSuggestedText() {
        String suggestedWord = getText();
        String text = textField.getText();
        String typedWord = autoSuggestor.getCurrentlyTypedWord();
        String t = text.substring(0, text.lastIndexOf(typedWord));
        String tmp = t + text.substring(text.lastIndexOf(typedWord)).replace(typedWord, suggestedWord);
        textField.setText(tmp + " ");
    }
}


