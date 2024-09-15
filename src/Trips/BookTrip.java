package Trips;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Passengers.Passenger;
import Passengers.PassengersDatabase;
import ETransportationsystem.Database;
import ETransportationsystem.GUI;
import ETransportationsystem.Main;

public class BookTrip {
	
	JComboBox<String> passenger;
	JLabel id, name, tel, email;
	
	public BookTrip(JFrame parent, Database database, Trip trip) throws SQLException {
		
		JFrame frame = new JFrame("Book Trip");
		frame.setSize(750, 675);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().setBackground(GUI.background);
		frame.setLocationRelativeTo(parent);
		
		JPanel panel = new JPanel(new GridLayout(9, 2, 20, 20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30, 50));
		
		panel.add(GUI.JLabel("Passenger:"));
		passenger = GUI.JComboBox(
				PassengersDatabase.getPassengersNames(database));
		panel.add(passenger);
		
		panel.add(GUI.JLabel("ID:"));
		id = GUI.JLabel("");
		panel.add(id);
		
		panel.add(GUI.JLabel("Name:"));
		name = GUI.JLabel("");
		panel.add(name);
		
		panel.add(GUI.JLabel("Tel:"));
		tel = GUI.JLabel("");
		panel.add(tel);
		
		panel.add(GUI.JLabel("Email:"));
		email = GUI.JLabel("");
		panel.add(email);
		
		panel.add(GUI.JLabel("Number of tickets:"));
		JTextField numOfTickets = GUI.JTextField();
		panel.add(numOfTickets);
		
		panel.add(GUI.JLabel("Price:"));
		JLabel price = GUI.JLabel(trip.getPrice()+" $");
		panel.add(price);
		
		panel.add(GUI.JLabel("Total:"));
		JLabel total = GUI.JLabel("");
		panel.add(total);
		
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
				try {
					Integer.parseInt(numOfTickets.getText());
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(frame, "Number of tickets must be int");
					return;
				}
				try {
					TripsDatabase.BookTrip(trip, id.getText(), numOfTickets.getText(), database);
					JOptionPane.showMessageDialog(frame, "Booked successfully");
					Main.refreshTable(TripsDatabase.getAllTrips(database));
					frame.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, "Operation Failed");
					frame.dispose();
				}
			}
		});
		panel.add(submit);
		
		if (passenger.getSelectedItem()!=null) refreshPassengerData(database);
		
		passenger.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					refreshPassengerData(database);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
					frame.dispose();
				}
			}
		});
		
		numOfTickets.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					Integer.parseInt(numOfTickets.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "Number of tickets must be int");
					return;
				}
				
				int num = Integer.parseInt(numOfTickets.getText());
				double p = trip.getPrice();
				double t = num*p;
				total.setText(t+" $");
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					Integer.parseInt(numOfTickets.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "Number of tickets must be int");
					return;
				}
				
				int num = Integer.parseInt(numOfTickets.getText());
				double p = trip.getPrice();
				double t = num*p;
				total.setText(t+" $");
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					Integer.parseInt(numOfTickets.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "Number of tickets must be int");
					return;
				}
				
				int num = Integer.parseInt(numOfTickets.getText());
				double p = trip.getPrice();
				double t = num*p;
				total.setText(t+" $");
				
			}
		});
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
	
	private void refreshPassengerData(Database database) throws SQLException {
		String Passenger = passenger.getSelectedItem().toString();
		Passenger p = PassengersDatabase.getPassengerByName(Passenger, database);
		id.setText(String.valueOf(p.getID()));
		name.setText(p.getName());
		tel.setText(p.getTel());
		email.setText(p.getEmail());
	}

}
