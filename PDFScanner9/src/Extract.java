import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class Extract {
	
	public static void createDatabase(String[] skills, String dbName) throws SQLException
	{
		String url = "jdbc:postgresql://localhost:5433/postgres?user=postgres&password=metroidVania54!";
		Connection conn = DriverManager.getConnection(url);
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("create table " + dbName + " (resume varchar(200) NOT NULL, ");
		for (int i = 0; i < skills.length; i++)
		{
			sqlBuffer.append(skills[i] + " varchar(5) NOT NULL, ");
		}
		sqlBuffer.append("PRIMARY KEY (resume))");
		String sqlString = sqlBuffer.toString();
		
		Statement sqlStatement = null;
		try {
			sqlStatement = conn.createStatement();
			sqlStatement.executeUpdate(sqlString);
		} finally {
			if (sqlStatement != null ) {sqlStatement.close(); }
		}
		
		String allData = ("select * from " + dbName);
		Statement sqlColumns = null;
		
		try {
			sqlColumns = conn.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			ResultSet columnNames = sqlColumns.executeQuery(allData);
			ResultSetMetaData cNMD = columnNames.getMetaData();
			int columnCount = cNMD.getColumnCount();			
			
			String[] columns = new String[columnCount];

			StringBuffer outputMessage = new StringBuffer();
			outputMessage.append("The database \"" + dbName + "\" was created with the following column headings: ");
			for (int i = 0; i < columnCount-1; i++)
			{
				columns[i] = cNMD.getColumnName(i+1);
				outputMessage.append(columns[i] + ", ");
			}
			columns[columns.length-1] = cNMD.getColumnName(columnCount); 
			outputMessage.append("and " + columns[columns.length - 1]);
			String finalOutput = outputMessage.toString();
			
			JFrame message = new JFrame();
			JOptionPane.showMessageDialog(message, finalOutput);

		} finally {
			if (sqlColumns != null) {sqlColumns.close();}
		}				
	
	}
	
	public static void insertCandidates (String[] resumes, String dbName) throws InvalidPasswordException, IOException, SQLException
	{
		String url = "jdbc:postgresql://localhost:5433/postgres?user=postgres&password=metroidVania54!";
		Connection conn = DriverManager.getConnection(url);
		
		String allData = ("select * from " + dbName);
		Statement sqlColumns = null;
		File[] resumeFiles = new File[resumes.length];
		
		try {
			sqlColumns = conn.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			ResultSet columnNames = sqlColumns.executeQuery(allData);
			ResultSetMetaData cNMD = columnNames.getMetaData();
			int columnCount = cNMD.getColumnCount();			
			
			String[] columns = new String[columnCount];

			for (int i = 0; i < columnCount; i++)
				columns[i] = cNMD.getColumnName(i+1);
			
			columnNames.beforeFirst();
			
			String[] skillCheck = new String[columns.length];
			for (int i = 0; i < resumeFiles.length; i++)
				resumeFiles[i] = new File(resumes[i]);
			
			skillCheck[0] = columns[0];
			for (int i = 1; i < skillCheck.length; i++)
				skillCheck[i] = "No";
			
			for (int i = 0; i < resumeFiles.length; i++)
			{
				PDDocument document = PDDocument.load(resumeFiles[i]);
				PDFTextStripper stripper = new PDFTextStripper();
				String text = stripper.getText(document); //Parses text in PDF and returns a string
				text = text.toLowerCase(); //Converts string to lowercase
				String[] resumeArray = text.split(" "); //Splits string into an array of words on spaces
				for (int j = 1; j < columns.length; j++)
				{
					for (String word: resumeArray)
						if (word.equals(columns[j]))
							skillCheck[j] = "Yes";
				}
				
				columnNames.moveToInsertRow();
				columnNames.updateString("resume", resumes[i]);
				
				for (int j = 1; j < skillCheck.length; j++)
				{
					columnNames.updateString(columns[j], skillCheck[j]);
				}
				
				columnNames.insertRow();
				columnNames.beforeFirst();

				for (int m = 0; m < skillCheck.length; m++)
					skillCheck[m] = "No";
				document.close();
			}
			
		} finally {
			if (sqlColumns != null) {sqlColumns.close();}
		}
		String finalOutput = "Resumes Deposited.";
		JFrame message = new JFrame();
		JOptionPane.showMessageDialog(message, finalOutput);
	}
	
	public static void showAllCandidates (String dbName) throws SQLException
	{
		String url = "jdbc:postgresql://localhost:5433/postgres?user=postgres&password=metroidVania54!";
		Connection conn = DriverManager.getConnection(url);
		
		String allData = ("select * from " + dbName);
		Statement sqlColumns = null;
		
		try {
			sqlColumns = conn.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			ResultSet columnNames = sqlColumns.executeQuery(allData);
			ResultSetMetaData cNMD = columnNames.getMetaData();
			int columnCount = cNMD.getColumnCount();			
			
			String[] columns = new String[columnCount];

			for (int i = 0; i < columnCount; i++)
				columns[i] = cNMD.getColumnName(i+1);

			columnNames.last();
			int numRows = columnNames.getRow();
			
			columnNames.beforeFirst();
			
			String[][] tableData = new String[numRows][columnCount];
			while (columnNames.next())
			{
				for (int i = 0; i < numRows; i++)
				{
					for (int j = 0; j < columnCount; j++)
					{
						tableData[i][j] = columnNames.getString(j+1);
					}
					columnNames.next();
				}
			}
						
			JFrame f = new JFrame();
			f.setTitle("Job Candidates");
			JTable t = new JTable(tableData, columns);
			t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					Font font = comp.getFont();
					boolean bestRow = true;
					for (int i = 0; i < columnCount; i++)
					{
						if (tableData[row][i].equals("No"))
							bestRow = false;
					}
					if (bestRow)
					{
						comp.setBackground(Color.YELLOW);
						comp.setFont(font.deriveFont(Font.BOLD));
					}
					else
					{
						comp.setBackground(Color.WHITE);
						comp.setFont(font.deriveFont(Font.PLAIN));
					}
					return comp;
				}
			});
			t.setBounds(20, 30, 600, 300);
			JScrollPane sp = new JScrollPane(t);
			f.add(sp);
			f.setSize(800, 400);
			f.setVisible(true);
		} finally {
			if (sqlColumns != null) {sqlColumns.close();}
		}
	}
	
	public static void showTopCandidates (String dbName, String[] skills) throws SQLException
	{
		String url = "jdbc:postgresql://localhost:5433/postgres?user=postgres&password=metroidVania54!";
		Connection conn = DriverManager.getConnection(url);
		
		Statement sqlColumns = null;
		
		StringBuffer columnRet = new StringBuffer();
		columnRet.append("select resume");
		if (skills == null)
		{
			columnRet.append(" FROM " + dbName);
		}
		else if (skills.length == 1)
		{
			columnRet.append(", " + skills[0] + " FROM " + dbName + " where " + skills[0] + " = 'Yes'");
		}
		else if (skills.length > 1)
		{
			columnRet.append(", ");
			for (int i = 0; i < (skills.length-1); i++)
				columnRet.append(skills[i] + ", ");
			columnRet.append(skills[skills.length-1]);
			columnRet.append(" FROM " + dbName + " where ");
			for (int i = 0; i < (skills.length-1); i++)
				columnRet.append(skills[i] + " = 'Yes' AND ");
			columnRet.append(skills[skills.length-1] + " = 'Yes'");
		}
		
		String columnReturn = columnRet.toString();

				try {
			sqlColumns = conn.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			ResultSet columnNames = sqlColumns.executeQuery(columnReturn);
			ResultSetMetaData cNMD = columnNames.getMetaData();
			int columnCount = cNMD.getColumnCount();			
			
			String[] columns = new String[columnCount];

			for (int i = 0; i < columnCount; i++)
				columns[i] = cNMD.getColumnName(i+1);

			columnNames.last();
			int numRows = columnNames.getRow();
			
			columnNames.beforeFirst();
			
			String[][] tableData = new String[numRows][columnCount];
			while (columnNames.next())
			{
				for (int i = 0; i < numRows; i++)
				{
					for (int j = 0; j < columnCount; j++)
					{
						tableData[i][j] = columnNames.getString(j+1);
					}
					columnNames.next();
				}
			}
			
			JFrame f = new JFrame();
			f.setTitle("Job Candidates Matching Skillset");
			JTable t = new JTable(tableData, columns);
			t.setBounds(20, 30, 600, 300);
			JScrollPane sp = new JScrollPane(t);
			f.add(sp);
			f.setSize(800, 400);
			f.setVisible(true);
		} finally {
			if (sqlColumns != null) {sqlColumns.close();}
		}
	}
	
	public static void main(String[] args) throws IOException, SQLException
	{
		
	}
}
