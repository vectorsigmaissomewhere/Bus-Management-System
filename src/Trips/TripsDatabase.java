package Trips;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Employees.EmployeesDatabase;
import ETransportationsystem.Database;
import Buss.BussDatabase;

public class TripsDatabase {
	
	public static void AddTrip(Trip t, Database database) throws SQLException {
		String insert = "INSERT INTO `trips`(`ID`, `Start`, `Destination`, `DepartureTime`, "
				+ "`ArrTime`, `Date`, `BookedSeats`, `Price`, `Driver`, `Bus`) VALUES "
				+ "('"+t.getID()+"','"+t.getStart()+"','"+t.getDestination()+"','"+
				t.getDepartureTime()+"','"+t.getArrivalTime()+"','"+t.getDate()+"','"+
				t.getBookedSeats()+"','"+t.getPrice()+"','"+t.getDriver().getID()+"','"+
				t.getBus().getID()+"');";
		database.getStatement().execute(insert);
		
		String create = "CREATE TABLE `Trip "+t.getID()+
				" Passengers` (Passenger int, Tickets int);";
		database.getStatement().execute(create);
	}
	
	public static int getNextID(Database database) throws SQLException {
		int id = 0;
		ArrayList<Trip> trips = getAllTrips(database);
		int size = trips.size();
		if (size!=0) id = trips.get(size-1).getID() + 1;
		return id;
	}
	
	public static ArrayList<Trip> getAllTrips(Database database) throws SQLException {
		ArrayList<Trip> trips = new ArrayList<>();
		ArrayList<Integer> drivers = new ArrayList<>();
		ArrayList<Integer> trains = new ArrayList<>();
		String select = "SELECT * FROM `trips`;";
		ResultSet rs = database.getStatement().executeQuery(select);
		while (rs.next()) {
			Trip t = new Trip();
			t.setID(rs.getInt("ID"));
			t.setStart(rs.getString("Start"));
			t.setDestination(rs.getString("Destination"));
			t.setDepartureTime(rs.getString("DepartureTime"));
			t.setArrivalTime(rs.getString("ArrTime"));
			t.setDate(rs.getString("Date"));
			t.setBookedSeats(rs.getInt("BookedSeats"));
			t.setPrice(rs.getDouble("Price"));
			drivers.add(rs.getInt("Driver"));
			trains.add(rs.getInt("Bus"));
			trips.add(t);
		}
		for (int i=0;i<trips.size();i++) {
			trips.get(i).setDriver(EmployeesDatabase.getEmployee(
					String.valueOf(drivers.get(i)), database));
			trips.get(i).setBus(BussDatabase.getBuss(
					String.valueOf(trains.get(i)), database));
		}
		return trips;
	}
	
	public static String[] getIDs(Database database) throws SQLException {
		ArrayList<Trip> trips = getAllTrips(database);
		String[] array = new String[trips.size()];
		for (int i=0;i<trips.size();i++) {
			array[i] = String.valueOf(trips.get(i).getID());
		}
		return array;
	}
	
	public static Trip getTrip(String id, Database database) throws SQLException {
		String select = "SELECT `ID`, `Start`, `Destination`, `DepartureTime`, `ArrTime`, "
				+ "`Date`, `BookedSeats`, `Price`, `Driver`, `Bus` FROM `trips` WHERE "
				+ "`ID` = "+id+" ;";
		ResultSet rs = database.getStatement().executeQuery(select);
		rs.next();
		Trip t = new Trip();
		t.setID(rs.getInt("ID"));
		t.setStart(rs.getString("Start"));
		t.setDestination(rs.getString("Destination"));
		t.setDepartureTime(rs.getString("DepartureTime"));
		t.setArrivalTime(rs.getString("ArrTime"));
		t.setDate(rs.getString("Date"));
		t.setBookedSeats(rs.getInt("BookedSeats"));
		t.setPrice(rs.getDouble("Price"));
		int driverID = rs.getInt("Driver");
		int busID = rs.getInt("Bus");
		t.setDriver(EmployeesDatabase.getEmployee(String.valueOf(driverID), database));
		t.setBus(BussDatabase.getBuss(String.valueOf(busID), database));
		return t;
	}
	
	public static void EditTrip(Trip t, Database database) throws SQLException {
		String update = "UPDATE `trips` SET `Start`='"+t.getStart()+"',`Destination`="
				+ "'"+t.getDestination()+"',`DepartureTime`='"+t.getDepartureTime()+"',"
						+ "`ArrTime`='"+t.getArrivalTime()+"',`Date`='"+t.getDate()+"',"
								+ "`Price`='"+t.getPrice()+"',`Driver`='"+
						t.getDriver().getID()+"',`Bus`='"+t.getBus().getID()+
						"' WHERE `ID` = "+t.getID()+" ;";
		database.getStatement().execute(update);
	}
	
	public static void DeleteTrip(String id, Database database) throws SQLException {
		String delete = "DELETE FROM `trips` WHERE `ID` = "+id+" ;";
		database.getStatement().execute(delete);
		
		String drop = "DROP TABLE `Trip "+id+" Passengers`;";
		database.getStatement().execute(drop);
	}
	
	public static void BookTrip(Trip t, String ID, String num, Database d) throws SQLException {
		String insert = "INSERT INTO `trip "+t.getID()+" passengers`(`Passenger`, `Tickets`)"
				+ " VALUES ('"+ID+"','"+num+"');";
		d.getStatement().execute(insert);
		
		t.setBookedSeats(t.getBookedSeats() + Integer.parseInt(num));
		String update = "UPDATE `trips` SET `BookedSeats`='"+t.getBookedSeats()+"' WHERE `ID` = "+
				t.getID()+" ;";
		d.getStatement().execute(update);
	}
	
	public static String[][] getPassengers(String id, Database database) throws SQLException {
		String select = "SELECT * FROM `Trip "+id+" Passengers`;";
		ResultSet rs = database.getStatement().executeQuery(select);
		ArrayList<Integer> ids = new ArrayList<>();
		ArrayList<Integer> nums = new ArrayList<>();
		while (rs.next()) {
			ids.add(rs.getInt("Passenger"));
			nums.add(rs.getInt("Tickets"));
		}
		String[][] array = new String[ids.size()][2];
		for (int i=0;i<ids.size();i++) {
			array[i][0] = String.valueOf(ids.get(i));
			array[i][1] = String.valueOf(nums.get(i));
		}
		return array;
	}

}
