package Buss;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ETransportationsystem.Database;
import ETransportationsystem.GUI;

public class EditBus {

	public EditBus(JFrame parent, Database database) throws SQLException {

		JFrame frame = new JFrame("Edit Bus");
		frame.setSize(750, 400);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setLocationRelativeTo(parent);
		frame.getContentPane().setBackground(Color.decode("#EBFFD8"));

		JPanel panel = new JPanel(new GridLayout(6, 2, 20, 20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30, 50));

		panel.add(GUI.JLabel("ID:"));

		JComboBox<String> id = GUI.JComboBox(BussDatabase.getBussIDs(database));
		panel.add(id);

		panel.add(GUI.JLabel("Capacity:"));
		JTextField capacity = GUI.JTextField();
		panel.add(capacity);

		panel.add(GUI.JLabel("Description:"));
		JTextField description = GUI.JTextField();
		panel.add(description);

		panel.add(GUI.JLabel("Type:"));
		JTextField type = GUI.JTextField();
		panel.add(type);

		panel.add(GUI.JLabel("Engine Number:"));
		JTextField engineNumber = GUI.JTextField();
		panel.add(engineNumber);

		JButton submit = GUI.JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Bus b = BussDatabase.getBuss(id.getSelectedItem().toString(), database);
					b.setCapacity(Integer.parseInt(capacity.getText()));
					b.setDescription(description.getText());
					b.setType(type.getText());
					b.setEngineNumber(engineNumber.getText());
					BussDatabase.EditBus(b, database);
					JOptionPane.showMessageDialog(frame, "Bus updated successfully");
					frame.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, "Operation Failed: " + e1.getMessage());
					e1.printStackTrace();
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(frame, "Invalid data entered!");
				}
			}
		});
		panel.add(submit);

		JButton delete = GUI.JButton("Delete");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					BussDatabase.DeleteBus(id.getSelectedItem().toString(), database);
					JOptionPane.showMessageDialog(frame, "Bus deleted successfully");
					frame.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, "Operation Failed: " + e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		panel.add(delete);

		id.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Bus b = BussDatabase.getBuss(id.getSelectedItem().toString(), database);
					capacity.setText(String.valueOf(b.getCapacity()));
					description.setText(b.getDescription());
					type.setText(b.getType());  // Populate Type field
					engineNumber.setText(b.getEngineNumber());  // Populate Engine Number field
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, "Operation Failed: " + e1.getMessage());
					e1.printStackTrace();
				}
			}
		});

		if (id.getSelectedItem() != null) {
			try {
				Bus b = BussDatabase.getBuss(id.getSelectedItem().toString(), database);
				capacity.setText(String.valueOf(b.getCapacity()));
				description.setText(b.getDescription());
				type.setText(b.getType());  
				engineNumber.setText(b.getEngineNumber());  
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(frame, "Operation Failed: " + e1.getMessage());
				e1.printStackTrace();
			}
		}

		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
