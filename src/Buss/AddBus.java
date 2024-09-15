package Buss;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ETransportationsystem.Database;
import ETransportationsystem.GUI;

import javax.swing.JButton;

public class AddBus {
	
	public AddBus(JFrame oldFrame, Database database) throws SQLException {
		
		JFrame frame = new JFrame("Add Bus");
		frame.setSize(750, 400);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setLocationRelativeTo(oldFrame);
		frame.getContentPane().setBackground(Color.decode("#EBFFD8"));
		
		JPanel panel = new JPanel(new GridLayout(6, 2, 20, 20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30, 50));
		
		panel.add(GUI.JLabel("ID:"));
		
		JLabel id = GUI.JLabel(String.valueOf(BussDatabase.getNextID(database)));
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

		JButton cancel = GUI.JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(cancel);
		
		JButton submit = GUI.JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bus b = new Bus();
				b.setID(Integer.parseInt(id.getText()));
				b.setCapacity(Integer.parseInt(capacity.getText()));
				b.setDescription(description.getText());
				b.setType(type.getText());  // Set the type field
				b.setEngineNumber(engineNumber.getText());  // Set the engine number field
				try {
					BussDatabase.AddBus(b, database);
					JOptionPane.showMessageDialog(frame, "Bus added successfully");
					frame.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, "Operation Failed");
				}
			}
		});
		panel.add(submit);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}

}
