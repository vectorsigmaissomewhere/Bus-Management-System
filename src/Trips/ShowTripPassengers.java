package Trips;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Passengers.Passenger;
import Passengers.PassengersDatabase;
import ETransportationsystem.Database;
import ETransportationsystem.GUI;

public class ShowTripPassengers {
	
	private JPanel table;
	private GridLayout gridLayout;
	private JComboBox<String> trip;
	
	public ShowTripPassengers(JFrame parent, Database database) throws SQLException {
		
		JFrame frame = new JFrame("Show Trip Passengers");
		frame.setSize(1050, 675);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().setBackground(GUI.background);
		frame.setLocationRelativeTo(parent);
		
		JPanel panel = new JPanel(new BorderLayout(20, 20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30, 50));
		
		JPanel top = new JPanel(new GridLayout(1, 2));
		top.setBackground(null);
		
		top.add(GUI.JLabel("Trip:"));
		trip = GUI.JComboBox(TripsDatabase.getIDs(database));
		top.add(trip);
		panel.add(top, BorderLayout.NORTH);
		
		gridLayout = new GridLayout(7, 1);
		table = new JPanel(gridLayout);
		if (trip.getSelectedItem()!=null) refreshTable(database);
		trip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					refreshTable(database);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
					frame.dispose();
				}
			}
		});
		
		JScrollPane sp = new JScrollPane(table);
		
		panel.add(sp, BorderLayout.CENTER);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
	private void refreshTable(Database database) throws SQLException {
		
		table.removeAll();
		table.repaint();
		table.revalidate();
		
		String[][] data = TripsDatabase.getPassengers(
				trip.getSelectedItem().toString(), database);
		
		if (data.length!=0 && data[0].length!=0) {
			int rows = data[0].length;
			if (rows<7) rows = 7;
			gridLayout.setRows(rows);
			
			JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
			row1.setBackground(Color.decode("#e5e5e5"));
			row1.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
			row1.add(JLabel("ID", 75));
			row1.add(JLabel("Name", 170));
			row1.add(JLabel("Tel", 170));
			row1.add(JLabel("Email", 250));
			row1.add(JLabel("Tickets", 100));
			table.add(row1);
			
			for (int i=0;i<data.length;i++) {
				Passenger p = PassengersDatabase.getPassenger(data[i][0], database);
				JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
				row.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
				if (i%2==1) row.setBackground(Color.decode("#e5e5e5"));
				row.add(JLabel(String.valueOf(p.getID()), 75));
				row.add(JLabel(p.getName(), 170));
				row.add(JLabel(p.getTel(), 170));
				row.add(JLabel(p.getEmail(), 250));
				row.add(JLabel(data[i][1], 100));
				table.add(row);
			}
		}
	}
	
	private JLabel JLabel(String text, int width) {
		JLabel label = new JLabel(text);
		label.setPreferredSize(new Dimension(width, 20));
		label.setFont(new Font(null, Font.PLAIN, 20));
		label.setForeground(Color.decode("#13678A"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}

}
