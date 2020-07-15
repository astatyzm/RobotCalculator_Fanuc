package Const;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Descriptions.Descriptions;

public interface FanucConstants {

	final JFrame frame = new JFrame();
	final JOptionPane fileSavedSuccesfully = new JOptionPane();
	final static JFileChooser fileInPath = new JFileChooser();
	final static JTextField fileLoadedPath = new JTextField();
	final static Charset charset = StandardCharsets.UTF_8;
	final static FileNameExtensionFilter filter = new FileNameExtensionFilter("Ls files", "LS");

	// Calendar
	final Calendar expireDate = Calendar.getInstance();
	final SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");
	final JCheckBox confiCheckBox = new JCheckBox(Descriptions.STRING_CONFIG_CHECKBOX.displayString());
	final JCheckBox speedCheckBox = new JCheckBox(Descriptions.STRING_SPEED_CHECKBOX.displayString());
	final JCheckBox zoneCheckBox = new JCheckBox(Descriptions.STRING_ZONE_CHECKBOX.displayString());


	public static final String loadedFileName = "";
	public static final Path path = Paths.get(loadedFileName);
}
