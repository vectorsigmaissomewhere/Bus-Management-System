package Buss;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ETransportationsystem.Database;

public class BussDatabase {
	
	public static void AddBus(Bus t, Database database) throws SQLException {
		String insert = "INSERT INTO `bus` (`ID`, `Capacity`, `Description`, `Type`, `Engine_number` ) VALUES" 
				+ "('"+t.getID()+"','"+t.getCapacity()+"','"+t.getDescription()+"', '"+t.getType()+"', '"+t.getEngineNumber()+"');";
		database.getStatement().execute(insert);
	}

	public static int getNextID(Database database) throws SQLException {
		int id = 0;
		if (getAllBuss(database).size() != 0) {
			id = getAllBuss(database).get(getAllBuss(database).size() - 1).getID() + 1;
		}
		return id;
	}
	
	public static ArrayList<Bus> getAllBuss(Database database) throws SQLException {
		String select = "SELECT * FROM bus;";
		ArrayList<Bus> bus = new ArrayList<>();
		ResultSet rs = database.getStatement().executeQuery(select);
		while (rs.next()) {
			Bus b = new Bus();
			b.setID(rs.getInt("ID"));
			b.setCapacity(rs.getInt("Capacity"));
			b.setDescription(rs.getString("Description"));
			b.setType(rs.getString("Type"));
			b.setEngineNumber(rs.getString("Engine_number")); 
			bus.add(b);
		}
		return bus;
	}
	
	public static String[] getBussIDs(Database database) throws SQLException {
		ArrayList<Bus> buss = getAllBuss(database);
		String[] array = new String[buss.size()];
		for (int i = 0; i < buss.size(); i++) {
			array[i] = String.valueOf(buss.get(i).getID());
		}
		return array;
	}
	
	public static Bus getBuss(String id, Database database) throws SQLException {
		Bus b = new Bus();
		String select = "SELECT `ID`, `Capacity`, `Description`, `Type`, `Engine_number` FROM `bus` "
				+ "WHERE `ID` = " + id + ";";
		ResultSet rs = database.getStatement().executeQuery(select);
		rs.next();
		b.setID(rs.getInt("ID"));
		b.setCapacity(rs.getInt("Capacity"));
		b.setDescription(rs.getString("Description"));
		b.setType(rs.getString("Type"));
		b.setEngineNumber(rs.getString("Engine_number"));
		return b;
	}
	
	public static void EditBus(Bus bus, Database database) throws SQLException {
		String update = "UPDATE `bus` SET `Capacity`='" + bus.getCapacity() + 
				"', `Description`='" + bus.getDescription() +
				"', `Type`='" + bus.getType() + 
				"', `Engine_number`='" + bus.getEngineNumber() + 
				"' WHERE `ID` = " + bus.getID() + " ;";
		database.getStatement().execute(update);
	}
	
	public static void DeleteBus(String id, Database database) throws SQLException {
		String delete = "DELETE FROM `bus` WHERE `ID` = " + id + " ;";
		database.getStatement().execute(delete);
	}
	
	public static String[] getBuss(Database database) throws SQLException {
		ArrayList<Bus> bus = getAllBuss(database);
		String[] array = new String[bus.size()];
		for (int i = 0; i < bus.size(); i++) {
			array[i] = bus.get(i).getDescription();
		}
		return array;
	}
	
	public static Bus getBusByDescription(String description, Database database) throws SQLException {
		Bus b = new Bus();
		String select = "SELECT `ID`, `Capacity`, `Description`, `Type`, `Engine_number` FROM `bus` "
				+ "WHERE `Description` = '" + description + "';";
		ResultSet rs = database.getStatement().executeQuery(select);
		rs.next();
		b.setID(rs.getInt("ID"));
		b.setCapacity(rs.getInt("Capacity"));
		b.setDescription(rs.getString("Description"));
		b.setType(rs.getString("Type"));
		b.setEngineNumber(rs.getString("Engine_number"));
		return b;
	}
}
