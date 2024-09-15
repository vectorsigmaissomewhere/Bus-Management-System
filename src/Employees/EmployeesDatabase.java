package Employees;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ETransportationsystem.Database;

public class EmployeesDatabase {
	
	public static void AddEmployee(Employee e, Database database) throws SQLException {
		String insert = "INSERT INTO `employees`(`ID`, `Name`, `Email`, `Tel`, `Salary`, "
				+ "`Position`) VALUES ('"+e.getID()+"','"+e.getName()+"','"+e.getEmail()+
				"','"+e.getTel()+"','"+e.getSalary()+"','"+e.getPosition()+"');";
		database.getStatement().execute(insert);
	}
	
	public static int getNextID(Database database) throws SQLException {
		int id = 0;
		int size = getAllEmployees(database).size();
		ArrayList<Employee> employees = getAllEmployees(database);
		if (size!=0) id = employees.get(size-1).getID() + 1;
		return id;
	}
	
	public static ArrayList<Employee> getAllEmployees(Database database) throws SQLException {
		ArrayList<Employee> employees = new ArrayList<>();
		String select = "SELECT * FROM `employees`;";
		ResultSet rs = database.getStatement().executeQuery(select);
		while (rs.next()) {
			Employee e = new Employee();
			e.setID(rs.getInt("ID"));
			e.setName(rs.getString("Name"));
			e.setEmail(rs.getString("Email"));
			e.setTel(rs.getString("Tel"));
			e.setSalary(rs.getDouble("Salary"));
			e.setPosition(rs.getString("Position"));
			employees.add(e);
		}
		return employees;
	}
	
	public static String[] getIDs(Database database) throws SQLException {
		ArrayList<Employee> employees = getAllEmployees(database);
		String[] ids = new String[employees.size()];
		for (int i=0;i<employees.size();i++) {
			ids[i] = String.valueOf(employees.get(i).getID());
		}
		return ids;
	}
	
	public static Employee getEmployee(String id, Database database) throws SQLException {
		Employee e = new Employee();
		String select = "SELECT `ID`, `Name`, `Email`, `Tel`, `Salary`, `Position` "
				+ "FROM `employees` WHERE `ID` = "+id+" ;";
		ResultSet rs = database.getStatement().executeQuery(select);
		rs.next();
		e.setID(rs.getInt("ID"));
		e.setName(rs.getString("Name"));
		e.setEmail(rs.getString("Email"));
		e.setTel(rs.getString("Tel"));
		e.setSalary(rs.getDouble("Salary"));
		e.setPosition(rs.getString("Position"));
		return e;
	}
	
	public static void EditEmployee(Employee e, Database database) throws SQLException {
		String update = "UPDATE `employees` SET `Name`='"+e.getName()+"',`Email`='"+
				e.getEmail()+"',`Tel`='"+e.getTel()+"',`Salary`='"+e.getSalary()+
				"',`Position`='"+e.getPosition()+"' WHERE `ID` = "+e.getID()+" ;";
		database.getStatement().execute(update);
	}
	
	public static void DeleteEmployee(String id, Database database) throws SQLException {
		String delete = "DELETE FROM `employees` WHERE `ID`= "+id+" ;";
		database.getStatement().execute(delete);
	}
	
	public static String[] getEmployeesNames(Database database) throws SQLException {
		ArrayList<Employee> employees = getAllEmployees(database);
		String[] array = new String[employees.size()];
		for (int i=0;i<employees.size();i++) {
			array[i] = employees.get(i).getName();
		}
		
		return array;
	}
	
	public static Employee getEmployeeByName(String name, Database database) throws SQLException {
		Employee e = new Employee();
		String select = "SELECT `ID`, `Name`, `Email`, `Tel`, `Salary`, `Position` "
				+ "FROM `employees` WHERE `Name` = '"+name+"' ;";
		ResultSet rs = database.getStatement().executeQuery(select);
		rs.next();
		e.setID(rs.getInt("ID"));
		e.setName(rs.getString("Name"));
		e.setEmail(rs.getString("Email"));
		e.setTel(rs.getString("Tel"));
		e.setSalary(rs.getDouble("Salary"));
		e.setPosition(rs.getString("Position"));
		return e;
	}

}
