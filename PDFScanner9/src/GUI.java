import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JScrollPane;

public class GUI {

	private JFrame frmResumeTrackingTool;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmResumeTrackingTool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmResumeTrackingTool = new JFrame();
		frmResumeTrackingTool.setFont(null);
		frmResumeTrackingTool.setTitle("Resume Tracking Tool");
		frmResumeTrackingTool.setBounds(100, 100, 362, 206);
		frmResumeTrackingTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmResumeTrackingTool.getContentPane().setLayout(null);
		
		JButton btnCreateDatabase = new JButton("Create Database");
		btnCreateDatabase.setBounds(65, 48, 217, 25);
		btnCreateDatabase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFrame f = new JFrame();
				f.setTitle("Create Database");
				f.setSize(400, 200);
				JTextField textField = new JTextField();
				textField.setBounds(150, 20, 150, 30);
				textField.setColumns(10);
				
				JTextField textField_1 = new JTextField();
				textField_1.setBounds(150, 70, 150, 30);
				textField_1.setColumns(10);
				
				JLabel lblDatabaseName = new JLabel("Database Name:");
				lblDatabaseName.setBounds(20, 20, 200, 30);
				lblDatabaseName.setFont(new Font("Tahoma", Font.PLAIN, 16));
				
				JLabel lblSkillsToTrack = new JLabel("Skills to Track:");
				lblSkillsToTrack.setBounds(20, 70, 200, 30);
				lblSkillsToTrack.setFont(new Font("Tahoma", Font.PLAIN, 16));
				
				JButton btnDatabase = new JButton("Create Database");
				btnDatabase.setBounds(150, 120, 150, 30);
				btnDatabase.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						String dbName = textField.getText();
						String[] skills = textField_1.getText().split(" ");
						try {
							Extract.createDatabase(skills, dbName);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println();
						}
						try (OutputStream output = new FileOutputStream("C:\\Users\\wolfe\\eclipse-workspace\\PDFScanner9\\src\\config.properties")) {
				            Properties prop = new Properties();
				            prop.setProperty("lastDatabase", dbName);
				            prop.store(output, null);
				        } catch (IOException io) {
				            io.printStackTrace();
				        }
					}
				});
				btnDatabase.setFont(new Font("Tahoma", Font.PLAIN, 16));
				
				Container c = f.getContentPane();
				c.setLayout(null);
				c.add(textField);
				c.add(textField_1);
				c.add(lblDatabaseName);
				c.add(lblSkillsToTrack);
				c.add(btnDatabase);
				f.setVisible(true);    
			}
		});
		btnCreateDatabase.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmResumeTrackingTool.getContentPane().add(btnCreateDatabase);
		
		JButton btnDepositResumes = new JButton("Deposit Resumes");
		btnDepositResumes.setBounds(65, 84, 217, 25);
		btnDepositResumes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFrame g = new JFrame();
				g.setTitle("Deposit Resumes");
				g.setSize(800, 550);
				JTextField textFieldy = new JTextField();
				textFieldy.setBounds(260, 20, 150, 30);
				textFieldy.setColumns(10);
				try (InputStream input = new FileInputStream("C:\\Users\\wolfe\\eclipse-workspace\\PDFScanner9\\src\\config.properties")) {
		            Properties prop = new Properties();
		            prop.load(input);
		            textFieldy.setText(prop.getProperty("lastDatabase"));
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
				
				JTextArea textArea = new JTextArea();
				textArea.setBounds(100, 100, 600, 300);
				textArea.setColumns(10);
				textArea.setLineWrap(true);
				
				JScrollPane sp = new JScrollPane(textArea);
				
				JLabel lbldbName = new JLabel("Enter Database Name:");
				lbldbName.setBounds(100, 20, 200, 30);
				lbldbName.setFont(new Font("Tahoma", Font.PLAIN, 16));
				try (InputStream input = new FileInputStream("C:\\Users\\wolfe\\eclipse-workspace\\PDFScanner9\\src\\config.properties")) {
		            Properties prop = new Properties();
		            prop.load(input);
		            textFieldy.setText(prop.getProperty("lastDatabase"));
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
				
				JLabel lblResumes = new JLabel("Resumes to Deposit:");
				lblResumes.setBounds(100, 70, 150, 20);
				lblResumes.setFont(new Font("Tahoma", Font.PLAIN, 16));
				
				JButton btnBrowse = new JButton("Browse");
				btnBrowse.setBounds(600, 70, 100, 20);
				btnBrowse.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
							JFileChooser j = new JFileChooser();
							int returnValue = j.showOpenDialog(null);
							if (returnValue == JFileChooser.APPROVE_OPTION)
							{
								File userFile = j.getSelectedFile();
								textArea.insert(userFile.toString() + "\n", 0);
							}
						}
				});
				btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 16));
				
				JButton btnResumes = new JButton("Deposit Resumes");
				btnResumes.setBounds(300, 430, 200, 30);
				btnResumes.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						String dbName = textFieldy.getText();
						String[] resumeArray = textArea.getText().split("\n");
						try {
							Extract.insertCandidates(resumeArray, dbName);
						} catch (InvalidPasswordException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try (OutputStream output = new FileOutputStream("C:\\Users\\wolfe\\eclipse-workspace\\PDFScanner9\\src\\config.properties")) {
				            Properties prop = new Properties();
				            prop.setProperty("lastDatabase", dbName);
				            prop.store(output, null);
				        } catch (IOException io) {
				            io.printStackTrace();
				        }
					}
				});
				
				btnResumes.setFont(new Font("Tahoma", Font.PLAIN, 16));
				
				Container c = g.getContentPane();
				c.setLayout(null);
				c.add(textFieldy);
				c.add(textArea);
				c.add(sp);
				c.add(lbldbName);
				c.add(lblResumes);
				c.add(btnResumes);
				c.add(btnBrowse);
				g.setVisible(true);
			}
			
		});
		btnDepositResumes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmResumeTrackingTool.getContentPane().add(btnDepositResumes);
		
		JLabel lblChooseATask = new JLabel("Select a Task");
		lblChooseATask.setBounds(109, 21, 126, 16);
		lblChooseATask.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frmResumeTrackingTool.getContentPane().add(lblChooseATask);
		
		JButton btnShowAllResumes = new JButton("Review Candidates");
		btnShowAllResumes.setBounds(65, 120, 217, 25);
		btnShowAllResumes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFrame h = new JFrame();
				h.setTitle("Review Candidates");
				h.setSize(350, 200);

				JTextField textField = new JTextField();
				textField.setBounds(150, 20, 150, 30);
				textField.setColumns(10);
				try (InputStream input = new FileInputStream("C:\\Users\\wolfe\\eclipse-workspace\\PDFScanner9\\src\\config.properties")) {
		            Properties prop = new Properties();
		            prop.load(input);
		            textField.setText(prop.getProperty("lastDatabase"));
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
				
				JTextField textField_1 = new JTextField();
				textField_1.setBounds(150, 50, 150, 30);
				textField_1.setColumns(10);
				
				JLabel lblDatabaseName = new JLabel("Database Name:");
				lblDatabaseName.setBounds(20, 20, 200, 30);
				lblDatabaseName.setFont(new Font("Tahoma", Font.PLAIN, 16));
				
				JLabel lblSkills = new JLabel("Select Skills:");
				lblSkills.setBounds(20, 50, 200, 30);
				lblSkills.setFont(new Font("Tahoma", Font.PLAIN, 16));
				
				JButton btnShowAllResumes = new JButton("Show All Resumes");
				btnShowAllResumes.setBounds(35, 120, 250, 25);
				btnShowAllResumes.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						String dbName = textField.getText();
						try {
							Extract.showAllCandidates(dbName);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try (OutputStream output = new FileOutputStream("C:\\Users\\wolfe\\eclipse-workspace\\PDFScanner9\\src\\config.properties")) {
				            Properties prop = new Properties();
				            prop.setProperty("lastDatabase", dbName);
				            prop.store(output, null);
				        } catch (IOException io) {
				            io.printStackTrace();
				        }
					}
				});
				btnShowAllResumes.setFont(new Font("Tahoma", Font.PLAIN, 16));
				frmResumeTrackingTool.getContentPane().add(btnShowAllResumes);

				JButton btnShowSkillResumes = new JButton("Show Resumes With Skills");
				btnShowSkillResumes.setBounds(35, 90, 250, 25);
				btnShowSkillResumes.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						String[] skills = null;
						if (!textField_1.getText().isEmpty())
						{
							skills = textField_1.getText().split(" ");
						}
						String dbName = textField.getText();
						try {
							Extract.showTopCandidates(dbName, skills);
						} catch (SQLException e4) {
							// TODO Auto-generated catch block
							e4.printStackTrace();
						}
						try (OutputStream output = new FileOutputStream("C:\\Users\\wolfe\\eclipse-workspace\\PDFScanner9\\src\\config.properties")) {
				            Properties prop = new Properties();
				            prop.setProperty("lastDatabase", dbName);
				            prop.store(output, null);
				        } catch (IOException io) {
				            io.printStackTrace();
				        }
					}
				});
				btnShowSkillResumes.setFont(new Font("Tahoma", Font.PLAIN, 16));
				
				Container c = h.getContentPane();
				c.setLayout(null);
				c.add(textField);
				c.add(textField_1);
				c.add(lblDatabaseName);
				c.add(lblSkills);
				c.add(btnShowAllResumes);
				c.add(btnShowSkillResumes);
				h.setVisible(true);
			}
		});
		btnShowAllResumes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmResumeTrackingTool.getContentPane().add(btnShowAllResumes);
	}
}
