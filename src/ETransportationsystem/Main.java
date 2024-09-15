package ETransportationsystem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Trips.BookTrip;
import Trips.Trip;
import Trips.TripsDatabase;

public class Main {
	
	private static JFrame frame;
	private static JPanel table;
	private static GridLayout gridLayout;
	private static Database database;

	public static void main(String[] args) throws SQLException {
		
		database = new Database();

		frame = new JFrame("E-Transportation Management System");
		frame.setSize(1050, 650);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().setBackground(Color.decode("#EBFFD8"));
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel(new BorderLayout(20, 20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50, 40, 30, 40));
		
		JLabel title = new JLabel("Welcome to E-Transportation Management System");
		title.setForeground(Color.decode("#012030"));
		title.setFont(new Font(null, Font.BOLD, 35));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(title, BorderLayout.NORTH);
		
		gridLayout = new GridLayout(6, 1);
		table = new JPanel(gridLayout);
		table.setBackground(Color.decode("#EBFFD8"));
		
		ArrayList<Trip> trips = TripsDatabase.getAllTrips(database);
		refreshTable(trips);
		
		JScrollPane sp = new JScrollPane(table);
		panel.add(sp, BorderLayout.CENTER);
		
		JButton modify = new JButton("Modify");
		modify.setBackground(Color.decode("#45C4B0"));
		modify.setForeground(Color.white);
		modify.setFont(new Font(null, Font.BOLD, 22));
		modify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ModifyList(frame, database);
			}
		});
		panel.add(modify, BorderLayout.SOUTH);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
	
	public static void refreshTable(ArrayList<Trip> trips) {
		table.removeAll();
		table.repaint();
		table.revalidate();
		int rows = trips.size() + 1;
		if (rows<6) rows = 6;
		gridLayout.setRows(rows);
		table.add(row(0, null));
		for (int i=0;i<trips.size();i++) {
			JPanel trip = row(i+1, trips.get(i));
			table.add(trip);
		}
	}
	
	private static JPanel row(int index, Trip trip) {
		JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		if (index%2==0) row.setBackground(Color.decode("#e5e5e5"));
		else row.setBackground(Color.decode("#EEEEEE"));
		
		String trainS, startS, destS, dateS, deptS, arrS, priceS, statusS;
		
		if (trip!=null) {
			trainS = trip.getBus().getDescription();
			startS = trip.getStart();
			destS = trip.getDestination();
			dateS = trip.getDate();
			deptS = trip.getDepartureTime();
			arrS = trip.getArrivalTime();
			priceS = trip.getPrice()+" $";
			statusS = "Booked";
			if (trip.getBus().getCapacity()>trip.getBookedSeats()) statusS = "Available";
			row.setCursor(new Cursor(Cursor.HAND_CURSOR));
			row.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						new BookTrip(frame, database, trip);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, e1.getMessage());
					}
				}
			});
		} else {
			trainS = "Bus";
			startS = "From";
			destS = "To";
			dateS = "Date";
			deptS = "Dept";
			arrS = "Arr";
			priceS = "Price";
			statusS = "Status";
		}
		
		JLabel train = JLabel(trainS, 100);
		row.add(train);
		
		JLabel start = JLabel(startS, 100);
		row.add(start);
		
		JLabel dest = JLabel(destS, 100);
		row.add(dest);
		
		JLabel date = JLabel(dateS, 150);
		row.add(date);
		
		JLabel deptTime = JLabel(deptS, 65);
		row.add(deptTime);
		
		JLabel arrTime = JLabel(arrS, 65);
		row.add(arrTime);
		
		JLabel price = JLabel(priceS, 70);
		row.add(price);
		
		JLabel status = JLabel(statusS, 100);
		row.add(status);
		
		return row;
	}
	
	private static JLabel JLabel(String text, int width) {
		JLabel label = new JLabel(text);
		label.setPreferredSize(new Dimension(width, 20));
		label.setFont(new Font(null, Font.PLAIN, 20));
		label.setForeground(Color.decode("#13678A"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}

}
