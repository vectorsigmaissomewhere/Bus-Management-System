package Employees;

import java.awt.BorderLayout;
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

public class EditEmployee {
	
	private JComboBox<String> id;
	private JTextField name;
	private JTextField email;
	private JTextField tel;
	private JTextField salary;
	private JTextField position;
	
	public EditEmployee(JFrame parent, Database database) throws SQLException {
		
		JFrame frame = new JFrame("Edit Employee");
		frame.setSize(750, 600);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setLocationRelativeTo(parent);
		frame.getContentPane().setBackground(GUI.background);
		
		JPanel panel = new JPanel(new GridLayout(7, 2, 20, 20));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30, 50));
		
		panel.add(GUI.JLabel("ID:"));
		id = GUI.JComboBox(EmployeesDatabase.getIDs(database));
		panel.add(id);
		
		panel.add(GUI.JLabel("Name:"));
		name = GUI.JTextField();
		panel.add(name);
		
		panel.add(GUI.JLabel("Email:"));
		email = GUI.JTextField();
		panel.add(email);
		
		panel.add(GUI.JLabel("Tel:"));
		tel = GUI.JTextField();
		panel.add(tel);
		
		panel.add(GUI.JLabel("Salary:"));
		salary = GUI.JTextField();
		panel.add(salary);
		
		panel.add(GUI.JLabel("Position:"));
		position = GUI.JTextField();
		panel.add(position);
		
		id.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					refreshData(database);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}
		});
		
		try {
			refreshData(database);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage());
		}
		
		JButton submit = GUI.JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e1) {
				Employee e = new Employee();
				e.setID(Integer.parseInt(id.getSelectedItem().toString()));
				e.setName(name.getText());
				e.setEmail(email.getText());
				e.setTel(tel.getText());
				e.setSalary(Double.parseDouble(salary.getText()));
				e.setPosition(position.getText());
				try {
					EmployeesDatabase.EditEmployee(e, database);
					JOptionPane.showMessageDialog(frame, "Employee updated successfully");
					frame.dispose();
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(frame, "Operation Failed");
					frame.dispose();
				}
			}
		});
		panel.add(submit);
		
		JButton delete = GUI.JButton("Delete");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					EmployeesDatabase.DeleteEmployee(
							id.getSelectedItem().toString(), database);
					JOptionPane.showMessageDialog(frame, "Employee deleted successfully");
					frame.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, "Operation Failed");
					frame.dispose();
				}
			}
		});
		panel.add(delete);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
	
	private void refreshData(Database database) throws SQLException {
		if (id.getSelectedItem()!=null) {
			Employee e = EmployeesDatabase.getEmployee(
					id.getSelectedItem().toString(), database);
			name.setText(e.getName());
			email.setText(e.getEmail());
			tel.setText(e.getTel());
			salary.setText(String.valueOf(e.getSalary()));
			position.setText(e.getPosition());
		}
	}

}
