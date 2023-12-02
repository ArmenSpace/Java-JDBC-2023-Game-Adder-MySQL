import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Window extends JFrame implements ActionListener {
	JTextField text1 = new JTextField(10);
	JTextField text2 = new JTextField(10);
	JButton button = new JButton("Add");
	JTable dataTable;
	DefaultTableModel tableModel;
	
	public Window() {
		setTitle("Games of 2023");
		setLayout(new BorderLayout());
		setSize(700, 500);
		JPanel inputPanel = new JPanel(new FlowLayout());
		inputPanel.add(text1);
		inputPanel.add(text2);
		inputPanel.add(button);
		button.addActionListener(this);
		add(inputPanel, BorderLayout.NORTH);
		String[] columnNames = {"ID", "Name", "Favorite Game"};
		tableModel = new DefaultTableModel(columnNames, 0);
		dataTable  = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(dataTable);
		add(scrollPane, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		displayDataFromDatabase();
		setVisible(true);
	}
	
	private void displayDataFromDatabase() {
		
		try {
			Statement statement = ConnectionDB.connect().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM players");
			while (resultSet.next()) {
				addRowToTable(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("favGame"));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addRowToTable(int id, String name, String favGame) {
		Object[] rowData = {id, name, favGame};
		tableModel.addRow(rowData);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			int id = getAutoIncrementedIdFromDatabase();
			addRowToTable(id, text1.getText(), text2.getText());
			insertDataIntoDatabase(id, text1.getText(), text2.getText());
			text1.setText("");
			text2.setText("");
		}
	}
	
	private int getAutoIncrementedIdFromDatabase() {
		int id = 0;
		try {
			Statement statement = ConnectionDB.connect().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM players");
			if (resultSet.next()) {
				id = resultSet.getInt(1) + 1;
			}
			resultSet.close();
			statement.close();
			ConnectionDB.connect().close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	private void insertDataIntoDatabase(int id, String name, String favGame) {
		try {
			String query = "INSERT INTO players (id, name, favGame) VALUES (?, ?, ?)";
			try (PreparedStatement preparedStatement = ConnectionDB.connect().prepareStatement(query)) {
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, name);
				preparedStatement.setString(3, favGame);
				preparedStatement.executeUpdate();
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Not Connected to DB");
		}
	}
}
