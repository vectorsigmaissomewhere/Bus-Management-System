package ETransportationsystem;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI {
	
	public static Color background = Color.decode("#EBFFD8");
	
	public static JLabel JLabel(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(Color.decode("#012030"));
		label.setFont(new Font(null, Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
	
	public static JTextField JTextField() {
		JTextField textField = new JTextField();
		textField.setForeground(Color.decode("#012030"));
		textField.setFont(new Font(null, Font.BOLD, 20));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		return textField;
	}
	
	public static JButton JButton(String text) {
		JButton btn = new JButton(text);
		btn.setBackground(Color.decode("#45C4B0"));
		btn.setForeground(Color.white);
		btn.setFont(new Font(null, Font.BOLD, 22));
		return btn;
	}
	
	public static JComboBox<String> JComboBox(String[] data) {
		JComboBox<String> box = new JComboBox<>(data);
		box.setFont(new Font(null, Font.BOLD, 20));
		box.setBackground(Color.white);
		return box;
	}

}
