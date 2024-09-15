package Trips;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Employees.EmployeesDatabase;
import ETransportationsystem.Database;
import ETransportationsystem.GUI;
import ETransportationsystem.Main;
import Buss.BussDatabase;

public class AddTrip {
	
	public AddTrip(JFrame parent, Database database) throws SQLException {
		
		String[] HH = new String[25];
		HH[0] = "HH";
		for (int i=0;i<24;i++) {
			HH[i+1] = String.format("%02d", i);
		}
		
		String[] mm = new String[61];
		mm[0] = "mm";
		for (int i=0;i<60;i++) {
			mm[i+1] = String.format("%02d", i);
		}
		
		JFrame frame = new JFrame("Add Trip");
		frame.setSize(750, 675);
		frame.getContentPane().setBackground(GUI.background);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setLocationRelativeTo(parent);
		
		JPanel panel = new JPanel(new GridLayout(10, 2, 20, 20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30, 50));
		
		panel.add(GUI.JLabel("ID:"));
		JLabel id = GUI.JLabel(String.valueOf(TripsDatabase.getNextID(database)));
		panel.add(id);
		
		panel.add(GUI.JLabel("Start:"));
		JTextField start = GUI.JTextField();
		panel.add(start);
		
		panel.add(GUI.JLabel("Destination:"));
		JTextField destination = GUI.JTextField();
		panel.add(destination);
		
		panel.add(GUI.JLabel("Departure Time:"));
		JPanel dept = new JPanel(new GridLayout(1, 2, 10, 10));
		dept.setBackground(null);
		JComboBox<String> deptHH = GUI.JComboBox(HH);
		JComboBox<String> deptmm = GUI.JComboBox(mm);
		dept.add(deptHH);
		dept.add(deptmm);
		panel.add(dept);
		
		panel.add(GUI.JLabel("Arrival Time:"));
		JPanel arr = new JPanel(new GridLayout(1, 2, 10, 10));
		arr.setBackground(null);
		JComboBox<String> arrHH = GUI.JComboBox(HH);
		JComboBox<String> arrmm = GUI.JComboBox(mm);
		arr.add(arrHH);
		arr.add(arrmm);
		panel.add(arr);
		
		String[] dd = new String[32];
		dd[0] = "dd";
		for (int i=1;i<32;i++) {
			dd[i] = String.format("%02d", i);
		}
		
		String[] MM = new String[13];
		MM[0] = "MM";
		for (int i=1;i<13;i++) {
			MM[i] = String.format("%02d", i);
		}
		
		String[] yyyy = new String[51];
		yyyy[0] = "yyyy";
		for (int i=2010;i<2060;i++) {
			yyyy[i-2009] = String.format("%02d", i);
		}
		
		panel.add(GUI.JLabel("Date:"));
		JPanel date = new JPanel(new GridLayout(1, 3, 10, 10));
		date.setBackground(null);
		JComboBox<String> datedd = GUI.JComboBox(dd);
		JComboBox<String> dateMM = GUI.JComboBox(MM);
		JComboBox<String> dateyyyy = GUI.JComboBox(yyyy);
		date.add(datedd);
		date.add(dateMM);
		date.add(dateyyyy);
		panel.add(date);
		
		panel.add(GUI.JLabel("Price:"));
		JTextField price = GUI.JTextField();
		panel.add(price);
		
		panel.add(GUI.JLabel("Driver:"));
		JComboBox<String> driver = GUI.JComboBox(
				EmployeesDatabase.getEmployeesNames(database));
		panel.add(driver);
		
		panel.add(GUI.JLabel("Bus"));
		JComboBox<String> bus = GUI.JComboBox(BussDatabase.getBuss(database));
		panel.add(bus);
		
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
				if (checkChoice(deptHH, frame)) return;
				if (checkChoice(deptmm, frame)) return;
				if (checkChoice(arrHH, frame)) return;
				if (checkChoice(arrmm, frame)) return;
				if (checkChoice(datedd, frame)) return;
				if (checkChoice(dateMM, frame)) return;
				if (checkChoice(dateyyyy, frame)) return;
				try {
					Double.parseDouble(price.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "Price must be a double");
					return;
				}
				
				Trip trip = new Trip();
				trip.setID(Integer.parseInt(id.getText()));
				trip.setStart(start.getText());
				trip.setDestination(destination.getText());
				String deptTime = deptHH.getSelectedItem().toString()
						+":"+deptmm.getSelectedItem().toString()+ ":00";;
				String arrTime = arrHH.getSelectedItem().toString()+":"+
						arrmm.getSelectedItem().toString()+ ":00";;
				trip.setDepartureTime(deptTime);
				trip.setArrivalTime(arrTime);
				String d = dateyyyy.getSelectedItem().toString()+
						"-"+dateMM.getSelectedItem().toString()+
						"-"+datedd.getSelectedItem().toString();
				trip.setDate(d);
				trip.setBookedSeats(0);
				trip.setPrice(Double.parseDouble(price.getText()));
				try {
					trip.setDriver(EmployeesDatabase.getEmployeeByName(
							driver.getSelectedItem().toString(), database));
					trip.setBus(BussDatabase.getBusByDescription(
							bus.getSelectedItem().toString(), database));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
				trip.setPassengers(new ArrayList<>());
				try {
					TripsDatabase.AddTrip(trip, database);
					JOptionPane.showMessageDialog(frame, "Trip added successfully");
					Main.refreshTable(TripsDatabase.getAllTrips(database));
					frame.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, "Operation Failed");
					frame.dispose();
				}
			}
		});
		panel.add(submit);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
	
	private boolean checkChoice(JComboBox<String> comboBox, JFrame frame) {
		boolean c = false;
		if (comboBox.getSelectedIndex()==0) {
			JOptionPane.showMessageDialog(frame, 
					"Data Error: "+comboBox.getSelectedItem().toString());
			c = true;
		}
		return c;
	}

}
